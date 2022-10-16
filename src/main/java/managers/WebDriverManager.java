package managers;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import enums.DriverType;
import enums.EnvironmentType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class WebDriverManager {
    private static WebDriver driver;
    private static DriverType driverType;
    private static EnvironmentType environmentType;
    private static final String CHROME_DRIVER_PROPERTY = "webdriver.chrome.driver";

    public WebDriverManager() {
        driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
        environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
    }

    public WebDriver getDriver() {
        if(driver == null) driver = createDriver();
        return driver;
    }

    private WebDriver createDriver() {
        switch (environmentType) {
            case LOCAL : driver = createLocalDriver();
                break;
            case REMOTE : driver = createRemoteDriver();
                break;
        }
        return driver;
    }

    private WebDriver createRemoteDriver() {
        throw new RuntimeException("RemoteWebDriver is not yet implemented");
    }

    private WebDriver createLocalDriver() {
        switch (driverType) {
            case FIREFOX : driver = new FirefoxDriver();
                break;
            case CHROME :
                System.setProperty(CHROME_DRIVER_PROPERTY, FileReaderManager.getInstance().getConfigReader().getDriverPath());
                driver = new ChromeDriver();
                break;
            case INTERNETEXPLORER : driver = new InternetExplorerDriver();
                break;
        }

        if(FileReaderManager.getInstance().getConfigReader().getBrowserWindowSize()) driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
        return driver;
    }
    public static void focusElement(By locator, int maxWaitTime) throws Exception {

        try {
            WebElement container = findElementDirect(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -200);", container);
        } catch (NoSuchElementException e) {
            throw new Exception(new NoSuchElementException("Cannot focus element. Element is not found\n"));
        } catch (StaleElementReferenceException se) {
            WebElement container = findElementDirect(locator);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0, -200);", container);
        }


    }
    public static WebElement findElementDirect(By locator) {

        if (driver == null) {
            throw new NullPointerException("ERROR: driver is null.");
        }
        return driver.findElement(locator);
    }
    public static WebElement findElement(By locator, int maxWaitTime) {

        WebElement element = null;
        if (driver == null) {
            throw new NullPointerException("ERROR: driver is null.");
        }
        try{
            if (isElementClickableWithinWait(locator, maxWaitTime)) {
                element = driver.findElement(locator);
            }
        }
        catch(StaleElementReferenceException se){
            element = driver.findElement(locator);
        }
        return element;
    }
    public static boolean isElementClickableWithinWait(By locator, int maxWaitTime) {
        try {
            waitFor(ExpectedConditions.elementToBeClickable(locator), maxWaitTime);
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }
    private static void waitFor(ExpectedCondition<WebElement> condition, int maxWaitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Long.valueOf(maxWaitTime)));
        wait.until(condition);
    }
    public void waitForPageComplete() {

        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        System.out.println("DEBUG: Waiting for page to complete.. \n");
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(20));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}