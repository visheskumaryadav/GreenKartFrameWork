package Util;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Utility {
// Scrolls the screen so that focus should be on element
    public static void scrollFocus(WebDriver driver, WebElement element){
//         Point position=element.getLocation();
         Actions action=new Actions(driver);
         action.scrollToElement(element).build().perform();
    }
// checking broken links
    public static boolean  checkUrl(String url){
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            if(connection.getResponseCode()!=200){
                return false;
            }
        }catch (Exception e){
            System.out.println("EXCEPTION: "+ e.getMessage());
        }
        return true;
    }

// Getting products from json TestData
    public static JSONArray getProductsCountFromTestData(){
        String path=  System.getProperty("user.dir")+"\\src\\test\\java\\TestData\\productPricing.json";
        JSONTokener token=null;
        try(FileReader file=new FileReader(path)){
             token=new JSONTokener(file);
            return new JSONArray(token);
        }catch(Exception e){
            e.printStackTrace();
            return null;

        }

    }

// validating product details
    public static boolean validateProductDetails(List<String> productsFromLandingPage, JSONArray productsFromTestData) {
            boolean validateStatus=true;
        if(productsFromTestData.length()==productsFromLandingPage.size()){

            for(int i=0;i<productsFromTestData.length();i++){
                String productNameFromTestData=productsFromTestData.getJSONObject(i).getString("productName").toLowerCase();
                String productNameFromLandingPage=productsFromLandingPage.get(i).toLowerCase();
                if(!productNameFromLandingPage.contains(productNameFromTestData)){
                    validateStatus=false;
                    break;
                }

            }
        }else {
            return false;
        }
        return validateStatus;
    }
// Generating different product with different qty
    public static LinkedHashMap<Integer, Integer> generateDifferentProductsWithDifferentQty() {
        LinkedHashMap<Integer,Integer> productQtyMap=new LinkedHashMap<>();
        Random random=new Random();
        int numberOfProducts=random.nextInt(1,3);
        for(int i=0;i<numberOfProducts;i++){
            int qtyOfProduct= random.nextInt(1,3);
            int productIndex= random.nextInt(0,30);//Total products we have is 30
            productQtyMap.put(productIndex,qtyOfProduct);
        }
        return productQtyMap;
    }
// Now we need to change product index with its name
    public static LinkedHashMap<String,Integer> setProductsNameWithQty(LinkedHashMap<Integer,Integer> productsAddedByUserInCart,List<String> products){
        LinkedHashMap<String,Integer> map=new LinkedHashMap<>();
        for(Integer key:productsAddedByUserInCart.keySet()){
            map.put(products.get(key),productsAddedByUserInCart.get(key));
        }
        return map;
    }

    public static void clickWithAction(WebDriver driver,WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }

}
