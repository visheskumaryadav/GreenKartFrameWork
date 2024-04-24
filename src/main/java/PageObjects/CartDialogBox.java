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

    public WebElement getProceedToCheckoutBtn(){
        return null;
    }
    public void deleteCartItem(String itemName){

    }

    public void clickProceedToCheckoutBtn(){
        WaitManager.waitTillElementClickable(driver,5000,proceedToCheckoutBtn);
        proceedToCheckoutBtn.click();
    }
}
