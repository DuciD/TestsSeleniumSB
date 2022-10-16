package managers;

import org.openqa.selenium.WebDriver;

import pageObjects.HomePage;

import pageObjects.ProductListingPage;



public class PageObjectManager {


    private WebDriver driver;

    private ProductListingPage productListingPage;

    private HomePage homePage;






    public PageObjectManager(WebDriver driver) {

        this.driver = driver;

    }



    public HomePage getHomePage(){

        return (homePage == null) ? homePage = new HomePage(driver) : homePage;

    }



    public ProductListingPage getProductListingPage() {

        return (productListingPage == null) ? productListingPage = new ProductListingPage(driver) : productListingPage;

    }




}
