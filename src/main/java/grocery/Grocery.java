package grocery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import exceptions.PastExpirationDateException;
import grocery.location.Location;


/**
 * Represents a grocery.
 */
public class Grocery {
    private String name;
    private int amount;
    private int threshold;
    private LocalDate expiration;
    private String category;
    private String unit;
    private double cost;
    private Location location;
    private int rating;
    private String review;
    private String remark;
    private boolean isSetCost;
    private boolean isSetAmount;



    /**
     * Constructs a Grocery.
     *
     * @param name Name.
     * @param amount Measurement of grocery.
     * @param expiration When grocery expires.
     * @param category Category of grocery.
     * @param location Location of where the grocery is stored.
     */
    public Grocery(String name, int amount, int threshold,
        LocalDate expiration, String category, double cost, Location location) {
        this.name = name;
        this.amount = amount;
        this.threshold = threshold;
        this.expiration = expiration;
        this.category = category;
        setUnit(category);
        this.cost = cost;
        this.isSetCost = true;
        this.isSetAmount = true;
        this.location = location;
        this.rating = 0;
        this.review = "";
        this.remark = "";
    }

    /**
     * Basic constructor for Grocery.
     * 
     * @param name Name.
     */
    public Grocery(String name) {
        this.name = name;
        this.amount = 0;
        this.expiration = null;
        this.category = "";
        this.cost = 0;
        this.isSetCost = false;
        this.isSetAmount = false;
        this.location = null;
        this.rating = 0;
        this.review = "";
        this.remark = "";
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDate getExpiration() {
        return expiration;
    }
    
    public String getCategory() {
        return this.category;
    }

    public double getCost() {
        return this.cost;
    }

    public Location getLocation() {
        return this.location;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public int getRating() {
        return this.rating;
    }

    public String getReview() {
        return this.review;
    }

    public String getRemark() {
        return remark;
    }

    public boolean getIsSetCost() {
        return isSetCost;
    }

    public boolean getIsSetAmount() {
        return isSetAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
        setUnit(category);
    }

    public void setAmount(int amount) {
        assert amount >= 0 : "Amount entered is invalid!";
        this.amount = amount;
        this.isSetAmount = true;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getUnit() {
        return unit;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Checks if the grocery is low in stock.
     *
     * @return True if current amount is lesser than threshold, or amount is 0.
     */
    public boolean isLow() {
        if (this.amount == 0) {
            return true;
        } else {
            return this.amount < this.threshold;
        }
    }

    /**
     * Set unit of the grocery based on its category.
     *
     * @param category Category of the grocery.
     */
    public void setUnit(String category) {
        switch (category.toLowerCase()){
        case "fruit":
            this.unit = "pieces";
            break;
        case "vegetable":
        case "meat":
            this.unit = "grams";
            break;
        case "beverage":
            this.unit = "ml";
            break;
        default:
            this.unit = "units";
            break;
        }

        if (this.amount == 0) {
            this.unit = "";
        }
    }

    /**
     * Formats the expiration date from type string to local date.
     *
     * @param expiration The expiration date of the grocery.
     * @throws PastExpirationDateException 
     */
    public void setExpiration(String expiration) throws PastExpirationDateException {
        assert !(expiration.isEmpty()) : "Expiration date entered is invalid!";
        if (expiration == null||expiration.isEmpty()) {
            throw new IllegalArgumentException("Expiration date entered is invalid!");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expirationDate = LocalDate.parse(expiration, formatter);

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new PastExpirationDateException();
        }

        this.expiration = LocalDate.parse(expiration, formatter);
    }

    /**
     * Sets the cost of the grocery.
     *
     * @param cost The cost of the grocery as a double.
     */
    public void setCost(double cost) {
        assert cost >= 0 : "Cost entered is invalid!"; // Ensure that the cost is non-negative.
        this.cost = cost;
        this.isSetCost = true;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Returns details that are set in the grocery.
     *
     * @return String representation of the Grocery.
     */
    public String printGrocery() {
        assert !(this.name.isEmpty()) : "Grocery does not exist!!";

        String categoryString = "";
        if (!category.isBlank()) {
            categoryString = " (" + category + ")";
        }

        String locationString = "";
        if (this.location != null) {
            locationString = ", location: " + this.location.getName();
        }

        String amountString = "";
        if (amount != 0) {
            amountString = ", amount: " + amount;
        } else if (isSetAmount) {
            amountString = ", amount: 0";
        }

        String unitString = "";
        if (unit != null) {
            unitString = unit;
        }

        String exp = "";
        if (expiration != null) {
            exp = ", expiration: " + expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        String price = "";
        if (cost != 0) {
            price = ", cost: $" + String.format("%.2f", cost);
        } else if (isSetCost) {
            price = ", cost: $0.00";
        }

        String remarkString = "";
        if (!remark.isEmpty()) {
            remarkString = ", remark: " + remark;
        }

        return this.name + categoryString + amountString + unitString + exp + price +
                locationString + remarkString;

    }

    /**
     * Returns the name, amount, threshold, expiration date, category, cost and location of the grocery for saving.
     *
     * @return String representation of the Grocery.
     */
    public String toSaveFormat() {
        assert !(this.name.isEmpty()) : "Grocery does not exist!!";

        String amountString;
        if (amount != 0) {
            amountString = "| " + amount + " ";
        } else {
            amountString = "| null ";
        }

        String locationString;
        if (this.location != null) {
            locationString = "| " + this.location.getName() + " ";
        } else {
            locationString = "| null ";
        }

        String thresholdString;
        if (this.threshold != 0){
            thresholdString = "| " + threshold + " ";
        } else {
            thresholdString = "| null ";
        }

        String exp;
        if (expiration != null) {
            exp = "| " + expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " ";
        } else {
            exp = "| null ";
        }

        String categoryString;
        if (category != null) {
            categoryString = "| " + this.category + " ";
        } else {
            categoryString = "| null ";
        }

        String price;
        if (cost != 0) {
            price = "| " + String.format("%.2f", cost) + " ";
        } else {
            price = "| null ";
        }

        String remarkString;
        if (remark != null) {
            remarkString = "| " + remark + " ";
        } else {
            remarkString = "| null ";
        }
        return this.name + " " + amountString + thresholdString + exp + categoryString + price + locationString;

    }
}

