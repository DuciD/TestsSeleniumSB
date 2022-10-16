package pageObjects;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import managers.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class ProductListingPage {
    WebDriver driver;
    public WebDriverManager webDriverManager = new WebDriverManager();
    public static String linkTxt;
    //locators
    By title = By.cssSelector(".AdViewInfo_name__s_9p5");
    By priceSP = By.cssSelector(".AdViewInfo_price__PfKUL");
    By name = By.cssSelector(".EdPage_adInfoHolder__EVLJy > section > section > div > div > span:nth-child(2)");

    public ProductListingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void getAlphaNumericString(String instrument) {
        // chose a Character random from this String
        List<String> myList = Arrays.asList("Truba", "Saksofon", "Gitara", "Harmonika", "Klarinet");
        Random r = new Random();
        int randomitem = r.nextInt(myList.size());
        String randomElement = myList.get(randomitem);
    }

    public void clickOnRandomLink() {
        //    Click on random link in List
        List<WebElement> links = driver.findElements(By.cssSelector(".AdItem_name__BppRQ"));
        int count = links.size();
        System.out.println("Number of links are:" + count + "\n");
        Random r = new Random();
        int linkNo = r.nextInt(count);
        WebElement link = links.get(linkNo);
        linkTxt = link.getText();
        System.out.println(linkTxt);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", link);
    }

    public boolean verifyLink() {
        //Retriving title text
        String item = driver.findElement(By.cssSelector(".AdViewInfo_name__s_9p5")).getText();
        System.out.println("Title text has been verified with the Header text: " + item + "\n");
        //verifying correct page is opened
        if (linkTxt.equalsIgnoreCase(item)) {
            driver.navigate().back();
            return true;
        } else {
            return false;
        }
    }

    public void displayResultOfRandomChosenItem() {

        webDriverManager.waitForPageComplete();
        // Retriving Title, price and seller name
        WebElement Title = webDriverManager.getDriver().findElement(title);
        String itemTitle = Title.getText();
        System.out.println("Title: " + itemTitle);
        WebElement Price = webDriverManager.getDriver().findElement(priceSP);
        String itemPrice = Price.getText();
        System.out.println("Price: " + itemPrice);
        WebElement Name = webDriverManager.getDriver().findElement(name);
        String itemName = Name.getText();
        System.out.println("Name of the Seller: " + itemName);

    }
}
