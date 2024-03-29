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
    public Parser() {
        groceryList = new GroceryList();
        ui = new Ui();
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
     * Handles commands.
     *
     * @throws GitException Exception thrown depending on specific error.
     */
    public void executeCommand(String[] commandParts) throws GitException {
        assert commandParts.length == 2 : "Command passed in wrong format";

        switch (commandParts[0]) {
        case "add":
            String category = ui.promptForCategory();
            int amount = ui.promptForAmount();
            String location = ui.promptForLocation();
            Grocery grocery = new Grocery(commandParts[1], amount, LocalDate.now(), category, 0, location);
            String expiration = ui.promptForExpiration();
            String cost = ui.promptForCost();
            grocery.setExpiration(expiration);
            grocery.setCost(cost);
            groceryList.addGrocery(grocery);
            break;

        case "exp":
            groceryList.editExpiration(commandParts[1]);
            break;

        case "amt":
        case "use":
            groceryList.editAmount(commandParts[1], commandParts[0].equals("use"));
            break;

        case "cost":
            groceryList.editCost(commandParts[1]);
            break;

        case "del":
            groceryList.removeGrocery(commandParts[1]);
            break;

        case "list":
            groceryList.listGroceries();
            break;

        case "listC":
            groceryList.sortByCost();
            break;

        case "help":
            ui.displayHelp();
            break;

        case "exit":
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
