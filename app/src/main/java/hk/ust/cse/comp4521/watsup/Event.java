package hk.ust.cse.comp4521.watsup;

import java.util.Date;

public class Event {


    private String eventID;
    private String name;
    private String userID;
    private int capacity;
    private String coordinates;
    private String date;
    private String description;
    private String type;

    public Event(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Event(String name, String userID, int capacity, String coordinates, String date, String description, String type) {
        this.name = name;
        this.userID = userID;
        this.capacity = capacity;
        this.coordinates = coordinates;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public void setEventID(String eventID){
        this.eventID = eventID;
    }

    public String getEventID() {
        return eventID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent(){
        return "content";
    }


}
