package food;

import git.GroceryUi;
import java.util.ArrayList;
import java.util.List;

public class FoodList {
    private List<Food> foods;

    /**
     * Constructs FoodList.
     */
    public FoodList() {
        foods = new ArrayList<>();
    }

    public List<Food> getFoods() {
        return foods;
    }

    /**
     * Adds a new food into the list of food.
     *
     * @param food New consumed food.
     */
    public void addFood(Food food) {
        try {
            foods.add(food);
            GroceryUi.printFoodAdded(food);
            assert foods.contains(food) : "Food should be added to the list";
        } catch (NullPointerException e) {
            System.out.println("Failed to add food: the food collection is null.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the food: " + e.getMessage());
        }
    }

    /**
     * Prints the list of food in the list.
     */
    public void printFoods() {
        if (foods.isEmpty()) {
            System.out.println("You have not consumed any food today");
        } else {
            System.out.println("Here are the food you have consumed today:");
            for (Food food : foods) {
                System.out.println(" - " + food.print());
            }
        }
    }
}
