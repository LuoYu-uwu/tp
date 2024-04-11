package git;

import exceptions.PastExpirationDateException;
import exceptions.nosuch.NoSuchObjectException;
import grocery.Grocery;
import grocery.GroceryList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import grocery.location.Location;
import grocery.location.LocationList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Handles loading from and saving tasks to a file.
 */
public class Storage {
    private Grocery grocery;
    private List<Grocery> groceries;
    private GroceryList groceryList;
    private static final String FILE_PATH = "./data/groceryList.txt";
    /**
     * Loads groceries from the file.
     * @return groceryList loaded from the file. If file does not exist, returns an empty groceryList.
     */
    public GroceryList loadFile(){
        GroceryList groceryList = new GroceryList();
        try {
            File file = new File(FILE_PATH);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                //                --------------------------------
                Grocery grocery = parseGrocery(line);
                System.out.println(grocery);
                groceryList.addGrocery(grocery);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved groceries found.\n ");
        }
        return groceryList;
    }
    /**
     * Parses a string from the file into a grocery object.
     *
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
        //return new Grocery(name, 0, 0, null, "", 0, null);
    }

    /**
     * Saves the current list of groceries to the file.
     *
     * @param groceries The list of groceries to save.
     */
    public static void saveFile(List<Grocery> groceries) {
        try {
            File directory = new File("./data");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            FileWriter writer = new FileWriter(FILE_PATH);
            for (Grocery grocery : groceries) {
                writer.write(grocery.toSaveFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving groceries.");
            e.printStackTrace();
        }
    }
}
