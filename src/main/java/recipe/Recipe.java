package recipe;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private ArrayList<String> ingredients;

    private ArrayList<String> steps;

    /**
     * Constructs a Recipe.
     *
     * @param title Title of the recipe.
     * @param ingredients Ingredients to be stored in the recipe.
     * @param steps Steps to be stored in the recipe.
     */
    public Recipe(String title, ArrayList<String> ingredients, ArrayList<String> steps) {
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Prints the title, ingredients and steps of the recipe.
     */
    public void viewRecipe() {
        System.out.println("Recipe title: " + title + "\n");
        System.out.println("Ingredients: ");
        for (String currIngredient : ingredients) {
            System.out.println("- " + currIngredient.trim());
        }
        System.out.println("\nSteps: ");
        int index = 1;
        for (String currStep : steps) {
            System.out.println(index + ": " + currStep.trim());
            index += 1;
        }
    }
    /**
     * Returns the title, ingredients and steps of the recipe for saving.
     *
     * @return String representation of the Recipe.
     */
    public String toRecipeSaveFormat (){
        assert !(this.title.isEmpty()) : "Recipe does not exist!!";

        String ingredientsString;
        if (this.ingredients != null) {
            ingredientsString = String.join(", ", ingredients);
        } else {
            ingredientsString = "null";
        }

        String stepsString;
        if (this.steps != null){
            stepsString = String.join(". ", steps);
        } else {
            stepsString = "null";
        }
        return this.title + " | " + ingredientsString + " | " + stepsString;
    }
}
