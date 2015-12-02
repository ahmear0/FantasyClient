import java.io.*;
import java.awt.event.*;
import javax.swing.*;

public class patch {
    public static void main(String [] args) {

        // The name of the file to open.

	String fileName = "week1.csv";
	String mod_fileName = "mod_" + fileName;
	
        // This will reference one line at a time
        String line = null;
	String table = "week1pass";
	String header = "INSERT INTO " + table + " VALUES(";
	String footer = ");";
	String newLine = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

	    PrintWriter writer = new PrintWriter(mod_fileName, "UTF-8");

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);


            while((line = bufferedReader.readLine()) != null) {
		//modify the line
		newLine = "" + header + line + footer;
		writer.println(newLine);
		
		//print the line
                System.out.println(newLine);
            }   

            // Always close files.
	    writer.close();
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
    }
}


