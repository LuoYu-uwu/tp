package recipe;

import git.Ui;
import java.util.ArrayList;
import java.util.List;

public class RecipeList {
    private ArrayList<Recipe> recipeArr;

    /**
     * Constructs RecipeList with recipe as an empty ArrayList.
     */
    public RecipeList() {
        recipeArr = new ArrayList<>();
    }

    /**
     * Adds a recipe to the recipe list.
     *
     * @param recipe Recipe to be added.
     */
    public void addRecipe(Recipe recipe) {
        try {
            recipeArr.add(recipe);
            Ui.printRecipeAdded(recipe);
            assert recipeArr.contains(recipe) : "Grocery should be added to the list";
        } catch (NullPointerException e) {
            System.out.println("Failed to add recipe: the recipe is null.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the recipe: " + e.getMessage());
        }

    }

}
