package object;

import application.Utils;

/**
 * Represents an exercise entry that is based on repetitions and sets.
 * <p>
 * A {@code RepBasedExerciseEntry} specifies the number of sets,
 * repetitions per set, and the average duration per repetition.
 * This class extends {@link ExerciseEntry} and calculates calories burned using the MET value of the sport,
 * the user’s weight, and the total exercise duration (sets x reps x average duration per repetition).
 */
public class RepBasedExerciseEntry extends ExerciseEntry {
	private int sets;
	private int reps;
	private double secPerRep;

    /**
     * Creates a new {@code RepBasedExerciseEntry}.
     *
     * @param type		the type of entry (planned, planned complete, or recorded)
     * @param note		an optional note or description for the entry
     * @param sport		the sport or activity associated with this entry
     * @param weight	the weight (kg) of the person performing the exercise, used for calorie calculation
     * @param sets      the number of sets performed
     * @param reps      the number of repetitions per set
     * @param secPerRep the average duration of one repetition, in seconds
     */
	public RepBasedExerciseEntry(EntryType type, String note, Sport sport, double weight, int sets, int reps, double secPerRep) {
		super(type, note, sport, weight);
		this.sets = sets;
		this.reps = reps;		
		this.secPerRep = secPerRep;
	}
	
    /**
     * Creates a copy of this {@code RepBasedExerciseEntry} with a new unique identifier.
     *
     * @return a cloned {@code RepBasedExerciseEntry} instance
     */
    @Override
    public RepBasedExerciseEntry clone() {
        return (RepBasedExerciseEntry) super.clone();
    }
	
    /**
     * Calculates the total exercise duration in minutes.
     *
     * @return the total duration of all sets and reps, in minutes
     */
	public double totalMinutes() {
		double totalSeconds = sets * reps * secPerRep;
		double totalMinutes = totalSeconds / 60.0;
		return totalMinutes;
	}

    /**
     * Calculates the calories burned for this entry.
     * <p>
	 * The calculation is based on the total duration (in hours), 
	 * the user's weight, and the MET value of the sport.
     * <p>
     * A negative value is returned to represent calorie burning
     * (so that net calorie calculations can subtract exercise calories directly).
     *
     * @return the negative number of calories burned
     */
	@Override
	public double calculateCalories() {
		double durationHours = totalMinutes() / 60.0;
		return -1 * durationHours * getWeight() * getSport().getMet();
	}

    /**
     * Returns detailed information for display purposes.
     * <p>
     * Example: "3 x 12 • 250.0 kcal".
     *
     * @return a descriptive string showing sets, reps, and calories burned
     */
	@Override
	public String getDisplayInfo() {
		return String.format("%d x %d • %.1f kcal", sets , reps , -1 * calculateCalories());
	}
	
    /**
     * Updates this repetition-based exercise entry with values from another entry.
     * <p>
     * The new entry must be a {@code RepBasedExerciseEntry}, otherwise an exception is thrown.
     * The identifier is not changed.
     *
     * @param newEntry the entry to copy values from
     * @throws IllegalArgumentException if {@code newEntry} is not a {@code RepBasedExerciseEntry}
     */
	@Override
	public void updateFrom(Entry newEntry) {
		newEntry.validate();
		if (!(newEntry instanceof RepBasedExerciseEntry)) {
		    throw new IllegalArgumentException("Expected RepBasedExerciseEntry, got " + newEntry.getClass().getSimpleName());
		}
		
		super.updateFrom(newEntry);
		RepBasedExerciseEntry newRepExeEntry = (RepBasedExerciseEntry) newEntry;
		this.setReps(newRepExeEntry.getReps());	
		this.setSets(newRepExeEntry.getSets());
		this.setSecPerRep(newRepExeEntry.getSecPerRep());
		
	}
	
    /**
     * Validates this repetition-based exercise entry to ensure it has valid data.
     *
     * @throws IllegalArgumentException if weight ≤ 0,
     *                                  reps ≤ 0,
     *                                  sets ≤ 0,
     *                                  or seconds per rep ≤ 0
     */
	@Override
	public void validate(){
	    if (getWeight() <= 0) {
	        throw new IllegalArgumentException("Weight must be greater than 0");
	    }
	    if (reps <= 0) {
	        throw new IllegalArgumentException("Reps must be greater than 0");
	    }
	    if (sets <= 0) {
	        throw new IllegalArgumentException("Sets must be greater than 0");
	    }

	    if (secPerRep <= 0) {
	        throw new IllegalArgumentException("Seconds per rep must be greater than 0");
	    }
	}

	public int getSets() {
		return sets;
	}

	public void setSets(int sets) {
		this.sets = sets;
	}

	public int getReps() {
		return reps;
	}

	public void setReps(int reps) {
		this.reps = reps;
	}

	public double getSecPerRep() {
		return secPerRep;
	}

	public void setSecPerRep(double secPerRep) {
		this.secPerRep = secPerRep;
	}




}
