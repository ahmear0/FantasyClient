//test.java

import TerminalIO.KeyboardReader;
import java.sql.*;

public class databaseAPI
{

	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/Fantasy";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "YyJ=q7Hrv&l0";


   //makes mysql query using connectorJ driver
   //gets playerID using the player's name
   //so that we can insert stats using playerID key
   public static int getPlayerID(String firstName, String lastName)
   {
      int playerID = 0;

      firstName = firstName.replaceAll("[()\\s-]+", "");
      lastName = lastName.replaceAll("[()\\s-]+", "");

      Connection conn = null;
      Statement stmt = null;
      try {

      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String query1;
      query1 = "SELECT playerID FROM Player where lastName='" + lastName + "' AND firstName='" + firstName + "';";

      ResultSet rs = stmt.executeQuery(query1);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         playerID  = rs.getInt("playerID");
         }

      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
      }  catch(SQLException se)
         {
            //Handle errors for JDBC
            se.printStackTrace();
         }  
         catch(Exception e)
         {
            e.printStackTrace();
         }  
         finally
         {
            try {
               if(stmt!=null)
                  stmt.close();
            }  catch(SQLException se2)
                  {
                     System.out.println("Exception thrown: " + se2.getStackTrace());
                  }
            try {
               if(conn!=null)
                  conn.close();
            }catch(SQLException se)
               {
                  se.printStackTrace();
               }
         }

      return playerID;
   }

   public static void main(String[] args)
	{
		KeyboardReader reader = new KeyboardReader();
		
		String first = "";
		String last = "";
      System.out.print("Enter First Name: ");
      first = reader.readLine();
      System.out.print("Enter Last Name: ");
      last = reader.readLine();

      int playerID = getPlayerID(first, last);

      System.out.println("PlayerID is: " + playerID);
   }
}
