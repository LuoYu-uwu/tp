package user;

import exceptions.GitException;
import exceptions.FailToCalculateCalories;
import exceptions.InsufficientInfoException;
import food.Food;
import git.Storage;

import java.util.List;

public class UserInfo {
    private String name;
    private double weight;
    private double height;
    private int age;
    private String gender;
    private String aim;
    private String activeness;
    private double BMR;
    private double AMR;
    private int caloriesCap;
    private int currentCalories;
    private Storage storage;

    public UserInfo() {
        this.name = null;
        this.weight = 0;
        this.height = 0;
        this.age = 0;
        this.BMR = 0;
        this.AMR = 0;
        this.currentCalories = 0;
        this.storage = new Storage();
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets weight in KG.
     *
     * @param weight User's weight.
     */
    public void setWeight(double weight) {
        assert weight >= 0 : "User should not be allowed to input negative weight";
        this.weight = weight;
    }

    /**
     * Sets height in cm.
     *
     * @param height User's height.
     */
    public void setHeight(double height) {
        assert height >= 0 : "User should not be allowed to input negative height";
        this.height = height;
    }

    /**
     * Sets age in years.
     *
     * @param age User's age.
     */
    public void setAge(int age) {
        assert age >= 0: "User should not be allowed to input negative age";
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public void setActiveness(String activeness) {
        this.activeness = activeness;
    }

    public double getCurrentCalories() {
        return currentCalories;
    }

    /**
     * Updates the user's information using the given input.
     *
     * @param name Entered name.
     * @param weight Entered weight.
     * @param height Entered height.
     * @param age Entered age.
     * @param gender Entered gender.
     * @param activeness Entered activeness.
     * @param aim Entered aim.
     */
    public void updateInfo(String name, double weight, double height, int age,
                           String gender, String activeness, String aim) {
        setName(name);
        setWeight(weight);
        setHeight(height);
        setAge(age);
        setGender(gender.toLowerCase());
        setAim(aim.toLowerCase());
        setActiveness(activeness.toLowerCase());
        try {
            calBMR();
            calAMR();
            setCaloriesCap();
            System.out.println("Your target calories intake a day should be "
                    + this.caloriesCap);
        } catch (GitException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Calculate's the user's Basal metabolic rate if there is sufficient information.
     *
     * @throws InsufficientInfoException When there is not enough information for calculation.
     */
    public void calBMR() throws InsufficientInfoException {
        if (this.weight == 0 || this.height == 0 || this.age == 0) {
            throw new InsufficientInfoException();
        }
        double result;
        if(gender.equalsIgnoreCase("F")) {
            result = 655 + (9.56 * this.weight) + (1.85 * this.height) - (4.68 * this.age);
        } else {
            result = 66.47 + (13.75 * this.weight) + (5 * this.height) - (6.76 * this.age);
        }
        this.BMR = result;
    }

    /**
     * Calculate's the user's Active Metabolic Rate given the activeness.
     *
     * @throws FailToCalculateCalories When invalid activeness was given.
     */
    public void calAMR() throws FailToCalculateCalories {
        switch (this.activeness) {
        case "inactive":
            this.AMR = this.BMR * 1.2;
            break;
        case "light":
            this.AMR = this.BMR * 1.38;
            break;
        case "moderate":
            this.AMR = this.BMR * 1.55;
            break;
        case "active":
            this.AMR = this.BMR * 1.73;
            break;
        case "very":
            this.AMR = this.BMR * 1.9;
            break;
        default:
            throw new FailToCalculateCalories();
        }
    }

    /**
     * Calculate's the user's target calories given the aim.
     *
     * @throws FailToCalculateCalories When invalid aim was given.
     */
    public void setCaloriesCap() throws FailToCalculateCalories {
        switch (this.aim) {
        case "lose":
            this.caloriesCap = (int)(this.AMR*0.8);
            break;
        case "maintain":
            this.caloriesCap = (int)(AMR);
            break;
        case "gain":
            this.caloriesCap = (int)(this.AMR*1.2);
            break;
        default:
            throw new FailToCalculateCalories();
        }
    }

    /**
     * Calculates the total calories consumed.
     * Only check if it has exceeded the target calories if sufficient information was given.
     *
     * @param foods The list of consumed food.
     * @throws InsufficientInfoException When insufficient information about the user was given.
     */
    public void consumptionOfCalories(List<Food> foods) throws InsufficientInfoException{
        assert !(foods.isEmpty()) : "Food should be added into list before storing consumed calories";
        this.currentCalories = 0;
        for (Food food : foods) {
            this.currentCalories = (int)(food.getCalories() + this.currentCalories);
        }
        if (this.weight == 0 || this.height == 0 || this.age == 0 ||
                this.gender.isEmpty() || this.aim.isEmpty() || this.activeness.isEmpty()) {
            throw new InsufficientInfoException();
        }
        if (this.currentCalories > this.caloriesCap) {
            System.out.println("You have exceeded your calories intake!");
            System.out.println("You have consumed " + currentCalories + "kcal");
            System.out.println("when your target is " + caloriesCap + "kcal");
        }
    }

    /**
     * Stores user details as a string.
     *
     * @return A string containing all the user's details.
     */
    public String viewProfile(){
        String userName = "Name: " + this.name + "\n";
        String height = "Height: " + this.height + "\n";
        String weight = "Weight: " + this.weight + "\n";
        String age = "Age: " + this.age + "\n";
        String gender = "Gender: " + this.gender + "\n";
        String target = "Target calories intake: " + this.caloriesCap;
        return userName + height + weight + age + gender + target;
    }

    /**
     * Stores user details as a string in format for saving.
     *
     * @return A string containing all the user's details.
     */
    public String toProfileSaveFormat(){
        assert !(this.name.isEmpty()) : "User does not exist!!";
        String userName = "Name: " + this.name + "\n";
        String height = "Height: " + this.height + "\n";
        String weight = "Weight: " + this.weight + "\n";
        String age = "Age: " + this.age + "\n";
        String gender = "Gender: " + this.gender + "\n";
        String aim = "Aim: " + this.aim + "\n";
        String activeness = "Activeness: " + this.activeness + "\n";
        String caloriesCap = "Calories: " + this.caloriesCap + "\n";
        return userName + height + weight + age + gender + aim + activeness + caloriesCap;
    }
    /**
     * Sets calories cap.
     *
     * @param caloriesCap Loaded from saved file.
     */
    public void setCaloriesCapFromLoad(int caloriesCap){
        this.caloriesCap = caloriesCap;
    }
    public String getName(){
        return this.name;
    }
}
