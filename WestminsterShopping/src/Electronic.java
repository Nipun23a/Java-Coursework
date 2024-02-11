import java.io.Serializable;

public class Electronic extends Product implements Serializable {

    // Additional Variable For Electronics
    private String electronicBrand;
    private int electronicWarrantyPeriodMonth;

    //Constructor For Electronics

    public Electronic(String productID, String productCategory,String productName, int productQuantity, double productPrice, String electronicBrand, int electronicWarrentyPeriodMonth){
        super(productID,productCategory,productName,productQuantity,productPrice);
        this.electronicBrand = electronicBrand;
        this.electronicWarrantyPeriodMonth = electronicWarrentyPeriodMonth;
    }

    //Getter And Setter For Electronic

    public String getElectronicBrand() {
        return electronicBrand;
    }
    public int getElectronicWarrantyPeriodMonth() {
        return electronicWarrantyPeriodMonth;
    }

    public void setElectronicBrand(String electronicBrand) {
        this.electronicBrand = electronicBrand;
    }

    public void setElectronicWarrantyPeriodMonth(int electronicWarrantyPeriodMonth) {
        this.electronicWarrantyPeriodMonth = electronicWarrantyPeriodMonth;
    }

    // Override abstract displayProductInfo method from product to electronic
    public void displayProductInfo() {
        System.out.println("Product Id :"+getProductID());
        System.out.println("Product Category :"+getProductCategory());
        System.out.println("Product Name :"+getProductName());
        System.out.println("Electronic Brand :"+getElectronicBrand());
        System.out.println("Warranty Period :"+getElectronicWarrantyPeriodMonth());
        System.out.println("Quantity :"+getProductQuantity());
        System.out.println("Price  :"+pound+" "+getProductPrice());
    }
    @Override
    public String toString() {
        return super.toString()
                +"\nElectronic Brand :"+electronicBrand
                +"\nWarranty Period: "+electronicWarrantyPeriodMonth;
    }
}
