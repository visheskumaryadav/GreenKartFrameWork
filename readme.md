# Automation-Framework-practice
This repo contains automation framework which I have been trying to make using a dummy [website](https://rahulshettyacademy.com/seleniumPractise/#/).

And I have created some [testcase](https://docs.google.com/spreadsheets/d/1dMsE50kiIePRwSjyk7W1baRD4LiG42CL7dozgxCFlMU/edit?usp=sharing) for this website.

## Tools/Tech Used:

- Programming language: Java
- Library: Selenium WebDriver
- Build Tool: Maven
- BDD: Cucumber

In this project i am using following things:
- Java language
- Selenium WebDriver
- Page Object Model
- Created Utility class
- Page Object Factory class
- Cucumber (to achieve BDD)
- Created Feature files using Gherkin
- Used "Test Data" in the form of JSON
- Maven

## About the Project:
So the application is dummy ecommerce platform where we have listing of  veggies with quantity,price,image. And we can add these into cart and able to place order after verifying content in the Bill.
Broadly we have 3 pages
- LandingPage containing listing and cart
- BillingPage
- Terms and condition page

## Folder Structure:

````
GreenKartV3
├───.idea
├───drivers
│   └───chrome-win64
│       ├───locales
│       └───MEIPreload
├───src
│   ├───main
│   │   └───java
│   │       ├───browserSetup
│   │       ├───configuration
│   │       ├───pageObjectFactory
│   │       ├───PageObjects
│   │       └───Util
│   └───test
│       └───java
│           ├───FeatureFiles
│           ├───Hooks
│           ├───StepDefinitionFiles
│           ├───TestCaseLibrary
│           ├───TestContext
│           ├───TestData
│           └───TestRunner
└───target

````
## Folder Description
- My code is placed inside `"src/main/java"` and `"src/test/java"` and browser driver is in drivers folder, although it is not need to run the selenium
- All my testcases are in `"src/test/java"` and support files are in `"src/main/java"`

>I am not showing all classes as rest of the classes are similar

### src/main/java
1. **browserSetUp:** Here I set up the browser launch based on input string and return the driver.
2. **configuration:** In this we have configuration file and json file where `baseURL` and browser name is present. Configuration file parse the json file.
3. **pageObjectFactory:** The purpose of this package is to give the object of every POM file.
````java
package pageObjectFactory;

import PageObjects.BillingPage;
import PageObjects.CartDialogBox;
import PageObjects.LandingPage;
import PageObjects.TermsAndConditions;
import org.openqa.selenium.WebDriver;
public class PageObjects {
WebDriver driver;
public PageObjects(WebDriver driver){
this.driver=driver;
}

    public LandingPage getLandingPageObject(){
        return new LandingPage(driver);
    }

    public BillingPage getBillingPageObject(){
        return new BillingPage(driver);
    }
    public CartDialogBox getCartDialogBoxObject(){
        return new CartDialogBox(driver);
    }
    public TermsAndConditions  getTermsAndConditions(){
        return new TermsAndConditions(driver);
    }
}
````
4. **PageObjects:** Here I defined every component as an object of every page.
This package contains classes like `BillingPage,
CartDialogBox,LandingPage,TermsAndConditions and SearchFiled Interface`



````java
package PageObjects;

import Util.Utility;
import Util.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LandingPage implements SearchField {

      // Locators setup
      @FindBy(css = ".brand.greenLogo")
      private WebElement logo;
      @FindBy(css = "div[class='product']")
      private List<WebElement> products;
      @FindBy(css = "input.search-keyword")
      private WebElement searchBox;
      @FindBy(css = "img[alt='Cart']")
      private WebElement cartIcon;
//----------------------------------------------------------------------------------
      String productTextPath = "./h4";
      String addToCartBtnPath = "./div[@class='product-action']/button";
      final private String increaseQty = "./div[@class='stepper-input']/a[@class='increment']";
      final private String decreaseQty = "./div[@class='stepper-input']/a[@class='decrement']";
      final private String qtyNum = "./div[@class='stepper-input']/input[@class='quantity']";
      final private String imgPath = "./div[@class='product-image']/img";
//---------------------------------------------------------------------------------
      // This is driver
      private WebDriver driver;
      public LandingPage(WebDriver driver) {
            this.driver = driver;
            PageFactory.initElements(driver, this);
      }
//----------------------------PRODUCT---------------------------------------------------
// The below method used for checking the broken image of products
      public List<WebElement> isAnyProductImageBroken() {
            List<WebElement> brokenImageProduct = new ArrayList<>();
            for (WebElement product : products) {
                  String productImgUrl = product.findElement(By.xpath(imgPath)).getAttribute("src");
                  if (!Utility.checkUrl(productImgUrl)) {
                        brokenImageProduct.add(product);
                  }
            }
            return brokenImageProduct;
      }
//The  below method is used to add product
      public String addProduct(int productIndex, int qty) {
            System.out.println("ProductINDEX:::" + productIndex + " &&& " + qty);
            System.out.println("Products Length:::" + products.size());
            if (qty > 1) {
                  //for increasing the qty of product
                  increaseQty(productIndex, qty);
            }
            //clicking the add to cart button.
            WebElement productQty = products.get(productIndex).findElement(By.xpath(qtyNum));
            WaitManager.waitTillAttributeAppears(driver, productQty, 5000, "value", String.valueOf(qty));
            Utility.clickWithAction(driver, products.get(productIndex).findElement(By.xpath(addToCartBtnPath)));
//        products.get(productIndex).findElement(By.xpath(addToCartBtnPath)).click();
            WebElement btn = products.get(productIndex).findElement(By.xpath(addToCartBtnPath));
            WaitManager.waitTillTextToBePresentInElement(driver, 5000, btn, "✔ ADDED");
            return products.get(productIndex).findElement(By.xpath(addToCartBtnPath)).getText();
      }
      public void increaseQty(int productIndex, int qty) {
            Utility.scrollFocus(driver, products.get(productIndex));
            WebElement plusBtn = products.get(productIndex).findElement(By.xpath(increaseQty));
            for (int i = 0; i < qty - 1; i++) {
                  plusBtn.click();
            }
      }
      //The below method give use a string arraylist of products which are present on the landing page.
      public List<String> getProducts() {
            return products.stream().map(element -> element.findElement(By.xpath(productTextPath)).getText().split("-")[0]).toList();
//        System.out.println("::::products read from the page::::::::");
//        data.forEach(System.out::println);
//        System.out.println("SIZE OF PRODUCTS:::::::::"+data.size());
      }
//----------------------CART-ICON-----------------------------------------------------------------
      public void clickCartIcon() {
            Utility.clickWithAction(driver, cartIcon);
      }
      private String getProductName(WebElement element) {
            Utility.scrollFocus(driver, element);
            return element.findElement(By.xpath(productTextPath)).getText();
      }
//---------------------------------- SEARCH -----------------------------------------------------------
      public void clickSearchBox() {
            searchBox.click();
      }
      @Override
      public String getPlaceholderText() {
            return searchBox.getAttribute("placeholder");
      }
      public List<String> getSearchResult() {
            return products.stream().map(this::getProductName).collect(Collectors.toList());
      }
      public void search(String searchInput) {
            searchBox.sendKeys(searchInput);
      }
}






````
````java
package PageObjects;

import Util.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartDialogBox {
      private WebDriver driver;
      @FindBy(css = ".cart-preview.active  .cart-items li") //This gives list of products inside the cart
      private List<WebElement> productsInCart;
      @FindBy(xpath = "//button[contains(text(),'PROCEED TO CHECKOUT')]")
      private WebElement proceedToCheckoutBtn;

      //--------------------------------------------------------------------------------------
      public final String crossBtn="./a";
      public final String productName=".//p[@class='product-name']";
      public final String productPrice=".//p[@class='product-price']";
      public final String productQty=".//p[@class='quantity']";
      public final String totalPriceOfProduct=".//p[@class='amount']";

      public CartDialogBox(WebDriver driver){
            this.driver=driver;
            PageFactory.initElements(driver,this);

      }
      //This will give us products inside the cart
      public List<WebElement>  getProductsInCart(){
            System.out.println("products::::XXX:::"+productsInCart.size());
            return productsInCart;
      }
      public boolean isProceedToCheckOutBtnEnabled(){
            if(proceedToCheckoutBtn.isDisplayed()){
                  String text=proceedToCheckoutBtn.getAttribute("class");
                  return !text.equalsIgnoreCase("disabled");

            }
            return true;
      }
      public void clickProceedToCheckoutBtn(){
            WaitManager.waitTillElementClickable(driver,5000,proceedToCheckoutBtn);
            proceedToCheckoutBtn.click();
      }
}
````

5. **Util:** This is a utility package containing `Utility file` and `WaitManager` file
 ````java
package Util;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Utility {
// Scrolls the screen so that focus should be on element
    public static void scrollFocus(WebDriver driver, WebElement element){
//         Point position=element.getLocation();
         Actions action=new Actions(driver);
         action.scrollToElement(element).build().perform();
    }
// checking broken links
    public static boolean  checkUrl(String url){
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            if(connection.getResponseCode()!=200){
                return false;
            }
        }catch (Exception e){
            System.out.println("EXCEPTION: "+ e.getMessage());
        }
        return true;
    }
// Getting products from json TestData
    public static JSONArray getProductsCountFromTestData(){
        String path=  System.getProperty("user.dir")+"\\src\\test\\java\\TestData\\productPricing.json";
        JSONTokener token=null;
        try(FileReader file=new FileReader(path)){
             token=new JSONTokener(file);
            return new JSONArray(token);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
// validating product details
    public static boolean validateProductDetails(List<String> productsFromLandingPage, JSONArray productsFromTestData) {
            boolean validateStatus=true;
        if(productsFromTestData.length()==productsFromLandingPage.size()){

            for(int i=0;i<productsFromTestData.length();i++){
                String productNameFromTestData=productsFromTestData.getJSONObject(i).getString("productName").toLowerCase();
                String productNameFromLandingPage=productsFromLandingPage.get(i).toLowerCase();
                if(!productNameFromLandingPage.contains(productNameFromTestData)){
                    validateStatus=false;
                    break;
                }
            }
        }else {
            return false;
        }
        return validateStatus;
    }
// Generating different product with different qty
    public static LinkedHashMap<Integer, Integer> generateDifferentProductsWithDifferentQty() {
        LinkedHashMap<Integer,Integer> productQtyMap=new LinkedHashMap<>();
        Random random=new Random();
        int numberOfProducts=random.nextInt(1,3);
        for(int i=0;i<numberOfProducts;i++){
            int qtyOfProduct= random.nextInt(1,3);
            int productIndex= random.nextInt(0,30);//Total products we have is 30
            productQtyMap.put(productIndex,qtyOfProduct);
        }
        return productQtyMap;
    }
// Now we need to change product index with its name    
    public static LinkedHashMap<String,Integer> setProductsNameWithQty(LinkedHashMap<Integer,Integer> productsAddedByUserInCart,List<String> products){
        LinkedHashMap<String,Integer> map=new LinkedHashMap<>();
        for(Integer key:productsAddedByUserInCart.keySet()){
            map.put(products.get(key),productsAddedByUserInCart.get(key));
        }
        return map;
    }

    public static void clickWithAction(WebDriver driver,WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }
}
````
### src/test/java
1. **FeatureFiles:** contains feature files. Example:-
````cucumber
Feature: Cart functionality

  Background:
    Given user is on landingPage

  Scenario: Verify added product details in cart is correct
    Given  user adds different products with different qty
    When  user open the cart
    Then  added products details should be correct

  Scenario: Verify products can be removed from the cart
    Given  user adds different products with different qty
    When  user open the cart
    And  user removes all products
    Then Proceed to checkout button is disabled
````
2. **Hooks:** contains hooks. Example:-
````java
package Hooks;

import TestContext.TestContext;
import io.cucumber.java.After;

public class Hook1 {
    TestContext context;
    public Hook1(TestContext context){
        this.context=context;
    }
    @After
    public void after(){
        context.assertAll();
        context.closeBrowser();
    }
}
````
3. **StepDefinitionFiles:** contains step definition files. Example:
````java
package StepDefinitionFiles;

import PageObjects.BillingPage;
import PageObjects.CartDialogBox;
import TestContext.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import java.util.LinkedHashMap;
import java.util.List;

public class BillingDefinition {
    private final TestContext context;
    private final BillingPage billingPage;

    public BillingDefinition( TestContext context){
        this.context=context;
       billingPage =context.getPageObject().getBillingPageObject();
    }
    @Then("billing data should be correct")
    public void billingDataShouldBeCorrect() {
        List<LinkedHashMap<String,String>> productInsideCart = context.getProductInsideCart();
        if(billingPage.getProductsInBillingTable().size()==productInsideCart.size()){
            CartDialogBox cartDialogbox = context.getPageObject().getCartDialogBoxObject();
            for(int i=0;i<billingPage.getProductsInBillingTable().size();i++){
                String productName=billingPage.getProductsInBillingTable().get(i).findElement(By.xpath(billingPage.productName)).getText();
                String productQty=billingPage.getProductsInBillingTable().get(i).findElement(By.xpath(billingPage.productQty)).getText();
                String productPrice=billingPage.getProductsInBillingTable().get(i).findElement(By.xpath(billingPage.productPrice)).getText();
                String productTotalPrice=billingPage.getProductsInBillingTable().get(i).findElement(By.xpath(billingPage.productTotal)).getText();
                context.assertion.assertEquals(productName,productInsideCart.get(i).get("productName"),"product name mismatch");
                context.assertion.assertEquals(productQty,productInsideCart.get(i).get("productQty"),"product qty mismatch");
                context.assertion.assertEquals(productPrice,productInsideCart.get(i).get("productPrice"),"productPrice mismatch");
                context.assertion.assertEquals(productTotalPrice,productInsideCart.get(i).get("productTotal"),"productTotal price mismatch");
            }
        }else{
            context.assertion.fail("Product count Mismatch!!!!");
        }
    }
    @Given("user place the order")
    public void userPlaceTheOrder() {
        billingPage.clickPlaceOrder();
    }
}
````
4. **TestContext**: contains text context for dependency injection. Helped me to share data between the files
```java
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

```     
5. **TestData:** contains product details in json format
6. **TestRunner:** contains test runner file


## Project Flow:
- User will open the website, search for the products and add them in the cart. Once added then he can place order after verifying order summary.
- So things starts from landing page.
- We want to add products in the cart randomly so we used random class in java
- This random class helps in generating random quantity of product and random index of product as we already know that there are 30 products
- This will create a Map<Integer,Integer> so when landing page is loaded then we store all products in a  list and then using map we trace the product using index in the list and add product with corresponding quantity.
- But when verifying the products in the cart i.e products and there quantity we only have product index and quantity i.e.  Map<Integer,Integer> (products added by user) so inorder to do this we fetched products during landing page loading  and map it in the cart so that we can have name and other details of product.
>> i know i made it confusing but just check the code and feature files once

#### Example:
```gherkin
Feature: Cart functionality

  Background:
    Given user is on landingPage

  Scenario: Verify added product details in cart is correct
    Given  user adds different products with different qty
    When  user open the cart
    Then  added products details should be correct
```

**Step1:** landingPage will get open
```
 @Given("user is on landingPage")
    public void userIsOnLandingPage() {
        context.openURL(context.getUrl());
        System.out.println("User is on LandingPage...");
        String favTabText=context.getDriver().getTitle();
        context.assertion.assertEquals(favTabText,"GreenKart - veg and fruits kart");
        //Every time we load the page then we are storing products data in the context so that we can use it
        context.setProducts(landingPage.getProducts());
    }
```
**Step2**: user adds different products with different qty
 ```
   @Given("user adds different products with different qty")
    public void userAddsDifferentProductsWithDifferentQty() {
        // Using linkedHashMap because it is storing the order in which user have selected the placed into cart.
        LinkedHashMap<Integer,Integer> productQtyMap=Utility.generateDifferentProductsWithDifferentQty();
        for (Integer productIndex : productQtyMap.keySet()) {
            String actualResult=landingPage.addProduct(productIndex,productQtyMap.get(productIndex));
            context.assertion.assertEquals(actualResult,"✔ ADDED","AddToCart button is not clicked");
        }
        // here we are storing the products which are added into the cart in the context
        // so that we can validate these whether these are present in the cart or not
        context.setProductsAddedByUserInCart(productQtyMap);
//        System.out.println(":::::::Products added by user::::::::");
//        System.out.println(productQtyMap);

    }
 ```
**Step3**: user opens the cart
```
 @When("user open the cart")
    public void userOpenTheCart() {
       landingPage.clickCartIcon();
    }
```
**Step4**: now we need to check that added products are correct
```
 @Then("added products details should be correct")
    public void addedProductsDetailsShouldBeCorrect() {
        System.out.println(">>>>>>>INSIDE THEN<<<<<<<<<");
        // We are fetching the products from the cart
        List<WebElement> productsInCart=cartDialogBox.getProductsInCart();
        // initially we stored products as there sequence and qty, but now we want to replace sequence number with product name.
        LinkedHashMap<String,Integer> productsAddedByUserInCart = Utility.setProductsNameWithQty(context.getProductsAddedByUserInCart(),context.getProducts());
        // products in cart and products added by user should be same
        if(productsInCart.size()==productsAddedByUserInCart.size()){
            Iterator<String> it=productsAddedByUserInCart.keySet().iterator();
            for (WebElement element : productsInCart) {
                String key = it.next();
                String productInCart = element.findElement(By.xpath(cartDialogBox.productName)).getText().split("-")[0].trim();
                Integer productQtyInCart = Integer.parseInt(element.findElement(By.xpath(cartDialogBox.productQty)).getText().split(" ")[0].trim());
                Integer productQtyByUser = productsAddedByUserInCart.get(key);
                context.assertion.assertTrue(key.trim().equalsIgnoreCase(productInCart), "Product mismatch!!!!");
                context.assertion.assertTrue(Objects.equals(productQtyByUser, productQtyInCart), "product count mismatch!!!");
            }
        }else{
            context.assertion.fail("products in cart are different which were added by user");
        }
    }
```
>[WORK IN PROGRESS](#)
