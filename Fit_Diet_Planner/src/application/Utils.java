package application;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class Utils {
    public static String capitalizeSafe(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
    public static int parseIntOrZero(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    public static String getIcon(double calories) {
     	if(calories > 0)
    		return "🔺";
    	else if(calories < 0)
    		return "🔻";
    	else
    		return "";
    }
    
    public static double parseDoubleOrZero(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public static void setIntField(TextField field) {
    	field.setTextFormatter(new TextFormatter<>(change -> {
    	    String newText = change.getControlNewText();
    	    if (newText.matches("\\d*")) { // only digits
    	        return change; 
    	    }
    	    return null;
    	}));
    }
    
    public static void setDoubleField(TextField field) {
    	field.setTextFormatter(new TextFormatter<>(change -> {
    	    String newText = change.getControlNewText();
    	    if (newText.matches("\\d*(\\.\\d*)?")) { // digits and optional decimal point
    	        return change; 
    	    }
    	    return null;
    	})); 
    }
}
