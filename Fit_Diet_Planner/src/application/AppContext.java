package application;

import object.Library;
import object.Schedule;
import object.User;

public class AppContext {
    private static final Library library = new Library();
    private static final User user = new User("abu",20,40,150,new Schedule());
        
    //prevent init
    private AppContext() {}

    public static Library getLibrary() {
        return library;
    }

    public static Schedule getSchedule() {
        return user.getSchedule();
    }
    
    public static User getUser() {
    	return user;
    }

}