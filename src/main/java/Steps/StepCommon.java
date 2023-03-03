package Steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


import org.openqa.selenium.*;

import managers.PageObjectManager;
import pageObjects.HomePage;
import pageObjects.ProductListingPage;
import managers.WebDriverManager;

import java.util.*;


public class StepCommon{

    public static String linkTxt;
    public int numOfRows;
    public int totalPrice;
    public int approximatevalue;
    public WebElement secondPage;

    WebDriver driver;
    HomePage homePage;
    ProductListingPage productListingPage;
    PageObjectManager pageObjectManager;
    WebDriverManager webDriverManager;

    //locators
    By listOfItems = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[position()>1]");
    By pricelist = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']/div/section[position()>1]//div/div[2]/div/div");
    //By listOfItems = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']//div[position()>1]//section[@class='AdItem_adOuterHolder__Z29Nf']");
    By getListOfItems = By.xpath("//div[@class='Grid_col-lg-10__tIdze Grid_col-xs__6oZvU Grid_col-sm__hxOHE Grid_col-md__1bRJZ']//section[@id='131058872']");

    By sList = By.cssSelector(".PopUp_content__juFV4");

    By secondCheapestItem = By.id("117327955");
    By linkSecondpage = By.xpath("//section[@class='Box_box__03Q3_ Pagination_paginationHolder__MeRSk']//ul/li[2]");



    @Given("a user is on Kupujem Prodajem website")
    public void a_user_is_on_kupujem_prodajem_website() throws Exception {
        webDriverManager = new WebDriverManager();
        driver = webDriverManager.getDriver();
        pageObjectManager = new PageObjectManager(driver);
        productListingPage = pageObjectManager.getProductListingPage();
        homePage = pageObjectManager.getHomePage();
        homePage.navigateTo_HomePage();

        System.out.println("User is on Kupujem Prodajem Website");
    }

    @When("a user search for drombulje")
    public void a_user_search_for_drombulje() throws Exception {
        homePage.perform_Search("drombulje");
        homePage.showItemsWithPriceOnly();
    }

    @Then("^the list of selected items are displayed$")
    public void the_list_of_selected_are_displayed() {
        // Searching if keyword appears in Search results
        List<WebElement> items = webDriverManager.getDriver().findElements(getListOfItems);
        for (WebElement item : items) {
            String s = item.getText();
            if (s.contains("Drombulje")) {
                System.out.println("Found and verified keyword 'Drombulje' in search results presented!");
            }
        }
    }

    @And("^the approximate value for an item has calculated$")
    public void the_approximate_value_for_item_has_calculated() {
        //Find out a total number of items displayed
        numOfRows = webDriverManager.getDriver().findElements(listOfItems).size();
        System.out.println("Number of Rows displayed : " + numOfRows);

        //Find out each price per item
        List<WebElement> rows = webDriverManager.getDriver().findElements(pricelist);
        for (WebElement row : rows) {
            String prices = row.getText().replace(".", "").substring(0, 4).replace(" ", "");
            System.out.println("Row text:" + row.getText().replace(".", "").substring(0, 4));

            // Total Sum
            totalPrice = totalPrice + Integer.parseInt(prices);

        }
        // Total sum and approximate value
        System.out.println("Total sum is: " + totalPrice + "\n");
        approximatevalue = totalPrice / numOfRows;
        System.out.println("Approximate value for 'drombulje' is:" + approximatevalue + "\n");
    }

    @When("^click on random link of any drombulje item$")
    public void user_click_on_random_link_of_any_item() {
        productListingPage.clickOnRandomLink();
    }

    @Then("^correct item page is displayed$")
    public void correct_item_page_is_displayed() {
        productListingPage.verifyLink();

    }

    @Then("^second cheapest item is known$")
    public void second_cheap_item_is_known() throws InterruptedException {
        Thread.sleep(3000);
        // Checking if number of items even/odd and displaying results
        if (numOfRows % 2 == 0) {
            homePage.sortCheap();
            // Retrieving second cheapest item
            WebElement secondItem = WebDriverManager.findElement(secondCheapestItem, 3);
            System.out.println("Second cheapest item: " + secondItem.getText());
        } else {
            System.out.println(numOfRows + " is an odd number.");
        }
    }

    @When("^user choose random link of any of 5 instruments$")
    public void user_choose_on_random_link_of_any_instrument() {
        homePage.sortRelevant();
        homePage.choseRandomInstrument();

    }

    @And("^Check if it is more than one page displayed$")
    public void check_if_it_is_more_tan_one_page_displayed() {
        try {
            WebDriverManager.focusElement(linkSecondpage, 5);
            secondPage = WebDriverManager.findElement(linkSecondpage, 5);
            secondPage.click();
            System.out.println("There is more than one page. second page is opened");
        } catch (Exception e) {
            System.out.println("There is one page with the results!");
        }
    }

    @When("^user click on random instrument link$")
    public void user_click_on_random_instrument_link() {
        webDriverManager.waitForPageComplete();
        homePage.clickRandomInstrument();

    }

    @Then("more information about random instrument has found")
    public void more_information_about_random_instrument_has_found() {
        productListingPage.displayResultOfRandomChosenItem();

    }
}









