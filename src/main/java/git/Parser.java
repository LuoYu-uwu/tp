package git;

import java.time.LocalDate;

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

    enum Command {
        ADD, EXP, AMT, USE, COST, DEL, LIST, LISTC, HELP, EXIT

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
     * Handles commands.
     *
     * @throws GitException Exception thrown depending on specific error.
     */
    public void executeCommand(String[] commandParts) throws GitException {
        assert commandParts.length == 2 : "Command passed in wrong format";
        Command command = Command.valueOf(commandParts[0].toUpperCase());
        switch (command) {
        case ADD:
            String category = ui.promptForCategory();
            int amount = ui.promptForAmount();
            String location = ui.promptForLocation();
            double cost = ui.promptForCost();
            Grocery grocery = new Grocery(commandParts[1], amount, LocalDate.now(), category, cost, location);
            String expiration = ui.promptForExpiration();
            grocery.setExpiration(expiration);
            groceryList.addGrocery(grocery);
            break;

        case EXP:
            groceryList.editExpiration(commandParts[1]);
            break;

        case AMT:
        case USE:
            groceryList.editAmount(commandParts[1], commandParts[0].equals("use"));
            break;

        case COST:
            groceryList.editCost(commandParts[1]);
            break;

        case DEL:
            groceryList.removeGrocery(commandParts[1]);
            break;

        case LIST:
            groceryList.listGroceries();
            break;

        case LISTC:
            groceryList.sortByCost();
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
