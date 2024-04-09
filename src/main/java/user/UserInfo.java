package user;

import exceptions.GitException;
import exceptions.FailToCalculateCalories;
import exceptions.InsufficientInfoException;
import food.Food;

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
    private double caloriesCap;
    private double currentCalories;

    public UserInfo(String name) {
        this.name = name;
        this.weight = 0;
        this.height = 0;
        this.age = 0;
        this.currentCalories = 0;
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
        this.weight = weight;
    }

    /**
     * Sets height in cm.
     *
     * @param height User's height.
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Sets age in years.
     *
     * @param age User's age.
     */
    public void setAge(int age) {
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
        setGender(gender);
        setAim(aim);
        setActiveness(activeness);
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
    private void calBMR() throws InsufficientInfoException {
        if (this.weight == 0 || this.height == 0 || this.age == 0) {
            throw new InsufficientInfoException();
        }
        double result;
        if(gender.equals("F")) {
            result = 655 + (9.56 * this.weight) + (1.85 * this.height) - (4.68 * this.height);
        } else {
            result = 66.47 + (13.75 * this.weight) + (5 * this.height) - (6.76 * this.height);
        }
        this.BMR = result;
    }

    /**
     * Calculate's the user's Active Metabolic Rate given the activeness.
     *
     * @throws GitException When invalid activeness was given.
     */
    private void calAMR() throws GitException {
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
     * @throws GitException When invalid aim was given.
     */
    private void setCaloriesCap() throws GitException {
        switch (this.aim) {
        case "lose":
            this.caloriesCap = this.AMR*0.8;
            break;
        case "maintain":
            this.caloriesCap = AMR;
            break;
        case "gain":
            this.caloriesCap = this.AMR*1.2;
            break;
        default:
            throw new FailToCalculateCalories();
        }
    }

    /**
     * Calculates the total calories consumed.
     * Only check if it has exceeded the target calories if sufficient information was given.
     *
     * @param food Consumed food.
     * @throws GitException When insufficient information about the user was given.
     */
    public void consumptionOfCalories(Food food) throws GitException{
        this.currentCalories = food.getCalories() + this.currentCalories;
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

}
