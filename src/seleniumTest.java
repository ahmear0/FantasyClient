import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class seleniumTest {
    
    public static void main(String[] args)
    {

	// Optional, if not specified, WebDriver will search your path for chromedriver.
	// System.setProperty("webdriver.chrome.driver", "chromedriver");

	WebDriver driver = new ChromeDriver();
	driver.get("http://sports.yahoo.com/nfl/stats/byposition?pos=RB");

	try {
	    //Thread.sleep(5000); // Let the user actually see something!

	    String Namepath = "[@id='my-players-table']/div[1]/div/table/tbody/tr[1]/td[1]/a";
	    String davidJohnson = "//*[@id='yui_3_18_1_4_1449631150551_218']/a";

		System.out.println("Statistics pulled using selenium are: ");

	    WebElement player1 = driver.findElement(By.id("yui_3_18_1_4_1449632328143_145"));


	    System.out.println(player1.getText());

	    Thread.sleep(6000);  // Let the user actually see something!

		} 
		catch (Exception e)
		{
			_showErrorMessage(e);
		}
	    finally
	    {
	        driver.quit();
	    }
    }

    private void _showErrorMessage(Exception e) {
    	LOGGER.error(e.getClass() + ": " +  e.getMessage(), e);
	}
}
