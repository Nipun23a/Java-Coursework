import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    // Additional Variable For Clothing
    private String clothingSize;
    private String clothingColour;

    //Constructor For Clothing

    public Clothing(String productID, String productName,String productCategory,int productQuantity, double productPrice, String clothingSize, String clothingColour){
        super(productID,productName,productCategory,productQuantity,productQuantity);
        this.clothingSize = clothingSize;
        this.clothingColour = clothingColour;
    }

    //Getter And Setter For Clothing
    public String getClothingColour() {
        return clothingColour;
    }

    public String getClothingSize() {
        return clothingSize;
    }

    public void setClothingColour(String clothingColour) {
        this.clothingColour = clothingColour;
    }

    public void setClothingSize(String clothingSize) {
        this.clothingSize = clothingSize;
    }

    // Override abstract displayProductInfo method from product to clothing
    @Override
    public void displayProductInfo() {
        System.out.println("Product Id :"+getProductID());
        System.out.println("Product Category :"+getProductCategory());
        System.out.println("Product Name :"+getProductName());
        System.out.println("Size :"+getClothingSize());
        System.out.println("Colour :"+getClothingColour());
        System.out.println("Quantity :"+getProductQuantity());
        System.out.println("Price  :"+pound+" "+getProductPrice());
    }
    @Override
    public String toString() {
        return super.toString()
                +"\nSize: "+clothingSize
                +"\nColour: "+clothingColour;
    }
}

