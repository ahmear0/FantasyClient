import TerminalIO.KeyboardReader;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class databaseAPI
{

	// JDBC driver name, url, credentials, query variables
   private static String JDBC_DRIVER, DB_URL, USER, PASS;
   private static ResultSet rs = null;
   private static Connection conn = null;
   private static Statement stmt = null;

   //construct an instance of the databaseAPI
   public databaseAPI()
   { 
      JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      DB_URL = "jdbc:mysql://localhost/Fantasy";
      USER = "root";
      PASS = "YyJ=q7Hrv&l0";

      startConnection();
   }

   public databaseAPI(String databaseUSERNAME, String databasePASSWORD)
   { 
      JDBC_DRIVER = "com.mysql.jdbc.Driver";  
      DB_URL = "jdbc:mysql://localhost/Fantasy";
      USER = databaseUSERNAME;
      PASS = databasePASSWORD;

      startConnection();
   }

   //the constructor method executes startConnection() at instantiation
   //users cannot manually start a JDBC connection
   private static void startConnection()
   {
      try 
      {
         //register the JDBC driver
         Class.forName(JDBC_DRIVER);

         conn = DriverManager.getConnection(DB_URL,USER,PASS);


      }  catch (ClassNotFoundException jdbcDriverException)
         {
            System.out.println("Error: JDBC Driver not found." + jdbcDriverException.getStackTrace());
         }

         catch (SQLException connException)
         {
            System.out.println("Error: Connection did not start." + connException.getStackTrace());
         }
   }

   //MUST end all connections after ALL queries have been made.
   //Start and End once--need to start/end repeatedly
   public void endConnection()
   {
      try {
               if (stmt!=null)
                  stmt.close();
            }  catch (SQLException stmtException)
                  {
                     System.out.println("Error: Statement did not close." + stmtException.getStackTrace());
                  }
            try {
               if (conn!=null)
                  conn.close();
            }catch (SQLException connException)
               {
                  System.out.println("Error: Connection did not close." + connException.getStackTrace());
               }
   }

   public int getPlayerID(String firstName, String lastName)
   {
      int playerID = 0;

      firstName = firstName.replaceAll("[()\\s-]+", "");
      firstName = firstName.replace("'","");
      lastName = lastName.replaceAll("[()\\s-]+", "");
      lastName = lastName.replace("'","");

      try 
      {
         stmt = conn.createStatement();
         String SQL_Query;

         SQL_Query = "SELECT playerID FROM Player where lastName='" + lastName + "' AND firstName='" + firstName + "';";

         rs = stmt.executeQuery(SQL_Query);

         while(rs.next())
         {
            playerID  = rs.getInt("playerID");
         }
         rs.close();
      
      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  

      return playerID;
   }

   //make a SQL INSERT for player scores, per week
   public int insert_playerScores(String insertQuery)
   {
      int insertStatus = -1;

      try 
      {
         stmt = conn.createStatement();
         insertStatus = stmt.executeUpdate(insertQuery);

      }  catch(SQLException sqlException)
         {
            System.out.println("Error.  Possible duplicate entry exists.");
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return insertStatus;
   }

   public double getPlayerPoints(int playerID, int week)
   {
      double points = 0.0;

      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT * FROM playerScores WHERE playerID=" + playerID + " AND week=" + week + ";";
      
         ResultSet rs = stmt.executeQuery(SQL_Query);

         int passTD = 0, passPick = 0, rushTD = 0, recTD = 0, retTD = 0, fLoss = 0;
         double passYds = 0.0, rushYds = 0.0, recYds = 0.0, retYds = 0.0;

         //parse scores from result set
         while(rs.next())
         {
            passTD = rs.getInt("passTD");
            passYds = rs.getDouble("passYds");
            passPick = rs.getInt("passPick");
            rushYds = rs.getDouble("rushYds");
            rushTD = rs.getInt("rushTD");
            recYds = rs.getDouble("recYds");
            recTD = rs.getInt("recTD");
            retYds = rs.getDouble("retYds");
            retTD = rs.getInt("retTD");
            fLoss = rs.getInt("fLoss");
         }

         points = 6.0*(passTD + rushTD + recTD + retTD) + 1.0*((passYds + retYds)/50.0) + 1.0*((recYds+rushYds)/10.0) - 2.0*(passPick + fLoss);

      rs.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  

      return points;
   }

   public String[][] getHeadToHead(String matchupQuery)
   {
      String[][] headToHead = null;
      try 
      {
         stmt = conn.createStatement();
         
         ResultSet result = stmt.executeQuery(matchupQuery);

         headToHead = new String[5][2];

         int i = 0;
         while(result.next())
         {
            headToHead[i][0] = result.getString("userName1");
            headToHead[i][1] = result.getString("userName2");
            i++;
         }

         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors*.
            exception.printStackTrace();
         }  
      return headToHead;
   }

   private ArrayList<Integer> getStartingLineupIDs(String userName)
   {
      ArrayList<Integer> startingLineupIDs = null;
      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT playerID FROM Rostered WHERE userName='" + userName + "' AND start=1;";
      
         ResultSet result = stmt.executeQuery(SQL_Query);

         startingLineupIDs = new ArrayList<Integer>();

         while(result.next())
         {
            startingLineupIDs.add((Integer)result.getInt("playerID"));
         }

         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return startingLineupIDs;
   }

   public Object[][] getCurrentRoster(String userName)
   {
      ResultSet roster = null;
      DefaultTableModel dtm = null;
      ArrayList<Object> rosterElements = new ArrayList<Object>(70);
      Object [][] data = null;
      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT playerID, start, position, firstName, lastName FROM Rostered NATURAL JOIN Player WHERE userName='" + userName + "' ORDER BY start DESC;";
      
         ResultSet result = stmt.executeQuery(SQL_Query);

         dtm = new DefaultTableModel();

         ResultSetMetaData meta = result.getMetaData();
         int numCols = meta.getColumnCount();

         while(result.next())
         {
            Object [] rowData = new Object[numCols];
            for (int i = 0; i < rowData.length; ++i)
            {
               rosterElements.add(result.getObject(i+1));
            }
         }
         int numRows = rosterElements.size()/numCols;

         data = new Object[numRows][numCols];
         int j=0;
         for (int x = 0; x<numRows; x++)
         {
            for (int y = 0; y<numCols; y++)
            {
               data[x][y] = rosterElements.get(j);
               j++;
            }
         }

         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return data;
   }    

   public Object[][] getPlayers()
   {
      ResultSet roster = null;
      DefaultTableModel dtm = null;
      ArrayList<Object> players = new ArrayList<Object>(400);
      Object [][] data = null;
      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT position, teamName, firstName, lastName FROM Player LIMIT 50;";
      
         ResultSet result = stmt.executeQuery(SQL_Query);

         dtm = new DefaultTableModel();

         ResultSetMetaData meta = result.getMetaData();
         int numCols = meta.getColumnCount();

         while(result.next())
         {
            Object [] rowData = new Object[numCols];
            for (int i = 0; i < rowData.length; ++i)
            {
               players.add(result.getObject(i+1));
            }
         }
         int numRows = players.size()/numCols;

         data = new Object[numRows][numCols];
         int j=0;
         for (int x = 0; x<numRows; x++)
         {
            for (int y = 0; y<numCols; y++)
            {
               data[x][y] = players.get(j);
               j++;
            }
         }

         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return data;
   }

   public double scoreStartingLineup(String userName, int week)
   {
      double teamScore = 0.0;
      ArrayList<Integer> lineupIDs = getStartingLineupIDs(userName);

      for (int i = 0; i<lineupIDs.size(); i++)
      {
         teamScore += getPlayerPoints(lineupIDs.get(i).intValue(), week);
      }
      return teamScore;
   }

   public int updateMatchup(String matchupString)
   {
      int insertStatus = -1;
      try 
      {
         stmt = conn.createStatement();
         insertStatus = stmt.executeUpdate(matchupString);

      }  catch(SQLException sqlException)
         {
            System.out.println("Error.  Possible duplicate entry exists.");
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return insertStatus;

   }

   public ArrayList<String> getFantasyTeams()
   {
      ArrayList<String> fantasyTeams = null;
      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT userName from FantasyTeam;";
      
         ResultSet result = stmt.executeQuery(SQL_Query);

         fantasyTeams = new ArrayList<String>(10);

         while(result.next())
         {
            fantasyTeams.add((String)result.getString("userName"));
         }
         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return fantasyTeams; 
   }

   public Object[][] getStandings()
   {
      ResultSet resultSet = null;
      DefaultTableModel dtm = null;

      ArrayList<Object> standings = new ArrayList<Object>(30);
      Object [][] data = null;
      try 
      {
         stmt = conn.createStatement();
         String SQL_Query = "SELECT FantasyTeam, Win, Loss from lossTable Natural Join winTable ORDER BY (Win) DESC;";
      
         ResultSet result = stmt.executeQuery(SQL_Query);

         dtm = new DefaultTableModel();

         ResultSetMetaData meta = result.getMetaData();
         int numCols = meta.getColumnCount();

         while(result.next())
         {
            Object [] rowData = new Object[numCols];
            for (int i = 0; i < rowData.length; ++i)
            {
               standings.add(result.getObject(i+1));
            }
         }
         int numRows = standings.size()/numCols;

         data = new Object[numRows][numCols];
         int j=0;
         for (int x = 0; x<numRows; x++)
         {
            for (int y = 0; y<numCols; y++)
            {
               data[x][y] = standings.get(j);
               j++;
            }
         }

         result.close();

      }  catch(SQLException sqlException)
         {
            sqlException.printStackTrace();
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return data;
   }

   private int createStandingsTables()
   {
      int insertStatus = -1;
      String insertWinTable = "CREATE VIEW winTable AS (SELECT table1.winner as FantasyTeam, count(table1.winner) as Win FROM ( SELECT userName1 as winner, score1 as winScore FROM Matchup WHERE score1>score2 UNION (SELECT userName2 as winner, score2 as winScore FROM Matchup WHERE score2>score1)) as table1 GROUP BY (table1.winner));";

      String insertLossTable = "CREATE VIEW lossTable AS (SELECT table2.loser as FantasyTeam, count(table2.loser) as Loss FROM (SELECT userName1 as loser, score1 as lossScore FROM Matchup WHERE score1<score2 UNION (SELECT userName2 as loser, score2 as lossScore FROM Matchup WHERE score2<score1)) as table2 GROUP BY (table2.loser));";
      try 
      {
         stmt = conn.createStatement();
         insertStatus = stmt.executeUpdate(insertWinTable);
         insertStatus = stmt.executeUpdate(insertLossTable);

      }  catch(SQLException sqlException)
         {
            System.out.println("Error.  Possible duplicate entry exists.");
         }  
         catch(Exception exception)
         {
            //general errors
            exception.printStackTrace();
         }  
      return insertStatus;
   }

   public static void main(String[] args)
	{
		KeyboardReader reader = new KeyboardReader();

		databaseAPI database = new databaseAPI();

      database.createStandingsTables();
  //     ArrayList<Integer> lineup = database.getStartingLineupIDs("Asheq");
		// String first = "";
		// String last = "";
  //     System.out.print("Enter First Name: ");
  //     first = reader.readLine();
  //     System.out.print("Enter Last Name: ");
  //     last = reader.readLine();

  //     int playerID = database.getPlayerID(first, last);

  //     System.out.println("PlayerID is: " + playerID);
  //     int week = 1;
  //     System.out.println("He scored " + database.getPlayerPoints(playerID, week) + " points in week " + week);

  //     System.out.println(lineup.get(0) + ", " + lineup.get(6));

  //     double week12score = database.scoreStartingLineup("Asheq",12);

  //     System.out.println("Asheq scored " + week12score + " points in week 12");

      database.endConnection();
   }
}
