package object;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

import application.AppContext;
import controller.EntryDuplicatePopUpController.ConflictAction;
import object.Entry.EntryType;

/**
 * Represents a schedule that organizes daily plans by date.
 * A schedule can calculate average intake, burn, and net calories
 * for a given month, and supports duplicating daily plans.
 */
public class Schedule {

	private Map<LocalDate, DailyPlan> plans;

    /**
     * Creates a new empty schedule.
     */
    public Schedule() { 	
    	plans = new HashMap<>();   	
    }

    /**
     * Returns the daily plan for the given date. 
     * If no plan exists, a new one is created and stored.
     *
     * @param date the date of the daily plan
     * @return the daily plan for the specified date
     */
    public DailyPlan getDailyPlan(LocalDate date) {
    	if (!plans.containsKey(date)) {
    		plans.put(date, new DailyPlan(date));
    	}  	
    	return plans.get(date);
    }
    
    /**
     * Adds a daily plan to the schedule. 
     * If a plan already exists for the date, it is replaced.
     *
     * @param dailyPlan the daily plan to add
     */
    public void addDailyPlan(DailyPlan dailyPlan) {
    	plans.put(dailyPlan.getDate(), dailyPlan);
    }
    
    /**
     * Calculates the average intake calories for a given month and entry type.
     * If some days do not have plans, they count as zero.
     *
     * @param month the target month
     * @param type the entry type to calculate
     * @return the average intake calories per day
     */
    public double getAverageIntake(YearMonth month, EntryType type) {
        double total = 0;
        int daysInMonth = month.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = month.atDay(day);
            DailyPlan plan = plans.get(date);
            if (plan != null) {
                total += plan.getIntakeCalories(type);
            }
        }

        return total / daysInMonth;
    }

    /**
     * Calculates the average calories burned for a given month and entry type.
     * If some days do not have plans, they count as zero.
     *
     * @param month the target month
     * @param type the entry type to calculate
     * @return the average burn calories per day
     */
    public double getAverageBurn(YearMonth month, EntryType type) {
        double total = 0;
        int daysInMonth = month.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = month.atDay(day);
            DailyPlan plan = plans.get(date);
            if (plan != null) {
                total += plan.getBurnCalories(type);
            }
        }
        return total / daysInMonth;
    }

    /**
     * Calculates the average net calories for a given month and entry type.
     * If some days do not have plans, they count as zero.
     *
     * @param month the target month
     * @param type the entry type to calculate
     * @return the average net calories per day
     */
    public double getAverageNet(YearMonth month, EntryType type) {
        double total = 0;
        int daysInMonth = month.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = month.atDay(day);
            DailyPlan plan = plans.get(date);
            if (plan != null) {
                total += plan.getNetCalories(type);
            }
        }
        return total / daysInMonth;
    }

    /**
     * Duplicates a daily plan from one date to another.
     * Existing entries are handled according to the conflict action.
     * <p>
	 * COMBINE will keep the existing entries and add those from the source. 
	 * REPLACE will clear all existing planned entries before adding from the source. 
	 * IGNORE will leave the target unchanged if target already have planned entry.
     * </p>
     *
     * @param from the source date
     * @param to the target date
	 * @param mode how to handle existing planned entries in the target. 
     */
    public void duplicatePlan(LocalDate from, LocalDate to, ConflictAction mode) {
        if(from.equals(to)){
            return; 
        }
        
        DailyPlan source = getDailyPlan(from);
        DailyPlan target = getDailyPlan(to);
       
        switch(mode){
        	case COMBINE:
        			for(Entry e:source.duplicateEntries())
        				target.addEntry(e);    		
        		break;
        	case REPLACE:
        			target.clearPlannedEntries();
    				for(Entry e:source.duplicateEntries())
    					target.addEntry(e);  
    			break;
        	case IGNORE:
        			if(!target.hasPlannedEntries()) {
	    				for(Entry e:source.duplicateEntries())
	    					target.addEntry(e);  	
        			}       		
        		break;
        	default:
        		break;
        }   
    }

    /**
     * Duplicates a daily plan from a date to all matching weekdays
     * in the given month. Existing entries are handled according to
     * the conflict action.
     *
     * @param from the source date
     * @param to the target month
     * @param weekDay the target day of the week (1–7)
     * @param mode how to handle existing planned entries in the target
     */
    public void duplicatePlan(LocalDate from, YearMonth to, int weekDay, ConflictAction mode) {
        LocalDate firstDay = to.atDay(1);
        LocalDate lastDay = to.atEndOfMonth();
        int diff = (weekDay - firstDay.getDayOfWeek().getValue() + 7) % 7; //get the diff between first day to desired weekday date
        LocalDate firstMatch = firstDay.plusDays(diff);

        for (LocalDate date = firstMatch; !date.isAfter(lastDay); date = date.plusWeeks(1)) {
            duplicatePlan(from, date, mode);
        }	
    }
    
}
