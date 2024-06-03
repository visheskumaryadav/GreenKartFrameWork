package browserSetup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 SetBowser class used for launching the browsers
 */
public class SetBrowser {

    private WebDriver driver;
    public  WebDriver launchBrowser(String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
//            WebDriverManager.chromedriver().setup();
            ChromeOptions options=new ChromeOptions();
//            System.out.println(System.getProperty("user.dir"));
//            options.setBinary(System.getProperty("user.dir")+"\\drivers\\chrome-win64\\chrome.exe");
//            driver=new ChromeDriver(options);
            driver=new ChromeDriver();

            driver.manage().window().maximize();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
            driver.manage().window().maximize();
        }else{
            System.out.println("Invalid browser");
        }
        return driver;
    }

    public void close(){
        driver.quit();
    }
}
