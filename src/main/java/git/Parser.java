package git;


import enumerations.CalCommand;
import enumerations.GroceryCommand;
import enumerations.Mode;
import enumerations.ProfileCommand;
import enumerations.RecipeCommand;
import exceptions.DuplicateGroceryException;
import exceptions.GitException;
import exceptions.InvalidCommandException;
import exceptions.emptyinput.EmptyInputException;
import food.Food;
import food.FoodList;
import grocery.Grocery;
import grocery.GroceryList;
import grocery.location.Location;
import grocery.location.LocationList;
import recipe.Recipe;
import recipe.RecipeList;
import user.UserInfo;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Deals with commands entered by user.
 */
public class Parser {
    private GroceryList groceryList;
    private FoodList foodList;
    private UserInfo userInfo;
    private Ui ui;
    private GroceryUi groceryUi;
    private RecipeUi recipeUi;
    private ProfileUi profileUi;
    private CaloriesUi caloriesUi;
    private RecipeList recipeList;
    private Storage storage;

    private boolean isRunning;
    private String currentMode;


    /**
     * Constructs Parser.
     * 
     * @param ui Ui object.
     */
    public Parser(Ui ui) {
        this.storage = new Storage();
        groceryList = storage.loadGroceryFile();
        foodList = new FoodList();
        userInfo = new UserInfo();
        recipeUi = new RecipeUi();
        groceryUi = new GroceryUi();
        profileUi = new ProfileUi();
        caloriesUi = new CaloriesUi();
        recipeList = storage.loadRecipeFile();
        this.ui = ui;
        isRunning = true;
    }

    /**
     * Processes a command and its details into a valid format for executing relevant code.
     *
     * @return Array of the fragments of the commands.
     */
    public String[] processCommandParts() {
        String[] commandParts = ui.processInput();
        if (commandParts.length == 1) {
            return new String[]{commandParts[0], ""};
        } else {
            return commandParts;
        }
    }

    /**
     * Handles all the user's commands depending on the selected mode.
     *
     * @param commandParts Fragments of the command entered by the user.
     * @param selectedMode Mode of GiT as selected by the user.
     * @throws GitException Exception thrown depending on specific error.
     */
    public void executeCommand(String[] commandParts, String selectedMode) throws GitException {
        this.currentMode = selectedMode;
        Mode mode;
        try {
            mode = Mode.valueOf(currentMode.toUpperCase());;
        } catch (Exception e) {
            throw new InvalidCommandException();
        }

        switch (mode) {
        case GROCERY:
            groceryManagement(commandParts);
            break;

        case CALORIES:
            caloriesManagement(commandParts);
            break;

        case PROFILE:
            profileManagement(commandParts);
            break;

        case RECIPE:
            recipeManagement(commandParts);
            break;

        case MODE:
            currentMode = Ui.switchMode();
            break;

        case HELP:
            Ui.displayHelp();
            break;

        case EXIT:
            if (commandParts[1].isEmpty()) {
                System.out.println("bye bye!");
                isRunning = false;
            } else {
                throw new InvalidCommandException();
            }
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to calorie tracking.
     *
     * @param commandParts Fragments of the command entered by the user.
     * @throws GitException Exception thrown depending on specific error.
     */
    public void caloriesManagement(String[] commandParts) throws GitException {
        CalCommand command;
        try {
            command = CalCommand.valueOf(commandParts[0].toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommandException();
        }

        switch (command) {
        case EAT:
            String name = commandParts[1];
            if (name == null || name.isBlank() || !name.matches("[a-zA-Z]+")) {
                throw new EmptyInputException("valid food name");
            }
            double calories = caloriesUi.promptForCalories();
            Food food = new Food(name, calories);
            foodList.addFood(food);
            userInfo.consumptionOfCalories(food);
            break;

        case VIEW:
            foodList.printFoods();
            System.out.println("You have consumed " + userInfo.getCurrentCalories() + " calories for today");
            break;

        case SWITCH:
            currentMode = Ui.switchMode();
            break;

        case HELP:
            Ui.displayHelpForCal();
            break;

        case EXIT:
            if (commandParts[1].isEmpty()) {
                System.out.println("bye bye!");
                isRunning = false;
            } else {
                throw new InvalidCommandException();
            }
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Sets username after user input.
     */
    public void setUsername(String username) {
        userInfo.setName(username);
    }

    /**
     * Handles commands related to the user's profile.
     *
     * @param commandParts Fragments of the command entered by the user.
     * @throws GitException Exception thrown depending on specific error.
     */
    public void profileManagement(String[] commandParts) throws GitException {
        ProfileCommand command;
        try {
            command = ProfileCommand.valueOf(commandParts[0].toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommandException();
        }

        switch (command) {
        case UPDATE:
            String name = profileUi.promptForName();
            double weight = profileUi.promptForWeight();
            double height = profileUi.promptForHeight();
            int age = profileUi.promptForAge();
            String gender = profileUi.promptForGender();
            String activeness = profileUi.promptForActiveness();
            String aim = profileUi.promptForAim();
            userInfo.updateInfo(name, weight,height,age,gender,activeness,aim);
            break;

        case VIEW:
            System.out.println(userInfo.viewProfile());
            break;

        case SWITCH:
            currentMode = Ui.switchMode();
            break;

        case HELP:
            Ui.displayHelpForProf();
            break;

        case EXIT:
            if (commandParts[1].isEmpty()) {
                System.out.println("bye bye!");
                isRunning = false;
            } else {
                throw new InvalidCommandException();
            }
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to recipe management.
     *
     * @param commandParts Fragments of the command entered by the user.
     * @throws GitException Exception thrown depending on specific error.
     */
    public void recipeManagement(String[] commandParts) throws GitException, EmptyInputException {
        RecipeCommand command;
        try {
            command = RecipeCommand.valueOf(commandParts[0].toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommandException();
        }

        switch (command) {
        case ADD:
            String title = recipeUi.promptForTitle();
            String ingredients  = recipeUi.promptForIngredients();
            String[] ingredientsList = ingredients.split("[,]");
            ArrayList<String> ingredientsArr = new ArrayList<String>(Arrays.asList(ingredientsList));
            String steps  = recipeUi.promptForSteps();
            String[] stepsList = steps.split("[.]");
            ArrayList<String> stepsArr = new ArrayList<String>(Arrays.asList(stepsList));
            recipeList.addRecipe(new Recipe(title, ingredientsArr, stepsArr));
            break;

        case LIST:
            recipeList.listRecipes();
            break;

        case VIEW:
            String titleView = recipeUi.promptForTitle();
            Recipe recipeToView = recipeList.getRecipe(titleView);
            recipeToView.viewRecipe();
            break;

        case DELETE:
            String recipeTitle = recipeUi.promptForTitle();
            recipeList.removeRecipe(recipeTitle);
            break;

        case SWITCH:
            currentMode = Ui.switchMode();
            executeCommand(commandParts, currentMode);
            break;

        case EXIT:
            if (commandParts[1].isEmpty()) {
                System.out.println("bye bye!");
                isRunning = false;
            } else {
                throw new InvalidCommandException();
            }
            break;

        case HELP:
            Ui.displayHelpForRecipe();
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to grocery management.
     *
     * @param commandParts Fragments of the command entered by the user.
     * @throws GitException Exception thrown depending on specific error.
     */
    public void groceryManagement(String[] commandParts) throws GitException {
        assert commandParts.length == 2 : "Command passed in wrong format";

        GroceryCommand command;
        try {
            command = GroceryCommand.valueOf(commandParts[0].toUpperCase());
        } catch (Exception e) {
            throw new InvalidCommandException();
        }

        int index = command.ordinal();
        if (index <= GroceryCommand.DEL.ordinal()) {
            addOrDelGrocery(command, commandParts);
        } else if (index <= GroceryCommand.STORE.ordinal()) {
            editGrocery(command, commandParts);
        } else if (index <= GroceryCommand.LISTLOC.ordinal()) {
            handleLocationCommands(command, commandParts[1]);
        } else if (index == GroceryCommand.FIND.ordinal()) {
            groceryList.findGroceries(commandParts[1]);
        } else {
            viewListOrHelp(command, commandParts);
        }
    }

    /**
     * Handles commands related to adding or deleting a grocery.
     *
     * @param command Command keyword of data type Enum.
     * @param commandParts Fragments of the command entered by user.
     * @throws GitException Exception thrown depending on specific error.
     */
    private void addOrDelGrocery(GroceryCommand command, String[] commandParts) throws GitException {
        switch (command) {
        case ADD:
            String name = commandParts[1];
            if (name == null || name.isBlank()) {
                throw new EmptyInputException("grocery");
            }

            if (groceryList.isGroceryExists(name)) {
                throw new DuplicateGroceryException(name);
            }

            Grocery grocery = new Grocery(commandParts[1]);
            groceryUi.promptAddMenu(grocery);
            groceryList.addGrocery(grocery);
            break;

        case ADDMULTI:
            Grocery[] groceries = groceryUi.promptAddMultipleMenu();
            for (Grocery g : groceries) {
                groceryList.addGrocery(g);
            }
            break;

        case DEL:
            groceryList.removeGrocery(commandParts[1]);
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to editing a grocery.
     *
     * @param command Command keyword of data type Enum.
     * @param commandParts Fragments of the command entered by user.
     * @throws GitException Exception thrown depending on specific error.
     */
    private void editGrocery(GroceryCommand command, String[] commandParts) throws GitException {
        switch (command) {
        case EXP:
            groceryList.editExpiration(commandParts[1]);
            break;
        case CAT:
            groceryList.editCategory(commandParts[1]);
            break;
        case AMT:
        case USE:
            groceryList.editAmount(commandParts[1], commandParts[0].equals("use"));
            break;

        case TH:
            groceryList.editThreshold(commandParts[1]);
            break;

        case COST:
            groceryList.editCost(commandParts[1]);
            break;
        
        case RATE:
            groceryList.editRatingAndReview(commandParts[1]);
            break;

        case STORE:
            groceryList.editLocation(commandParts[1]);
            break;

        case REMARK:
            groceryList.editRemark(commandParts[1]);
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to locations.
     *
     * @param command Command keyword of data type Enum.
     * @param name Location name.
     * @throws GitException Exception thrown depending on specific error.
     */
    private void handleLocationCommands(GroceryCommand command, String name) throws GitException {
        switch (command) {
        case LOC:
            LocationList.addLocation(name);
            break;

        case LISTLOC:
            if (name.isBlank()) {
                LocationList.listLocations();
            } else {
                Location location = LocationList.findLocation(name);
                location.listGroceries();
            }
            break;

        case DELLOC:
            LocationList.removeLocation(name);
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to viewing the grocery list, getting help, or switching modes.
     *
     * @param command Command keyword of data type Enum.
     * @throws GitException Exception thrown depending on specific error.
     */
    private void viewListOrHelp(GroceryCommand command, String[] commandParts) throws GitException {
        switch (command) {

        case VIEW:
            groceryList.viewGrocery(commandParts[1]);
            break;

        case LIST:
            groceryList.listGroceries();
            break;

        case LISTCAT:
            groceryList.sortByCategory();
            break;

        case LISTCOST:
            groceryList.sortByCost();
            break;

        case LISTEXP:
            groceryList.sortByExpiration();
            break;

        case EXPIRING:
            groceryList.displayGroceriesExpiringInNext3Days();
            break;

        case LOW:
            groceryList.listLowStocks();
            break;

        case HELP:
            Ui.displayHelpForGrocery();
            break;

        case SWITCH:
            currentMode = Ui.switchMode();
            break;

        case EXIT:
            if (commandParts[1].isEmpty()) {
                System.out.println("bye bye!");
                isRunning = false;
            } else {
                throw new InvalidCommandException();
            }
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    // Getters
    public boolean getIsRunning() {
        return isRunning;
    }

    public String getCurrentMode() {
        return currentMode;
    }
}
