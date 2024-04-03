package git;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.GitException;
import exceptions.InvalidCommandException;
import exceptions.InvalidCostException;
import exceptions.nosuch.NoSuchObjectException;
import food.Food;
import exceptions.PastExpirationDateException;
import grocery.Grocery;
import grocery.location.Location;

import enumerations.Mode;
import grocery.location.LocationList;
import recipe.Recipe;


/**
 * Deals with interactions with the user.
 */
public class Ui {
    // ATTRIBUTES
    public static final String DIVIDER = "- - - - -";
    private static Ui singleUi = null;
    private static Scanner in;
    private static String userName;

    // METHODS
    /**
     * Constructs Ui and initialises Scanner to read input.
     */
    private Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Returns the single instance of Ui.
     */
    public static Ui getInstance() {
        if (singleUi == null) {
            singleUi = new Ui();
        }
        return singleUi;
    }

    public static String getUserName() {
        return userName;
    }

    /**
     * Prints welcome message.
     */
    public void printWelcome() {
        final String gitlogo =
                "   ______   _  _________\n" +
                " .' ___  | (_)|  _   _  |\n" +
                "/ .'   \\_| __ |_/ | | \\_|\n" +
                "| |   ____[  |    | |\n" +
                "\\ `.___]  || |   _| |_\n" +
                " `._____.'[___] |_____|";

        System.out.println(gitlogo + System.lineSeparator());
        System.out.println("Hello from GiT");
        System.out.println("What is your name?");
        printLine();
        userName = in.nextLine();
        printHello(userName);
        displayHelp();
    }

    /**
     * Prints Hello with user's name.
     *
     * @param userName User's name.
     */
    public void printHello(String userName) {
        System.out.println("Hello " + userName + "!");
        printLine();
    }

    public static void displayCommands(String selectedMode) throws GitException {
        Mode mode;
        try {
            mode = Mode.valueOf(selectedMode.toUpperCase());;
        } catch (Exception e) {
            throw new InvalidCommandException();
        }
        switch (mode) {
        case GROCERY:
            displayHelpForGrocery();
            System.out.println("Enter command:");
            break;

        case CALORIES:
            displayHelpForCal();
            System.out.println("Enter command:");
            break;

        case PROFILE:
            displayHelpForProf();
            System.out.println("Enter command:");
            break;

        case RECIPE:
            displayHelpForRecipe();
            System.out.println("Enter command:");
            break;

        case EXIT:
            System.out.println("Enter anything again to confirm exit");
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Processes user input into a command and its details for Parser.
     *
     * @return Array of the fragments of the command.
     */
    public String[] processInput() {
        String commandLine = in.nextLine();
        String[] commandParts = commandLine.strip().split(" ", 2);
        assert commandParts.length > 0 : "Failed to read user input";

        return commandParts;
    }

    /**
     * Prints the add grocery menu.
     * 
     * @param grocery The grocery to be added.
     */
    public void printAddMenu(Grocery grocery) {
        System.out.println("Do you want to include the following details?");
        System.out.println("1. Category");
        System.out.println("2. Amount");
        System.out.println("3. Location");
        System.out.println("4. Expiration Date");
        System.out.println("5. Cost");
        System.out.println("6. Threshold Amount");
        System.out.println("7. Help");
        System.out.println("8. Skip");
        System.out.println("Please enter the number of the details you want to include:");
        System.out.println("You may enter multiple numbers. (e.g. 1234)");
        
        // Reading the user input as a string
        String input = singleUi.processInput()[0];
        // Iterating over each character in the string
        for (char choice : input.toCharArray()) {
            switch (choice) {
            case '1':
                System.out.println("Including Category");
                String category = singleUi.promptForCategory();
                grocery.setCategory(category);
                break;

            case '2':
                System.out.println("Including Amount");
                int amount = singleUi.promptForAmount();
                grocery.setAmount(amount);
                break;

            case '3':
                System.out.println("Including Location");
                Location location = singleUi.promptForLocation();
                grocery.setLocation(location);
                if (location != null) {
                    location.addGrocery(grocery);
                }
                break;

            case '4':
                System.out.println("Including Expiration Date");
                String expiration = singleUi.promptForExpiration();
                try {
                    grocery.setExpiration(expiration);
                } catch (PastExpirationDateException e) {
                    e.printStackTrace();
                }
                break;

            case '5':
                System.out.println("Including Cost");
                Double cost = singleUi.promptForCost();
                grocery.setCost(cost);
                break;

            case '6':
                System.out.println("Including Threshold Amount");
                int threshold = singleUi.promptForThreshold();
                grocery.setThreshold(threshold);
                break;

            case '7':
                System.out.println("Displaying help");
                singleUi.displayAddHelp();
                break;

            case '8':
                System.out.println("Skipping additional details");
                break;

            default:
                System.out.println("Invalid choice: " + choice);
                break;
            }

            if (choice == '6') {
                break;
            }
        }
    }

    /**
     * Prompts user for expiration date.
     *
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
                System.out.println("Invalid date. Please ensure the year, month, and day are correct and try again.");
            }
        }
        return expirationDate.toString(); // Formats to YYYY-MM-DD by default.
    }

    /**
     * Prompts user for title when adding recipe in RECIPE mode.
     * @return the title of the recipe
     */
    public String promptForTitle(){
        System.out.println("Please enter the title of the recipe (e.g. fried egg):");
        return in.nextLine().trim();
    }

    /**
     * Prompts user for ingredients when adding recipe in RECIPE mode.
     * @return the ingredients in a single line, trimmed only
     */
    public String promptForIngredients(){
        System.out.println("Please enter the ingredients for this recipe in one line (e.g. egg, salt):");
        return in.nextLine().trim();
    }

    /**
     * Prompts user for steps when adding recipe in RECIPE mode.
     * @return the steps in a single line, trimmed only
     */
    public String promptForSteps(){
        System.out.println("Please enter the steps for this recipe in one line (e.g. Fry the egg. Add salt. Serve.):");
        return in.nextLine().trim();
    }

    /**
     * Informs the user that the recipe has been added to the recipe list.
     *
     * @param recipe Recipe added.
     */
    public static void printRecipeAdded(Recipe recipe){
        assert !(recipe.getTitle().isEmpty()): "grocery name should not be empty";
        System.out.println(recipe.getTitle() + " added!");
    }

    /**
     * Prints out when there are no recipe.
     */
    public static void printNoRecipe() {
        System.out.println("There's no recipe!");
    }

    /**
     * Prints all recipes.
     *
     * @param recipeArr An array list of groceries.
     */
    public static void printRecipeList(ArrayList<Recipe> recipeArr) {
        assert !recipeArr.isEmpty() : "recipe list should not be empty";
        System.out.println("Here are your recipe titles!");
        int num = 1;
        for (Recipe recipe: recipeArr) {
            System.out.println(num + ". " + recipe.getTitle());
            num += 1;
        }
    }

    /**
     * Prints output when the selected recipe is removed.
     *
     * @param recipe The recipe that is removed.
     */
    public static void printRecipeRemoved(Recipe recipe) {
        assert recipe != null : "Recipe does not exist";
        System.out.println(recipe.getTitle() + " is removed from the recipe list.");
    }

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
                if (rating >= 0 && rating <= 5){
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

    public double promptForCalories() {
        System.out.println("Please enter the calories of the food in kcal:");
        double calories = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                calories = Double.parseDouble(input);
                if (calories > 0 ){
                    break;
                } else {
                    calories = 0;
                    System.out.println("Calories entered is invalid!");
                    System.out.println("Please enter the calories of the food in kcal:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Calories entered is invalid!");
                System.out.println("Please enter the calories of the food in kcal:");
            }
        }
        return calories;
    }

    public String promptForName() {
        System.out.println("Please enter your name");
        return in.nextLine().trim();
    }

    public double promptForWeight() {
        System.out.println("Please enter your weight in KG:");
        double weight = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                weight = Double.parseDouble(input);
                if (weight > 0) {
                    break;
                } else {
                    weight = 0;
                    System.out.println("Weight entered is invalid!");
                    System.out.println("Please enter your weight in KG:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Weight entered is invalid!");
                System.out.println("Please enter your weight in KG:");
            }
        }
        return weight;
    }

    public double promptForHeight() {
        System.out.println("Please enter your height in cm:");
        double height = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                height = Double.parseDouble(input);
                if (height > 0 ){
                    break;
                } else {
                    height = 0;
                    System.out.println("Height entered is invalid!");
                    System.out.println("Please enter your height in cm:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Height entered is invalid!");
                System.out.println("Please enter your height in cm:");
            }
        }
        return height;
    }

    public int promptForAge() {
        System.out.println("Please enter your age in years:");
        int age = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                age = Integer.parseInt(input);
                if (age > 0 ){
                    break;
                } else {
                    age = 0;
                    System.out.println("Age entered is invalid!");
                    System.out.println("Please enter your age in years:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Age entered is invalid!");
                System.out.println("Please enter your age in years:");
            }
        }
        return age;
    }

    public String promptForGender() {
        System.out.println("Please enter your gender (e.g. F):");
        String gender = "";
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            if (input.length() == 1 &&
                    (input.equalsIgnoreCase("F")
                            || input.equalsIgnoreCase("M"))) {
                gender = input;
                break;
            } else {
                System.out.println("Gender entered is invalid!");
                System.out.println("Please enter your age in years:");
            }
        }
        return gender;
    }

    public String promptForAim() {
        System.out.println("Please enter your aim (e.g. lose/maintain/gain):");
        String aim = "";
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("lose")
                    || input.equalsIgnoreCase("maintain")
                    || input.equalsIgnoreCase("gain")) {
                aim = input;
                break;
            } else {
                System.out.println("Gender entered is invalid!");
                System.out.println("Please enter your age in years:");
            }
        }
        return aim;
    }

    public String promptForActiveness() {
        System.out.println("Please enter your activeness " +
                "(e.g. inactive/light/moderate/active/very):");
        String activeness = "";
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("inactive")
                    || input.equalsIgnoreCase("light")
                    || input.equalsIgnoreCase("moderate")
                    || input.equalsIgnoreCase("active")
                    || input.equalsIgnoreCase("very")) {
                activeness = input;
                break;
            } else {
                System.out.println("Gender entered is invalid!");
                System.out.println("Please enter your age in years:");
            }
        }
        return activeness;
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

    public static String switchMode() throws GitException {
        System.out.println("What mode would you like to enter?");
        System.out.println("Please select a mode: " + "grocery, profile, calories or recipe:");
        String newMode = in.nextLine().trim();
        displayCommands(newMode);
        return newMode;
    }

    /**
     * Displays help message containing all possible commands.
     */
    public static void displayHelpForGrocery() {
        System.out.println(
                "Here are some ways you can manage your groceries!\n" +
                        "add GROCERY: adds the item GROCERY.\n" +
                        "find KEYWORD: finds all groceries containing the KEYWORD.\n" +
                        "loc LOCATION: adds a LOCATION to track.\n" +
                        "exp GROCERY d/EXPIRATION_DATE: edits the expiration date for GROCERY.\n" +
                        "amt GROCERY a/AMOUNT: sets the amount of GROCERY.\n" +
                        "use GROCERY a/AMOUNT: updates the total amount after using a GROCERY.\n" +
                        "th GROCERY a/AMOUNT: updates the threshold amount of GROCERY.\n" +
                        "cost GROCERY $PRICE: updates the price of GROCERY.\n" +
                        "store GROCERY l/LOCATION: sets the location of GROCERY.\n" +
                        "rate GROCERY: rates and reviews GROCERY.\n" +
                        "del GROCERY: deletes GROCERY.\n" +
                        "delloc LOCATION: removes LOCATION from tracking.\n" +
                        "list: shows list of all groceries you have.\n" +
                        "listc: shows the list sorted by price.\n" +
                        "liste: shows the list sorted by expiration date.\n" +
                        "listl [LOCATION]: shows all locations, or all groceries stored in [LOCATION].\n" +
                        "expiring: shows a list of groceries that are expiring soon.\n" +
                        "low: shows a list of groceries that are low in stock.\n" +
                        "exit: exits the program.\n" +
                        "switch: switches the mode.\n" +
                        "help: view all the possible commands."
        );
    }

    public static void displayHelpForCal() {
        System.out.println(
                "Here are some ways you can manage your calories intake!\n" +
                        "eat FOOD: adds the food that you have eaten.\n" +
                        "view: adds the food you have eaten and total calories intake.\n" +
                        "switch: switches the mode.\n" +
                        "exit: exits the program.\n" +
                        "help: view all the possible commands for calories management."
        );
    }

    public static void displayHelpForProf() {
        System.out.println(
                "Here are some ways you can manage your profile!\n" +
                        "update: stores information needed to manage your calories intake.\n" +
                        "view: view your profile details.\n" +
                        "switch: switches the mode.\n" +
                        "exit: exits the program.\n" +
                        "help: view all the possible commands for profile management."
        );
    }

    public static void displayHelpForRecipe() {
        System.out.println(
                "Here are some ways you can manage your recipes!\n" +
                        "add: add a new recipe. \n" +
                        "list: list all your recipes. \n" +
                        "view: view your recipes details.\n" +
                        "delete: delete the recipe. \n" +
                        "switch: switches the mode.\n" +
                        "exit: exits the program.\n" +
                        "help: view all the possible commands for recipes management."
        );
    }

    public static void displayHelp() {
        System.out.println(
                "Here are some ways you can use our app!\n" +
                        "grocery: manages your groceries.\n" +
                        "calories: manages your calories intake.\n" +
                        "profile: manages your profile.\n" +
                        "recipe: manages your recipe. \n" +
                        "exit: exits the program.\n"
        );
    }

    /**
     * Display help message for the user when adding grocery.
     */
    public void displayAddHelp() {
        System.out.println(
            "Here are some details you can include when adding a grocery:\n" +
            "Category - Enter the category of the grocery.\n" +
            "Amount - Enter the amount of the grocery.\n" +
            "Location - Enter the location of the grocery.\n" +
            "Expiration Date - Enter the expiration date of the grocery.\n" +
            "Cost - Enter the cost of the grocery.\n" +
            "Minimum Amount - Enter the minimum amount of the grocery to set reminder.\n" +
            "Skip - Skip adding additional details."
        );
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
     * Prints the new threshold set for the selected grocery.
     *
     * @param grocery The grocery that should be updated.
     */
    public static void printThresholdSet(Grocery grocery) {
        System.out.println(grocery.getName() + "'s threshold is now " +
                grocery.getThreshold() + " " + grocery.getUnit());
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

    /**
     * Prints all groceries with amount less than threshold set.
     *
     * @param groceries An array list of groceries.
     */
    public static void printLowStocks(List<Grocery> groceries) {
        assert !groceries.isEmpty() : "grocery list should not be empty";
        System.out.println("Time to top up these groceries!");
        for (Grocery grocery: groceries) {
            if (grocery.isLow()) {
                System.out.println(" - " + grocery.getName()
                        + " only left: " +grocery.getAmount());
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

    public static void printFoodAdded(Food food) {
        assert !(food.getName().isEmpty()): "food name should not be empty";
        System.out.println(food.print() + " was consumed!");
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
     * Prints divider for user readability.
     */
    public static void printLine() {
        System.out.println(DIVIDER);
    }
}
