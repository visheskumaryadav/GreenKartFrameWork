package StepDefinitionFiles;

import PageObjects.CartDialogBox;
import TestContext.TestContext;
import Util.Utility;
import Util.WaitManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bouncycastle.jcajce.provider.asymmetric.util.GOST3410Util;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.*;

public class CartDialogBoxDefinition {
    private TestContext context;
    private CartDialogBox cartDialogBox;
    public CartDialogBoxDefinition(TestContext context){
        this.context=context;
        this.cartDialogBox=context.getPageObject().getCartDialogBoxObject();
    }


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

    @And("user removes all products")
    public void userRemovesAllProducts() {
        List<WebElement>productsInCart=cartDialogBox.getProductsInCart();
        for(WebElement product:productsInCart){
            WaitManager.waitTillElementClickable(context.getDriver(),5000,product);
            String productName=product.findElement(By.xpath(cartDialogBox.productName)).getText().split("-")[0].trim();
            product.findElement(By.xpath(cartDialogBox.crossBtn)).click();
        }
    }

    @Then("Proceed to checkout button is disabled")
    public void buttonIsDisabled() {
        context.assertion.assertFalse(cartDialogBox.isProceedToCheckOutBtnEnabled(),"Proceed to checkout button is not disabled");
    }

    @When("user proceeds to checkout")
    public void userProceedsToCheckout() {
        context.getPageObject().getLandingPageObject().clickCartIcon();
        // We are storing the product in cart in this way because once we are on billing screen then we will stalemate exception as
        // locators of cart will not work because while assertion the screen will be billing screen not cart dialog box.

        List<LinkedHashMap<String,String>> list=new ArrayList<>();
        for(WebElement p:cartDialogBox.getProductsInCart()) {
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("productName", p.findElement(By.xpath(cartDialogBox.productName)).getText());
            map.put("productQty", p.findElement(By.xpath(cartDialogBox.productQty)).getText().split(" ")[0]);
            map.put("productPrice", p.findElement(By.xpath(cartDialogBox.productPrice)).getText());
            map.put("productTotal", p.findElement(By.xpath(cartDialogBox.totalPriceOfProduct)).getText());
            list.add(map);
        }
        context.setProductInsideCart(list);
        cartDialogBox.clickProceedToCheckoutBtn();
    }


}
