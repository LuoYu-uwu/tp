package grocery;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import exceptions.PastExpirationDateException;


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
    private String location;


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
                   LocalDate expiration, String category, double cost, String location) {
        this.name = name;
        this.amount = amount;
        this.threshold = threshold;
        this.expiration = expiration;
        this.category = category;
        setUnit(category);
        this.cost = cost;
        this.location = location;
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

    public double getCost() {
        return this.cost;
    }

    public String getLocation() {
        return this.location;
    }

    public int getThreshold() {
        return this.threshold;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        assert amount >= 0 : "Amount entered is invalid!";
        this.amount = amount;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String getUnit() {
        return unit;
    }

    /**
     * Checks if the grocery is low in stock.
     *
     * @return True if current amount is lesser than threshold.
     */
    public boolean isLow() {
        return this.amount < this.threshold;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expirationDate = LocalDate.parse(expiration, formatter);

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new PastExpirationDateException();
        }

        this.expiration = LocalDate.parse(expiration, formatter);
    }

    /**
     * Converts the cost from type String to double and store it.
     *
     * @param cost The cost of grocery in String type.
     */
    public void setCost(String cost) {
        assert !(cost.isEmpty()) : "Cost entered is invalid!";
        this.cost = Double.parseDouble(cost);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the name, amount, expiration date and cost of the grocery.
     *
     * @return String representation of the Grocery.
     */
    public String printGrocery() {
        assert !(this.name.isEmpty()) : "Grocery does not exist!!";

        String locationString = ", location: " + location;

        String amountString;
        if (amount != 0) {
            amountString = ", amount: " + amount + " ";
        } else {
            amountString = ", amount not set";
        }

        String exp;
        if (expiration != null) {
            exp = ", expiration: " + expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            exp = ", expiration date not set";
        }

        String price;
        if (cost != 0) {
            price = ", cost: $" + String.format("%.2f", cost);
        } else {
            price = ", cost not set";
        }

        return this.name + " (" + this.category + ")" + amountString + this.unit + exp + price + locationString;

    }
}

