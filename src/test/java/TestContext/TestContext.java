package TestContext;


import browserSetup.SetBrowser;
import configuration.Configuration;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pageObjectFactory.PageObjects;

import java.util.*;

public class TestContext {

    private WebDriver driver;
    private PageObjects page;
    public  SoftAssert assertion;
    private SetBrowser browser;
    private List<String> products;
    private LinkedHashMap<Integer,Integer> productsAddedByUserInCart;
    private List<LinkedHashMap<String,String>> productsInsideCart;
//--------------------------------------------------------------------------------------------------

    public TestContext(){
        System.out.println("TestContext:::::::::::::::::");
        initializeBrowser();
        initializePageObjectFactory();
        assertion=new SoftAssert();
    }

    private void initializeBrowser(){
        browser=new SetBrowser();
        driver=browser.launchBrowser(initializeConfiguration().getString("browser"));
        if(driver==null){
            System.out.println("Driver is null!!");
        }
    }

    private void initializePageObjectFactory() {
        page=new PageObjects(driver);
    }

    public void openURL(String url){
        if(driver!=null){
            driver.get(url);
        }
    }
    public WebDriver getDriver(){return driver;}
    public PageObjects getPageObject(){
        return page;
    }
    public void assertAll(){
        assertion.assertAll();
    }
    private JSONObject initializeConfiguration(){
        Configuration config=new Configuration();
        return config.getJsonConfigData();
    }
    public void setProducts(List<String> products){
        this.products=products;
    }
    public List<String> getProducts(){
        return products;
    }

   public void setProductsAddedByUserInCart(LinkedHashMap<Integer, Integer> productsWithQty){
        productsAddedByUserInCart=productsWithQty;
   }

   public void setProductInsideCart(List<LinkedHashMap<String,String>> products){
       productsInsideCart=products;

   }
   public List<LinkedHashMap<String,String>>  getProductInsideCart(){
        return productsInsideCart;
   }

    public LinkedHashMap<Integer, Integer> getProductsAddedByUserInCart() {
        return  productsAddedByUserInCart;
    }

    public String getUrl(){
        return initializeConfiguration().getString("url");
    }
    public void closeBrowser(){
        browser.close();
    }
}
