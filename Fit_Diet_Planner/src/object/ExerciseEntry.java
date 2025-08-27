package object;

import application.Utils;

/**
 * Represents a generic exercise-related entry in the system.
 * An {@code ExerciseEntry} specifies the sport performed and the user's weight,
 * which can be used by subclasses to calculate calories burned.
 * <p>
 * This class is abstract and must be extended by concrete exercise types
 * (e.g., time-based or rep-based exercises).
 */
public abstract class ExerciseEntry extends Entry{
	private Sport sport;
	private double weight;

    /**
     * Creates a new {@code ExerciseEntry}.
     *
     * @param type		the type of entry (planned, planned complete, or recorded)
     * @param note		an optional note or description for the entry
     * @param sport		the sport or activity associated with this entry
     * @param weight	the weight (kg) of the person performing the exercise, used for calorie calculation
     */
	public ExerciseEntry(EntryType type, String note, Sport sport, double weight) {
		super(type, note);
		this.sport = sport;
		this.weight = weight;
	}
	
    /**
     * Creates a copy of this {@code ExerciseEntry} with a new unique identifier.
     *
     * @return a cloned {@code ExerciseEntry} instance
     */
	@Override
	public ExerciseEntry clone() {
	    return (ExerciseEntry) super.clone(); 
	}
	
    /**
     * Returns a display-friendly name for this exercise entry.
     * <p>
     * Example: "Running".
     *
     * @return the capitalized name of the sport
     */
	@Override
	public String getDisplayName() {
	    return Utils.capitalizeSafe(getSport().getName()) ;
	}
	
    /**
     * Returns a human-readable preview of calories burned.
     * <p>
     * The {@code calculateCalories()} method returns a negative number 
     * (e.g., -300.5) for net calorie calculations. 
     * An extra -1 is applied here to convert it back to a positive value 
     * for display purposes.
     *
     * @return a string with the positive calorie burn value followed by "kcal"
     */
	@Override
	public String getCaloriesPreview() {
		return String.format("%.1f kcal", -1 * calculateCalories());		 
	};
	
    /**
     * Updates this exercise entry with the values from another entry.
     * <p>
     * The new entry must be an {@code ExerciseEntry}, otherwise an exception is thrown.
     * The identifier is not changed.
     *
     * @param newEntry the entry to copy values from
     * @throws IllegalArgumentException if {@code newEntry} is not an {@code ExerciseEntry}
     */
	@Override
	public void updateFrom(Entry newEntry) {
		newEntry.validate();
		if (!(newEntry instanceof ExerciseEntry)) {
		    throw new IllegalArgumentException("Expected ExerciseEntry, got " + newEntry.getClass().getSimpleName());
		}
		super.updateFrom(newEntry);
		ExerciseEntry newExeEntry = (ExerciseEntry)newEntry;
		this.setWeight(newExeEntry.getWeight());
	}
	
	public Sport getSport() {
		return sport;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
}
