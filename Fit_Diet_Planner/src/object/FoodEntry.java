package object;

import application.Utils;

/**
 * Represents a food-related entry in the system.
 * <p>
 * This class extends {@link Entry}.
 * A {@code FoodEntry} specifies the food item, the number of portions,
 * and the weight in grams per portion. It calculates calories
 * based on these values and the food's calories per gram.
 */
public class FoodEntry extends Entry {
	private Food food;
	private int quantity;
	private double gram ;
		
    /**
     * Creates a new {@code FoodEntry}.
     *
     * @param type 		the type of entry (planned, planned complete, or recorded)
     * @param note 		an optional note or description for the entry
     * @param food 		the food item associated with this entry
     * @param quantity 	the number of portions
     * @param gram 		the weight in grams for a single portion
     */
	public FoodEntry(EntryType type, String note, Food food, int quantity, double gram) {
		super(type, note);
		this.food = food;
		this.quantity = quantity;
		this.gram = gram;
	}
	
    /**
     * Creates a copy of this {@code FoodEntry} with a new unique identifier.
     *
     * @return a cloned {@code FoodEntry} instance
     */
    @Override
    public FoodEntry clone() {
        return (FoodEntry) super.clone();
    }
	
    /**
     * Calculates the calories of this entry based on the
     * weight in grams, quantity, and the food’s calorie density.
     *
     * @return the calculated calories for this food entry
     */
	@Override
	public double calculateCalories() {		
		return gram * quantity * food.getCaloriePerG();
	}

    /**
     * Returns a display-friendly name for this food entry.
     * <p>
     * Example: "Pizza (2)".
     *
     * @return the capitalized food name followed by the quantity
     */
	@Override
	public String getDisplayName() {
	    return Utils.capitalizeSafe(food.getName()) + " (" + quantity + ")";
	}

    /**
     * Returns detailed information for display purposes.
     * <p>
     * Example: "150.00 g • 320.5 kcal".
     *
     * @return a descriptive string with total weight and calories
     */
	@Override
	public String getDisplayInfo() {
		return String.format("%.2f g • %.1f kcal", getGram() * quantity , calculateCalories());
	}
	
    /**
     * Validates this food entry to ensure it has valid data.
     *
     * @throws IllegalArgumentException if quantity ≤ 0 or gram ≤ 0
     */
	@Override
	public void validate(){
	    if (quantity <= 0) {
	        throw new IllegalArgumentException("Quantity must be greater than 0");
	    }
	    if (gram <= 0) {
	        throw new IllegalArgumentException("Gram must be greater than 0");
	    }
	}
	
    /**
     * Updates this food entry with the values from another entry.
     * <p>
     * The new entry must be a {@code FoodEntry}, otherwise an exception is thrown.
     * The identifier is not changed.
     *
     * @param newEntry the entry to copy values from
     * @throws IllegalArgumentException if {@code newEntry} is not a {@code FoodEntry}
     */
	@Override
	public void updateFrom(Entry newEntry) {
		newEntry.validate();
		if (!(newEntry instanceof FoodEntry)) {
		    throw new IllegalArgumentException("Expected FoodEntry, got " + newEntry.getClass().getSimpleName());
		}
		
		super.updateFrom(newEntry);
		FoodEntry newFoodEntry = (FoodEntry) newEntry;
		this.setGrams(newFoodEntry.getGram());
		this.setQuantity(newFoodEntry.getQuantity());	
	}

	public Food getFood() {
		return food;
	}

	public double getGram() {
		return gram;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
	    if (quantity < 0) 
	    	quantity = 1;
	    this.quantity = quantity;
		
	}

	public void setGrams(double gram) {
	    if (gram < 0) 
	    	gram = 0.0;
	    this.gram = gram; 
	}

}
