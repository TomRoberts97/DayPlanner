package com.tomrob.dayplanner;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CustomArrayAdapter extends RecyclerView.Adapter<CustomArrayAdapter.MyViewHolder> {
    private ArrayList<TimeSlot> timeSlotList;
    private OnItemClickListener myOnItemCLickListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnDeleteClick(int position);

    }
    public void setOnItemCLickListener(OnItemClickListener onItemCLickListener){
        myOnItemCLickListener = onItemCLickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView myImageView;
        public TextView startTimeTV;
        public TextView endTimeTV;
        public TextView descriptionHeaderTV;
        public ImageView myDeleteImage;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            myImageView = itemView.findViewById(R.id.imageView);
            startTimeTV = itemView.findViewById(R.id.startTimeTextView);
            endTimeTV = itemView.findViewById(R.id.endTimeTextView);
            descriptionHeaderTV = itemView.findViewById(R.id.descriptionHeaderTextView);
            myDeleteImage = itemView.findViewById(R.id.image_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            myDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public CustomArrayAdapter(ArrayList<TimeSlot> itemList){
        timeSlotList = itemList;
    }

    public void updateReceiptsList(List<TimeSlot> newList) {
        timeSlotList.clear();
        timeSlotList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        CustomArrayAdapter.MyViewHolder evh = new CustomArrayAdapter.MyViewHolder(v, myOnItemCLickListener);
        return evh;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TimeSlot currentItem = timeSlotList.get(position);

        // if statement for which image display here
        // problem started after adding validation on Add activity , no idea why,
        final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String time = dateFormat.format(new Date());

        LocalTime timeSlotStartTime = LocalTime.parse(currentItem.getStartTime());
        LocalTime timeSlotEndTime = LocalTime.parse(currentItem.getEndTime());
        LocalTime realTime = LocalTime.parse(time);

        boolean isRealTimeAfterStartTime = realTime.isAfter(timeSlotStartTime);
        boolean isRealTimeAfterEndTime = realTime.isAfter(timeSlotEndTime); // true if realTime is after end time

        switch (currentItem.getTimeSlotType()) {
            case "Work":

                if (isRealTimeAfterEndTime) {
                    // timeSlotTime is smaller
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_laptop_windows_green);
                } else if (!isRealTimeAfterEndTime && isRealTimeAfterStartTime){
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_laptop_windows_yellow);
                } else {
                    // timeSlotTime is larger
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_laptop_windows_24);
                }

                break;
            case "Workout":
                if (isRealTimeAfterEndTime) {
                    // timeSlotTime is smaller
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_fitness_center_green);
                }  else if (!isRealTimeAfterEndTime && isRealTimeAfterStartTime){
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_fitness_center_yellow);
                }  else {
                    // timeSlotTime is larger
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_fitness_center_24);
                }
                break;
            case "Meditate":
                if (isRealTimeAfterEndTime) {
                    // timeSlotTime is smaller
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_spa_green);
                }  else if (!isRealTimeAfterEndTime && isRealTimeAfterStartTime){
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_spa_yellow);
                } else {
                    // timeSlotTime is larger
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_spa_24);
                }
                break;
            default:
                if (isRealTimeAfterEndTime) {
                    // timeSlotTime is smaller
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_blur_circular_green);
                }  else if (!isRealTimeAfterEndTime && isRealTimeAfterStartTime){
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_blur_circular_yellow);
                }  else {
                    // timeSlotTime is larger
                    holder.myImageView.setImageResource(R.drawable.ic_baseline_blur_circular_24);
                }
                break;
        }

        //holder.myImageView.setImageResource(R.drawable.ic_android);
        holder.startTimeTV.setText(currentItem.getStartTime());
        holder.endTimeTV.setText(currentItem.getEndTime());
        holder.descriptionHeaderTV.setText(currentItem.getDescriptionHeader());
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }
}
