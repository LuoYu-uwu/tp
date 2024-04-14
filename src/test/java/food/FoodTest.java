package food;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    public void print_success() {
        Food food = new Food("apple", 52);
        assertEquals("apple, with 52.0 calories", food.print());
    }

    @Test
    public void print_foodHasInvalidName_throwsAssertionError() {
        Food food = new Food("", 52);
        assertThrows(AssertionError.class, food::print,
                "Should throw AssertionError for negative cost.");
    }
}