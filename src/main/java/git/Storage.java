package git;

import exceptions.GitException;
import exceptions.emptyinput.EmptyInputException;
import exceptions.nosuch.NoSuchObjectException;
import grocery.Grocery;
import grocery.GroceryList;
import grocery.location.LocationList;
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
     *     If file is corrupted, wipe file.
     *
     */
    public GroceryList loadGroceryFile(){
        GroceryList groceryList = new GroceryList();
        try {
            File file = new File("./data/groceryList.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Grocery grocery = parseGrocery(line);
                    Location location = grocery.getLocation();
                    if (grocery != null) { //if not corrupted
                        if (location != null){
                            location.addGrocery(grocery);
                        }
                        groceryList.addGrocery(grocery);
                    } else {
                        wipeFile(file);
                        return new GroceryList();
                    }
                } catch (Exception e) {
                    wipeFile(file);
                    return new GroceryList();
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            //System.out.println("No saved groceries found.\n ");
        }
        return groceryList;
    }
    /**
     * Parses a string from the file into a grocery object.
     * @param line The string to parse.
     * @return The parsed grocery object. Returns null if file is corrupted.
     */
    private Grocery parseGrocery(String line) throws EmptyInputException {
        String[] parts = line.split(" \\| ");
        if (parts.length != 7) {
            return null;
        } else {
            String name = parts[0].trim();
            int amount = parts[1].equalsIgnoreCase("null") ? 0 : Integer.parseInt(parts[1].trim());
            int threshold = parts[2].equalsIgnoreCase("null") ? 0 : Integer.parseInt(parts[2].trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expiration = parts[3].equals("null") ? null : LocalDate.parse(parts[3].trim(), formatter);
            String category = parts[4].equalsIgnoreCase("") ? "" : parts[4].trim().toUpperCase();
            double cost = parts[5].equalsIgnoreCase("null") ? 0 : Double.parseDouble(parts[5].trim());
            Location location = null;
            String locString = parts[6].strip();
            if (!locString.equalsIgnoreCase("null")) {
                try {
                    location = LocationList.findLocation(locString);
                } catch (NoSuchObjectException e) {
                    try {
                        LocationList.addLocation(locString);
                        location = LocationList.findLocation(locString);
                    } catch (GitException ignore) {
                        assert !locString.isBlank() : "No empty strings at this point.";
                    }
                }
            }
            return new Grocery(name, amount, threshold, expiration, category, cost, location);
        }
    }
    /**
     * Wipes the contents of the specified file.
     *
     * @param file The file to wipe.
     */
    private void wipeFile(File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while wiping the file: " + file.getName());
            e.printStackTrace();
        }
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
                try {
                    Recipe recipe = parseRecipe(line);
                    if (recipe != null) {
                        recipeList.addRecipe(recipe);
                    } else {
                        wipeFile(file);
                        return new RecipeList();
                    }
                } catch (Exception e) {
                    wipeFile(file);
                    return new RecipeList();
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
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
        if (parts.length != 3) {
            return null;
        } else {
            String title = parts[0].trim();
            String[] ingredientsArray = parts[1].equalsIgnoreCase("null") ? null : parts[1].split(", ");
            ArrayList<String> ingredientsList = new ArrayList<>(Arrays.asList(ingredientsArray));
            String[] stepsArray = parts[2].equalsIgnoreCase("null") ? null : parts[2].split(". ");
            ArrayList<String> stepsList = new ArrayList<>(Arrays.asList(stepsArray));
            return new Recipe(title, ingredientsList, stepsList);
        }
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
            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineCount ++;
                if (!parseProfile(line, userInfo)) { //if corrupted
                    wipeFile(file);
                    return new UserInfo();
                }
            }
            scanner.close();
            if (lineCount != 8){
                wipeFile(file);
                return new UserInfo();
            }
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
    private boolean parseProfile(String line, UserInfo userInfo) throws GitException {
        String[] parts = line.split(": ");
        if (parts.length != 2) {
            return false; // Line is corrupted
        }
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
            break;
        default:
            break;
        }
        return true;
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
