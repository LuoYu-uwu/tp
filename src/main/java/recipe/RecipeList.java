package recipe;

import exceptions.GitException;
import exceptions.emptyinput.EmptyInputException;
import exceptions.nosuch.NoSuchObjectException;
import git.RecipeUi;
import git.Storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeList {
    private ArrayList<Recipe> recipeArr;
    private Storage storage;
    /**
     * Constructs RecipeList with recipe as an empty ArrayList.
     */
    public RecipeList() {
        recipeArr = new ArrayList<>();
        this.storage = new Storage();

    }

    /**
     * Adds a recipe to the recipe list.
     * Parser will not allow duplicated recipe.
     * @param recipe Recipe to be added.
     */
    public void addRecipe(Recipe recipe) {
        try {
            recipeArr.add(recipe);
            RecipeUi.printRecipeAdded(recipe);
            storage.saveRecipeFile(recipeArr);
            assert recipeArr.contains(recipe) : "Grocery should be added to the list";
        } catch (NullPointerException e) {
            System.out.println("Failed to add recipe: the recipe is null.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the recipe: " + e.getMessage());
        }
    }

    /**
     * Lists all the user's recipes.
     */
    public void listRecipes() {
        int size = recipeArr.size();
        if (size == 0) {
            RecipeUi.printNoRecipe();
        } else {
            RecipeUi.printRecipeList(recipeArr);
        }
    }

    /**
     * Returns the desired recipe.
     *
     * @param title Title of the recipe.
     * @return The specific recipe.
     * @throws NoSuchObjectException If the selected grocery does not exist.
     */
    public Recipe getRecipe(String title) throws NoSuchObjectException {
        int index = -1;
        for (Recipe recipe : recipeArr) {
            if(recipe.getTitle().equalsIgnoreCase(title)) {
                index = recipeArr.indexOf(recipe);
                break;
            }
        }

        if (index != -1) {
            assert recipeArr != null : "Found grocery should not be null";
            return recipeArr.get(index);
        } else {
            throw new NoSuchObjectException("recipe");
        }
    }

    /**
     * Removes a recipe.
     *
     * @param title Recipe title from user input.
     * @throws GitException If recipe is empty.
     */
    public void removeRecipe(String title) throws GitException {
        if (title.isEmpty()) {
            throw new EmptyInputException("recipe");
        }

        Recipe currRecipe = getRecipe(title);
        recipeArr.remove(currRecipe);
        RecipeUi.printRecipeRemoved(currRecipe);
        storage.saveRecipeFile(recipeArr);
    }

    /**
     * Searches for recipes containing the given keyword.
     */
    public void findRecipe(String key) throws EmptyInputException {
        if (key.isEmpty()) {
            throw new EmptyInputException("keyword");
        }

        List<Recipe> relevantRecipe = new ArrayList<>();
        for (Recipe currRecipe : recipeArr) {
            if(currRecipe.getTitle().toLowerCase().contains(key.toLowerCase())) {
                relevantRecipe.add(currRecipe);
            }
        }

        RecipeUi.printRecipesFound(relevantRecipe, key);
    }

    /**
     * Checks if a recipe exists.
     *
     * @param title Title of the recipe
     * @return True if the recipe exists, false otherwise.
     */
    public boolean isRecipeExists(String title) {
        for (Recipe currRecipe : recipeArr) {
            if (currRecipe.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an existing grocery.
     *
     * @param title The title of the recipe to be edited.
     * @throws GitException is input is not valid
     */
    public void editRecipe(String title, String editPart, String editLine) throws GitException {
        Recipe currRecipe = getRecipe(title);
        if (editPart.equalsIgnoreCase("title")) {
            currRecipe.editTitle(editLine);
        } else if (editPart.equalsIgnoreCase("ingredients")) {
            String[] ingredientsList = editLine.split("[,]");
            ArrayList<String> ingredientsArr = new ArrayList<String>(Arrays.asList(ingredientsList));
            currRecipe.editIngredients(ingredientsArr);
        } else if (editPart.equalsIgnoreCase("steps")) {
            String[] stepsList = editLine.split("[.]");
            ArrayList<String> stepsArr = new ArrayList<String>(Arrays.asList(stepsList));
            currRecipe.editSteps(stepsArr);
        }
        System.out.println("This is the edited recipe:");
        currRecipe.viewRecipe();
        storage.saveRecipeFile(recipeArr);
    }
}
