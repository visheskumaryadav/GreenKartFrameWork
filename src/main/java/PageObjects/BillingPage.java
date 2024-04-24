package PageObjects;

import Util.WaitManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BillingPage {

    private WebDriver driver;
//---------------------------------------------------------------------------
    @FindBy(css = "div[class='products'] tbody tr")
    public List<WebElement> productsInBillingTable;
    @FindBy(css = "div[class='products']>div")
    public WebElement extraBillingDetail;
    @FindBy(xpath = "//button[text()='Place Order']")
    public WebElement placeOrderBtn;
//----------------------------------------------------------------------------
    public final String totalAmt=".//[@class='totAmt'";
    public final String productName="./td/p[@class='product-name']";
    public final String productQty="./td/p[@class='quantity']";
    public final String productPrice="./td/p[@class='amount']";
    public final String productTotal="./td[last()]";

//-----------------------------------------------------------------------------

    public BillingPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
//-----------------------------------------------------------------------------

    public List<WebElement> getProductsInBillingTable(){
        WaitManager.waitTillVisible(driver,5000,productsInBillingTable);
        return productsInBillingTable;
    }
    public void clickPlaceOrder(){
        placeOrderBtn.click();
    }
}
