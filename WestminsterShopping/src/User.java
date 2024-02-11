import java.util.HashMap;
import java.util.Map;
class User {
    private String username;
    private char[] password;
    private boolean firstPurchase;
    private Map<String, Integer> productCategoryCounts;

    public User(String username, char[] password, boolean firstPurchase) {
        this.username = username;
        this.password = password.clone();
        this.firstPurchase = firstPurchase;
        this.productCategoryCounts = new HashMap<>();
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(char[] enteredPassword) {
        return java.util.Arrays.equals(password, enteredPassword);
    }

    public boolean isFirstPurchase() {
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        this.firstPurchase = firstPurchase;
    }
}

