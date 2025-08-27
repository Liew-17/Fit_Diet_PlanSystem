package controller;

import java.io.IOException;
import java.time.LocalDate;

import application.AppContext;
import application.Main;
import application.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import object.DailyPlan;
import object.Entry.EntryType;

public class DayCellController {

	private LocalDate date;

    @FXML
    private Label actualNetCalLabel;

    @FXML
    private Label planNetCalLabel;

    @FXML
    private Rectangle progressBarFg;

    @FXML
    private Rectangle progressBarBg;
	
	@FXML
	private Pane dayCell;
	
	@FXML
	private Label dayNumberLabel;
	
	@FXML
	private void viewDetails() throws IOException {
		if(date != null) {
			Main.setPage("/DetailPage");
			DetailController controller = (DetailController) Main.getController("/DetailPage");
			controller.setDate(date);		
		}
	}
	
	public void updateUI() {
        if (date != null) {
            dayNumberLabel.setText(String.valueOf(date.getDayOfMonth()));
            dayCell.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1;");    
            DailyPlan plan = AppContext.getSchedule().getDailyPlan(date);
            
            double plannedCalories = plan.getNetCalories(EntryType.PLANNED);
            double actualCalories = plan.getNetCalories(EntryType.RECORDED);
            

                
            planNetCalLabel.setText(String.format("Plan: %.0f kcal %s", plannedCalories,Utils.getIcon(plannedCalories)));
            actualNetCalLabel.setText(String.format("%.0f kcal %s", actualCalories,Utils.getIcon(actualCalories)));

            if((plannedCalories<0 && actualCalories <= plannedCalories)||(plannedCalories>0 && actualCalories >= plannedCalories)) {
            	actualNetCalLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            } else if(plannedCalories == 0){
                actualNetCalLabel.setStyle("-fx-text-fill: black; -fx-font-weight: bold;");
            } else {
            	actualNetCalLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            }
            	

            double ratio = plannedCalories == 0 ? 0 : Math.min(actualCalories / plannedCalories, 1.0); //prevent divide by 0
            double totalWidth = progressBarBg.getWidth();
            progressBarFg.setWidth(totalWidth * ratio);
            dayCell.setVisible(true);
            
        } else {
            dayCell.setVisible(false);
        }        
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
		updateUI();
	}
	
	
}
