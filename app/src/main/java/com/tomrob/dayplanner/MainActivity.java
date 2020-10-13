package com.tomrob.dayplanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener {



    static ArrayList<TimeSlot> timeSlotList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    static CustomArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;

    private NotificationHelper mNotificationHelper;

    @Override //Menu creation
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.start_new_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        // code for switching activites when add is clicked
       if (item.getItemId() == R.id.start_new_menu){

           new AlertDialog.Builder(MainActivity.this)
                   .setIcon(android.R.drawable.ic_dialog_alert)
                   .setTitle("Are you sure?")
                   .setMessage("Do you want to start a new day?")
                   .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {


                           // need to clear list, and recycleView?
                           // then saveData
                           timeSlotList.clear();
                           mAdapter.notifyDataSetChanged();
                           saveData();


                       }
                   })
                   .setNegativeButton("Cancel", null)
                   .show();


           return true;
        }
         //return false;

       if(item.getItemId() == R.id.email_menu_item){
           Toast.makeText(getApplicationContext(), "Email Time!", Toast.LENGTH_LONG).show();

           // need to build dialog for capturing email address
           // then need to build sendEmail method
           // which will create the subject and body of the email
           // loop through arrayList<timeslot>
           // formatting each object , so it can be easily read

            //openDialog();
            sendNotification("title", "message");

           //sendEmail();
       }

        return false;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        buildRecyclerView();
        buildAddButton();

        mNotificationHelper = new NotificationHelper(this);
        startAlarm();
        //insertItem(new TimeSlot("12:30","17:00", "Work", "App Save implementation", "des body","03/09/2020"));
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void createTimeSlotList(){

        timeSlotList.add(new TimeSlot("10:00","12:00", "Work", "App Save implementation", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("13:00","14:00", "Work", "Chest workout", "des body","03/09/2020"));

    }

    public void buildRecyclerView(){
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); //remove this because i will be changing the size of the view
        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new CustomArrayAdapter(timeSlotList);

        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemCLickListener(new CustomArrayAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(final int position) {
                // on click working!
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Turn notification on?")
                        .setMessage("Notify me when the time slot ends?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                //startAlarm(on TimeSlot EndTime);
                                startTimeSlotAlarm(position);

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                //Toast.makeText(getApplicationContext(), "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnDeleteClick(final int position) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeItem(position);
                                saveData();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();



            }
        });
    }

    public void buildAddButton(){
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action_button_main);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTimeSlotActivity.class);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void insertItem(TimeSlot timeSlot){
        //mAdapter = new CustomArrayAdapter(timeSlotList);


        // CHECK THIS! WILL THE RECYCLER VIEW UPDATE WITH THE LIST IN ORDER

        // loop through list comparing each TimeSlot objects startTime
        // if the insert items start time is before the other objects
        // insert new object in that index
        // if start time is after
        // check the next list item
        // insert at end of list in start time after every other
        for(int i = 0; i < timeSlotList.size(); i++){

            TimeSlot obj = timeSlotList.get(i);

            LocalTime start = LocalTime.parse(timeSlot.getStartTime());
            LocalTime stop = LocalTime.parse(obj.getStartTime());

            boolean isStopAfterStart = stop.isAfter(start);

            if (isStopAfterStart) {
                // start time is smaller
                timeSlotList.add(i,timeSlot);
                mAdapter.notifyDataSetChanged();
                return;
            } else {
                // start time is larger
                continue;
            }


        }
        timeSlotList.add(timeSlot);
        mAdapter.notifyDataSetChanged();
        //timeSlotList.add(timeSlot);
        //mAdapter.updateReceiptsList(timeSlotList);
        //mAdapter.notifyDataSetChanged();
        //mAdapter.notifyItemInserted(timeSlotList.size() +1 );

    }

    public static void insertItem(String startTime, String endTime){

        //mAdapter = new CustomArrayAdapter(timeSlotList);
        timeSlotList.add(new TimeSlot(startTime,endTime, "Work", "New Item", "des body","03/09/2020"));
        mAdapter.notifyItemInserted(timeSlotList.size() +1 );

        //mAdapter.notifyDataSetChanged();// without animation which i cant see anyway cos its at the bottom of the view
        // could try to implement in time order inserting , so the time slots will be in order no matter what
    }



    public void removeItem(int position){
        // both methods need an index/postion number
        // this will be attained once OnLongClick has been worked out
        timeSlotList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void updateData(ArrayList<TimeSlot> data) {
        timeSlotList = new ArrayList<>(data);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void openDialog(){
        CustomDialog customDialog = new CustomDialog();



        customDialog.show(getSupportFragmentManager(), "customDialog");
    }

  /*  @Override
    public void applyTexts(String email) {
        insertItem(startTime, endTime);
    }*/

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(timeSlotList);
        editor.putString("time slot list", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("time slot list", null);
        Type type = new TypeToken<ArrayList<TimeSlot>>() {}.getType();
        timeSlotList = gson.fromJson(json, type);

        if(timeSlotList == null){
            timeSlotList = new ArrayList<>();
        }

    }

    public void sendEmail(String email){

        String recipient = email; // THIS WILL BE SET BY USER FROM DIALOG
        String subject = "Todays plan!"; // Date to be added



        String emailStart = "Todays Plan!\n" + timeSlotList.get(0).getDate() + "\n\n";

        String timeSlotString = "";
        StringBuilder timeSlots = new StringBuilder();
        for(int i = 0; i < timeSlotList.size() ; i++){
            TimeSlot timeSlot = timeSlotList.get(i);

            timeSlotString = timeSlot.getStartTime() + " - " + timeSlot.getEndTime() + "\n"
                              + timeSlot.getTimeSlotType() + "\n"
                                + timeSlot.getDescriptionHeader() + "\n\n";
            timeSlots.append(timeSlotString);
        }


        String message = emailStart + timeSlots;
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();



        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));

    }


    @Override
    public void applyTexts(String email) {
        sendEmail(email);
    }

    public void sendNotification(String title,String message){
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification();
        mNotificationHelper.getManager().notify(1,nb.build());
    }

    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
       /* if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }*/

        int interval = 1000 * 60 * 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // set the triggered time to currentHour:08:00 for testing
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        //calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 9);

        if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

      /*  alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 0, pendingIntent);*/

        //AlarmManager.INTERVAL_DAY

        if(alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        }
    }


    private void startTimeSlotAlarm(int position) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        TimeSlot timeSlot = timeSlotList.get(position);
        String timeString = timeSlot.getEndTime(); //"HH:mm" mm cant have 0 in front
        String[] separated = timeString.split(":");
        String hour = separated[0];
        String min = separated[1];

        hour = hour.replaceAll("^0+(?=.)", "");
        min = min.replaceAll("^0+(?=.)", "");

        Calendar calendar = Calendar.getInstance();
        //calendar.setTimeInMillis(System.currentTimeMillis());
        // set the triggered time to currentHour:08:00 for testing
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        //calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, Integer.parseInt(min));

        /*if (calendar.getTime().compareTo(new Date()) < 0)
            calendar.add(Calendar.DAY_OF_MONTH, 1);*/

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getApplicationContext(), "Alarm Set " + hour +":"+ min, Toast.LENGTH_LONG).show();

    }

}