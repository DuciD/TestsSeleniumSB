package pageObjects;

import managers.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HomePage {
    WebDriver driver;
    public String randomElement;
    By searchBox = By.id("keywords");
    By linkTrb = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[2]//div//a/div");
    By linkSax = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[4]//div//a/div");
    By linkGtr = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[6]//div//a/div");
    By linkHrk = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[8]//div//a/div");
    By linkKlr = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[3]//div//a/div");
    By sItem = By.cssSelector("div[id='kp-portal'] button:nth-child(1)");
    By rItem = By.cssSelector("div[id='kp-portal'] button:nth-child(5)");
    By sort = By.cssSelector("div[class='PopUp_root__H7y4T'] button[type='button']");
    By PriceBtn = By.className("SearchTag_priceTag__fZkiH");
    By PriceOnly = By.className("Checkbox_label___QfTq");
    By ApplyFilter = By.cssSelector("button[class='Button_base__Pz8U1 ButtonPrimaryBlue_primaryBlue__Uz5k1']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void navigateTo_HomePage() {
        driver.get("https://kupujemprodajem.com/");
    }

    public void perform_Search(String search) {
        WebElement box = WebDriverManager.findElement(searchBox, 5);
        box.sendKeys(Keys.CONTROL + "a");
        box.sendKeys(Keys.DELETE); // As .click() doesn't work
        box.sendKeys(search);
        box.sendKeys(Keys.ENTER);
    }

    public void choseRandomInstrument() {
        List<String> myList = Arrays.asList("Truba", "Saksofon", "Gitara", "Harmonika", "Klarinet");
        Random r = new Random();
        int randomitem = r.nextInt(myList.size());
        //get random instrument
        randomElement = myList.get(randomitem);
        System.out.println("Random Instrument: " + randomElement);

        //searching for random instrument
        perform_Search(randomElement);
        System.out.println("Searching for a Random Instrument: " + randomElement);
    }

    public void clickRandomInstrument() {
        switch (randomElement.toUpperCase()) {
            case "TRUBA":
                driver.findElement(linkTrb).click();
                break;
            case "SAKSOFON":
                driver.findElement(linkSax).click();
                break;
            case "GITARA":
                JavascriptExecutor exe = (JavascriptExecutor) driver;
                WebElement elem = driver.findElement(linkGtr);
                exe.executeScript("arguments[0].click();", elem);
                break;
            case "HARMONIKA":
                JavascriptExecutor exec = (JavascriptExecutor) driver;
                WebElement harm = driver.findElement(linkHrk);
                exec.executeScript("arguments[0].click();", harm);
                break;
            case "KLARINET":
                driver.findElement(linkKlr).click();
                break;
        }
    }

    public void sortCheap() {
        WebElement sort_button = WebDriverManager.findElement(sort, 5);
        sort_button.click();
        WebElement sortList = WebDriverManager.findElement(sItem, 3);
        sortList.click();
    }

    public void sortRelevant() {
        WebElement sort_button = WebDriverManager.findElement(sort, 5);
        sort_button.click();
        WebElement revertList = WebDriverManager.findElement(rItem, 3);
        revertList.click();
    }
    public void showItemsWithPriceOnly(){
        //remove wthout price
        WebElement priceBtn = WebDriverManager.findElement(PriceBtn,3);
        priceBtn.click();
        WebElement priceOnly = WebDriverManager.findElement(PriceOnly,3);
        priceOnly.click();
        WebElement applyFilter = WebDriverManager.findElement(ApplyFilter,3);
        applyFilter.click();

    }
}