package user;

import exceptions.FailToCalculateCalories;
import exceptions.GitException;
import exceptions.InsufficientInfoException;
import food.Food;
import food.FoodList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTest {

    @Test
    void setName_success() {
        UserInfo user = new UserInfo();
        user.setName("Tom");
        assertEquals(user.getName(), "Tom");
    }

    @Test
    void setWeight_negativeWeight_throwsAssertionError() {
        UserInfo user = new UserInfo();
        assertThrows(AssertionError.class, () ->user.setWeight(-50),
                "Should throw AssertionError for negative weight.");
    }

    @Test
    void setHeight_negativeHeight_throwsAssertionError() {
        UserInfo user = new UserInfo();
        assertThrows(AssertionError.class, () ->user.setHeight(-1),
                "Should throw AssertionError for negative height.");
    }

    @Test
    void setAge_negativeAge_throwsAssertionError() {
        UserInfo user = new UserInfo();
        assertThrows(AssertionError.class, () ->user.setAge(-1),
                "Should throw AssertionError for negative age.");
    }

    @Test
    void calBMR_heightIsZero_exceptionThrown(){
        try {
            UserInfo user = new UserInfo();
            user.setHeight(0);
            user.calBMR();
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void calBMR_weightIsZero_exceptionThrown(){
        try {
            UserInfo user = new UserInfo();
            user.setWeight(0);
            user.calBMR();
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void calBMR_ageIsZero_exceptionThrown(){
        try {
            UserInfo user = new UserInfo();
            user.setAge(0);
            user.calBMR();
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void calBMR_validInput_success(){
        try {
            UserInfo user = new UserInfo();
            user.setWeight(50);
            user.setHeight(165);
            user.setAge(21);
            user.setGender("F");
            user.calBMR();
        } catch (InsufficientInfoException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void calAMR_invalidActiveness_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            user.setActiveness("no");
            user.calAMR();
            fail("Expected a FailToCalculateCalories to be thrown");
        } catch (FailToCalculateCalories e) {
            String expectedMessage = "Failed to calculate target calories. \n" +
                    "Please check if sufficient information has been given." ;
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void calAMR_validActiveness_success() {
        try {
            UserInfo user = new UserInfo();
            user.setActiveness("active");
            user.calAMR();
        } catch (FailToCalculateCalories e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void setCaloriesCap_invalidAim_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            user.setAim("nil");
            user.setCaloriesCap();
            fail("Expected a FailToCalculateCalories to be thrown");
        } catch (FailToCalculateCalories e) {
            String expectedMessage = "Failed to calculate target calories. \n" +
                    "Please check if sufficient information has been given." ;
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void setCaloriesCap_validAim_success() {
        try {
            UserInfo user = new UserInfo();
            user.setAim("maintain");
            user.setCaloriesCap();
        } catch (FailToCalculateCalories e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void consumptionOfCalories_emptyFoodList_throwsAssertionError() {
        UserInfo user = new UserInfo();
        List<Food> foods = new ArrayList<>();
        assertThrows(AssertionError.class, () ->user.consumptionOfCalories(foods),
                "Should throw AssertionError for empty food list.");
    }

    @Test
    void consumptionOfCalories_weightIsZero_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 0, 165, 22, "f" , "active", "lose");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_heightIsZero_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 0, 22, "f" , "active", "lose");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_ageIsZero_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 165, 0, "f" , "active", "lose");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_genderIsEmpty_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 165, 21, "" , "active", "lose");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_activenessIsEmpty_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 165, 21, "f" , "", "lose");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_aimIsEmpty_exceptionThrown() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 165, 21, "f" , "active", "");
            user.consumptionOfCalories(foods);
            fail("Expected a InsufficientInfoException to be thrown");
        } catch (InsufficientInfoException e) {
            String expectedMessage = "User's information is insufficient to calculate BMR," +
                    " please check the current information";
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void consumptionOfCalories_validDetails_success() {
        try {
            UserInfo user = new UserInfo();
            List<Food> foods = new ArrayList<>();
            foods.add(new Food("apple", 52));
            user.updateInfo("Alice", 50, 165, 21, "f" , "active", "lose");
            user.consumptionOfCalories(foods);
        } catch (InsufficientInfoException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    void viewProfile() {
        UserInfo user = new UserInfo();
        user.updateInfo("Alice", 50, 165, 21, "f" , "active", "lose");
        String details = "Name: Alice\n"
                + "Height: 165.0\n"
                +"Weight: 50.0\n"
                +"Age: 21\n"
                +"Gender: f\n"
                +"Target calories intake: 1854";
        assertEquals(user.viewProfile(), details);
    }
}