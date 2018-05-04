package hk.ust.cse.comp4521.watsup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity{

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




}
