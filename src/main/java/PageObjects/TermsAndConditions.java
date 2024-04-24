package PageObjects;

import Util.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TermsAndConditions {
    private WebDriver driver;

    @FindBy(css = ".products select")
    WebElement select;
    @FindBy(css = ".products .chkAgree")
    WebElement checkbox;
    @FindBy(css = ".products button")
    WebElement proceedBtn;
    @FindBy(css = ".products  .errorAlert")
    WebElement errorMsg;
    String successMsg=null;

    public TermsAndConditions(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    public void selectCountry(String countryName){
        Select option=new Select(select);
        option.selectByValue(countryName);
    }
    public void selectCheckbox(){
        checkbox.click();
    }
    public void clickProceedBtn(){
        if(checkbox.isSelected()){
            proceedBtn.click();
            WaitManager.waitTillElementClickable(driver,5000,driver.findElement(By.cssSelector("div[class='wrapperTwo'] span ")));
            successMsg=driver.findElement(By.cssSelector("div[class='wrapperTwo'] span ")).getText();
        }else{
            proceedBtn.click();

        }
    }
    public String getErrorMsg(){
        return errorMsg.getText();
    }
    public String getSuccessMsg(){
        return successMsg;
    }
}
