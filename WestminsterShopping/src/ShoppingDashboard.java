import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ShoppingDashboard extends JFrame {
    public DefaultTableModel itemTable;
    public JTable table;
    JTable shoppingCartTable;
    public JComboBox<String> categoryDropdown;
    private static ArrayList<User> userArrayList = new ArrayList<>();
    public String categorySelected;
    private int selectedRow;
    Product selectedProduct;
    private ArrayList<Product> buyProductList = new ArrayList<>();

    public ArrayList<Product> getBuyProductList() {
        return buyProductList;
    }

    private WestminsterShoppingManagerOther manager;
    JLabel productIDLabel;
    JLabel categoryLabel;
    JLabel nameLabel;
    JLabel sizeLabel;
    JLabel colourLabel;
    JLabel itemsAvailableLabel;
    JLabel totalPriceLabel;
    JLabel firstPurchaseLabel;
    JLabel threeItemDiscountLabel;
    JLabel finalPriceLabel;

    private User currentUser;


    public ShoppingDashboard(WestminsterShoppingManagerOther manager) {
        this.manager = manager;
        setTitle("Westminster Shopping Center");

        // Shopping Card Button Panel
        JPanel shoppingCartButtonPanel = new JPanel();
        JButton shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        shoppingCartButtonPanel.add(shoppingCartButton);
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shoppingCartFrame(manager);
                manager.updateShoppingCart();
            }
        });

        //Title Menu And Categorised
        JPanel titleAndDropDownPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel titleLabel = new JLabel("Select Category");
        String[] dropdownItems = {"ALL", "CLOTH", "ELECTRONIC"};
        categoryDropdown = new JComboBox<>(dropdownItems);
        categoryDropdown.setPreferredSize(new Dimension(200, 30));
        categoryDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categorySelected = (String) categoryDropdown.getSelectedItem();
                manager.sortFromCategoryElectronic(categorySelected);
            }
        });
        titleAndDropDownPanel.add(titleLabel);
        titleAndDropDownPanel.add(categoryDropdown);
        // Title set Layout to GridBag
        JPanel titlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        titlePanel.add(titleAndDropDownPanel, gbc);

        //Set Upper Panel
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setPreferredSize(new Dimension(1000, 150));
        upperPanel.add(titlePanel, BorderLayout.CENTER);
        upperPanel.add(shoppingCartButtonPanel, BorderLayout.EAST);

        //Create Table Model to Show Information
        Object[][] data = {

        };
        String[] columnName = {"Product ID", "Name", "Category", "Price", "Info"};
        itemTable = new DefaultTableModel(data, columnName);
        table = new JTable(itemTable) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells read-only
            }
        };
        JScrollPane scrollPane = new JScrollPane(table);
        table.setPreferredScrollableViewportSize(new Dimension(950, 300));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                ArrayList<Product> productList = manager.getProductList();
                if (selectedRow != -1) {
                    selectedProduct = productList.get(selectedRow);
                    System.out.println("Product is succefuly clicked" + selectedProduct.getProductID());
                    updateInformationPanel(selectedProduct);
                }

            }
        });
        //Create Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.add(scrollPane);

        //Create Description Panel
        JPanel descriptionPanel = new JPanel();
        JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        informationPanel.setLayout(new GridLayout(7, 1, 50, 10));
        JLabel selectedProduct1 = new JLabel("Selected Product-Details");
        selectedProduct1.setFont(new Font("Serif", Font.PLAIN, 18));
        productIDLabel = new JLabel("Product ID:");
        categoryLabel = new JLabel("Category:");
        nameLabel = new JLabel("Name:");
        sizeLabel = new JLabel("Size:");
        colourLabel = new JLabel("Colour:");
        itemsAvailableLabel = new JLabel("Items Available:");

        informationPanel.add(selectedProduct1);
        informationPanel.add(productIDLabel);
        informationPanel.add(categoryLabel);
        informationPanel.add(nameLabel);
        informationPanel.add(itemsAvailableLabel);

        informationPanel.add(sizeLabel);
        informationPanel.add(colourLabel);


        descriptionPanel.add(informationPanel);
        // Create ShowingDetail Area Panel
        JPanel showingDetailAreaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        showingDetailAreaPanel.add(descriptionPanel);

        //Add to Shopping Cart Panel
        JPanel shoopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addCartBtn = new JButton("Add To Shopping Cart");
        addCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.addToShoppingCart(selectedProduct);
                //updateTotalDisplay();
            }
        });
        shoopPanel.add(addCartBtn);

        //Create Add to ShoppingCartPanel
        JPanel addToShoppingCart = new JPanel(new BorderLayout());
        addToShoppingCart.add(shoopPanel, BorderLayout.SOUTH);

        //Create Under Panel
        JPanel underPanel = new JPanel();
        underPanel.setLayout(new GridLayout(1, 2));
        underPanel.setPreferredSize(new Dimension(500, 250));
        underPanel.add(showingDetailAreaPanel);
        underPanel.add(addToShoppingCart);

        //Set Border Layout
        add(upperPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(underPanel, BorderLayout.SOUTH);

    }

    public void display() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        manager.updateTable();
        setVisible(true);
    }

    private void updateInformationPanel(Product selectedProduct) {
        productIDLabel.setText("Product ID: " + selectedProduct.getProductID());
        categoryLabel.setText("Product Category: " + selectedProduct.getProductCategory());
        nameLabel.setText("Product Name: " + selectedProduct.getProductName());
        itemsAvailableLabel.setText("Product Quantity: " + selectedProduct.getProductQuantity());
        if (selectedProduct instanceof Clothing) {
            Clothing clothing = (Clothing) selectedProduct;
            sizeLabel.setText("Size: " + clothing.getClothingSize());
            colourLabel.setText("Colour: " + clothing.getClothingColor());
        } else if (selectedProduct instanceof Electronic) {
            Electronic electronic = (Electronic) selectedProduct;
            sizeLabel.setText("Warranty Period: " + electronic.getElectronicWarrantyPeriodMonth() + " months");
            colourLabel.setText("Brand: " + electronic.getElectronicBrand());
        } else {
            // Default case, hide unnecessary labels
            sizeLabel.setText("");
            colourLabel.setText("");
        }
    }


    public void shoppingCartFrame(WestminsterShoppingManagerOther manager) {
        JFrame newFrame = new JFrame();
        newFrame.setTitle("ShoppingCart");
        newFrame.setSize(800, 800);
        newFrame.setLocationRelativeTo(null);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setLayout(new BorderLayout());
        Panel mainBodyPanel = new Panel();
        Panel totalDispalyPanel = new Panel();
        totalDispalyPanel.setLayout(new BorderLayout());
        totalDispalyPanel.setPreferredSize(new Dimension(800, 300));
        // Add Table Panel
        Object[][] data = {

        };
        String[] columnName = {"Product", "Quantity", "Price"};
        DefaultTableModel shoppingCartTableModel = new DefaultTableModel(data, columnName);
        shoppingCartTable = new JTable(shoppingCartTableModel);
        JScrollPane scrollPane = new JScrollPane(shoppingCartTable);
        shoppingCartTable.setPreferredScrollableViewportSize(new Dimension(700, 400));
        mainBodyPanel.add(scrollPane);

        //Add To Total Panel
        Panel totalPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));

        Panel descriptionPanel = new Panel();
        descriptionPanel.setLayout(new GridLayout(4, 2, 10, 50));
        totalPriceLabel = new JLabel("Total Price: ");
        firstPurchaseLabel = new JLabel("First Purchase: ");
        threeItemDiscountLabel = new JLabel("Three Item Category Discount: ");
        finalPriceLabel = new JLabel("Final Price: ");
        descriptionPanel.add(totalPriceLabel);
        totalPriceLabel.setText("Total Price: £" + String.format("%.2f", manager.calculateTotalPrice()));
        descriptionPanel.add(firstPurchaseLabel);
        User currentUser =(User) getCurrentUser(); // Obtain the current user object
        firstPurchaseLabel.setText("First purchase: £" + String.format("%.2f", manager. calculateFirstPurchaseDiscount(currentUser)));
        descriptionPanel.add(threeItemDiscountLabel);
        threeItemDiscountLabel.setText("Category Discount: £" + String.format("%.2f", manager.isCategoryDiscount(manager.grandTotal)));

        descriptionPanel.add(finalPriceLabel);
        finalPriceLabel.setText("Total Price: £" + String.format("%.2f",manager.calculateTotalPriceWithDiscounts(currentUser)));
        totalPanel.add(descriptionPanel);
        totalDispalyPanel.add(totalPanel, BorderLayout.CENTER);
        newFrame.add(mainBodyPanel, BorderLayout.CENTER);
        newFrame.add(totalDispalyPanel, BorderLayout.SOUTH);
        newFrame.setVisible(true);
    }

    //User Login Part
    public void userLogin(WestminsterShoppingManagerOther manager) {
        JFrame newuserlogin = new JFrame();
        newuserlogin.setSize(400, 200);
        newuserlogin.setLayout(new BorderLayout());
        newuserlogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newuserlogin.setLocationRelativeTo(null);
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleText = new JLabel("Login");
        upperPanel.add(titleText);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 2));
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(1);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(1);
        textPanel.add(usernameLabel);
        textPanel.add(usernameField);
        textPanel.add(passwordLabel);
        textPanel.add(passwordField);

        JPanel underPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginbutton = new JButton("Login");
        loginbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = usernameField.getText();
                char[] enteredPassword = passwordField.getPassword();

                // Check if the user is in the list
                User user = findUser(enteredUsername, enteredPassword);

                if (user != null) {
                    if (user.isFirstPurchase()) {
                        JOptionPane.showMessageDialog(null, "First purchase discount applied!");
                        user.setFirstPurchase(false);

                    } else {
                        JOptionPane.showMessageDialog(null, "Welcome back!");
                    }
                    newuserlogin.dispose();
                    currentUser = user;
                    display();
                } else {
                    userArrayList.add(new User(enteredUsername, enteredPassword, true));
                    JOptionPane.showMessageDialog(null, "New user added with first purchase discount");
                    currentUser = userArrayList.get(userArrayList.size() - 1);
                    newuserlogin.dispose();
                    display();
                }
            }
        });

        underPanel.add(loginbutton);
        newuserlogin.add(upperPanel, BorderLayout.NORTH);
        newuserlogin.add(textPanel, BorderLayout.CENTER);
        newuserlogin.add(underPanel, BorderLayout.SOUTH);
        newuserlogin.setVisible(true);

    }

    private static User findUser(String username, char[] password) {
        for (User user : userArrayList) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }


    public User getCurrentUser() {
        return currentUser;
    }
}



    //



