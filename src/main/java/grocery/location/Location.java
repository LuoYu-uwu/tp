package grocery.location;

import git.Ui;
import grocery.Grocery;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a location to store groceries.
 */
public class Location {
    // ATTRIBUTES
    private String name;
    private List<Grocery> groceries;

    // METHODS
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
            Ui.printNoGrocery();
        } else {
            Ui.printGroceryList(groceries);
        }
    }

    public String getName() {
        return name;
    }

}
