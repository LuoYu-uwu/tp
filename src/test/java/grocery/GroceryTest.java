package grocery;

import java.time.LocalDate;

import grocery.location.Location;
import org.junit.jupiter.api.Test;

import exceptions.PastExpirationDateException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroceryTest {
    @Test
    public void setExpiration_validDate() throws PastExpirationDateException {
        Grocery grocery = new Grocery("Milk");
        grocery.setExpiration("2024-12-31");
        assertEquals(LocalDate.parse("2024-12-31"), grocery.getExpiration(), 
            "Expiration date should be set correctly.");
    }

    @Test
    public void setExpiration_pastDate_throwsException() {
        Grocery grocery = new Grocery("Milk");
        // Adjust the exception type to match the actual implementation
        assertThrows(PastExpirationDateException.class, () -> grocery.setExpiration("2020-01-01"),
                     "Should throw PastExpirationDateException for past dates.");
    }

    @Test
    public void setAmount_positiveAmount() {
        Grocery grocery = new Grocery("Water", 0, 0, LocalDate.parse("2024-12-31"), "Beverage", 1.00, null);
        grocery.setAmount(10);
        assertEquals(10, grocery.getAmount(), "Amount should be set correctly.");
    }

    @Test
    public void setAmount_negativeAmount_throwsAssertionError() {
        Grocery grocery = new Grocery("Water");
        assertThrows(AssertionError.class, () -> grocery.setAmount(-5),
                     "Should throw AssertionError for negative amount.");
    }

    @Test
    public void setCost_positiveValue() {
        Grocery grocery = new Grocery("Butter", 5, 3, LocalDate.now(), "Dairy", 0, null);
        grocery.setCost(3.99);
        assertEquals(3.99, grocery.getCost(), "Cost should be set correctly.");
    }

    @Test
    public void setCost_negativeValue_throwsAssertionError() {
        Grocery grocery = new Grocery("Butter");
        assertThrows(AssertionError.class, () -> grocery.setCost(-1.00),
                     "Should throw AssertionError for negative cost.");
    }

    @Test
    public void isLow_belowThreshold_true() {
        Grocery grocery = new Grocery("Eggs", 1, 5, LocalDate.parse("2024-12-31"), "Poultry", 1.50, null);
        assertTrue(grocery.isLow(), "Should return true as the amount is below the threshold.");
    }

    @Test
    public void isLow_aboveThreshold_false() {
        Grocery grocery = new Grocery("Eggs", 10, 5, LocalDate.parse("2024-12-31"), "Poultry", 1.50, null);
        assertFalse(grocery.isLow(), "Should return false as the amount is above the threshold.");
    }
    
    @Test
    public void printGrocery_noAmountNoExpiration_leaveEmpty() {
        Grocery grocery = new Grocery("apple", 0, 0, null, "fruit", 0, new Location("Pantry"));
        String message = "apple (fruit), amount: 0 pieces, " +
                "cost: $0.00, location: Pantry";
        assertEquals(message, grocery.printGrocery());
    }

    @Test
    public void printGrocery_costWrongFormat_formattedCost() {
        Grocery grocery = new Grocery("chicken", 1, 0, LocalDate.now().plusDays(1), "meat",1,new Location("Pantry"));
        String message = "chicken (meat)" + ", amount: 1 grams" + ", expiration: "
                + LocalDate.now().plusDays(1) + ", cost: $1.00, location: Pantry";
        assertEquals(message, grocery.printGrocery());
    }

    @Test
    public void printGrocery_correctAmtAndExpAndCost() {
        Grocery grocery = new Grocery("chicken", 1, 0, LocalDate.now().plusDays(1), "meat",1.20,new Location("Pantry"));
        String message = "chicken (meat)" + ", amount: 1 grams" + ", expiration: "
                + LocalDate.now().plusDays(1) + ", cost: $1.20, location: Pantry";
        assertEquals(message, grocery.printGrocery());
    }
}
