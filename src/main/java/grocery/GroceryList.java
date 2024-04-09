package grocery;

import exceptions.emptyinput.EmptyInputException;
import git.Ui;
import exceptions.GitException;
import exceptions.nosuch.NoSuchObjectException;
import exceptions.LocalDateWrongFormatException;
import exceptions.PastExpirationDateException;
import exceptions.InvalidAmountException;
import exceptions.InvalidCostException;
import exceptions.CannotUseException;
import exceptions.commands.IncompleteParameterException;
import exceptions.commands.CommandWrongFormatException;
import grocery.location.Location;
import grocery.location.LocationList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Collections;
import java.util.stream.Collectors;


/**
 * Stores all the user's groceries in a main list.
 */
public class GroceryList {
    private List<Grocery> groceries;
    private Logger logger;

    /**
     * Constructs GroceryList.
     */
    public GroceryList() {
        groceries = new ArrayList<>();
        LoggerGroceryList.setupLogger();
        logger = Logger.getLogger(GroceryList.class.getName());
    }

    /**
     * Adds a grocery.
     *
     * @param grocery Grocery to be added.
     */
    public void addGrocery(Grocery grocery) {
        try {
            groceries.add(grocery);
            Ui.printGroceryAdded(grocery);
            assert groceries.contains(grocery) : "Grocery should be added to the list";
        } catch (NullPointerException e) {
            System.out.println("Failed to add grocery: the grocery is null.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the grocery: " + e.getMessage());
        }

        logger.log(Level.INFO, "Added " + grocery.printGrocery());
    }

    /**
     * Checks if a grocery exists.
     *
     * @param name Name of the grocery.
     * @return True if the grocery exists, false otherwise.
     */
    private boolean isGroceryExists(String name) {
        for (Grocery grocery : groceries) {
            if (grocery.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the desired grocery.
     *
     * @param name Name of the grocery.
     * @return The needed grocery.
     * @throws NoSuchObjectException If the selected grocery does not exist.
     */
    private Grocery getGrocery(String name) throws NoSuchObjectException {
        int index = -1;
        for (Grocery grocery : groceries) {
            if(grocery.getName().equalsIgnoreCase(name)) {
                index = groceries.indexOf(grocery);
                break;
            }
        }

        if (index != -1) {
            assert groceries != null : "Found grocery should not be null";
            return groceries.get(index);
        } else {
            throw new NoSuchObjectException("grocery");
        }
    }

    /**
     * Checks whether details are valid, else throw GitException accordingly.
     *
     * @param details User input.
     * @param command Command word.
     * @param parameter Parameter for the command.
     * @return String array of valid details.
     * @throws GitException Exception thrown depending on error.
     */
    private String[] checkDetails(String details, String command, String parameter) throws GitException {
        if (details.isEmpty()) {
            throw new EmptyInputException("grocery");
        }

        // Split the input into the grocery name and the detail part.
        String[] detailParts = details.split(parameter, 2);

        // Check if the grocery exists
        if (!isGroceryExists(detailParts[0].strip())) {
            throw new NoSuchObjectException("grocery");
        }   

        if (detailParts.length < 2) {
            throw new CommandWrongFormatException(command, parameter);
        }

        String attribute = detailParts[1].strip();
        if (attribute.isEmpty()) {
            throw new IncompleteParameterException(parameter);
        }

        return new String[] {detailParts[0].strip(), attribute};
    }

    /**
     * Sets the expiration date of an existing grocery.
     *
     * @param details A string containing grocery name and details.
     * @throws GitException Exception thrown depending on error.
     */
    public void editExpiration(String details) throws GitException {
        String[] expParts = checkDetails(details, "exp", "d/");
        Grocery grocery = getGrocery(expParts[0].strip());
        
        // Parse the date string to LocalDate
        LocalDate date;
        try {
            date = LocalDate.parse(expParts[1].strip(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new LocalDateWrongFormatException();
        }
    
        // Convert LocalDate back to String to match the setExpiration signature
        String dateString = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try {
            grocery.setExpiration(dateString);
        } catch (PastExpirationDateException e) {
            System.out.println(e.getMessage());
        }

        // Verification and UI feedback
        Ui.printExpSet(grocery);
    }

    /**
     * Sets the category of an existing grocery.
     *
     * @param details User input.
     * @throws GitException Exception thrown depending on error.
     */
    public void editCategory(String details) throws GitException {
        String[] catParts = checkDetails(details, "cat", "c/");
        Grocery grocery = getGrocery(catParts[0].strip());
        String newCategory = catParts[1].strip();

        grocery.setCategory(newCategory);
        Ui.printCategorySet(grocery);
    }
    /**
     * Sets the amount of an existing grocery.
     *
     * @param details User input.
     * @param use True to reduce the amount of a grocery, false to set a new amount.
     * @throws GitException Exception thrown depending on error.
     */
    public void editAmount(String details, boolean use) throws GitException {
        // Assuming the format is "amt GROCERY a/AMOUNT"
        String [] amtParts;
        if (use) {
            amtParts = checkDetails(details, "use", "a/");
        } else {
            amtParts = checkDetails(details, "amt", "a/");
        }
        Grocery grocery = getGrocery(amtParts[0].strip());
        String amountString = amtParts[1].strip();

        int amount;
        try {
            amount = Integer.parseInt(amountString);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException();
        }

        // "use" is not valid if an amount was not previously set
        if (use && grocery.getAmount() == 0) {
            throw new CannotUseException();
        } else if (use) {
            amount = Math.max(0, grocery.getAmount() - amount);
        }

        grocery.setAmount(amount);
        if (amount == 0) {
            Ui.printAmtDepleted(grocery);
        } else if (grocery.isLow()){
            Ui.lowStockAlert(grocery);
        } else {
            Ui.printAmtSet(grocery);
        }

    }

    /**
     * Updates the cost of an existing grocery.
     *
     * @param details A string containing grocery name and details.
     * @throws GitException If the input new cost is not numeric.
     */
    public void editCost(String details) throws GitException {
        String[] costParts = checkDetails(details, "cost", "\\$");
        Grocery grocery = getGrocery(costParts[0].strip());
        String price = costParts[1].strip();

        try {
            double cost = Double.parseDouble(price);
            if (cost < 0) {
                throw new InvalidCostException();
            }
            grocery.setCost(cost);
            Ui.printCostSet(grocery);
        } catch (NumberFormatException e) {
            throw new InvalidCostException();
        }
    }

    /**
     * Updates the threshold of an existing grocery.
     *
     * @param details A string containing grocery name and details.
     * @throws GitException If the input new cost is not numeric.
     */
    public void editThreshold(String details) throws GitException {
        String [] amtParts = checkDetails(details, "th", "a/");
        Grocery grocery = getGrocery(amtParts[0].strip());
        String thresholdString = amtParts[1].strip();

        try {
            int threshold = Integer.parseInt(thresholdString);
            grocery.setThreshold(threshold);
            Ui.printThresholdSet(grocery);
        } catch (NumberFormatException e) {
            throw new InvalidAmountException();
        }
    }

    /**
     * Updates the location of an existing grocery.
     *
     * @param details A string containing grocery name and details.
     * @throws GitException Thrown if given location name is empty.
     */
    public void editLocation(String details) throws GitException {
        String[] locationParts = checkDetails(details, "store", "l/");
        Grocery grocery = getGrocery(locationParts[0].strip());
        String name = locationParts[1].strip();

        Location location;
        try {
            location = LocationList.findLocation(name);
        } catch (NoSuchObjectException e) {
            LocationList.addLocation(name);
            location = LocationList.findLocation(name);
        }

        grocery.setLocation(location);
        location.addGrocery(grocery);
        Ui.printLocationSet(grocery);
    }

    /**
     * Searches for groceries containing the given keyword.
     */
    public void findGroceries(String key) throws EmptyInputException {
        if (key.isEmpty()) {
            throw new EmptyInputException("keyword");
        }

        List<Grocery> relevantGroceries = new ArrayList<>();
        for (Grocery grocery : groceries) {
            if(grocery.getName().toLowerCase().contains(key.toLowerCase())) {
                relevantGroceries.add(grocery);
            }
        }

        Ui.printGroceriesFound(relevantGroceries, key);
    }

    /**
     * Updates the rating and review of an existing grocery.
     * 
     * @param details A string containing grocery name and details.
     * @throws GitException If the input grocery is empty.
     */
    public void editRatingAndReview(String details) throws GitException {
        if (details.isEmpty()) {
            throw new EmptyInputException("grocery");
        }
        Grocery grocery = getGrocery(details);
        Ui.promptForRatingAndReview(grocery);
    }

    /**
     * Lists all the user's groceries.
     */
    public void listGroceries() {
        int size = groceries.size();
        if (size == 0) {
            Ui.printNoGrocery();
        } else {
            Ui.printGroceryList(groceries);
        }
    }

    /**
     * Lists all the groceries that are low in stock.
     */
    public void listLowStocks() {
        int size = groceries.size();
        if (size == 0) {
            Ui.printNoGrocery();
        } else {
            Ui.printLowStocks(groceries);
        }
    }

    /**
     * Sorts the groceries by expiration date.
     */
    public void sortByExpiration() {
        Collections.sort(groceries, (g1, g2) -> g1.getExpiration().compareTo(g2.getExpiration()));
        Ui.printGroceryList(groceries);
    }

    /**
     * Gets a list of groceries expiring in the next 3 days.
     *
     * @return A list of groceries expiring within the next 3 days.
     */
    public List<Grocery> getGroceriesExpiringInNext3Days() {
        LocalDate today = LocalDate.now();
        LocalDate threeDaysFromNow = today.plusDays(3);

        return groceries.stream()
                .filter(grocery -> {
                    LocalDate expirationDate = grocery.getExpiration();
                    return !expirationDate.isBefore(today) && !expirationDate.isAfter(threeDaysFromNow);
                })
                .collect(Collectors.toList());
    }

    /**
     * display the groceries that are expiring in the next 3 days.
     */
    public void displayGroceriesExpiringInNext3Days() {
        List<Grocery> groceriesExpiringInNext3Days = getGroceriesExpiringInNext3Days();
        if (groceriesExpiringInNext3Days.isEmpty()) {
            Ui.printNoGrocery();
        } else {
            System.out.println("Here are the groceries expiring in the next 3 days:");
            Ui.printGroceryList(groceriesExpiringInNext3Days);
        }
    }

    /**
     * Sorts the groceries by descending cost.
     */
    public void sortByCost() {
        int size = groceries.size();
        if (size == 0) {
            Ui.printNoGrocery();
        } else {
            List<Grocery> groceriesByDate = groceries;
            groceriesByDate.sort((g1, g2) -> Double.compare(g1.getCost(), g2.getCost()));
            Collections.reverse(groceriesByDate);
            Ui.printGroceryList(groceriesByDate);
        }
    }
    /**
     * Sorts the groceries by category.
     */
    public void sortByCategory(){
        Collections.sort(groceries, Comparator.comparing(Grocery::getCategory));
        Ui.printGroceryList(groceries);
    }
    /**
     * Removes a grocery.
     *
     * @param name Grocery name from user input.
     * @throws GitException If grocery is empty.
     */
    public void removeGrocery(String name) throws GitException {
        if (name.isEmpty()) {
            throw new EmptyInputException("grocery");
        }

        // Assuming the format is "del GROCERY"
        Grocery grocery = getGrocery(name);
        groceries.remove(grocery);
        Location location = grocery.getLocation();
        location.removeGrocery(grocery);

        Ui.printGroceryRemoved(grocery, groceries);
    }
}
