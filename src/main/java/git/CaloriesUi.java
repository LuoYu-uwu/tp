package git;

import java.util.Scanner;

public class CaloriesUi {

    // ATTRIBUTES
    public static final String DIVIDER = "- - - - -";
    private static Scanner in;

    // METHODS
    /**
     * Constructs Ui and initialises Scanner to read input.
     */
    public CaloriesUi() {
        in = new Scanner(System.in);
    }

    //@@author LuoYu-uwu
    /**
     * Prompts user for calories of the food.
     *
     * @return The calories of the consumed food.
     */
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
    //@@author LuoYu-uwu
}
