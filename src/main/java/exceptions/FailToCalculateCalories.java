package exceptions;

public class FailToCalculateCalories extends GitException {

    /**
     * Constructs failToCalculateCalories.
     */
    public FailToCalculateCalories() {
        message = "Failed to calculate target calories. \n" +
                "Please check if sufficient information has been given." ;
    }
}
