package hk.ust.cse.comp4521.watsup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DataBaseCommunicator {

    public static boolean saveEvent(Event e){
        DatabaseReference eventDB = FirebaseDatabase.getInstance().getReference("event");
        String eventID = eventDB.push().getKey();
        e.setEventID(eventID);
        eventDB.child(eventID).setValue(e);
        return false;
    }


    public static
    }


}
