package hk.ust.cse.comp4521.watsup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

    EditText nameText;
    EditText decriptionText;
    EditText capacityText;
    EditText coordinatesText;
    EditText dateText;

    private TextView dateResult;
    private TextView timeResult;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        nameText = (EditText) findViewById(R.id.eventNameText);
        decriptionText = (EditText) findViewById(R.id.descriptionText);
        capacityText = (EditText) findViewById(R.id.eventCapacity);
        //coordinatesText = (EditText) findViewById(R.id.eventPlace);
        //dateText = (EditText) findViewById(R.id.dateText);
        Button addEventButton = (Button) findViewById(R.id.addEventButton);
        Button locationButton = (Button) findViewById(R.id.location);



        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isServicesOK()) {
                    Intent i = new Intent(AddEventActivity.this, MapActivity.class);
                    i.putExtra(Event.EVENT_NAME, nameText.getText().toString());
                    i.putExtra(Event.EVENT_CAPACITY, Integer.parseInt(capacityText.getText().toString()));
                    i.putExtra(Event.EVENT_DATE, dateResult.getText().toString());
                    i.putExtra(Event.EVENT_DESCRIPTION, decriptionText.getText().toString());
                    i.putExtra(Event.EVENT_TYPE, decriptionText.getText().toString());
                    startActivity(i);
                }
            }
        });

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEvent();
            }
        });

        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        dateResult = (TextView) findViewById(R.id.dateText);

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

        //Button timeText = (Button) findViewById(R.id.timetext);
        timeResult = (TextView) findViewById(R.id.timetext);
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
