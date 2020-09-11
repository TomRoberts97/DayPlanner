package com.tomrob.dayplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomDialog.CustomDialogListener{



    static ArrayList<TimeSlot> timeSlotList;

    private RecyclerView mRecyclerView;
    static CustomArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;


    @Override //Menu creation
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_time_slot_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        // code for switching activites when add is clicked
       if (item.getItemId() == R.id.add_time_slot){
            Intent intent = new Intent(getApplicationContext(), AddTimeSlotActivity.class);

            startActivity(intent);
            return true;
        }
         return false;


            //insertItem();
            //openDialog();
            //return false;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTimeSlotList();

        buildRecyclerView();


    }


    public void createTimeSlotList(){
        timeSlotList = new ArrayList<>();
        timeSlotList.add(new TimeSlot("09:00","10:00", "Work", "App UI Design", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("10:00","12:00", "Work", "App Save implementation", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("13:00","14:00", "Work", "Chest workout", "des body","03/09/2020"));

        timeSlotList.add(new TimeSlot("09:00","10:00", "Work", "App UI Design", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("10:00","12:00", "Work", "App Save implementation", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("13:00","14:00", "Work", "Chest workout", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("09:00","10:00", "Work", "App UI Design", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("10:00","12:00", "Work", "App Save implementation", "des body","03/09/2020"));
        timeSlotList.add(new TimeSlot("13:00","14:00", "Work", "Chest workout", "des body","03/09/2020"));
        insertItem(new TimeSlot("13:99","14:99", "Work", "Chest workout", "des body","03/09/2020"));
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
            public void OnItemClick(int position) {
                // on click working!

                Toast.makeText(getApplicationContext(), "selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnDeleteClick(final int position) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this time slot?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeItem(position);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();



            }
        });
    }

    public static void insertItem(TimeSlot timeSlot){
        mAdapter = new CustomArrayAdapter(timeSlotList);
        timeSlotList.add(timeSlot);
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(timeSlotList.size() +1 );

    }

    public static void insertItem(String startTime, String endTime){

        timeSlotList.add(new TimeSlot(startTime,endTime, "Work", "New Item", "des body","03/09/2020"));
        mAdapter.notifyItemInserted(timeSlotList.size() +1 );

        //mAdapter.notifyDataSetChanged();// without animation which i cant see anyway cos its at the bottom of the view
        // could try to implement in time order inserting , so the time slots will be in order no matter what
    }

    public static void insertItem(String startTime, String endTime, String timeSlotType, String desHeader,String date){

        timeSlotList.add(new TimeSlot(startTime,endTime, timeSlotType, desHeader, "Please add more details",date));
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

    public void openDialog(){
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getSupportFragmentManager(), "customDialog");
    }

    @Override
    public void applyTexts(String startTime, String endTime) {
        insertItem(startTime, endTime);
    }
}