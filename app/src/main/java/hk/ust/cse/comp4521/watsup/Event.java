package hk.ust.cse.comp4521.watsup;

import java.util.Date;

public class Event {

    public String eventID;
    public String name;
    public String userID;
    public int capacity;
    public String coordinates;
    public Date date;

    public Event(String name, String userID, int capacity, String coordinates, Date date) {
        this.name = name;
        this.userID = userID;
        this.capacity = capacity;
        this.coordinates = coordinates;
        this.date = date;
    }

    public void setEventID(String eventID){
        this.eventID = eventID;
    }

}
