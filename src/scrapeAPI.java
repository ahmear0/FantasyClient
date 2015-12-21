import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.Select;
import java.util.HashMap;
import java.util.Map;

public class scrapeAPI {

    private static databaseAPI database;
    
    public static void scrapeTheWeek(int week)
    {
    	database = new databaseAPI();
    	
    	scrape(week, "QB", 100);
        scrape(week, "RB", 100);
        scrape(week, "WR", 150);
        scrape(week, "TE", 100);
        
        database.endConnection();
    }

    private static int scrape(int week, String position, int rows)
    {
        //player attributes
        String lastName, firstName;

        //playerScore attributes
        int passTD, passPick, rushTD, recTD, retTD, fLoss;
        double passYds, rushYds, recYds, retYds;

    	int playerID = -1;
        String statEntry = "";
        int offset = 0;
        int rowStart = 3;
        int rowEnd = rows + 3;

        Map<String, Object> contentSettings = new HashMap<String, Object>();
        contentSettings.put("images", 2);

        Map<String, Object> preferences = new HashMap<String, Object>();
        preferences.put("profile.default_content_settings", contentSettings);

        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setCapability("chrome.prefs", preferences);

	    WebDriver driver = new ChromeDriver(caps);

		try 
        {
			String statisticsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=RB";

            String statsURL = "http://sports.yahoo.com/nfl/stats/byposition?pos=" + position + "&conference=NFL&year=season_2015&timeframe=Week" + week;
            driver.get(statsURL);

            
            for (int j = rowStart; j<=rowEnd; j++) {
                String statisticRowPath = "//*[@id='yom-league-stats']/table[5]/tbody/tr[" + j + "]";

                try 
                {
                    WebElement statElement = driver.findElement(By.xpath(statisticRowPath));


                    String stringOfStats = statElement.getText();
                    stringOfStats = stringOfStats.replace("N/A","0");

                    String[] stats = stringOfStats.split("\\s+");

                    lastName = stats[2];
                    firstName = stats[1];
                    offset = 0;
                    if (stats[3].contains("Jr.") || stats[3].contains("III"))
                    {
                        offset = 1;
                    }

                    playerID = database.getPlayerID(firstName, lastName);

                    if (playerID > 0) 
                    {
                        if (position.equals("RB"))
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
                        }
                        else if (position.equals("QB"))
                        {
                            passYds = Double.parseDouble(stats[8 + offset]);
                            passTD = Integer.parseInt(stats[12 + offset]);
                            passPick = Integer.parseInt(stats[11 + offset]);
                            rushYds = Double.parseDouble(stats[14 + offset]);
                            rushTD = Integer.parseInt(stats[17 + offset]);
                            recYds = 0;
                            recTD = 0;
                            retYds = 0;
                            retTD = 0;
                            fLoss = Integer.parseInt(stats[21 + offset]);
                        }
                        else if (position.equals("WR"))
                        {
                            passYds = 0;
                            passTD = 0;
                            passPick = 0;
                            rushYds = 0;
                            rushTD = 0;
                            recYds = Double.parseDouble(stats[7 + offset]);
                            recTD = Integer.parseInt(stats[10 + offset]);
                            retYds = Double.parseDouble(stats[12 + offset]) + Double.parseDouble(stats[17 + offset]);
                            retTD = Integer.parseInt(stats[15 + offset]) + Integer.parseInt(stats[20 + offset]);
                            fLoss = Integer.parseInt(stats[22 + offset]);
                        }
                        else if (position.equals("TE"))
                        {
                            passYds = 0;
                            passTD = 0;
                            passPick = 0;
                            rushYds = Double.parseDouble(stats[12 + offset]);
                            rushTD = Integer.parseInt(stats[15 + offset]);
                            recYds = Double.parseDouble(stats[7 + offset]);
                            recTD = Integer.parseInt(stats[10 + offset]);
                            retYds = 0;
                            retTD = 0;
                            fLoss = Integer.parseInt(stats[17 + offset]);
                        }
                        else
                        {
                            passYds = 0;
                            passTD = 0;
                            passPick = 0;
                            rushYds = 0;
                            rushTD = 0;
                            recYds = 0;
                            recTD = 0;
                            retYds = 0;
                            retTD = 0;
                            fLoss = 0;
                        }

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

                        System.out.format("%40s%40s", "/* "+firstName + " " + lastName+" */    ", statEntry);
                        System.out.println();

                        database.insert_playerScores(statEntry);

                    }
                    else
                    {
                        System.out.println("/* " + firstName + " " + lastName + " gives invalid ID. */");
                    }

                } catch (NoSuchElementException exception)
                    {
                        System.out.println("No more elements on this page.");
                        break;
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
		
		return 1;
    }

}
