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

    	int playerID = -1;
        String position = "RB";
        String statEntry = "";
    	int week = 13;
        int offset = 0;
        int numPlayersListed = 5;

	WebDriver driver = new ChromeDriver();

		try{
			String statisticsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=RB";

            String statsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=" + position + "&conference=NFL&year=season_2015&timeframe=Week" + week;
            driver.get(statsURL);

            
            for (int j = 3; j<=numPlayersListed; j++) {
                String statisticRowPath = "//*[@id='yom-league-stats']/table[5]/tbody/tr[" + j + "]";

                WebElement name1 = driver.findElement(By.xpath(statisticRowPath));

                String currentPlayer = name1.getText();
                String[] stats = currentPlayer.split("\\s+");

                lastName = stats[2];
                firstName = stats[1];
                if (stats[3].contains("Jr.") || stats[3].contains("III"))
                {
                    offset = 1;
                }

                playerID = databaseAPI.getPlayerID(firstName, lastName);

                if (playerID > 0) 
                {
                    passYds = 0;
                    passTD = 0;
                    passPick = 0;
                    rushYds = Double.parseDouble(stats[6 + offset]);
                    rushTD = Integer.parseInt(stats[9 + offset]);
                    recYds = Double.parseDouble(stats[12 + offset]);
                    recTD = Integer.parseInt(stats[15 + offset]);
                    retYds = 0;
                    retTD = 0;
                    fLoss = Integer.parseInt(stats[17 + offset]);

                    statEntry = "INSERT INTO playerScores VALUES(" + week + "," + passYds + "," + 
        				passTD + "," + 
        				passPick + "," +  
        				rushYds + "," + 
        				rushTD + "," +  
        				recYds + "," + 
        				recTD + "," +  
        				retYds + "," +  
        				retTD + "," +  
        				fLoss + "," +  playerID + ");";

        			System.out.println(statEntry);

                    databaseAPI.insert_playerScores(statEntry);

                    //System.out.println(stats[1] + " " + stats[2] + " caught for " + recYds + " yards.");
                }
                else
                {
                    System.out.println("/* " + firstName + " " + lastName + " gives invalid ID. */");
                }

            }
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
