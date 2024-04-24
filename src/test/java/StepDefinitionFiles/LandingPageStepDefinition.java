package StepDefinitionFiles;

import PageObjects.LandingPage;
import TestContext.TestContext;
import Util.Utility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class LandingPageStepDefinition {
    TestContext context;
    LandingPage landingPage;
    String productName;
    Random random=new Random();
    int randomProductIndex;

//-------------------------------------------------------------------------------------
    public LandingPageStepDefinition( TestContext context){
        this.context=context;
        landingPage=context.getPageObject().getLandingPageObject();
    }

    @Given("user is on landingPage")
    public void userIsOnLandingPage() {
        context.openURL(context.getUrl());
        System.out.println("User is on LandingPage...");
        String favTabText=context.getDriver().getTitle();
        context.assertion.assertEquals(favTabText,"GreenKart - veg and fruits kart");
        //Every time we load the page then we are storing products data in the context so that we can use it
        context.setProducts(landingPage.getProducts());
    }

//--------------------------- SEARCH FIELD ----------------------------------------

    @Then("user click on search input field")
    public void userClickOnSearchInputField() {
        landingPage.clickSearchBox();
        System.out.println("User clicked on searchbox");
        context.assertion.assertEquals(landingPage.getPlaceholderText(),"Search for Vegetables and Fruits","Placeholder text mismatch!!! ");
    }

    @When("user enter {string} in search input")
    public void userEnterInSearchInput(String searchInput) {
        landingPage.search(searchInput);
        System.out.println("User has entered " + searchInput);
    }

    @Then("that {string} should appear in result.")
    public void thatProductShouldAppearInResult(String searchInput) {
        List<String> searchedProducts=landingPage.getSearchResult();
        for(String product:searchedProducts){
            String productName=product.split("-")[0].trim();
            context.assertion.assertTrue(productName.equalsIgnoreCase(searchInput),searchInput+" product mismatch!!!");
        }
    }

    @Then("products starting with {string} appears")
    public void productsStartingWithAppears(String startsWithText) {
        List<String> searchedProducts=landingPage.getSearchResult();
        for(String product:searchedProducts){
            String productName=product.split("-")[0].trim().toLowerCase();
            context.assertion.assertTrue(productName.contains("cu"),productName+" product mismatch!!!");
        }
    }
//----------------
    private List<String> productDataFromLandingPage=null;
    @When("all products appears")
    public void allProductsAppears() {
        productDataFromLandingPage=landingPage.getSearchResult();
        context.assertion.assertEquals(productDataFromLandingPage.size(),Utility.getProductsCountFromTestData().length(),"Product count is incorrect!!!!");
    }

    @Then("product details are same as jsonData")
    public void productDetailsAreSameAsJsonData() {
      context.assertion.assertTrue(Utility.validateProductDetails(productDataFromLandingPage,Utility.getProductsCountFromTestData()),"Products data mismatch!!!");
      productDataFromLandingPage=null;
    }

    @And("product images are not broken")
    public void productImagesAreNotBroken() {
        List< WebElement> brokenImageProducts =landingPage.isAnyProductImageBroken();
        if(!brokenImageProducts.isEmpty()){
             for (WebElement product:brokenImageProducts){
                  context.assertion.fail(product.getText()+ "::: have Broken image!!!");
             }
        }
    }

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

    @When("user open the cart")
    public void userOpenTheCart() {
       landingPage.clickCartIcon();

    }


//
//    @When("user click on AddToCart button Once")
//    public void userClickOnAddToCartButtonOnce() {
////        random=new Random();
//       int  randomProductIndex1=random.nextInt(31);
//        String actualResult=landingPage.addProduct(randomProductIndex1);
//        context.assertion.assertEquals(actualResult,"✔ ADDED","AddToCart button is not clicked");
//    }
//
//
//    @When("user increase the product quantity by {int}")
//    public void userIncreaseTheProductQuantityBy(int qty) {
//        randomProductIndex=random.nextInt(31);
//        int initialProductQty=landingPage.getQtyDisplayingForProduct(randomProductIndex);
//        landingPage.increaseQty(randomProductIndex, qty);
//        context.assertion.assertTrue(landingPage.getQtyDisplayingForProduct(randomProductIndex)>initialProductQty,
//                "Product qty doesn't increased");
//    }
//
//    @Then("quantity should appear {int}")
//    public void quantityShouldAppear(int qty) {
//        context.assertion.assertEquals(landingPage.getQtyDisplayingForProduct(randomProductIndex),qty+1,"Product qty added incorrectly");
//    }
//
//
//    @And("user should able to decrease quantity to minimum")
//    public void userShouldAbleToDecreaseQuantityToMinimum() {
//     landingPage.decreaseQty(randomProductIndex);
//        context.assertion.assertEquals(landingPage.getQtyDisplayingForProduct(randomProductIndex),1,"Product qty added incorrectly");
//
//    }
//

//

//
//    @Then("product details are same as jsonData")
//    public void productDetailsAreSameAsJsonData() {
//    }
}

