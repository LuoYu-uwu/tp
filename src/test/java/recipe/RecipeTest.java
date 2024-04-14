package recipe;

import exceptions.DuplicateException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RecipeTest {
    @Test
    public void getTitle_validDetails() {
        String title = "Egg";
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("egg");
        ingredients.add("salt");
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("Fry." );
        steps.add("Serve.");
        Recipe recipe = new Recipe(title, ingredients, steps);
        assertEquals("Egg", recipe.getTitle());
    }

    @Test
    public void editTitle_validDetails() {
        String title = "Egg";
        ArrayList<String> ingredients = new ArrayList<String>();
        ingredients.add("egg");
        ingredients.add("salt");
        ArrayList<String> steps = new ArrayList<String>();
        steps.add("Fry." );
        steps.add("Serve.");
        Recipe recipe = new Recipe(title, ingredients, steps);
        recipe.editTitle("Not egg");
        assertEquals("Not egg", recipe.getTitle());
    }
}
