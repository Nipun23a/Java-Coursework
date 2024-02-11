import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManagerOther {
    private ShoppingDashboard shoppingDashboard;
    private ArrayList<Product> productList = new ArrayList<>();


    public ArrayList<Product> getProductList() {
        return productList;
    }

    private int totalProduct = 0;
    double firstPurchaseDiscount;
    Scanner input = new Scanner(System.in);
    int mainArrayListData;
    double totalPrice;
    double grandTotal;
    int productQunatity;
    double categoryDiscount;
    double firstpurchaseDiscout;

    public WestminsterShoppingManagerOther() {
        this.shoppingDashboard = new ShoppingDashboard(this);
        // Rest of your constructor code...
    }

    public void callShowMenu() {
        showMenu(this);
    }

    private void showMenu(WestminsterShoppingManagerOther manager) {
        WestminsterShoppingManagerOther other = new WestminsterShoppingManagerOther();
        shoppingDashboard = new ShoppingDashboard(this);
        System.out.println("**********************************************************************************************");
        System.out.println("**********                  WESTMINSTER SHOPPING MANAGER PROGRAM                    **********");
        System.out.println("**********************************************************************************************");
        int choice;
        do {
            System.out.println("\n1.Add New Product\n2.Remove Product\n3.Print the list of products\n4.Save product in file\n5.Load from file\n6.Open Customer Dashboaard\n7.Exit");
            try {
                System.out.print("Your Choice: ");
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        manager.addNewProduct();
                        break;
                    case 2:
                        manager.removeAnItem();
                        break;
                    case 3:
                        for (Product product : productList) {
                            manager.printListProduct(product);
                        }
                        break;
                    case 4:

                        manager.save();
                        break;
                    case 5:
                            manager.read(productList);

                        break;
                    case 6:
                        shoppingDashboard.userLogin(this);
                        break;
                    case 7:
                        System.out.print("Thank you for using our product. Hava a nice day");
                        break;
                    default:
                        System.out.print("");


                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } while (choice != 7);

    }

    private void addNewProduct() {
        System.out.print("---Add New Items---\n");
        while (true) {
            System.out.println("Product ID(Must Enter With AO numbers before): ");
            String productID = input.next();
            System.out.print("Product Category (Electronic or Cloth): ");
            String productCategory = input.next();
            productCategory = productCategory.toUpperCase();
            if (productCategory.equals("ELECTRONIC")) {
                Product product = addNewElectronic(productID, productCategory);
                addProduct(product);
                updateTable();
                exitStatement();
            } else if (productCategory.equals("CLOTH")) {
                Product product = addNewClothing(productID, productCategory);
                addProduct(product);
                updateTable();
                exitStatement();
            } else {
                System.out.print("Enter Valid Category(Electronic or Cloth)");
            }

        }
    }

    private void printListProduct(Product product) {
        if (product instanceof Electronic) {
            Electronic electronic = (Electronic) product;
            System.out.println("Electronic Product - ID: " + electronic.getProductID() +
                    ", Name: " + electronic.getProductName() +
                    ", Price: $" + electronic.getProductPrice() +
                    ", Warranty: " + electronic.getElectronicWarrantyPeriodMonth() +
                    ", Brand: " + electronic.getElectronicBrand() +
                    ", Quantity: " + electronic.getProductQuantity() +
                    "\n\n"
            );
        } else if (product instanceof Clothing) {
            Clothing cloth = (Clothing) product;
            System.out.println("Cloth Product - ID: " + cloth.getProductID() +
                    ", Name: " + cloth.getProductName() +
                    ", Price: $" + cloth.getProductPrice() +
                    ", Color: " + cloth.getClothingColour() +
                    ", Size: " + cloth.getClothingSize() +
                    ", Quantity: " + cloth.getProductQuantity() +
                    "\n\n");
        }
    }


    private void removeAnItem() {
        System.out.print("Enter product ID to remove: ");
        String productIDToRemove = input.next();

        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product currentProduct = iterator.next();
            if (currentProduct instanceof Electronic electronic) {
                if (productIDToRemove.equals(electronic.getProductID())) {
                    iterator.remove();
                    System.out.println("Product Remove successfully");
                    return;
                }
            } else if (currentProduct instanceof Clothing clothing) {
                if (productIDToRemove.equals(clothing.getProductID())) {
                    iterator.remove();
                    System.out.println("Product Remove Successfully");
                    return;
                }
            }
        }

        System.out.println("Item Not Found");
    }


    public void save() throws IOException {
        File file = new File("itemInfo.txt");
        try (FileOutputStream fout = new FileOutputStream(file, false);
             ObjectOutputStream objout = new ObjectOutputStream(fout)) {
            Iterator it = productList.iterator();
            while (it.hasNext()) {
                Product product1 = (Product) it.next();
                objout.writeObject(product1);
            }
        }
        productList.clear();
    }

    public void read(ArrayList<Product> productList) throws IOException, ClassNotFoundException {
        try (FileInputStream fin = new FileInputStream("itemInfo.txt");
             ObjectInputStream objin = new ObjectInputStream(fin)) {
            try {
                while (true) {
                    Product p = (Product) objin.readObject();
                    productList.add(p);
                }
            } catch (EOFException e) {
                // End of file reached
            }
            System.out.println(productList);
        }
    }

    public void productNameSorting() {
        Collections.sort(productList, Comparator.comparing(Product::getProductName));

    }
    // Other Methods Using In This Program.
    private Product addNewElectronic(String productID, String productCategory) {
        Product generalInfo = getProductGeneralAttributes(input);
        System.out.print("Product Brand: ");
        String productBrand = input.next();
        int warrantyPeriodInMonths = 0;
        while (true) {
            try {
                System.out.print("Enter warranty period (in months): ");
                warrantyPeriodInMonths = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for the warranty period.");
                input.next();
            }
        }
        input.nextLine();

        return new Electronic(productID, productCategory, generalInfo.getProductName(), generalInfo.getProductQuantity(),
                generalInfo.getProductPrice(), productBrand, warrantyPeriodInMonths);
    }

    private Product addNewClothing(String productID, String productCategory) {
        Product generalInfo = getProductGeneralAttributes(input);
        System.out.print("Size: ");
        String clothingSize = input.next();
        System.out.print("Color: ");
        String clothingColor = input.next();
        return new Clothing(productID, productCategory, generalInfo.getProductName(), generalInfo.getProductQuantity(),
                generalInfo.getProductPrice(), clothingSize, clothingColor);
    }

    private Product getProductGeneralAttributes(Scanner scanner) {
        System.out.print("Enter product name: ");
        String productName = scanner.next();

        int availableItems = 0;
        while (true) {
            try {
                System.out.print("Enter available items: ");
                availableItems = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer for available items.");
                scanner.next();
            }
        }

        double price = 0.0;
        while (true) {
            try {
                System.out.print("Enter price: ");
                price = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid double for price.");
                scanner.next();
            }
        }

        return new Product(productName, availableItems, price) {
            @Override
            public void displayProductInfo() {
            }
        };
    }

    private void exitStatement() {
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to add another product: ");
        String choice = input.nextLine();
        choice = choice.toLowerCase();
        if (choice.equals("no")) {
            showMenu(this);
        }
    }

    public void addProduct(Product product) {
        if (!isValidProductID(product.getProductID())) {
            System.out.println("Invalid product ID. Product ID must start with 'A0'.");
            return;
        }
        if (isProductIDExists(product.getProductID())) {
            System.out.println("Product with ID " + product.getProductID() + " already exists.");
            return;
        }

        if (totalProduct < 50) {
            if (product instanceof Electronic) {
                Electronic electronic = (Electronic) product;
                product.setProductBrand(electronic.getElectronicBrand());
                product.setWarrantyPeriodInMonths(electronic.getElectronicWarrantyPeriodMonth());
            } else if (product instanceof Clothing) {
                Clothing clothing = (Clothing) product;
                product.setClothingSize(clothing.getClothingSize());
                product.setClothingColor(clothing.getClothingColor());
            }

            productList.add(product);
            totalProduct++;
            System.out.println(product.getProductName() + " added successfully");
        } else {
            System.out.println("Maximum number of products added.");
        }
    }

    private boolean isProductIDExists(String productID) {
        for (Product existingProduct : productList) {
            if (existingProduct.getProductID().equals(productID)) {
                return true;
            }
        }
        return false;

    }
    private boolean isValidProductID(String productID) {
        return productID.startsWith("A0");
    }
        private String getProdunctInfo(Product product) {
        if (product instanceof Electronic) {
            Electronic electronic = (Electronic) product;
            return electronic.getElectronicWarrantyPeriodMonth() + ", " + electronic.getElectronicBrand();
        } else if (product instanceof Clothing) {
            Clothing cloth = (Clothing) product;
            return cloth.getClothingColour() + ", " + cloth.getClothingSize();
        } else {
            return "";
        }
    }

    public void updateTable() {
        DefaultTableModel itemTable = (DefaultTableModel) shoppingDashboard.table.getModel();
        itemTable.setRowCount(0);
        productNameSorting();
        for (Product product : productList) {
            Object[] rowData = {
                    product.getProductID(),
                    product.getProductName(),
                    product.getProductCategory(),
                    product.getProductPrice(),
                    getProdunctInfo(product)
            };
            itemTable.addRow(rowData);
            if (product.getProductQuantity() < 3) {
                // Highlight the row with a red background
                int rowIndex = itemTable.getRowCount() - 1;
                for (int i = 0; i < itemTable.getColumnCount(); i++) {
                    itemTable.setValueAt("<html><font color='red'>" + itemTable.getValueAt(rowIndex, i) + "</font></html>", rowIndex, i);
                }
            }
        }

        // Calculate the number of empty rows needed
        int totalRows = shoppingDashboard.table.getRowCount();
        int emptyRows = Math.max(0, shoppingDashboard.table.getRowCount() - productList.size());

        // Add empty rows at the end of the table
        for (int i = 0; i < emptyRows; i++) {
            Object[] emptyRow = new Object[]{
                    "", "", "", "", ""
            };
            itemTable.addRow(emptyRow);
        }
    }

    public void sortFromCategoryElectronic(String selectedCategory) {
        ArrayList<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            if (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("ALL")) {
                filteredList.add(product);
            } else if (selectedCategory.equalsIgnoreCase(product.getProductCategory())) {
                filteredList.add(product);
            }
        }

        createTable(filteredList);
    }

    public void createTable(ArrayList<Product> list) {
        DefaultTableModel itemTable = (DefaultTableModel) shoppingDashboard.table.getModel();
        itemTable.setRowCount(0); // Clear the existing rows
        for (Product product : list) {
            Object[] rowData = {
                    product.getProductID(),  // Assuming getProductID() exists in your Product class
                    product.getProductName(),
                    product.getProductCategory(),
                    product.getProductPrice(),
                    getProdunctInfo(product)
            };
            itemTable.addRow(rowData);
        }

    }


    public void addToShoppingCart(Product selectedProduct) {
        ArrayList<Product> buyProductList = shoppingDashboard.getBuyProductList();

        if (selectedProduct.getProductQuantity() > 0) {

            if (buyProductList.contains(selectedProduct)) {
                int index = buyProductList.indexOf(selectedProduct);
                Product existingProduct = buyProductList.get(index);
                existingProduct.setProductQuantity(existingProduct.getProductQuantity() + 1);
                totalPrice = existingProduct.calculateTotalPrice(existingProduct.getProductQuantity());
                System.out.println("Product Quantity and Price Updated " + existingProduct.getProductQuantity() + " " + totalPrice);
            } else {
                selectedProduct.setProductQuantity(1);
                selectedProduct.setProductPrice(selectedProduct.getProductPrice());
                buyProductList.add(selectedProduct);
                System.out.println("New Product Added " + selectedProduct.getProductPrice());
            }

        } else {

            JOptionPane.showMessageDialog(shoppingDashboard, "Insufficient quantity available for the product.", "Error", JOptionPane.ERROR_MESSAGE);

        }
        calculateTotalPrice();
    }

    public void updateShoppingCart() {
        DefaultTableModel addToCartTable = (DefaultTableModel) shoppingDashboard.shoppingCartTable.getModel();
        addToCartTable.setRowCount(0);
        ArrayList<Product> buyProductList = shoppingDashboard.getBuyProductList();

        for (Product product : buyProductList) {
            double productTotalPrice = product.calculateTotalPrice(product.getProductQuantity());

            Object[] rowData = {
                    product.getProductID() + "\n" + product.getProductName() + "\n",
                    product.getProductQuantity(),
                    productTotalPrice
            };

            addToCartTable.addRow(rowData);
        }
    }


    public double  calculateTotalPrice() {
        grandTotal = 0;
        ArrayList<Product> buyProductList = shoppingDashboard.getBuyProductList();
        for (Product product : buyProductList) {
            grandTotal += product.calculateTotalPrice(product.getProductQuantity());
        }
        return grandTotal;
    }

    public double calculateFirstPurchaseDiscount(User currentUser) {
        firstPurchaseDiscount = 0;

        boolean isFirstPurchase = currentUser.isFirstPurchase();
        if (isFirstPurchase) {
            firstPurchaseDiscount = grandTotal * 0.1;
        }

        return firstPurchaseDiscount;
    }
    public double isCategoryDiscount(double grandTotal) {
        categoryDiscount = 0; // Initialize categoryDiscount to 0
        ArrayList<Product> buyProductList = shoppingDashboard.getBuyProductList();
        Map<String, Integer> categoryCounts = new HashMap<>();

        for (Product product : buyProductList) {
            String category = product.getProductCategory();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        boolean eligibleForDiscount = categoryCounts.values().stream().anyMatch(count -> count >= 3);

        if (eligibleForDiscount) {
            categoryDiscount = grandTotal * 0.2;
        }

        return categoryDiscount;
    }


    public double getCategoryDiscount() {
        return categoryDiscount;
    }

    public double getFirstPurchaseDiscount() {
        return firstPurchaseDiscount;
    }

    public double getGrandTotal() {
        return grandTotal;
    }
    public double calculateTotalPriceWithDiscounts(User currentUser) {
        // Calculate the grand total
        calculateTotalPrice();

        // Apply first purchase discount
        calculateFirstPurchaseDiscount(currentUser);

        // Apply category discount
        isCategoryDiscount(grandTotal);

        // Calculate the final total
        double totalPrice1 = getGrandTotal() - (getCategoryDiscount() + getFirstPurchaseDiscount());

        return totalPrice1;
    };
}












