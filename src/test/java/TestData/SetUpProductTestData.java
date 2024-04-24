package TestData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.util.List;

public class SetUpProductTestData {
    public static  void main(String[] args) {

        WebDriver driver=new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
        String path=  System.getProperty("user.dir")+"\\src\\test\\java\\TestData\\productPricing.json";
        JSONArray arr=new JSONArray();
        try(FileWriter file=new FileWriter(path)){
            List<WebElement> products=driver.findElements(By.cssSelector("div[class=products]>.product"));
            for(int i=0;i<products.size();i++){
                String product=products.get(i).findElement(By.xpath("./h4")).getText();
                String productPrice= products.get(i).findElement(By.xpath("./p")).getText();
                String[] productArray= product.split("-");
                if(productArray.length>=2){
                    String productName=productArray[0].trim();
                    String productWight=productArray[1].trim();
                    JSONObject object=new JSONObject();
                    object.put("productName",productName);
                    object.put("price",productPrice);
                    object.put("Weight",productWight);
                    arr.put(object);
                }else{
                    String productName=productArray[0].trim();
                    JSONObject object=new JSONObject();
                    object.put("productName",productName);
                    object.put("price",productPrice);
                    object.put("Weight","N/A");
                    arr.put(object);
                }

            }
            file.write(arr.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println(path);

    }
}
