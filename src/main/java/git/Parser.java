package git;


import exceptions.GitException;
import exceptions.InvalidCommandException;
import grocery.Grocery;
import grocery.GroceryList;

/**
 * Deals with commands entered by user.
 */
public class Parser {
    private GroceryList groceryList;
    private Ui ui;

    private boolean isRunning;

    /**
     * Constructs Parser.
     */
    public Parser(Ui ui) {
        groceryList = new GroceryList();
        this.ui = ui;
        isRunning = true;
    }

    /**
     * Enums containing the possible commands for groceries.
     */
    enum Command {
        ADD, DEL, EXP, AMT, TH, USE, COST, LIST, LISTC, LOW, HELP, EXIT

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
    public void executeCommand(String[] commandParts) throws GitException {
        assert commandParts.length == 2 : "Command passed in wrong format";

        switch (commandParts[0]) {
        case ADD:
            Grocery grocery = new Grocery(commandParts[1]);
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
    private void editGrocery(Command command, String[] commandParts) throws GitException {
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
    private void viewListOrHelp(Command command) throws GitException {
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
            ui.displayHelp();
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
