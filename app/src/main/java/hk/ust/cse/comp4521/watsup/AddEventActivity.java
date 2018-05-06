package hk.ust.cse.comp4521.watsup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import hk.ust.cse.comp4521.watsup.models.Event;

public class AddEventActivity extends AppCompatActivity{


    private static final String TAG = "AddEventActivity" ;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int REQUEST_CODE = 1234;


    private EditText nameText;
    private EditText descriptionText;
    private EditText capacityText;
    private TextView dateResult;
    private TextView timeResult;
    private Button locationButton;
    private String eventCoordinates;
    private Spinner dropdown;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        nameText = (EditText) findViewById(R.id.eventNameText);
        descriptionText = (EditText) findViewById(R.id.descriptionText);
        capacityText = (EditText) findViewById(R.id.eventCapacity);
        Button addEventButton = (Button) findViewById(R.id.addEventButton);
        locationButton = (Button) findViewById(R.id.location);
        dateResult = (TextView) findViewById(R.id.dateText);
        //Button timeText = (Button) findViewById(R.id.timetext);
        timeResult = (TextView) findViewById(R.id.timetext);


        dateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                dateResult.setText(date);
            }
        };


        timeResult.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener,
                        hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });

        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String time = hour + " : " + minute;
                timeResult.setText(time);

            }
        };

        dropdown = findViewById(R.id.type);
        String[] items = new String[]{"No Type", "Sport", "Party", "Outdoor", "Hangout", "Viewing", "Birthday", "Study"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isServicesOK()) {
                    Intent i = new Intent(AddEventActivity.this, MapActivity.class);
//                    i.putExtra(Event.EVENT_NAME, nameText.getText().toString());
//                    i.putExtra(Event.EVENT_CAPACITY, Integer.parseInt(capacityText.getText().toString()));
//                    i.putExtra(Event.EVENT_DATE, dateResult.getText().toString());
//                    i.putExtra(Event.EVENT_DESCRIPTION, descriptionText.getText().toString());
//                    i.putExtra(Event.EVENT_TYPE, descriptionText.getText().toString());
//                    i.putExtra(Event.EVENT_TIME, timeResult.getText().toString());
                    i.putExtra(MapActivity.CALLING_ACTIVITY, MapActivity.ADDEVENT_ACITIVTY);
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addEvent()) {
                    Toast.makeText(AddEventActivity.this, "Event Added Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddEventActivity.this, OptionsActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                eventCoordinates = data.getStringExtra(Event.EVENT_COORDINATES);
                locationButton.setText("Location Selected Successfully");
                Log.d(TAG, "onActivityResult: "+ eventCoordinates);
            }
        }
    }

    private boolean addEvent(){
        String eventName = nameText.getText().toString();
        if(TextUtils.isEmpty(eventName)){
            nameText.setError("The event should have a name");
            return false;
        }

        String eventDescription = descriptionText.getText().toString();
        if(TextUtils.isEmpty(eventDescription)){
            descriptionText.setError("The event should have a description");
            return false;
        }

        String eventCapacity = capacityText.getText().toString();
        if(TextUtils.isEmpty(eventCapacity)){
            capacityText.setError("The event should have a capacity");
            return false;
        }

        String eventDate = dateResult.getText().toString();
        if(eventDate.equals("Date")){
            dateResult.setError("The event should have a date");
            return false;
        }

        String eventTime = timeResult.getText().toString();
        if(eventTime.equals("Time")){
            timeResult.setError("The event should have a time");
             return false;
        }

        String eventType = dropdown.getSelectedItem().toString();

        if(TextUtils.isEmpty(eventCoordinates)){
            locationButton.setError("The event should have a location");
            return false;
        }

        String userID = FirebaseAuth.getInstance().getUid();
        Event e = new Event(eventName, userID, eventCapacity, eventCoordinates, eventDate, eventTime, eventDescription, eventType);
        DataBaseCommunicator.saveEvent(e);
        return true;
    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddEventActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddEventActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}
