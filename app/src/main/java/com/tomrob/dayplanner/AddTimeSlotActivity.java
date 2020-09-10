package com.tomrob.dayplanner;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class AddTimeSlotActivity extends AppCompatActivity {

    TimePickerDialog startTimePicker, endTimePicker;
    EditText editTextStartTime, editTextEndTime;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_slot);


        spinner = findViewById(R.id.time_slot_type_spinner);

        fillSpinner();












        // code block for Start time picker dialog
        editTextStartTime = findViewById(R.id.EditTextStartTime);
        editTextStartTime.setInputType(InputType.TYPE_NULL);
        editTextStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                startTimePicker = new TimePickerDialog(AddTimeSlotActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String startTimeString = null;
                                if(sHour < 10 && sMinute < 10){
                                    startTimeString = "0" + sHour + ":0" + sMinute;
                                } else if(sHour < 10) {
                                    startTimeString = "0" + sHour + ":" + sMinute;
                                } else if(sMinute < 10){
                                    startTimeString = sHour + ":0" + sMinute;
                                } else {
                                    startTimeString = sHour + ":" + sMinute;
                                }
                                editTextStartTime.setText(startTimeString);

                            }
                        }, hour, minutes, true);
                startTimePicker.show();
            }
        }); // end of start time picker dialog
        // code block for End time picker dialog
        editTextEndTime = findViewById(R.id.EditTextEndTime);
        editTextEndTime.setInputType(InputType.TYPE_NULL);
        editTextEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                endTimePicker = new TimePickerDialog(AddTimeSlotActivity.this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                String endTimeString = null;
                                if(sHour < 10 && sMinute < 10){
                                    endTimeString = "0" + sHour + ":0" + sMinute;
                                } else if(sHour < 10) {
                                    endTimeString = "0" + sHour + ":" + sMinute;
                                } else if(sMinute < 10){
                                    endTimeString = sHour + ":0" + sMinute;
                                } else {
                                    endTimeString = sHour + ":" + sMinute;
                                }
                                editTextEndTime.setText(endTimeString);

                            }
                        }, hour, minutes, true);
                endTimePicker.show();
            }
        }); // end of end time picker dialog


        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_LONG).show();
            }
        });



    } //  end of OnCreate

    private void fillSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.time_slot_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
