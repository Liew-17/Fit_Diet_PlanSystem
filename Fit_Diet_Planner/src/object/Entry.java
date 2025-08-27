package object;

import java.util.UUID;

/**
 * Represents a generic entry (e.g., food or exercise record) in the system.
 * Each entry belongs to a specific daily plan and records a unique identifier,
 * an entry type, and an optional note.
 * <p>
 * Subclasses must provide their own logic for calculating calories,
 * displaying entry information, and validating their data.
 */
public abstract class Entry implements Cloneable{
	private String id;
	private EntryType type;
	private String note;
	
    /**
     * Defines the possible statuses for an entry.
     */
	public enum EntryType {
        /** 
         * An entry that is scheduled or planned by the user 
         * but has not yet been completed. 
         */
        PLANNED,

        /** 
         * An entry that was planned by the user 
         * and has been marked as completed. 
         */
        PLANNED_COMPLETE,

        /** 
         * An entry that has been directly recorded 
         * without being scheduled beforehand. 
         */
        RECORDED
	}	
	
    /**
     * Creates a new Entry with the specified type and note.
     * A unique identifier is automatically generated.
     *
     * @param type the type of entry (planned, planned complete, or recorded)
     * @param note an optional note or description for the entry
     */
	public Entry(EntryType type, String note) {
		setId(UUID.randomUUID().toString());
		this.setType(type);	
		this.note = note;
	}
		
    /**
     * Calculates the calories for this entry.
     * <p>
     * Implementations must provide logic based on the type of entry,
     * e.g., food entries calculate from quantity and gram,
     * exercise entries calculate from duration and intensity.
     *
     * @return the calculated calories (in kcal) for this entry
     */
	public abstract double calculateCalories();
	
    /**
     * Returns a display-friendly name for this entry.
     * <p>
     *
     * @return the display name of the entry
     */
	public abstract String getDisplayName();
	
    /**
     * Returns detailed information for display purposes.
     * <p>
     *
     * @return a descriptive string containing details about the entry
     */
	public abstract String getDisplayInfo();
	
    /**
     * Validates the state of this entry.
     * <p>
     * Subclasses must implement checks to ensure
     * that their data is logically valid.
     * For example, food entries must have a portion > 0,
     * and exercise entries must have a duration > 0.
     *
     * @throws IllegalArgumentException if the entry contains invalid data
     */
	public abstract void validate();
	
    /**
     * Updates the current entry’s type and note
     * based on another entry. The unique identifier is not changed.
     *
     * @param newEntry the entry whose data is used for updating
     */
	public void updateFrom(Entry newEntry) {
		this.type = newEntry.getType();
		this.note = newEntry.getNote();		
	};
	
    /**
     * Creates a copy of this entry with a new unique identifier.
     * <p>
     * The cloned entry is treated as a new record,
     * while preserving the data from the original.
     *
     * @return a new {@code Entry} instance with copied values
     *         and a new unique identifier
     * @throws AssertionError if cloning is not supported
     */
    @Override
    public Entry clone() {
        try {
            Entry copy = (Entry) super.clone();
            copy.setId(UUID.randomUUID().toString()); // assign new unique ID
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    } 
	
    /**
     * Returns a human-readable preview of the calories.
     * <p>
     * For example, "320 kcal".
     *
     * @return a string with the calorie value and "kcal" unit
     */
	public String getCaloriesPreview() {
		return String.format("%.0f kcal", calculateCalories());		 
	};

	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
