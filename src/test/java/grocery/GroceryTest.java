package grocery;

import java.time.LocalDate;

import grocery.location.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GroceryTest {
    @Test
    public void printGrocery_noAmountNoExpiration_leaveEmpty() {
        Grocery grocery = new Grocery("apple", 0, 0, null, "fruit", 0,new Location("Pantry"));
        String message = "apple (fruit), amount not set, expiration date not set, " +
                "cost: $0.00, location: Pantry, remark not set";
        assertEquals(message, grocery.printGrocery());
    }

    @Test
    public void printGrocery_costWrongFormat_formattedCost() {
        Grocery grocery = new Grocery("chicken", 1, 0, LocalDate.now().plusDays(1), "meat",1,new Location("Pantry"));
        String message = "chicken (meat)" + ", amount: 1 grams" + ", expiration: "
                + LocalDate.now().plusDays(1) + ", cost: $1.00, location: Pantry, remark not set";
        assertEquals(message, grocery.printGrocery());
    }

    @Test
    public void printGrocery_correctAmtAndExpAndCost() {
        Grocery grocery = new Grocery("chicken", 1, 0, LocalDate.now().plusDays(1), "meat",1.20,new Location("Pantry"));
        String message = "chicken (meat)" + ", amount: 1 grams" + ", expiration: "
                + LocalDate.now().plusDays(1) + ", cost: $1.20, location: Pantry, remark not set";
        assertEquals(message, grocery.printGrocery());
    }

}
