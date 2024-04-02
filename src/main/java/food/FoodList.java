package food;

import exceptions.commands.EmptyFoodException;
import git.Ui;
import java.util.ArrayList;
import java.util.List;

public class FoodList {
    private List<Food> foods;

    /**
     * Constructs GroceryList.
     */
    public FoodList() {
        foods = new ArrayList<>();
    }

    public void addFood(Food food) throws EmptyFoodException {
        if (food.getName() == null) {
            throw new EmptyFoodException();
        }

        try {
            foods.add(food);
            Ui.printFoodAdded(food);
            assert foods.contains(food) : "Food should be added to the list";
        } catch (NullPointerException e) {
            System.out.println("Failed to add food: the food collection is null.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the food: " + e.getMessage());
        }
    }

    public void printFoods() {
        assert !foods.isEmpty() : "food list should not be empty";
        System.out.println("Here are the food you have consumed today:");
        for (Food food: foods) {
            System.out.println(" - " + food.print());
        }
    }
}
