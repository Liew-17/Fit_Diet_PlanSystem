package object;
import java.util.UUID;

/**
 * Represents a generic item stored in a library.
 * <p>
 * Each {@code LibraryItem} has a unique identifier, a name, and a
 * {@link LibraryType} that determines whether it is a default, custom,
 * or archived item. Subclasses are responsible for providing
 * validation rules, display categorization, and item descriptions.
 * </p>
 */
public abstract class LibraryItem {
	
	private String id;
	private String name;
	private LibraryType libraryType;
	
    /**
     * Defines the possible categories of a library item.
     */
    public enum LibraryType {
        /** A system-provided default item. */
        DEFAULT,   

        /** A user-defined custom item. */
        CUSTOM,    

        /** 
         * An item that has been archived and is no longer displayed in the library. 
         * It can still be referenced by an entry if it has already been applied. 
         */
        ARCHIVED   
    }
	
    /**
     * Creates a new {@code LibraryItem} with the specified id, name, and type.
     *
     * @param id the unique identifier of the item
     * @param name the name of the item
     * @param libraryType the type of library item
     */
	public LibraryItem(String id, String name, LibraryType libraryType) {
		this.id = id;
		this.name = name;
		this.libraryType = libraryType;
	}
		
    /**
     * Creates a new {@code LibraryItem} with a randomly generated unique id,
     * using the specified name and type.
     *
     * @param name the name of the item
     * @param libraryType the type of library item
     */
	public LibraryItem(String name, LibraryType libraryType) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.libraryType = libraryType;
	}
	
    /**
     * Validates the current item.
     * <p>
     * Subclasses should implement their own validation logic
     * (e.g., ensuring required fields are set).
     * </p>
     */
	public abstract void validate();

	 /**
     * Returns the display category of the item.
	 *
     * @return the display category as a string
     */
	public abstract String getDisplayCategory();
	
    /**
     * Generates a description of the item.
     * <p>
     * Subclasses should provide a human-readable summary
     * of the item’s properties.
     * </p>
     *
     * @return the generated description
     */
	public abstract String generateDescription();
	
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public LibraryType getLibraryType() {
		return libraryType;
	}

    /**
     * Archives this item if it is of type {@link LibraryType#CUSTOM}.
     * <p>
     * Once archived, the item is no longer displayed in the library but remains
     * stored in the system. This ensures that any existing entries referencing
     * this item continue to function properly.
     * </p>
     */
	public void archieve() {
		if(libraryType==LibraryType.CUSTOM)
			libraryType = LibraryType.ARCHIVED;
	}

}
