package git;

import java.util.Scanner;
import exceptions.GitException;
import exceptions.InvalidCommandException;
import enumerations.Mode;
import user.UserInfo;


/**
 * Deals with interactions with the user.
 */
public class Ui {
    // ATTRIBUTES
    private Storage storage;
    private UserInfo userInfo;
    public static final String DIVIDER = "- - - - -";
    private static Ui singleUi = null;
    private static Scanner in;
    private static String userName;
    private static final double MAX_HEIGHT = 280;
    private static final double MAX_WEIGHT = 370;
    private static final double MAX_AGE = 160;


    // METHODS
    /**
     * Constructs Ui and initialises Scanner to read input.
     */
    private Ui() {
        in = new Scanner(System.in);
        storage = new Storage();
        userInfo = storage.loadProfileFile();
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
    public String printWelcome() {
        final String gitlogo =
                "   ______   _  _________\n" +
                " .' ___  | (_)|  _   _  |\n" +
                "/ .'   \\_| __ |_/ | | \\_|\n" +
                "| |   ____[  |    | |\n" +
                "\\ `.___]  || |   _| |_\n" +
                " `._____.'[___] |_____|";

        System.out.println(gitlogo + System.lineSeparator());

            System.out.println("Hello from GiT");
            userName = null;
            while (userName == null) {
                System.out.println("What is your name?");
                printLine();
                userName = in.nextLine();
                if (userName.isEmpty()) {
                    System.out.println("Invalid input. Please enter a valid name.");
                    userName = null;
                }
            }
            printHello(userName);
        displayHelp();

        return userName;
    }
    /**
     * Prints welcome message to an existing user.
     */
    public String printWelcomeToExistingUser() {
        final String gitlogo =
                "   ______   _  _________\n" +
                        " .' ___  | (_)|  _   _  |\n" +
                        "/ .'   \\_| __ |_/ | | \\_|\n" +
                        "| |   ____[  |    | |\n" +
                        "\\ `.___]  || |   _| |_\n" +
                        " `._____.'[___] |_____|";

        System.out.println(gitlogo + System.lineSeparator());

        System.out.println("Hello from GiT");
        userName = userInfo.getName();
        printHello(userName);
        displayHelp();

        return userName;
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

    public static String switchMode() throws GitException {
        System.out.println("What mode would you like to enter?");
        System.out.println("Please select a mode: " + "grocery, profile, calories or recipe:");
        String newMode = in.nextLine().trim();
        Mode mode;
        while (true) {
            try {
                mode = Mode.valueOf(newMode.toUpperCase());
                break;
            } catch (Exception e) {
                System.out.println("Please enter a valid mode:");
                newMode = in.nextLine().trim();
            }
        }
        displayCommands(newMode);
        return newMode;
    }

    /**
     * Displays help message containing all possible commands for grocery management.
     */
    public static void displayHelpForGrocery() {
        System.out.println(
                "Here are some ways you can manage your groceries!\n" +
                        "add GROCERY: adds the item GROCERY.\n" +
                        "addmulti: adds multiple items GROCERIES.\n" +
                        "find KEYWORD: finds all groceries containing the KEYWORD.\n" +
                        "loc LOCATION: adds a LOCATION to track.\n" +
                        "exp GROCERY d/EXPIRATION_DATE: edits the expiration date for GROCERY.\n" +
                        "cat GROCERY c/CATEGORY: edits the category for GROCERY.\n" +
                        "amt GROCERY a/AMOUNT: sets the amount of GROCERY.\n" +
                        "use GROCERY a/AMOUNT: updates the total amount after using a GROCERY.\n" +
                        "th GROCERY a/AMOUNT: edits the threshold amount of GROCERY.\n" +
                        "cost GROCERY $PRICE: edits the price of GROCERY.\n" +
                        "store GROCERY l/LOCATION: sets the location of GROCERY.\n" +
                        "rate GROCERY: rates and reviews GROCERY.\n" +
                        "remark GROCERY r/REMARK: updates the remark of the GROCERY.\n" +
                        "del GROCERY: deletes GROCERY.\n" +
                        "delloc LOCATION: removes LOCATION from tracking.\n" +
                        "list: shows list of all groceries you have.\n" +
                        "listcat: shows the list sorted by category.\n" +
                        "listcost: shows the list sorted by price.\n" +
                        "listexp: shows the list sorted by expiration date.\n" +
                        "listloc [LOCATION]: shows all locations, or all groceries stored in [LOCATION].\n" +
                        "expiring: shows a list of groceries that are expiring soon.\n" +
                        "low: shows a list of groceries that are low in stock.\n" +
                        "view GROCERY: view all the details of GROCERY.\n" +
                        "exit: exits the program.\n" +
                        "switch: switches the mode.\n" +
                        "help: view all the possible commands."
        );
    }

    /**
     * Displays help message containing all possible commands for calories management.
     */
    public static void displayHelpForCal() {
        System.out.println(
                "Here are some ways you can manage your calories intake!\n" +
                        "eat FOOD: adds the food that you have eaten.\n" +
                        "view: shows the food you have eaten and total calories intake.\n" +
                        "switch: switches the mode.\n" +
                        "exit: exits the program.\n" +
                        "help: view all the possible commands for calories management."
        );
    }

    /**
     * Displays help message containing all possible commands for profile management.
     */
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

    /**
     * Displays help message containing all possible commands for this app.
     */
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
     * Prints divider for user readability.
     */
    public static void printLine() {
        System.out.println(DIVIDER);
    }
}
