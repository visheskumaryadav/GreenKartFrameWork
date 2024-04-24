package StepDefinitionFiles;//package StepDefinitionFiles;
//
//import PageObjects.Cart;
//import PageObjects.CartDialogBox;
//import TestContext.TestContext;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//
//public class CartStepDefinition {
//
//    private TestContext context;
//    private CartDialogBox cart;
//    public CartStepDefinition( TestContext context){
//        this.context=context;
//        cart=context.getPageObject().getCartDialogBoxObject();
//    }
//
//    @Then("product is added to Cart")
//    public void productIsAddedToCart() {
//        cart.getAddedProductCount();
//    }
//
//    @Then("product count should be {int}")
//    public void productCountShouldBe(int qty) {
//        context.assertion.assertEquals(cart.getAddedProductCount(),qty,"Qty mismatched");
//    }
//
//
//}
