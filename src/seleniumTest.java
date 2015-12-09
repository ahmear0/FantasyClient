import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.*;

public class seleniumTest {
    
    public static void main(String[] args)
    {
    	//player attributes
    	String lastName, firstName;

    	//playerScore attributes
    	int passTD, passPick, rushTD, recTD, retTD, fLoss;
    	double passYds, rushYds, recYds, retYds;

    	int playerID = 33190;
    	int week = 13;

	WebDriver driver = new ChromeDriver();

		try{
			String statisticsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=RB";
			String statisticRowPath = "//*[@id='yom-league-stats']/table[5]/tbody/tr[3]";
            driver.get(statisticsURL);


            WebElement name1 = driver.findElement(By.xpath(statisticRowPath));

            String currentPlayer = name1.getText();
            String[] stats = currentPlayer.split("\\s+");

            lastName = stats[2];
            firstName = stats[1];
            passYds = 0;
            passTD = 0;
            passPick = 0;
            rushYds = Double.parseDouble(stats[6]);
            rushTD = Integer.parseInt(stats[9]);
            recYds = Double.parseDouble(stats[12]);
            recTD = Integer.parseInt(stats[15]);
            retYds = 0;
            retTD = 0;
            fLoss = Integer.parseInt(stats[17]);

            int i = 0;
            for (String stat : stats)
            {
            	System.out.println("Stat " + i + ": " + stat);
            	i++;
            }

            String runningBackEntry = "INSERT INTO playerScores VALUES(" + week + "," + passYds + "," + 
				passTD + "," + 
				passPick + "," +  
				rushYds + "," + 
				rushTD + "," +  
				recYds + "," + 
				recTD + "," +  
				retYds + "," +  
				retTD + "," +  
				fLoss + "," +  playerID + ");";

			System.out.println(runningBackEntry);
			
            System.out.println(stats[1] + " " + stats[2] + " caught for " + recYds + " yards.");
        }
        catch (Exception e)
        {
        	System.out.println("Exception is: " + e);
        }
        finally {
            driver.close();
        }

    }


}
