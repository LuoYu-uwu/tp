package git;

import exceptions.GitException;

/**
 * Represents the Grocery in Time (GiT) program, allowing users to store and track their groceries!
 */
public class Git {
    // ATTRIBUTES
    private Ui ui;
    private boolean isRunning;

    private Parser parser;

    // METHODS
    /**
     * Constructs Git.
     */
    public Git() {
        ui = Ui.getInstance();
        parser = new Parser(ui);
        isRunning = true;
    }

    /**
     * Runs Git.
     */
    private void run() {
        String mode = null;
        try {
            mode = ui.printWelcome();
        } catch (GitException e) {
            System.out.println(e.getMessage());
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
                ui.printLine();
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
