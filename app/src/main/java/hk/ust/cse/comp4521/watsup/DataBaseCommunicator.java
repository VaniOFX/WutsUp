package hk.ust.cse.comp4521.watsup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hk.ust.cse.comp4521.watsup.dummy.Observer;

public class DataBaseCommunicator {

    public static List<Event> events = new ArrayList<>();

    public static List<Observer> observers = new ArrayList<>();

    public static boolean saveEvent(Event e){
        DatabaseReference eventDB = FirebaseDatabase.getInstance().getReference("event");
        String eventID = eventDB.push().getKey();
        e.setEventID(eventID);
        eventDB.child(eventID).setValue(e);
        return false;
    }

    public static void setUpDataBase(){
        DatabaseReference eventDB = FirebaseDatabase.getInstance().getReference("event");
        eventDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                events.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Event e = ds.getValue(Event.class);
                    events.add(e);
                }
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addObserver(Observer o){
        observers.add(o);
    }

    public static void removeObserver(Observer o){
        observers.remove(o);
    }

    private static void notifyObservers(){
        for(Observer o : observers){
            o.update();
        }
    }



}
