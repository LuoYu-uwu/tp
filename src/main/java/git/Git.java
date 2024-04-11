package git;

import exceptions.GitException;

/**
 * Represents the Grocery in Time (GiT) program, allowing users to store and track their groceries!
 */
public class Git {
    // ATTRIBUTES
    private Ui ui;
    private GroceryUi groceryUi;
    private boolean isRunning;

    private Parser parser;

    // METHODS
    /**
     * Constructs Git.
     */
    public Git() {
        ui = Ui.getInstance();
        groceryUi = GroceryUi.getInstance();
        parser = new Parser(ui);
        isRunning = true;
    }

    /**
     * Runs Git.
     */
    private void run() {
        String username = ui.printWelcome();
        parser.setUsername(username);

        String mode = null;
        boolean isInitialised = false;
        while (!isInitialised) {
            try {
                mode = Ui.switchMode();
                isInitialised = true;
            } catch (GitException e) {
                Ui.printLine();
            }
        }

        while (isRunning) {
            try {
                String[] commandParts = parser.processCommandParts();
                parser.executeCommand(commandParts, mode);
                isRunning = parser.getIsRunning();
                mode = parser.getCurrentMode();
            } catch (GitException e) {
                System.out.println(e.getMessage());
            } finally {
                Ui.printLine();
            }
        }
    }

    /**
     * Main for GiT.
     */
    public static void main(String[] args) {
        new Git().run();
    }

}
