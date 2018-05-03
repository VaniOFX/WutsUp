package hk.ust.cse.comp4521.watsup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class AddEventActivity extends AppCompatActivity {

    EditText nameText;
    EditText decriptionText;
    EditText capacityText;
    EditText coordinatesText;
    EditText dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        nameText = (EditText) findViewById(R.id.eventNameText);
        decriptionText = (EditText) findViewById(R.id.descriptionText);
        capacityText = (EditText) findViewById(R.id.eventCapacity);
        coordinatesText = (EditText) findViewById(R.id.eventPlace);
        dateText = (EditText) findViewById(R.id.eventDate);
        Button addEventButton = (Button) findViewById(R.id.addEventButton);

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });
    }


    private void addEvent(){
        String name = nameText.getText().toString().trim();
        String decription = decriptionText.getText().toString().trim();
        int capacity = Integer.parseInt(capacityText.getText().toString());
        String coordinates = coordinatesText.getText().toString().trim();
        String date = dateText.getText().toString();
        String userID = FirebaseAuth.getInstance().getUid();
        Event e = new Event(name, userID, capacity, coordinates, date, decription, null);
        DataBaseCommunicator.saveEvent(e);
    }
}
