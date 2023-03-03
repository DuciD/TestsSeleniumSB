package Steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import managers.PageObjectManager;
import managers.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.HomePage;
import pageObjects.ProductListingPage;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Hooks extends WebDriverManager {
   public WebDriverManager base;
    WebDriver driver;
    HomePage homePage;
    ProductListingPage productListingPage;
    PageObjectManager pageObjectManager;
     WebDriverManager webDriverManager;
    public Hooks(WebDriverManager base) {
        this.base = base;

    }

    @Before
    public void BeforeSteps() throws IOException {
        /*Starting a webdriver, Setting up DB connections, Setting up test data, Setting up browser cookies, Navigating to certain page, or anything before the test*/

            Logger logger = Logger.getLogger(Hooks.class.getName());

            FileHandler fileHandler = new FileHandler("app.log", true);
            logger.addHandler(fileHandler);

            if (logger.isLoggable(Level.INFO)) {
                logger.info("Information message");
            }

            if (logger.isLoggable(Level.WARNING)) {
                logger.warning("Warning message");
            }




        // Checking website URL without using selenium(backend)
        URL url = new URL("http://www.kupujemprodajem.com/");
        HttpURLConnection.setFollowRedirects(false);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        int responseCode = huc.getResponseCode();
//added exception for code 307 Temporary redirection as it is active at the moment. Library doesn't support a code 307  that is active at the moment, then alternative method is used
// If it's 307 or 200 then pass, otherwise fails
        if (responseCode == 307) {
            HttpURLConnection.setFollowRedirects(true);
            System.out.println("Website is redirected!" + "Code: " + responseCode);
        } else  {
            System.out.println("HTTP code: " + responseCode);
            Assert.assertEquals(HttpURLConnection.HTTP_OK, responseCode, "Wrong HTTP status code found: ");
        }

    }
 public void initialize(){
     webDriverManager = new WebDriverManager();
     driver = webDriverManager.getDriver();
     pageObjectManager = new PageObjectManager(driver);
     productListingPage = pageObjectManager.getProductListingPage();
     homePage = pageObjectManager.getHomePage();
 }
    @After

    public void tearDown(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            TakesScreenshot scrShot =((TakesScreenshot)base.getDriver());
            File source=scrShot.getScreenshotAs(OutputType.FILE);
            File destination =new File("image/" + java.time.LocalDate.now() + ".jpg");
            FileUtils.copyFile(source, destination);
        }
        base.getDriver().quit();
    }
}