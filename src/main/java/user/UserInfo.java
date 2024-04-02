package user;

import exceptions.*;
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

    public void updateInfo(double weight, double height, int age,
                           String gender, String activeness, String aim) {
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
            throw new InvalidActivenessException();
        }
    }

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
            throw new InvalidAimException();
        }
    }

    public void consumptionOfCalories(Food food) {
        this.currentCalories = food.getCalories() + this.currentCalories;
        if (this.currentCalories > this.caloriesCap) {
            System.out.println("You have exceeded your calories intake!");
            System.out.println("You have consumed " + currentCalories + "kcal");
            System.out.println("when your target is " + caloriesCap + "kcal");
        }
    }

}
