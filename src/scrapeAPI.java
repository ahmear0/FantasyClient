import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import java.util.HashMap;
import java.util.Map;

public class scrapeAPI {
    
    public static void main(String[] args)
    {
        for (int k = 1; k<=13; k++)
        {
            System.out.println("Scraping stats from week " + k + ".");
            scrape(k);
        }
    }

    public static void scrape(int week)
    {
    	//player attributes
    	String lastName, firstName;

    	//playerScore attributes
    	int passTD, passPick, rushTD, recTD, retTD, fLoss;
    	double passYds, rushYds, recYds, retYds;

    	int playerID = -1;
        String position = "RB";
        String statEntry = "";
        int offset = 0;
        int rowStart = 3;
        int rowEnd = 100;

        Map<String, Object> contentSettings = new HashMap<String, Object>();
        contentSettings.put("images", 2);

        Map<String, Object> preferences = new HashMap<String, Object>();
        preferences.put("profile.default_content_settings", contentSettings);

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("chrome.prefs", preferences);

	    WebDriver driver = new ChromeDriver(caps);

		try{
			String statisticsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=RB";

            String statsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=" + position + "&conference=NFL&year=season_2015&timeframe=Week" + week;
            driver.get(statsURL);

            
            for (int j = rowStart; j<=rowEnd; j++) {
                String statisticRowPath = "//*[@id='yom-league-stats']/table[5]/tbody/tr[" + j + "]";

                WebElement name1 = driver.findElement(By.xpath(statisticRowPath));

                String currentPlayer = name1.getText();
                String[] stats = currentPlayer.split("\\s+");

                lastName = stats[2];
                firstName = stats[1];
                offset = 0;
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
            e.printStackTrace();
        }
        finally {
            driver.close();
        }

    }


}
