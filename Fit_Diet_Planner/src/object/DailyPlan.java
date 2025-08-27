package object;

import java.time.LocalDate;
import java.util.List;

import object.Entry.EntryType;

import java.util.ArrayList;

/**
 * Represents a daily plan that contains a list of {@link Entry} items for a specific date.
 * <p>
 * A plan can store food and sport entries, track intake and burn calories,
 * and manage planned or completed entries.
 * </p>
 */
public class DailyPlan {	
	private LocalDate date;
	private List<Entry> entries;

    /**
     * Creates a daily plan for the given date with no entries.
     *
     * @param date the date of the plan
     */
	public DailyPlan(LocalDate date) {		
		this.date = date;	
		entries = new ArrayList<>();
	}
	
	public List<Entry> getEntries() {
		return entries;
	}

	public LocalDate getDate() {
		return date;
	}
	
    /**
     * Adds an entry to the plan after validation.
     *
     * @param entry the entry to add
     * @throws IllegalArgumentException if validation fails
     */
	public void addEntry(Entry entry) {
		entry.validate();
		entries.add(entry);
	}
	
    /**
     * Removes an entry from the plan.
     *
     * @param entry the entry to remove
     */
	public void deleteEntry(Entry entry) {
		entries.remove(entry);
	} 
	
    /**
     * Marks an entry as {@link EntryType#PLANNED_COMPLETE} 
     * and adds a new {@link EntryType#RECORDED} entry cloned from it.
     *
     * @param entry the entry to complete
     */
	public void completeEntry(Entry entry) {
		entry.setType(EntryType.PLANNED_COMPLETE);
		
		Entry newEntry = entry.clone();
		newEntry.setType(EntryType.RECORDED);
		addEntry(newEntry);	
	}
	
    /**
     * Returns the total intake calories for the given entry type.
     *
     * @param type the entry type filter
     * @return total intake calories
     */
	public double getIntakeCalories(EntryType type) {
	    double total = 0.0;
	    for (Entry entry : entries){
	    	EntryType t = entry.getType();
	        if ((type == EntryType.PLANNED && (t == EntryType.PLANNED || t == EntryType.PLANNED_COMPLETE))|| t == type){
	        	double cal = entry.calculateCalories();
	            if (cal > 0){
	                total += cal;
	            }
	        }
	    }
	    return total;
	}
	
    /**
     * Returns the total burn calories for the given entry type.
     *
     * @param type the entry type filter
     * @return total burn calories
     */
	public double getBurnCalories(EntryType type) {
	    double total = 0.0;
	    for(Entry entry : entries){
	    	EntryType t = entry.getType();
	        if((type == EntryType.PLANNED && (t == EntryType.PLANNED || t == EntryType.PLANNED_COMPLETE))|| t == type){
	        	double cal = entry.calculateCalories();
	            if (cal < 0){
	                total -= cal;
	            }
	        }
	    }
	    return total;
	}


    /**
     * Returns the net calories (intake - burn) for the given entry type.
     *
     * @param type the entry type filter
     * @return net calories
     */
	public double getNetCalories(EntryType type) {
	    double total = 0.0;
	    for(Entry entry : entries){
	    	EntryType t = entry.getType();
	        if((type == EntryType.PLANNED && (t == EntryType.PLANNED || t == EntryType.PLANNED_COMPLETE))|| t == type){
	        	double cal = entry.calculateCalories();
	        	total+=cal;
	        }
	    }
	    return total;		
	}
	
    /**
     * Returns a list of duplicated planned and planned-complete entries with type reset to {@link EntryType#PLANNED}.
     *
     * @return list of duplicated entries
     */
	public List<Entry> duplicateEntries() {
	    List<Entry> duplicates = new ArrayList<>();
	    for(Entry entry : entries) {    	
	    	if(entry.getType()==EntryType.PLANNED || entry.getType()==EntryType.PLANNED_COMPLETE) {
	    		Entry newEntry = entry.clone();
	    		newEntry.setType(EntryType.PLANNED);
	    		duplicates.add(newEntry);
	    	}        
	    }
	    return duplicates;
	}
	
    /**
     * Removes all planned or planned-complete entries from the plan.
     */
	public void clearPlannedEntries() {
		for(Entry entry: entries) {
			if(entry.getType() == EntryType.PLANNED || entry.getType() == EntryType.PLANNED_COMPLETE) 
				deleteEntry(entry);		
		}
	}
	
    /**
     * Checks if the plan contains any planned or planned-complete entries.
     *
     * @return true if planned entries exist, false otherwise
     */
	public boolean hasPlannedEntries() {
	    for(Entry entry : entries) {
	        if(entry.getType() == EntryType.PLANNED || entry.getType() == EntryType.PLANNED_COMPLETE) {
	            return true;
	        }
	    }
	    return false;
	}
}
