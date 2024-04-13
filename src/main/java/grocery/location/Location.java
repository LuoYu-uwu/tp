package grocery.location;

import git.GroceryUi;
import grocery.Grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a location to store groceries.
 */
public class Location {
    private String name;
    private List<Grocery> groceries;

    /**
     * Constructs Location.
     */
    public Location (String name) {
        this.name = name;
        groceries = new ArrayList<>();
    }
    /**
     * Adds a grocery to this location.
     */
    public void addGrocery(Grocery grocery) {
        groceries.add(grocery);
    }

    /**
     * Removes the specified grocery from this location.
     */
    public void removeGrocery(Grocery grocery) {
        groceries.remove(grocery);
    }

    /**
     * Lists all groceries stored at this location.
     */
    public void listGroceries() {
        System.out.println("Viewing location: " + name);
        if (groceries.isEmpty()) {
            GroceryUi.printNoGrocery();
        } else {
            GroceryUi.printGroceryList(groceries);
        }
    }

    /**
     * Sets the location attribute of all stored groceries to NULL.
     * Called when a location is being removed from tracking.
     */
    public void clearLocation() {
        for (Grocery grocery : groceries) {
            grocery.setLocation(null);
        }
    }

    /**
     * Gets name of the location.
     */
    public String getName() {
        return name;
    }
}
