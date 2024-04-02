package git;

import java.time.LocalDate;

import exceptions.GitException;
import exceptions.InvalidCommandException;
import food.Food;
import food.FoodList;
import grocery.Grocery;
import grocery.GroceryList;
import enumerations.Mode;
import enumerations.GroceryCommand;
import user.UserInfo;

/**
 * Deals with commands entered by user.
 */
public class Parser {
    private GroceryList groceryList;
    private FoodList foodList;
    private UserInfo userInfo;
    private Ui ui;

    private boolean isRunning;
    private String currentMode;


    /**
     * Constructs Parser.
     */
    public Parser(Ui ui) {
        groceryList = new GroceryList();
        foodList = new FoodList();
        String userName = ui.getUserName();
        userInfo = new UserInfo(userName);
        this.ui = ui;
        isRunning = true;
    }

    public String getCurrentMode() {
        return currentMode;
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
     * Handles different types of commands.
     *
     * @param commandParts Fragments of the command entered by user.
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

        default:
            throw new InvalidCommandException();
        }
    }

    public void caloriesManagement(String[] commandParts) throws GitException {
        String command = commandParts[0];
        switch (command) {
        case "eat":
            double calories = ui.promptForCalories();
            Food food = new Food(commandParts[1], calories);
            foodList.addFood(food);
            userInfo.consumptionOfCalories(food);
            break;

        case "view":
            foodList.printFoods();
            System.out.println("You have consumed " + userInfo.getCurrentCalories() + " calories for today");
            break;

        case "switch":
            currentMode = Ui.switchMode();
            break;

        case "exit":
            System.out.println("bye bye!");
            isRunning = false;
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    public void profileManagement(String[] commandParts) throws GitException {
        String command = commandParts[0];
        switch (command) {
        case "update":
            double weight = ui.promptForWeight();
            double height = ui.promptForHeight();
            int age = ui.promptForAge();
            String gender = ui.promptForGender();
            String activeness = ui.promptForActiveness();
            String aim = ui.promptForAim();
            userInfo.updateInfo(weight,height,age,gender,activeness,aim);
            break;

        case "switch":
            currentMode = Ui.switchMode();
            break;

        case "exit":
            System.out.println("bye bye!");
            isRunning = false;
            break;

        default:
            throw new InvalidCommandException();
        }
    }

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
        } else if (index <= GroceryCommand.COST.ordinal()) {
            editGrocery(command,commandParts);
        } else {
            viewListOrHelp(command);
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
            String category = ui.promptForCategory();
            int amount = ui.promptForAmount();
            int threshold = ui.promptForThreshold();
            String location = ui.promptForLocation();
            double cost = ui.promptForCost();
            Grocery grocery = new Grocery(commandParts[1], amount, threshold,
                    LocalDate.now(), category, cost, location);
            String expiration = ui.promptForExpiration();
            grocery.setExpiration(expiration);
            groceryList.addGrocery(grocery);
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

        default:
            throw new InvalidCommandException();
        }
    }

    /**
     * Handles commands related to viewing the grocery list.
     *
     * @param command Command keyword of data type Enum.
     * @throws GitException Exception thrown depending on specific error.
     */
    private void viewListOrHelp(GroceryCommand command) throws GitException {
        switch (command) {
        case LIST:
            groceryList.listGroceries();
            break;

        case LISTC:
            groceryList.sortByCost();
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
            System.out.println("bye bye!");
            isRunning = false;
            break;

        default:
            throw new InvalidCommandException();
        }
    }

    public boolean getIsRunning() {
        return isRunning;
    }
}
