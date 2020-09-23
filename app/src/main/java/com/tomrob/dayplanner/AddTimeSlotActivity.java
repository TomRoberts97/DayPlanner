package com.tomrob.dayplanner;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AddTimeSlotActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    TimePickerDialog startTimePicker, endTimePicker;
    EditText editTextStartTime, editTextEndTime, editTextDesHeader;
    TextView textViewDate;
    Spinner spinner;
    AutoCompleteTextView filledExposedDropdown;
    TextInputLayout textInputLayoutStartTime, textInputLayoutEndTime, textInputLayoutDesHeader;
    String selectedType = "Other";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_slot);

        filledExposedDropdown = findViewById(R.id.autoCompleteExposedDropdown);
        //spinner = findViewById(R.id.autoCompleteExposedDropdown);
        editTextDesHeader = findViewById(R.id.EditTextDescriptionHeader);
        fillSpinner();


        textViewDate = findViewById(R.id.TextViewDateDisplay);
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(date);
        textViewDate.setText(todayDate);





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





        // start of add/save button click
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            
                boolean timeFlag = false, desHeadFlag;
                
                textInputLayoutStartTime = findViewById(R.id.outlinedTextFieldStartTime);
                textInputLayoutEndTime = findViewById(R.id.outlinedTextFieldEndTime);
                textInputLayoutDesHeader = findViewById(R.id.outlinedTextFieldDescriptionHeader);

                if(editTextStartTime.getText().toString().isEmpty()){
                    textInputLayoutStartTime.setError("Missing data!");
                } else {
                    textInputLayoutStartTime.setError(null);
                }
                if (editTextEndTime.getText().toString().isEmpty()){
                    textInputLayoutEndTime.setError("Missing data!");
                } else {
                    textInputLayoutEndTime.setError(null);
                }
                if (editTextDesHeader.getText().toString().isEmpty()){
                    textInputLayoutDesHeader.setError("Missing data!");
                    desHeadFlag = false;
                } else {
                    textInputLayoutDesHeader.setError(null);
                    desHeadFlag = true;
                }


                if(!editTextStartTime.getText().toString().isEmpty() && !editTextEndTime.getText().toString().isEmpty() ) {
                    
                    String strStartTime = editTextStartTime.getText().toString();
                    String strEndTime = editTextEndTime.getText().toString();

                    LocalTime start = LocalTime.parse(strStartTime);
                    LocalTime stop = LocalTime.parse(strEndTime);

                    boolean isStopAfterStart = stop.isAfter(start);

                    if (isStopAfterStart) {
                       // textInputLayoutStartTime.setError("Start time is after end time!");
                        textInputLayoutStartTime.setError(null);
                        timeFlag = true;
                    } else {
                        textInputLayoutStartTime.setError("Start time cannot be after End time!");
                        timeFlag = false;
                    }
                }




                //Data Validation to be added!!
                if(timeFlag && desHeadFlag) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String todayDate = dateFormat.format(date);

                    TimeSlot timeSlot = new TimeSlot(editTextStartTime.getText().toString(), editTextEndTime.getText().toString(), selectedType, editTextDesHeader.getText().toString(), "Please add more details", todayDate);
                    //MainActivity.timeSlotList.add(timeSlot);
                    //MainActivity.mAdapter.notifyDataSetChanged();

                    MainActivity.insertItem(timeSlot);

                    saveData();
                    //Toast.makeText(getApplicationContext(), MainActivity.timeSlotList.get(MainActivity.timeSlotList.size() -1).toString() , Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });



    } //  end of OnCreate

    private void fillSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.time_slot_type_array, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);

        filledExposedDropdown.setText("Other");
        filledExposedDropdown.setAdapter(adapter);
        filledExposedDropdown.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // fetch the user selected value
        String item = parent.getItemAtPosition(position).toString();

        // create Toast with user selected value
        Toast.makeText(AddTimeSlotActivity.this, "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();

        selectedType = item;
        // set user selected value to the TextView
        //tvDisplay.setText(item);

    }



    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(MainActivity.timeSlotList);
        editor.putString("time slot list", json);
        editor.apply();
    }

    boolean isTimeAfter(Date startTime, Date endTime) {
        if (endTime.before(startTime)) {
            return false;
        } else {
            return true;
        }
    }

}
