package com.tomrob.dayplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class CustomDialog extends DialogFragment {
//public class CustomDialog extends AppCompatDialogFragment {

    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private Spinner spinnerTimeSlotType;
    private CustomDialogListener listener;

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullScreenDialogTheme);
    }*/

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle("Add time slot")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String startTime = editTextStartTime.getText().toString();
                        String endTime = editTextEndTime.getText().toString();
                        listener.applyTexts(startTime,endTime);
                    }
                });

        editTextStartTime = view.findViewById(R.id.edit_start_time);
        editTextEndTime = view.findViewById(R.id.edit_end_time);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CustomDialogListener");
        }
    }

    public interface CustomDialogListener{
        void applyTexts(String startTime, String endTime);
    }


}
