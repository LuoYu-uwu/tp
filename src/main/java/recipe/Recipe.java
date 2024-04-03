package recipe;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private ArrayList<String> ingredients;

    public Recipe(String title, ArrayList<String> ingredients) {
        this.title = title;
        this.ingredients = ingredients;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void viewRecipe() {
        System.out.println("Recipe title: " + title);
        System.out.println("Ingredients: ");
        for (String currIng : ingredients) {
            System.out.println("- " + currIng);
        }
    }
}
