package food;

public class Food {
    private String name;
    private double calories;
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

    public String print() {
        return this.name + ", with " + this.calories + " calories";
    }
}
