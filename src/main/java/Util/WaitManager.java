package Util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitManager {

    public static void waitTillVisible(WebDriver driver, int duration, WebElement element){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitTillVisible(WebDriver driver, int duration, List<WebElement> elements){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    public static void waitTillElementClickable(WebDriver driver,int duration,WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitTillDisappear(WebDriver driver,int duration,WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }
    public static void waitTillTextToBePresentInElement(WebDriver driver,int duration,WebElement element,String text){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.textToBePresentInElement(element,text));
    }
    public static void waitTillAttributeAppears(WebDriver driver,WebElement element,int duration,String attributeName,String attributeValue){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.attributeToBe(element,attributeName,attributeValue));
    }

}
