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
//    public Cart getCartObject(){
//        return new Cart(driver);
//    }

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
