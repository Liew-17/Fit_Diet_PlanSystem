package object;

import application.Utils;

/**
 * Represents an exercise entry where calories burned are based on duration (in minutes).
 * <p>
 * A {@code TimeBasedExerciseEntry} specifies the sport activity, 
 * the user’s weight, and the total duration of the activity in minutes. 
 * This class extends {@link ExerciseEntry} and calculates calories burned using the MET value of the sport,
 * the user’s weight, and the total exercise duration.
 * </p>
 */
public class TimeBasedExerciseEntry extends ExerciseEntry {
	public double duration;
	
    /**
     * Creates a new {@code TimeBasedExerciseEntry}.
     *
     * @param type      the type of entry (planned, planned complete, or recorded)
     * @param note      an optional note or description for the entry
     * @param sport     the sport or activity associated with this entry
     * @param weight    the weight (kg) of the person performing the exercise, used for calorie calculation
     * @param duration  the duration of the exercise in minutes
     */
	public TimeBasedExerciseEntry(EntryType type, String note, Sport sport, double weight, double duration) {
		super(type, note, sport, weight);
		this.duration = duration;
	}
	
    /**
     * Creates a copy of this {@code TimeBasedExerciseEntry} with a new unique identifier.
     *
     * @return a cloned {@code TimeBasedExerciseEntry} instance
     */
    @Override
    public TimeBasedExerciseEntry clone() {
        return (TimeBasedExerciseEntry) super.clone();
    }

    /**
     * Calculates the calories burned during the exercise.
     * <p>
	 * The calculation is based on the duration (converted to hours),  
	 * the user’s weight, and the MET value of the sport.
     * <p>
     * The result is returned as a negative value to represent net calorie expenditure.
     * </p>
     *
     * @return the calculated calories burned as a negative number
     */
	@Override
	public double calculateCalories() {
		double durationHours = duration / 60;
		return -1 * durationHours * getSport().getMet() * getWeight();
	}

    /**
     * Returns a human-readable summary of this exercise.
     * <p>
     * Example: {@code "30.0 min • 250.0 kcal"}
     * </p>
     *
     * @return a formatted string with duration in minutes and positive calories burned
     */
	@Override
	public String getDisplayInfo() {
		return String.format("%.1f min • %.1f kcal", duration , -1 * calculateCalories());
	}
	
    /**
     * Updates this time-based exercise entry with values from another entry.
     * <p>
     * The new entry must be a {@code TimeBasedExerciseEntry}, otherwise an exception is thrown.
     * The identifier is not changed.
     * </p>
     *
     * @param newEntry the entry to copy values from
     * @throws IllegalArgumentException if {@code newEntry} is not a {@code TimeBasedExerciseEntry}
     */
	@Override
	public void updateFrom(Entry newEntry) {
		newEntry.validate();
		if (!(newEntry instanceof TimeBasedExerciseEntry)) {
		    throw new IllegalArgumentException("Expected TimeBasedExerciseEntry, got " + newEntry.getClass().getSimpleName());
		}
		
		super.updateFrom(newEntry);
		TimeBasedExerciseEntry newTimeExeEntry = (TimeBasedExerciseEntry) newEntry;
		this.setDuration(newTimeExeEntry.getDuration());	
	}

    /**
     * Validates this entry.
     * <p>
     * Both weight and duration must be greater than 0.
     * </p>
     *
     * @throws IllegalArgumentException if weight or duration is less than or equal to 0
     */
	@Override
	public void validate() {
	    if (getWeight() <= 0) {
	        throw new IllegalArgumentException("Weight must be greater than 0");
	    }
	    if (duration <= 0) {
	        throw new IllegalArgumentException("Duration must be greater than 0");
	    }
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

}
