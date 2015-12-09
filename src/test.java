//test.java

import TerminalIO.KeyboardReader;
import java.sql.*;

public class test
{

	// JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/Fantasy";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "YyJ=q7Hrv&l0";

   public static void main(String[] args)
	{
		KeyboardReader reader = new KeyboardReader();
		
		String first = "";
		String last = "";

		System.out.print("Enter First Name: ");
		first = reader.readLine();

		System.out.print("Enter Last Name: ");
		last = reader.readLine();

   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String query1;
      query1 = "SELECT playerID FROM Player where lastName='" + last + "' AND firstName='" + first + "';";

      ResultSet rs = stmt.executeQuery(query1);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("playerID");

         //Display values
         System.out.println("ID:  " + id);
         System.out.println("Name: " + first + " " + last);
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end FirstExample
