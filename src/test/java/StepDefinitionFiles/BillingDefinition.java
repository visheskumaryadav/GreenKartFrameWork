package StepDefinitionFiles;

import PageObjects.BillingPage;
import PageObjects.CartDialogBox;
import TestContext.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import java.util.LinkedHashMap;
import java.util.List;

public class BillingDefinition {
    private TestContext context;
    private BillingPage billingPage;

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
