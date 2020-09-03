package com.tomrob.dayplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
      /* if (item.getItemId() == R.id.add_note){
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);

            startActivity(intent);
            return true;
        }
         return false;*/

            insertItem();




        return false;
    }


    static ArrayList<TimeSlot> timeSlotList;

    private RecyclerView mRecyclerView;
    private CustomArrayAdapter mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;


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
        });
    }

    public void insertItem(){

        timeSlotList.add(new TimeSlot("10:00","12:00", "Work", "New Item", "des body","03/09/2020"));
        mAdapter.notifyItemInserted(timeSlotList.size() +1 );
        //mAdapter.notifyDataSetChanged();// without animation
    }

    public void removeItem(){
        // both methods need an index/postion number
        // this will be attained once OnLongClick has be worked out
        //timeSlotList.remove();
        //mAdapter.notifyItemRemoved();
    }



}