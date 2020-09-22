package com.tomrob.dayplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TimeSlot currentItem = timeSlotList.get(position);

        // if statement for which image display here
        // problem started after adding validation on Add activity , no idea why,
        /*if(currentItem.getTimeSlotType().equals("Work")){
            holder.myImageView.setImageResource(R.drawable.ic_baseline_laptop_windows_24);
        } else if(currentItem.getTimeSlotType().equals("Workout")){
            holder.myImageView.setImageResource(R.drawable.ic_baseline_fitness_center_24);
        } else if(currentItem.getTimeSlotType().equals("Meditate")){
            holder.myImageView.setImageResource(R.drawable.ic_baseline_spa_24);
        } else {
            //holder.myImageView.setImageResource(R.drawable.ic_baseline_spa_24);
            holder.myImageView.setImageResource(R.drawable.ic_android);
        }*/

        holder.myImageView.setImageResource(R.drawable.ic_android);
        holder.startTimeTV.setText(currentItem.getStartTime());
        holder.endTimeTV.setText(currentItem.getEndTime());
        holder.descriptionHeaderTV.setText(currentItem.getDescriptionHeader());
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }
}
