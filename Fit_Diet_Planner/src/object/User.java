package object;

/**
 * Represents a user with personal details and a schedule.
 * Provides methods to calculate and interpret BMI values.
 */
public class User {
	private String name;
	private int age;
	private double weight;
	private double height;
	private Schedule schedule;

    /**
     * Creates a new user with the given details.
     *
     * @param name     the user's name
     * @param age      the user's age
     * @param weight   the user's weight in kilograms
     * @param height   the user's height in centimeters
     * @param schedule the user's schedule
     */
	public User(String name, int age, double weight, double height, Schedule schedule) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.schedule = schedule;
	}

    /**
     * Calculates the Body Mass Index (BMI) using the given weight and height.
     *
     * @param weight the weight in kilograms
     * @param height the height in centimeters
     * @return the BMI score rounded to two decimals
     * @throws IllegalArgumentException if weight or height are non-positive
     */
	public double calculateBMIScore(double weight, double height) {
	    if (height <= 0) throw new IllegalArgumentException("Height must be greater than 0");
	    if (weight <= 0) throw new IllegalArgumentException("Weight must be greater than 0");

	    double heightM = height / 100.0;
	    double bmi = weight / (heightM * heightM);
	    return Math.round(bmi * 100.0) / 100.0;
	}
	
    /**
     * Returns the BMI category based on the given weight and height.
     *
     * @param weight the weight in kilograms
     * @param height the height in centimeters
     * @return a string representing the BMI category
     */
	public String getBMIResult(double weight, double height) {
	    double bmi = calculateBMIScore(weight, height);
	    
	    if (bmi < 18.5) {
	        return "Underweight";
	    } else if (bmi < 25) {
	        return "Normal weight";
	    } else if (bmi < 30) {
	        return "Overweight";
	    } else {
	        return "Obese";
	    }
	}

    /**
     * Calculates the BMI score using the user's stored weight and height.
     *
     * @return the BMI score
     */
	public double calculateBMIScore() {
	    return calculateBMIScore(weight,height);
	}
	
    /**
     * Returns the BMI category using the user's stored weight and height.
     *
     * @return a string representing the BMI category
     */
	public String getBMIResult() {
		return getBMIResult(weight,height);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age <= 0) throw new IllegalArgumentException("Age must be greater than 0");
		this.age = age;
	}

	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		if (weight <= 0) throw new IllegalArgumentException("Weight must be greater than 0");
		this.weight = weight;
	}
	
	public double getHeight() {
		
		return height;
	}

	public void setHeight(double height) {
		if (height <= 0) throw new IllegalArgumentException("Height must be greater than 0");
		this.height = height;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
}
