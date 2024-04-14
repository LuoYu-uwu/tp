package food;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodListTest {
    @Test
    void addFood_success() {
        FoodList fl= new FoodList();
        Food food = new Food("apple", 52);
        fl.addFood(food);
        assertTrue(fl.getFoods().contains(food), "Food should be added to the list.");
    }

}