package PageObjects;

public interface Product {

    public boolean isAnyProductImageBroken();
    public int getQtyDisplayingForProduct(int randomProductIndex);
    public void increaseQty(int randomProductIndex, int qty);
    public void decreaseQty(int randomProductIndex, int qty);
    public void decreaseQty(int randomProductIndex);


    }
