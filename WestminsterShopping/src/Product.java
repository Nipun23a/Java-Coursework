import java.io.Serializable;

public abstract class Product implements Serializable {

    //Variables
    private String productID;
    private String productCategory;
    private String productName;
    private  int productQuantity;
    private double productPrice;
    String pound = "£";

    private double totalPrice;
    private String productBrand;
    private int warrantyPeriodInMonths;
    private String clothingSize;
    private String clothingColor;

    // Constructors
    public Product(String productID,String productCategory,String productName,int productQuantity,double productPrice){
        this.productID = productID;
        this.productCategory=  productCategory;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
    public Product(String productName,int productQuantity,double productPrice){
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    //Abstract method to be implemented by subclasses.
    public abstract void displayProductInfo();

    // Getter and Setter.
    public String getProductID() {
        return productID;
    }
    public double getTotalPrice(){return totalPrice;}

    public String getProductCategory() {return productCategory;}

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductName() {
        return productName;
    }
    public double getProductPrice() {
        return productPrice;
    }

    public String getClothingColor(){
        return  clothingColor;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setProductCategory(String productCategory) {this.productCategory = productCategory;}

    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setTotalPrice(Double productPrice){
        this.productPrice = productPrice;
    }
    public void setClothingSize(String clothingSize){
        this.clothingSize = clothingSize;
    }
    public void setProductBrand(String productBrand){
        this.productBrand = productBrand;
    }
    public void setWarrantyPeriodInMonths(int warrantyPeriodInMonths){
        this.warrantyPeriodInMonths = warrantyPeriodInMonths;
    }
    public void setClothingColor(String clothingColor){
        this.clothingColor = clothingColor;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
    public double calculateTotalPrice(int productQuantity){
        return this.productPrice*productQuantity ;
    }


    @Override
    public String toString() {
        return "Product ID: " + productID +
                "\nProduct Category: "+ productCategory +
                "\nProduct Name : " + productName +
                "\nProduct Price: " + productPrice +
                "\nProduct Quantity: " + productQuantity;
    }

}





