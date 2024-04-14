package food;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    public void print_success() {
        Food food = new Food("apple", 52);
        assertEquals("apple, with 52.0 calories", food.print());
    }
}