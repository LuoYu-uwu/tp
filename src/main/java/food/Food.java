package food;

public class Food {
    private String name;
    private double calories;

    /**
     * Constructs the food to store name and calories.
     *
     * @param name Name of the food.
     * @param calories Calories of the food.
     */
    public Food(String name, double calories) {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    /**
     * Stores the food's name and calories as a string
     * @return A string containing the food's name and calories.
     */
    public String print() {
        assert !(this.name.isEmpty()) : "Name should not be empty. Food constructed wrongly.";
        return this.name + ", with " + this.calories + " calories";
    }
}
