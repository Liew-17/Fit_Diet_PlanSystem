package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;

import application.AppContext;
import application.Main;
import application.Utils;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import object.Entry.EntryType;
import object.Schedule;

public class ScheduleController {
	
	@FXML 
	private NavBarController navBarController;
	
	DayCellController[][] dayCellControllers;
	
	@FXML
	private GridPane calendar;
	
	@FXML
	private Label yearLabel;
	
	@FXML
	private Label monthLabel;

	@FXML
	private Label plannedIntakeLabel;

	@FXML
	private Label actualIntakeLabel;

	@FXML
	private Label plannedBurnLabel;

	@FXML
	private Label actualBurnLabel;

	@FXML
	private Label plannedNetLabel;

	@FXML
	private Label actualNetLabel;
	
	private YearMonth currentMonth = null; 

	@FXML
    public void initialize() {
		navBarController.setCurrentPage("schedule");
        // Default: current month and year	
		dayCellControllers = new DayCellController[6][7];
        LocalDate today = LocalDate.now();
        currentMonth = YearMonth.of(today.getYear(), today.getMonthValue());
        generateCalendar();
        updateUI();
    }
	
    public void nextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        updateUI();
    }

    public void prevMonth() {
        currentMonth = currentMonth.minusMonths(1);
        updateUI();
    }
    
    public void prevYear() {
        currentMonth = currentMonth.minusYears(1);
        updateUI();
    }
    
    public void nextYear() {
        currentMonth = currentMonth.plusYears(1);
        updateUI();
    }
	
	public void updateYearMonthLabel() {
		switch (currentMonth.getMonthValue()) {
		    case 1:  monthLabel.setText("January");   
		    	break;
		    case 2:  monthLabel.setText("February");  
		    	break;
		    case 3:  monthLabel.setText("March");     
		    	break;
		    case 4:  monthLabel.setText("April");     
		    	break;
		    case 5:  monthLabel.setText("May");       
		    	break;
		    case 6:  monthLabel.setText("June");      
		    	break;
		    case 7:  monthLabel.setText("July");      
		    	break;
		    case 8:  monthLabel.setText("August");    
		    	break;
		    case 9:  monthLabel.setText("September"); 
		    	break;
		    case 10: monthLabel.setText("October");   
		    	break;
		    case 11: monthLabel.setText("November");  
		    	break;
		    case 12: monthLabel.setText("December");  
		    	break;
		    default: monthLabel.setText("####");
		}
		yearLabel.setText(String.valueOf(currentMonth.getYear()));
	}
	
	public void setCalendarGridSize() {
		calendar.getColumnConstraints().clear();
		calendar.getRowConstraints().clear();		
		
	    for (int i = 0; i < 7; i++) {
	        ColumnConstraints col = new ColumnConstraints();
	        col.setPrefWidth(120); // fixed column width
	        col.setHalignment(HPos.CENTER);
	        calendar.getColumnConstraints().add(col);
	    }

	    for (int i = 0; i < 7; i++) {
	        RowConstraints row = new RowConstraints();
	        row.setPrefHeight(75); // fixed row height
	        row.setValignment(VPos.CENTER);
	        calendar.getRowConstraints().add(row);
	    }
	}
	
    public void generateCalendar() {
        setCalendarGridSize();      
        //generate header
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label header = new Label(daysOfWeek[i]);
            header.setAlignment(Pos.CENTER);
            calendar.add(header, i, 0);
        }
        
        for (int row = 1; row < 7; row++) {
            for (int col = 0; col < 7; col++) {
                try {
                    FXMLLoader loader = Main.createLoader("/DayCell");
                    Parent dayPane = loader.load();
                    DayCellController controller = loader.getController();
                    dayCellControllers[row-1][col] = controller;
                    calendar.add(dayPane, col, row);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }       

    }

    public void updateCalendar() {
    	   	
        int daysInMonth = currentMonth.lengthOfMonth();
        int startDay = currentMonth.atDay(1).getDayOfWeek().getValue() % 7; //sunday = 0
        int dayCounter = 1;

        for (int row = 0; row < 6; row++) {        
            for (int col = 0; col < 7; col++) {    
                DayCellController controller = dayCellControllers[row][col];
                int cellIndex = row * 7 + col;
                
                if (cellIndex < startDay || dayCounter > daysInMonth) {
                    controller.setDate(null); 
                } else {
                    LocalDate currentDate = currentMonth.atDay(dayCounter);
                    controller.setDate(currentDate);
                    dayCounter++;
                }
            }
        }

    }
    
    public void updateUI() {
    	updateYearMonthLabel();	
    	updateCalendar();
        Schedule schedule = AppContext.getSchedule();

        plannedIntakeLabel.setText(String.format("%.0f kcal", 
            schedule.getAverageIntake(currentMonth, EntryType.PLANNED)));
        actualIntakeLabel.setText(String.format("%.0f kcal", 
            schedule.getAverageIntake(currentMonth, EntryType.RECORDED)));

        plannedBurnLabel.setText(String.format("%.0f kcal", 
            schedule.getAverageBurn(currentMonth, EntryType.PLANNED)));
        actualBurnLabel.setText(String.format("%.0f kcal", 
            schedule.getAverageBurn(currentMonth, EntryType.RECORDED)));
        
        double planAvgNet = schedule.getAverageNet(currentMonth, EntryType.PLANNED);
        double actualAvgNet = schedule.getAverageNet(currentMonth, EntryType.RECORDED);
 
        plannedNetLabel.setText(String.format("%.0f kcal %s",planAvgNet,Utils.getIcon(planAvgNet))); 
            
        actualNetLabel.setText(String.format("%.0f kcal %s",actualAvgNet,Utils.getIcon(actualAvgNet))); 
            
    
    }
    
    public void home() throws IOException {
    	Main.setPage("/Main");
    }
}


