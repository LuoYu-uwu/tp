package git;

import exceptions.GitException;
import grocery.Grocery;
import grocery.GroceryList;
import recipe.Recipe;
import recipe.RecipeList;
import user.UserInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import grocery.location.Location;
import java.time.format.DateTimeFormatter;


/**
 * Handles loading from and saving tasks to a file.
 */
public class Storage {
    private Grocery grocery;
    private List<Grocery> groceries;
    private GroceryList groceryList;
    private Recipe recipe;
    private RecipeList recipeList;
    private UserInfo userInfo;
    /**
     * Saves the current list of groceries to the file.
     * @param groceries The list of groceries to save.
     */
    public void saveGroceryFile(List<Grocery> groceries) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter("./data/groceryList.txt");
            for (Grocery grocery : groceries) {
                writer.write(grocery.toSaveFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving groceries.");
            e.printStackTrace();
        }
    }
    /**
     * Loads groceries from the file.
     * @return groceryList loaded from the file. If file does not exist, returns an empty groceryList.
     */
    public GroceryList loadGroceryFile(){
        GroceryList groceryList = new GroceryList();
        try {
            File file = new File("./data/groceryList.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Grocery grocery = parseGrocery(line);
                groceryList.addGrocery(grocery);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //System.out.println("No saved groceries found.\n ");
        }
        return groceryList;
    }
    /**
     * Parses a string from the file into a grocery object.
     * @param line The string to parse.
     * @return The parsed grocery object.
     */
    private Grocery parseGrocery(String line) {
        String[] parts = line.split(" \\| ");
        String name = parts[0].trim();
        int amount = parts[1].equalsIgnoreCase("null") ? 0 : Integer.parseInt(parts[1].trim());
        int threshold = parts[2].equalsIgnoreCase("null") ? 0 : Integer.parseInt(parts[2].trim());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expiration = parts[3].equalsIgnoreCase("null") ? null : LocalDate.parse(parts[3].trim(), formatter);
        String category = parts[4].equalsIgnoreCase("") ? "" : parts[4].trim();
        double cost = parts[5].equalsIgnoreCase("null") ? 0 : Double.parseDouble(parts[5].trim());
        Location location = parts[6].equalsIgnoreCase("null") ? null : new Location(parts[6].trim());
        return new Grocery(name, amount, threshold, expiration, category, cost, location);
    }
    /**
     * Saves the current list of recipes to the file.
     * @param recipeArr The list of recipes to save.
     */
    public void saveRecipeFile(ArrayList<Recipe> recipeArr) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter("./data/recipeList.txt");
            for (Recipe recipe : recipeArr) {
                writer.write(recipe.toRecipeSaveFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving recipes.");
            e.printStackTrace();
        }
    }
    /**
     * Loads recipes from the file.
     * @return recipeList loaded from the file. If file does not exist, returns an empty recipeList.
     */
    public RecipeList loadRecipeFile(){
        RecipeList recipeList = new RecipeList();
        try {
            File file = new File("./data/recipeList.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Recipe recipe = parseRecipe(line);
                recipeList.addRecipe(recipe);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            //System.out.println("No saved recipes found.\n ");
        }
        return recipeList;
    }
    /**
     * Parses a string from the file into a Recipe object.
     *
     * @param line The string to parse.
     * @return The parsed recipe object.
     */
    private Recipe parseRecipe(String line) {
        String[] parts = line.split(" \\| ");
        String title = parts[0].trim();
        String[] ingredientsArray = parts[1].equalsIgnoreCase("null") ? null : parts[1].split(", ");
        ArrayList<String> ingredientsList = new ArrayList<>(Arrays.asList(ingredientsArray));
        String[] stepsArray = parts[2].equalsIgnoreCase("null") ? null : parts[2].split(". ");
        ArrayList<String> stepsList = new ArrayList<>(Arrays.asList(stepsArray));
        return new Recipe(title, ingredientsList, stepsList);
    }
    /**
     * Saves the current user profile to the file.
     * @param userInfo The user profile to save.
     */
    public void saveProfileFile(UserInfo userInfo) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter("./data/userProfile.txt");
            writer.write(userInfo.toProfileSaveFormat());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving recipes.");
            e.printStackTrace();
        }
    }
    /**
     * Loads the user profile from the file.
     * @return userInfo loaded from the file. If file does not exist, returns an empty userInfo.
     */
    public UserInfo loadProfileFile(){
        UserInfo userInfo = new UserInfo();
        try {
            File file = new File("./data/userProfile.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                parseProfile(line, userInfo);
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            //System.out.println("No saved recipes found.\n ");
        } catch (GitException e) {
            throw new RuntimeException(e);
        }
        return userInfo;
    }
    /**
     * Parses a string from the file into a userInfo object.
     *
     * @param line The string to parse.
     * @param userInfo The UserInfo object to store the parsed information.
     */
    private void parseProfile(String line, UserInfo userInfo) throws GitException {
        String[] parts = line.split(": ");
        switch (parts[0]) {
            case "Name":
                userInfo.setName(parts[1]);
                break;
            case "Height":
                userInfo.setHeight(Double.parseDouble(parts[1]));
                break;
            case "Weight":
                userInfo.setWeight(Double.parseDouble(parts[1]));
                break;
            case "Age":
                userInfo.setAge(Integer.parseInt(parts[1]));
                break;
            case "Gender":
                userInfo.setGender(parts[1]);
                break;
            case "Aim":
                userInfo.setAim(parts[1]);
                break;
            case "Activeness":
                userInfo.setActiveness(parts[1]);
                break;
            case "Calories":
                userInfo.setCaloriesCapFromLoad(Integer.parseInt(parts[1]));
            default:
                break;
        }
    }
    /**
     * Checks if the user's profile file exists.
     *
     * @return True if the profile file exists, false otherwise.
     */
    public boolean isProfileSaved() {
        File profileFile = new File("./data/userProfile.txt");
        return profileFile.exists();
    }
}
