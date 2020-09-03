package com.tomrob.dayplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomArrayAdapter extends RecyclerView.Adapter<CustomArrayAdapter.MyViewHolder> {
    private ArrayList<TimeSlot> timeSlotList;
    private OnItemClickListener myOnItemCLickListener;

    public interface OnItemClickListener{
        void OnItemClick(int postion);


    }
    public void setOnItemCLickListener(OnItemClickListener onItemCLickListener){
        myOnItemCLickListener = onItemCLickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView myImageView;
        public TextView startTimeTV;
        public TextView endTimeTV;
        public TextView descriptionHeaderTV;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            myImageView = itemView.findViewById(R.id.imageView);
            startTimeTV = itemView.findViewById(R.id.startTimeTextView);
            endTimeTV = itemView.findViewById(R.id.endTimeTextView);
            descriptionHeaderTV = itemView.findViewById(R.id.descriptionHeaderTextView);

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
        }
    }

    public CustomArrayAdapter(ArrayList<TimeSlot> itemList){
        timeSlotList = itemList;
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
