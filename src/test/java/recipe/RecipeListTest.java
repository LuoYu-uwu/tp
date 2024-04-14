package recipe;

import exceptions.nosuch.NoSuchObjectException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RecipeListTest {
    @Test
    public void addRecipe_success() throws NoSuchObjectException {
        String title = "Egg";
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("egg");
        ingredients.add("salt");
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("Fry." );
        steps.add("Serve.");
        Recipe recipe = new Recipe(title, ingredients, steps);
        RecipeList recipeArr = new RecipeList();
        recipeArr.addRecipe(recipe);
        assertEquals(recipe, recipeArr.getRecipe("Egg"));
    }

    @Test
    public void getRecipe_NoSuchObjectException(){
        String title = "Egg";
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("egg");
        ingredients.add("salt");
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("Fry." );
        steps.add("Serve.");
        Recipe recipe = new Recipe(title, ingredients, steps);
        RecipeList recipeArr = new RecipeList();
        assertThrows(NoSuchObjectException.class, () -> recipeArr.getRecipe("egg"), "No Such Recipe");
    }
}
