package git;

import exceptions.DuplicateException;
import exceptions.GitException;
import exceptions.invalidinput.InvalidCostException;
import exceptions.PastExpirationDateException;
import exceptions.nosuch.NoSuchObjectException;
import food.Food;
import grocery.Grocery;
import grocery.GroceryList;
import grocery.location.Location;
import grocery.location.LocationList;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.HashSet;
import java.util.Scanner;

public class GroceryUi {
    // ATTRIBUTES
    public static final String DIVIDER = "- - - - -";
    private static GroceryUi singleGroceryUi = null;
    private static Scanner in;

    // METHODS
    /**
     * Constructs Ui and initialises Scanner to read input.
     */
    public GroceryUi() {
        in = new Scanner(System.in);
    }

    /**
     * Returns the single instance of Ui.
     */
    public static GroceryUi getInstance() {
        if (singleGroceryUi == null) {
            singleGroceryUi = new GroceryUi();
        }
        return singleGroceryUi;
    }

    public static void printLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Prompts user for additional details when adding a grocery.
     *
     * @param grocery The grocery to be added.
     */
    //@@author wallywallywally
    public void promptAddMenu(Grocery grocery) {
        printAddMenu(grocery.getName());
        String rawInput = in.nextLine().replaceAll(" ", "");

        // Help is always shown first
        if (rawInput.contains("8")) {
            System.out.println("Displaying help:");
            singleGroceryUi.displayAddHelp();
            printLine();
            rawInput = rawInput.replaceAll("8","");
        }

        // Remove duplicates
        StringBuilder addNums = new StringBuilder();
        for (char choice : rawInput.toCharArray()) {
            if (!addNums.toString().contains(String.valueOf(choice))) {
                addNums.append(choice);
            }
        }

        processAddMenu(grocery, addNums.toString());
    }

    /**
     * Prompts user for multiple grocery names.
     *
     * @return the list of the groceries.
     * @throws DuplicateException 
     */
    public Grocery[] promptAddMultipleMenu() throws DuplicateException {
        System.out.println("\nHow many groceries would you like to add?");
        int num = 0;
        while (true) {
            try {
                num = Integer.parseInt(in.nextLine().trim());
                if (num <= 0) {
                    System.out.println("\nPlease enter a positive number.");
                } else {
                    break; // Break loop if input is a positive integer
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
            }
        }

        Grocery[] groceries = new Grocery[num];
        Storage storage = new Storage();
        GroceryList groceryList = new GroceryList();
        groceryList = storage.loadGroceryFile();
        HashSet<String> existingGroceryNames = new HashSet<>();

        for (int i = 0; i < num; i++) {
            String name;
            while (true) {
                System.out.println("\nAdding item " + (i + 1) + " of " + num);
                System.out.println("\nPlease enter the name of the grocery:");
                name = in.nextLine().trim();
                if (groceryList.isGroceryExists(name)){
                    throw new DuplicateException("grocery", name);
                }
                if (name.isEmpty()) {
                    System.out.println("\nInvalid input. Please enter a non-empty grocery name.");
                } else if (existingGroceryNames.contains(name)) {
                    System.out.println("\nThis grocery has already been added. Please enter a different grocery name.");
                } else {
                    existingGroceryNames.add(name);
                    break;
                }
            }

            Grocery grocery = new Grocery(name);

            while (true) {
                System.out.println("\nDo you want to include additional details for " + grocery.getName() + "? (Y/N)");
                String choice = in.nextLine().trim().toUpperCase();
                if (choice.equals("Y")) {
                    promptAddMenu(grocery); // Assuming you have this method implemented elsewhere
                    break;
                } else if (choice.equals("N")) {
                    System.out.println("\nNo additional details will be added for " + grocery.getName());
                    break;
                } else {
                    System.out.println("\nInvalid input. Please enter 'Y' for yes or 'N' for no.");
                }
            }

            groceries[i] = grocery;
        }

        return groceries;
    }

    /**
     * Prints output when a location is added to LocationList.
     *
     * @param name Location name.
     */
    public static void printLocationAdded(String name) {
        System.out.println("New location added: " + name);
    }

    /**
     * Prints output when a location is removed from LocationList.
     *
     * @param name Location name.
     */
    public static void printLocationRemoved(String name) {
        System.out.println("Location: " + name + " has been removed from tracking!");
    }

    /**
     * Prints all locations.
     *
     * @param locations List of locations.
     */
    public static void printLocationList(List<Location> locations) {
        if (locations.isEmpty()) {
            System.out.println("No locations are currently being tracked!");
        } else {
            System.out.println("Here's all the locations you are tracking:");
            for (Location loc : locations) {
                System.out.println(" - " + loc.getName());
            }
        }
    }

    /**
     * Prints the new location set for the selected grocery.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printLocationSet(Grocery grocery) {
        assert !grocery.getLocation().getName().isEmpty() : "Grocery location should not be empty";
        System.out.println(grocery.getName() + " stored in " + grocery.getLocation().getName());
    }

    /**
     * Prints the all the grocery found containing the keyword.
     *
     * @param groceries The list of groceries.
     * @param key The keyword to search for.
     */
    public static void printGroceriesFound(List<Grocery> groceries, String key) {
        if (groceries.isEmpty()) {
            System.out.println("No groceries contain: " + key);
        } else {
            System.out.println("Here are the groceries containing: " + key);
            for (Grocery grocery: groceries) {
                System.out.println(" - " + grocery.printGrocery());
            }
        }
    }

    /**
     * Prints output after a grocery's amount is set to 0.
     *
     * @param grocery The grocery that is depleted.
     */
    public static void printAmtDepleted(Grocery grocery) {
        System.out.println(grocery.getName() + " is now out of stock!");
    }

    /**
     * Display help message for the user when adding grocery.
     */
    public void displayAddHelp() {
        System.out.println(
                "Here are some details you can include when adding a grocery:\n" +
                        "1. Category - what type of grocery is it.\n" +
                        "2. Amount - how much of the grocery is stored.\n" +
                        "3. Location - where the grocery is stored.\n" +
                        "4. Expiration Date - when the grocery expires.\n" +
                        "5. Cost - how much did the grocery cost.\n" +
                        "6. Threshold Amount - the minimum amount of the grocery that sets reminder.\n" +
                        "7. Remark - extra information about the grocery.\n");
    }

    /**
     * Prompts the user to enter the location of the grocery.
     * If the location is new, it is automatically created
     * If left blank, location is set to null.
     *
     * @return Location of selected grocery.
     */
    public Location promptForLocation() {
        System.out.println("Please enter the location (e.g. freezer first compartment)");
        String name = in.nextLine().strip();

        while (name.isBlank()) {
            System.out.println("The location cannot be empty!");
            name = in.nextLine().strip();
        }

        Location location;
        try {
            location = LocationList.findLocation(name);
        } catch (NoSuchObjectException e1) {
            try {
                LocationList.addLocation(name);
                location = LocationList.findLocation(name);
            } catch (GitException e2) {
                location = null;
            }
        }

        return location;
    }

    //@@author wallywallywally

    //@@author LuoYu-uwu
    /**
     * Prompts user for category.
     */
    public String promptForCategory(){
        System.out.println("Please enter the category (e.g. fruit):");
        return in.nextLine().trim();
    }

    /**
     * Prompts user for amount.
     */
    public int promptForAmount(){
        System.out.println("Please enter the amount (e.g. 3):");
        try {
            return Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e){
            System.out.println("Please enter a valid integer for the amount (e.g. 3)!");
            return promptForAmount();
        }
    }

    /**
     * Prompts the user to enter the cost of the grocery for at most 5 times.
     * If invalid value is entered for the 6th time, auto set the cost to 0.
     *
     * @return the cost to be set for the grocery.
     */
    public double promptForCost() {
        System.out.println("Please enter the cost (e.g., $1.20) or nil:");
        double cost = 0;
        for (int i = 0; i < 5; i++) {
            String price = in.nextLine().trim();
            if (price.equals("nil")) {
                break;
            }
            try {
                cost = convertCost(price);
                if (cost >= 0 ){
                    break;
                } else {
                    cost = 0;
                    System.out.println("Cost entered is invalid!");
                    System.out.println("Please enter the cost (e.g., $1.20) or nil:");
                }
            } catch (GitException e) {
                System.out.println(e.getMessage());
            }
        }
        return cost;
    }

    /**
     * Removes dollar sign from input cost and convert to double.
     *
     * @param price Input cost entered by user.
     * @return Cost in desired format.
     * @throws GitException If there is no Dollar sign or cost entered is not numeric.
     */
    private double convertCost(String price) throws GitException{
        if(price.contains("$")) {
            String formattedPrice = price.replace("$", "");
            try {
                return Double.parseDouble(formattedPrice);
            } catch (NumberFormatException nfe) {
                throw new InvalidCostException();
            }
        } else {
            throw new InvalidCostException();
        }
    }

    /**
     * Prompts user for threshold amount.
     */
    public int promptForThreshold(){
        System.out.println("Please enter the threshold amount (e.g. 3) or nil:");
        int threshold = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            if (input.equals("nil")) {
                break;
            }
            try {
                threshold = Integer.parseInt(input);
                if (threshold >= 0 ){
                    break;
                } else {
                    threshold = 0;
                    System.out.println("Amount entered is invalid!");
                    System.out.println("Please enter the threshold amount (e.g. 3) or nil:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Amount entered is invalid!");
                System.out.println("Please enter the threshold amount (e.g. 3) or nil:");
            }
        }
        return threshold;
    }

    /**
     * Prints all groceries with amount less than threshold set.
     *
     * @param groceries An array list of groceries.
     */
    public static void printLowStocks(List<Grocery> groceries) {
        int size = groceries.size();
        if (size == 0) {
            System.out.println("There are no items low in stock :)");
        } else {
            System.out.println("Time to top up these groceries!");
            for (Grocery grocery : groceries) {
                System.out.println(" - " + grocery.getName()
                        + " only left: " + grocery.getAmount());
            }
        }
    }

    public static void lowStockAlert(Grocery grocery) {
        System.out.println(grocery.getName() + " is low in stock!");
        System.out.println("There's only " +grocery.getAmount() + " left");
    }

    /**
     * Prints output when the selected grocery is removed.
     *
     * @param grocery The grocery that is removed.
     * @param groceries The array list of groceries.
     */
    public static void printGroceryRemoved(Grocery grocery, List<Grocery> groceries) {
        assert grocery!=null : "Grocery does not exist";
        System.out.println("This grocery is removed:");
        System.out.println(grocery.printGrocery());
        System.out.println("You now have " + groceries.size() + " groceries left");
    }

    /**
     * Prints the new threshold set for the selected grocery.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printThresholdSet(Grocery grocery) {
        String unit;
        if (grocery.getUnit() == null) {
            unit = "";
        } else {
            unit = " " + grocery.getUnit();
        }
        System.out.println(grocery.getName() + "'s threshold is now " +
                grocery.getThreshold() + unit);
    }

    /**
     * Prints output after editing the selected grocery's cost.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printCostSet(Grocery grocery) {
        assert (grocery.getCost()!= 0): "grocery cost should not be empty";
        double cost = grocery.getCost();
        String price = "$" + String.format("%.2f", cost);
        System.out.println(grocery.getName() + " is now " + price);
    }
    //@@author LuoYu-uwu

    //@@author lsiyi
    /**
     * Prints the additional details menu.
     */
    public void printAddMenu(String name) {
        System.out.println("Before adding " + name + ", do you want to include the following details?");
        System.out.println("1. Category");
        System.out.println("2. Amount");
        System.out.println("3. Location");
        System.out.println("4. Expiration Date");
        System.out.println("5. Cost");
        System.out.println("6. Threshold Amount");
        System.out.println("7. Remark");
        System.out.println("8. Help");
        System.out.println("Please enter the numbers of the details you want to include:");
        System.out.println("You may enter multiple numbers. (e.g. 1234)");
        System.out.println("To skip this step, do not enter any values.");
    }

    /**
     * Processes the additional details of the grocery to be added.
     *
     * @param grocery The grocery to be added.
     * @param addNums String containing the numbers of the additional details to be added.
     */
    public void processAddMenu (Grocery grocery, String addNums) {
        for (char choice : addNums.toCharArray()) {
            switch (choice) {
            case '1':
                System.out.println("Including Category");
                String category = singleGroceryUi.promptForCategory();
                grocery.setCategory(category);
                break;

            case '2':
                System.out.println("Including Amount");
                int amount = singleGroceryUi.promptForAmount();
                grocery.setAmount(amount);
                break;

            case '3':
                System.out.println("Including Location");
                Location location = singleGroceryUi.promptForLocation();
                grocery.setLocation(location);
                if (location != null) {
                    location.addGrocery(grocery);
                }
                break;

            case '4':
                System.out.println("Including Expiration Date");
                String expiration = singleGroceryUi.promptForExpiration();
                try {
                    grocery.setExpiration(expiration);
                } catch (PastExpirationDateException e) {
                    e.printStackTrace();
                }
                break;

            case '5':
                System.out.println("Including Cost");
                Double cost = singleGroceryUi.promptForCost();
                grocery.setCost(cost);
                break;

            case '6':
                System.out.println("Including Threshold Amount");
                int threshold = singleGroceryUi.promptForThreshold();
                grocery.setThreshold(threshold);
                break;

            case '7':
                System.out.println("Including Remark");
                String remark = singleGroceryUi.promptForRemark();
                grocery.setRemark(remark);
                break;

            default:
                System.out.println("Invalid choice: " + choice);
                break;
            }

            printLine();
        }
    }

    /**
     * Prompts user for expiration date.
     * Validates the input date for correct format and future dates.
     *
     * @return Formatted expiration date in the format YYYY-MM-DD.
     */
    public String promptForExpiration() {
        LocalDate expirationDate = null;
        while (expirationDate == null) {
            try {
                System.out.println("Please enter the year of expiry (e.g. 2024):");
                int year = Integer.parseInt(in.nextLine().trim());

                System.out.println("Please enter the month of expiry (e.g. July or 07):");
                String monthInput = in.nextLine().trim();
                String monthString = convertMonthToNumber(monthInput);
                int month = Integer.parseInt(monthString);

                System.out.println("Please enter the date of expiry (e.g. 19):");
                int day = Integer.parseInt(in.nextLine().trim());

                // Attempt to create a date from the input.
                expirationDate = LocalDate.of(year, month, day);

                // Check if the date is in the past.
                if (expirationDate.isBefore(LocalDate.now())) {
                    System.out.println("The expiration date cannot be in the past. Please try again.");
                    expirationDate = null; // Reset to null to re-prompt the user.
                }
            } catch (DateTimeException | NumberFormatException e) {
                System.out.println("Invalid date. Please ensure the year, " +
                        "month, and day are correct and try again.");
            }
        }
        return expirationDate.toString(); // Formats to YYYY-MM-DD by default.
    }

    /**
     * Prompts user for rating and review.
     *
     * @param grocery for rate and review.
     */
    public static void promptForRatingAndReview(Grocery grocery) {
        System.out.println("Please enter the rating from 1 to 5:");
        int rating = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                rating = Integer.parseInt(input);
                if (rating > 0 && rating <= 5){
                    break;
                } else {
                    rating = 0;
                    System.out.println("Rating entered is invalid!");
                    System.out.println("Please enter the rating (e.g. 5):");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Rating entered is invalid!");
                System.out.println("Please enter the rating in integer(e.g. 5):");
            }
        }
        grocery.setRating(rating);

        System.out.println("Please enter the review:");
        String review = in.nextLine().trim();
        grocery.setReview(review);
    }

    /**
     * Reads expiration date from user input.
     *
     * @param month Month of expiration.
     * @return Month in numerical format.
     */
    private String convertMonthToNumber(String month) {
        // Convert month from name to number (e.g., "July" to "07")
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
        String[] monthNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        for (int i = 0; i < monthNames.length; i++) {
            if (month.equalsIgnoreCase(monthNames[i]) || month.equals(monthNumbers[i])) {
                return monthNumbers[i]; // Found a match, return the month number
            }
        }
        // If no match found or input is already in numeric format, return original input
        // This part can be enhanced to handle invalid months.
        return month;
    }
    //@@author lsiyi

    //@@author SharlynLui
    /**
     * Prints grocery details for view command.
     *
     * @param grocery The grocery that should be printed.
     */
    public static void printViewGrocery(Grocery grocery) {
        assert !(grocery.getName().isEmpty()): "grocery name should not be empty";
        System.out.println("These are the details of " + grocery.getName() + ": ");
        if (grocery.getAmount() != 0) {
            System.out.println("Amount: " + grocery.getAmount());
        } else if (grocery.getIsSetAmount()){
            System.out.println("Amount:" + grocery.getAmount());
        } else {
            System.out.println("Amount: not set");
        }
        if (grocery.getExpiration() != null) {
            System.out.println("Expiry date: " + grocery.getExpiration());
        } else {
            System.out.println("Expiry date: not set");
        }
        if (!grocery.getCategory().isEmpty()) {
            System.out.println("Category: " + grocery.getCategory());
        } else {
            System.out.println("Category: not set");
        }
        if (grocery.getCost() != 0) {
            System.out.println("Cost: " + grocery.getCost());
        } else if (grocery.getIsSetCost()) {
            System.out.println("Cost: " + grocery.getCost());
        } else {
            System.out.println("Cost: not set");
        }
        if (grocery.getLocation() != null) {
            System.out.println("Location: " + grocery.getLocation().getName());
        } else {
            System.out.println("Location: not set");
        }
        if (grocery.getRating() != 0) {
            System.out.println("Rating: " + grocery.getRating());
        } else {
            System.out.println("Rating: not set");
        }
        if (!grocery.getReview().isEmpty()) {
            System.out.println("Review: " + grocery.getReview());
        } else {
            System.out.println("Review: not set");
        }
        if (!grocery.getRemark().isEmpty()) {
            System.out.println("Remark: " + grocery.getRemark());
        } else {
            System.out.println("Remark: not set");
        }
    }

    /**
     * Inform user that Grocery does not exist.
     *
     */
    public static void printGroceriesNotFound() {
        System.out.println("Grocery not found. Please check if the name is correct or try another name.");
    }

    /**
     * Prints output after setting the selected grocery's remark.
     *
     */
    public static void printRemarkSet(Grocery grocery) {
        assert !(grocery.getRemark().isEmpty()): "grocery remark should not be empty";
        System.out.println("remark:" + grocery.getRemark());
    }

    /**
     * Prints output after setting the selected grocery's expiration date.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printExpSet(Grocery grocery) {
        assert !(grocery.getName().isEmpty()): "grocery name should not be empty";
        System.out.println(grocery.getName() + " will expire on: " + grocery.getExpiration());
    }
    /**
     * Prints output after editing the selected grocery's category.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printCategorySet(Grocery grocery){
        assert !(grocery.getCategory().isEmpty()): "grocery category should not be empty";
        System.out.println(grocery.getName() + " is now a " + grocery.getCategory());
    }


    /**
     * Prints output after adding a grocery.
     *
     * @param grocery Grocery added.
     */
    public static void printGroceryAdded(Grocery grocery) {
        assert !(grocery.getName().isEmpty()): "grocery name should not be empty";
        System.out.println(grocery.getName() + " added!");
    }

    /**
     * Prints the new amount set for the selected grocery.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printAmtSet(Grocery grocery) {
        assert grocery.getAmount() >= 0 : "grocery amount should not be empty";
        System.out.println(grocery.getName() + ": " + grocery.getAmount());
    }


    /**
     * Prints out when there are no groceries.
     */
    public static void printNoGrocery() {
        System.out.println("There's no groceries!");
    }

    /**
     * Prints all groceries.
     *
     * @param groceries An array list of groceries.
     */
    public static void printGroceryList(List<Grocery> groceries) {
        assert !groceries.isEmpty() : "grocery list should not be empty";
        System.out.println("Here are your groceries!");
        for (Grocery grocery: groceries) {
            System.out.println(" - " + grocery.printGrocery());
        }
    }


    public static void printFoodAdded(Food food) {
        assert !(food.getName().isEmpty()): "food name should not be empty";
        System.out.println(food.print() + " was consumed!");
    }

    /**
     * Prompts user for remark for grocery.
     *
     * @return the remark to be added.
     */
    public String promptForRemark() {
        System.out.println("Please enter the remark for this grocery:");
        return in.nextLine().trim();
    }
    //@@author SharlynLui

    //@@author luozihui
}
