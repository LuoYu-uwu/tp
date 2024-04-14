package git;

import exceptions.GitException;
import grocery.Grocery;
import grocery.location.Location;
import grocery.location.LocationList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void parseGrocery_success() {
        String groceryString = "Meat | 0 | null | 2024-04-14 | Meat | 0.00 | bottom freezer ";
        Storage storage = new Storage();
        Grocery grocery = storage.parseGrocery(groceryString);
        String expectedGrocery = "Meat (MEAT), amount: 0 grams, expiration: 2024-04-14, cost: $0.00, location: bottom freezer";
        assertEquals(expectedGrocery, grocery.printGrocery());
    }

    @Test
    public void parseGrocery_invalidFormat_returnsNull() {
        String groceryString = "Meat | 0 | null | 2024 | bottom freezer ";
        Storage storage = new Storage();
        Grocery grocery = storage.parseGrocery(groceryString);
        assertNull(grocery);
    }

    @Test
    public void parseGroceryLocation_success() {
        String locString = "burger stand";
        try {
            LocationList.addLocation(locString);
            Location expectedLocation = LocationList.findLocation(locString);

            Storage storage = new Storage();
            Location actualLocation = storage.parseGroceryLocation(locString);
            assertEquals(expectedLocation.getName(), actualLocation.getName());
        } catch (GitException ignore) {
            fail("parseGroceryLocation should not fail.");
        }
    }

    @Test
    public void parseGroceryLocation_noLocation_returnsNull() {
        String locString = "null";
        Storage storage = new Storage();
        Location location = storage.parseGroceryLocation(locString);
        assertNull(location);
    }
}
