package git;

import java.util.Scanner;

public class ProfileUi {
    // ATTRIBUTES
    public static final String DIVIDER = "- - - - -";
    private static Scanner in;
    private static final double MAX_HEIGHT = 280;
    private static final double MAX_WEIGHT = 370;
    private static final double MAX_AGE = 160;

    // METHODS
    /**
     * Constructs Ui and initialises Scanner to read input.
     */
    public ProfileUi() {
        in = new Scanner(System.in);
    }

    //@@author LuoYu-uwu
    /**
     * Prompts user for a name.
     *
     * @return The entered valid name or empty.
     */
    public String promptForName() {
        System.out.println("Please enter your name");
        String name = "";
        for (int i = 0; i < 5; i++) {
            name = in.nextLine().trim();
            if (name.isBlank()) {
                if (i == 4) {
                    System.out.println("Failed to enter valid name, " +
                            "name will be stored as empty");
                } else {
                    System.out.println("Please enter a valid name");
                }
            } else {
                break;
            }
        }
        return name;
    }

    /**
     * Prompts user for weight.
     *
     * @return The entered valid weight or 0.
     */
    public double promptForWeight() {
        System.out.println("Please enter your weight in KG:");
        double weight = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                weight = Double.parseDouble(input);
                if (weight > 0 && weight < MAX_WEIGHT) {
                    break;
                } else {
                    weight = 0;
                    System.out.println("Weight entered is invalid!");
                    System.out.println("Please enter your weight in KG:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Weight entered is invalid!");
                if(i == 4) {
                    System.out.println("Failed to enter valid weight, " +
                            "weight will be stored as 0");
                } else {
                    System.out.println("Please enter your weight in KG:");
                }
            }
        }
        return weight;
    }

    /**
     * Prompts user for height.
     *
     * @return The entered valid height or 0.
     */
    public double promptForHeight() {
        System.out.println("Please enter your height in cm:");
        double height = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                height = Double.parseDouble(input);
                if (height > 0 && height < MAX_HEIGHT){
                    break;
                } else {
                    height = 0;
                    System.out.println("Height entered is invalid!");
                    System.out.println("Please enter your height in cm:");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Height entered is invalid!");
                if(i == 4) {
                    System.out.println("Failed to enter valid height, " +
                            "height will be stored as 0");
                } else {
                    System.out.println("Please enter your height in cm:");
                }
            }
        }
        return height;
    }

    /**
     * Prompts user for age.
     *
     * @return The entered valid age or 0.
     */
    public int promptForAge() {
        System.out.println("Please enter your age in years (nearest whole number):");
        int age = 0;
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            try {
                age = Integer.parseInt(input);
                if (age > 0 && age < MAX_AGE){
                    break;
                } else {
                    age = 0;
                    System.out.println("Age entered is invalid!");
                    System.out.println("Please enter your age in years (nearest whole number):");
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Age entered is invalid!");
                if(i == 4) {
                    System.out.println("Failed to enter valid age, " +
                            "age will be stored as 0");
                } else {
                    System.out.println("Please enter your age in years " +
                            "(nearest whole number):");
                }
            }
        }
        return age;
    }

    /**
     * Prompts user for gender.
     *
     * @return The entered valid gender or empty.
     */
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
                if (i == 4) {
                    System.out.println("Failed to enter valid gender, " +
                            "gender will be stored as empty");
                } else {
                    System.out.println("Please enter your gender (e.g. F):");
                }
            }
        }
        return gender;
    }

    /**
     * Prompts user for aim.
     *
     * @return The entered valid aim or empty.
     */
    public String promptForAim() {
        System.out.println("Please enter your weight aim (e.g. lose/maintain/gain):");
        String aim = "";
        for (int i = 0; i < 5; i++) {
            String input = in.nextLine().trim();
            if (input.equalsIgnoreCase("lose")
                    || input.equalsIgnoreCase("maintain")
                    || input.equalsIgnoreCase("gain")) {
                aim = input;
                break;
            } else {
                System.out.println("Aim entered is invalid!");
                if (i == 4) {
                    System.out.println("Failed to enter valid aim, " +
                            "aim will be stored as empty");
                } else {
                    System.out.println("Please enter your aim (e.g. lose/maintain/gain):");
                }
            }
        }
        return aim;
    }

    /**
     * Prompts user for activeness.
     *
     * @return The entered valid activeness or empty.
     */
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
                System.out.println("Activeness entered is invalid!");
                if (i == 4) {
                    System.out.println("Failed to enter valid activeness, " +
                            "activeness will be stored as empty");
                } else {
                    System.out.println("Please enter your activeness " +
                            "(e.g. inactive/light/moderate/active/very):");
                }
            }
        }
        return activeness;
    }
    //@@author LuoYu-uwu
}
