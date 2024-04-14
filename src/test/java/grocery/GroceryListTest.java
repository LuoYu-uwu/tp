package grocery;

import exceptions.SameLocationException;
import exceptions.commands.CommandWrongFormatException;
import exceptions.CannotUseException;
import exceptions.GitException;

import exceptions.emptyinput.EmptyInputException;
import exceptions.invalidinput.InvalidAmountException;
import exceptions.nosuch.NoSuchObjectException;
import grocery.location.Location;
import grocery.location.LocationList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class GroceryListTest {
    @Test
    public void addGrocery_success() {
        GroceryList gl = new GroceryList();
        Grocery grocery = new Grocery("Apples", 10, 5, LocalDate.of(2024, 12, 31), "Fruit", 2.99, null);
        gl.addGrocery(grocery);
        assertTrue(gl.getGroceries().contains(grocery), "Grocery should be added to the list.");
    }

    @Test
    public void addGrocery_throwNULL_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery(null));
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (NullPointerException e) {
            assertNull(e.getMessage());
        }
    }

    @Test
    public void isGroceryExists_true() {
        GroceryList gl = new GroceryList();
        gl.addGrocery(new Grocery("Bananas"));
        assertTrue(gl.isGroceryExists("Bananas"), "Grocery should exist in the list.");
    }

    @Test
    public void isGroceryExists_false() {
        GroceryList gl = new GroceryList();
        assertFalse(gl.isGroceryExists("Bananas"), "Grocery should not exist in the list.");
    }

    @Test
    public void editExpiration_success() {
        GroceryList gl = new GroceryList();
        try {
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(),"Meat", 0, new Location("Freezer")));
            gl.editExpiration("Meat d/2024-07-19");
        } catch (GitException e) {
            fail("editExpiration should not throw an exception");
        }
    }

    @Test
    public void listGroceries_emptyList() {
        GroceryList gl = new GroceryList();
        gl.listGroceries();
        assertTrue(gl.getGroceries().isEmpty(), "There should be no groceries to list.");
    }

    @Test
    public void listGroceries_containsItems() {
        GroceryList gl = new GroceryList();
        gl.addGrocery(new Grocery("Potatoes", 50, 20, LocalDate.of(2024, 11, 30), "Vegetable", 0.25, null));
        gl.listGroceries();
        assertFalse(gl.getGroceries().isEmpty(), "Grocery list should contain items.");
    }

    @Test
    public void editExpiration_noSuchGrocery_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.editExpiration("nonexistentGrocery d/2024-07-19");
            fail("Expected NoSuchGroceryException not thrown");
        } catch (NoSuchObjectException e) {
            assertEquals("The grocery (nonexistentGrocery) does not exist!", e.getMessage());
        } catch (GitException e) {
            fail("Expected NoSuchGroceryException, but another GitException was thrown");
        }
    }

    @Test
    public void editExpiration_wrongFormat_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, new Location("Freezer")));
            gl.editExpiration("Meat d/2024-07-19");
        } catch (GitException e) {
            String message = "Command is in the wrong format, type \"help\" for more information." +
                    System.lineSeparator() +
                    "exp needs 'd/'";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void editCategory_nonExistingGrocery_throwsException() {
        GroceryList gl = new GroceryList();
        Exception exception = assertThrows(NoSuchObjectException.class, () -> gl.editCategory("Milk c/Beverage"));
        assertTrue(exception.getMessage().contains("does not exist"), 
            "Expected NoSuchObjectException for non-existing grocery.");
    }


    @Test
    public void removeGrocery_groceryDelete_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("fooood", 0, 0, null, "Meat", 0,new Location("Freezer")));
            gl.removeGrocery("food");
            fail("Expected NoSuchGroceryException not thrown");
        } catch (GitException e) {
            // NoSuchGroceryException
            assertEquals("The grocery (food) does not exist!", e.getMessage());
        }
    }

    @Test
    public void editAmount_success() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, new Location("Freezer")));
            gl.editAmount("Meat a/5", false);
            assertEquals(gl.getGrocery("Meat").getAmount(), 5);
        } catch (GitException e) {
            fail("Test should not fail.");
        }
    }

    @Test
    public void editAmount_noSuchGrocery_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.editAmount("Meat", false);
        } catch (NoSuchObjectException e) {
            String expectedMessage = "The grocery (Meat) does not exist!";
            assertEquals(expectedMessage.trim(), e.getMessage().trim());
        } catch (GitException e) {
            fail("Expected a NoSuchObjectException, but another GitException was thrown");
        }
    }

    @Test
    public void editAmount_wrongFormat_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, new Location("Freezer")));
            gl.editAmount("Meat", false);
            fail("Expected a WrongFormatException to be thrown");
        } catch (CommandWrongFormatException e) {
            String expectedMessage = "Command is in the wrong format, type \"help\" for more information." +
                    System.lineSeparator() +
                    "amt needs 'a/'";
            assertEquals(expectedMessage.trim(), e.getMessage().trim());
        } catch (GitException e) {
            fail("Expected a WrongFormatException, but another GitException was thrown");
        }
    }

    @Test
    public void editAmount_negativeInteger_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, new Location("Freezer")));
            gl.editAmount("Meat a/-5", false);
        } catch (InvalidAmountException e) {
            String expectedMessage = "Please input a valid integer that is greater than 0!";
            assertEquals(expectedMessage.trim(), e.getMessage().trim());
        } catch (GitException e) {
            fail("Expected a InvalidAmountException, but another GitException was thrown");
        }
    }

    @Test
    public void editAmountUseTrue_amountReaches0_success() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 5, 0, LocalDate.now(), "Meat", 0, new Location("Freezer")));
            gl.editAmount("Meat a/5", true);
        } catch (GitException e) {
            fail("editAmount_useTrue should not throw an exception");
        }
    }
    

    @Test
    public void editAmountUseTrue_noAmountCannotUse_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0,new Location("Freezer")));
            gl.editAmount("Meat a/5", true);
            fail("Expected a CannotUseException to be thrown");
        } catch (CannotUseException e) {
            String expectedMessage = "The grocery you want to use is already out of stock - time to replenish!";
            assertEquals(expectedMessage, e.getMessage());
        } catch (GitException e) {
            fail("Expected a CannotUseException, but another GitException was thrown");
        }
    }

    @Test
    public void editLocation_noSuchLocation_success() {
        try {
            GroceryList gl = new GroceryList();
            LocationList.addLocation("Cold part");
            Location location = LocationList.findLocation("Cold part");
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, location));
            gl.editLocation("Meat l/bottom freezer");
            String finalLocation = gl.getGrocery("Meat").getLocation().getName();
            assertEquals(finalLocation, "bottom freezer");
        } catch (GitException ignore) {
            fail("Location should be automatically created, so no exception thrown.");
        }
    }

    @Test
    public void editLocation_sameLocation_exceptionThrown() {
        try {
            GroceryList gl = new GroceryList();
            LocationList.addLocation("Freezer");
            Location location = LocationList.findLocation("Freezer");
            gl.addGrocery(new Grocery("Meat", 0, 0, LocalDate.now(), "Meat", 0, location));
            gl.editLocation("Meat l/freezer");
        } catch (SameLocationException e) {
            String expectedMessage = "Meat is already stored in Freezer.";
            assertEquals(expectedMessage, e.getMessage());
        } catch (GitException ignore) {
            fail("Should throw SameLocationException.");
        }
    }

    @Test
    public void testSortByExpiration() {
        // Create a grocery list instance
        GroceryList gl = new GroceryList();

        // Create and add groceries with various expiration dates
        Grocery grocery1 = new Grocery("Milk", 0, 0, LocalDate.parse("2024-04-10"), "dairy", 0, null);
        Grocery grocery2 = new Grocery("Bread", 0, 0, LocalDate.parse("2024-04-20"), "baked", 0, null);
        Grocery grocery3 = new Grocery("Eggs"); // No expiration date set

        gl.addGrocery(grocery1);
        gl.addGrocery(grocery2);
        gl.addGrocery(grocery3);

        // Sort the groceries by expiration
        gl.sortByExpiration();

        // Get the sorted list of groceries
        List<Grocery> sortedGroceries = gl.getGroceries();

        // Assertions to check if the groceries are sorted correctly
        assertEquals(grocery1, sortedGroceries.get(0), "Milk should be first as it expires first.");
        assertEquals(grocery2, sortedGroceries.get(1), "Bread should be second as it expires next.");
        assertEquals(grocery3, sortedGroceries.get(2), "Eggs should be last as it has no expiration date.");
    }

    @Test
    public void findGroceries_emptyInput_exceptionThrown() {
        GroceryList gl = new GroceryList();
        assertThrows(EmptyInputException.class, () -> gl.findGroceries(""));
    }

    @Test
    public void removeGrocery_success() throws GitException {
        GroceryList gl = new GroceryList();
        Grocery grocery = new Grocery("Oranges", 20, 10, LocalDate.of(2024, 12, 31), "Fruit", 0.99, null);
        gl.addGrocery(grocery);
        gl.removeGrocery("Oranges");
        assertFalse(gl.getGroceries().contains(grocery), "Grocery should be removed from the list.");
    }

    @Test
    public void removeGrocery_nonExisting_throwsException() {
        GroceryList gl = new GroceryList();
        assertThrows(NoSuchObjectException.class, () -> gl.removeGrocery("Oranges"), 
            "Should throw an exception for non-existing grocery.");
    }

}
