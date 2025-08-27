package object;

import java.util.UUID;

import application.Utils;

/**
 * Represents a physical activity stored in the library.
 * <p>
 * A {@code Sport} is a specific type of {@link LibraryItem} that stores 
 * the activity's MET (Metabolic Equivalent of Task) value and
 * the default average seconds per repetition for rep-based exercises.
 * </p>
 */
public class Sport extends LibraryItem{
	private double met;
    private double secondsPerRep; 
    private boolean isRepBased;
    
    /**
     * Constructs a time-based sport.
     *
     * @param id          the unique identifier of the sport
     * @param name        the name of the sport
     * @param libraryType the library type category
     * @param met         the metabolic equivalent value
     */
    public Sport(String id, String name, LibraryType libraryType, double met) {
		super(id, name, libraryType);
		this.met = met;
		this.isRepBased = false;
	}

    /**
     * Constructs a rep-based sport.
     *
     * @param id            the unique identifier of the sport
     * @param name          the name of the sport
     * @param libraryType   the library type category
     * @param met           the metabolic equivalent value
     * @param secondsPerRep the average time required per repetition
     */
    public Sport(String id, String name, LibraryType libraryType, double met, double secondsPerRep) {
		super(id, name, libraryType);
		this.met = met;
		this.secondsPerRep = secondsPerRep;
		this.isRepBased = true;
	}

    /**
     * Constructs a time-based sport without an explicit ID.
     *
     * @param name        the name of the sport
     * @param libraryType the library type category
     * @param met         the metabolic equivalent value
     */
    public Sport(String name, LibraryType libraryType, double met) {
		super(name, libraryType);
		this.met = met;
		this.isRepBased = false;
	}

    /**
     * Constructs a rep-based sport without an explicit ID.
     *
     * @param name          the name of the sport
     * @param libraryType   the library type category
     * @param met           the metabolic equivalent value
     * @param secondsPerRep the average time required per repetition
     */    
    public Sport(String name, LibraryType libraryType, double met, double secondsPerRep) {
		super(name, libraryType);
		this.met = met;
		this.secondsPerRep = secondsPerRep;
		this.isRepBased = true;
	}


    /**
     * Validates this sport object to ensure its values are valid.
     * <p>
     * This includes checking that the name is not empty, 
     * the met value is greater than zero, 
     * and if rep-based, secondsPerRep is also greater than zero.
     * </p>
     *
     * @throws IllegalArgumentException if validation fails
     */
	@Override
    public void validate() {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sport name cannot be empty.");
        }
        if (met <= 0) {
            throw new IllegalArgumentException("MET value must be greater than 0.");
        }
        if (isRepBased && secondsPerRep <= 0) {
            throw new IllegalArgumentException("Seconds per rep must be greater than 0 for rep-based exercises.");
        }
    }
	
	/**
	 * Returns the display category of this sport.
	 * <p>
	 * If the sport is rep-based, returns "Rep-based"; otherwise returns "Time-based".
	 * </p>
	 *
	 * @return the display category as a string
	 */
	@Override
	public String getDisplayCategory() {
		return isRepBased ? "Rep-based" : "Time-based";
	}

	/**
	 * Generates a description of this sport with its details.
	 * <p>
	 * For rep-based sports, includes the seconds per rep.
	 * For time-based sports, includes the MET value.
	 * </p>
	 *
	 * @return a description string of this sport
	 */
	@Override
	public String generateDescription() {
	    if (isRepBased()) {
	        return String.format("%s (Rep-Based): Perform reps with approximately %.1f seconds per rep.",
	        	Utils.capitalizeSafe(getName()),
	        	getSecondsPerRep());	       
	    } else {
	        return String.format("%s (Time-Based): Perform the exercise for the specified duration at MET %.1f.",           
	        	Utils.capitalizeSafe(getName()),
	            getMet()
	        );
	    }
	}
	
	public double getSecondsPerRep() {
    	return secondsPerRep;
    }
    
    public double getMet() {
    	return met;
    }
    
	public boolean isRepBased() {
		return isRepBased;
	}
}
