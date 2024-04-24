package PageObjects;

import Util.Utility;
import Util.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LandingPage implements SearchField {

    // Locators setup
    @FindBy(css = ".brand.greenLogo")
    private WebElement logo;
    @FindBy(css = "div[class='product']")
    private List<WebElement> products;
    @FindBy(css = "input.search-keyword")
    private WebElement searchBox;
    @FindBy(css = "img[alt='Cart']")
    private WebElement cartIcon;
//----------------------------------------------------------------------------------

    String productTextPath = "./h4";
    String addToCartBtnPath = "./div[@class='product-action']/button";
    final private String increaseQty = "./div[@class='stepper-input']/a[@class='increment']";
    final private String decreaseQty = "./div[@class='stepper-input']/a[@class='decrement']";
    final private String qtyNum = "./div[@class='stepper-input']/input[@class='quantity']";
    final private String imgPath = "./div[@class='product-image']/img";

    //---------------------------------------------------------------------------------
    // This is driver
    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

//----------------------------PRODUCT---------------------------------------------------

    // The below method used for checking the broken image of products

    public List<WebElement> isAnyProductImageBroken() {
        List<WebElement> brokenImageProduct = new ArrayList<>();
        for (WebElement product : products) {
            String productImgUrl = product.findElement(By.xpath(imgPath)).getAttribute("src");
            if (!Utility.checkUrl(productImgUrl)) {
                brokenImageProduct.add(product);
            }
        }
        return brokenImageProduct;
    }

    //The  below method is used to add product
    public String addProduct(int productIndex, int qty) {
        System.out.println("ProductINDEX:::" + productIndex + " &&& " + qty);
        System.out.println("Products Length:::" + products.size());
        if (qty > 1) {
            //for increasing the qty of product
            increaseQty(productIndex, qty);
        }
        //clicking the add to cart button.
        WebElement productQty = products.get(productIndex).findElement(By.xpath(qtyNum));
        WaitManager.waitTillAttributeAppears(driver, productQty, 5000, "value", String.valueOf(qty));
        Utility.clickWithAction(driver, products.get(productIndex).findElement(By.xpath(addToCartBtnPath)));
//        products.get(productIndex).findElement(By.xpath(addToCartBtnPath)).click();
        WebElement btn = products.get(productIndex).findElement(By.xpath(addToCartBtnPath));
        WaitManager.waitTillTextToBePresentInElement(driver, 5000, btn, "✔ ADDED");
        return products.get(productIndex).findElement(By.xpath(addToCartBtnPath)).getText();

    }

    public void increaseQty(int productIndex, int qty) {
        Utility.scrollFocus(driver, products.get(productIndex));
        WebElement plusBtn = products.get(productIndex).findElement(By.xpath(increaseQty));
        for (int i = 0; i < qty - 1; i++) {
            plusBtn.click();
        }
    }

    //The below method give use a string arraylist of products which are present on the landing page.
    public List<String> getProducts() {
        return products.stream().map(element -> element.findElement(By.xpath(productTextPath)).getText().split("-")[0]).toList();
//        System.out.println("::::products read from the page::::::::");
//        data.forEach(System.out::println);
//        System.out.println("SIZE OF PRODUCTS:::::::::"+data.size());

    }
//----------------------CART-ICON-----------------------------------------------------------------

    public void clickCartIcon() {
        Utility.clickWithAction(driver, cartIcon);

    }

    private String getProductName(WebElement element) {
        Utility.scrollFocus(driver, element);
        return element.findElement(By.xpath(productTextPath)).getText();
    }

//---------------------------------- SEARCH -----------------------------------------------------------

    public void clickSearchBox() {
        searchBox.click();
    }

    @Override
    public String getPlaceholderText() {
        return searchBox.getAttribute("placeholder");

    }

    public List<String> getSearchResult() {
        return products.stream().map(this::getProductName).collect(Collectors.toList());
    }

    public void search(String searchInput) {
        searchBox.sendKeys(searchInput);
    }
}




