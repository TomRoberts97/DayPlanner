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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class CustomDialog extends DialogFragment {
//public class CustomDialog extends AppCompatDialogFragment {

    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private TextInputEditText editTextEmail;
    private Spinner spinnerTimeSlotType;
    private CustomDialogListener listener;
    private TextInputLayout textInputLayout;
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

        textInputLayout = view.findViewById(R.id.TextInputLayoutEmail);

        builder.setView(view)
                .setTitle("Send day plan via Email")
                .setMessage("Please enter email you wish to send to")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       // String startTime = editTextStartTime.getText().toString();
                       // String endTime = editTextEndTime.getText().toString();

                        if(editTextEmail.getText().toString().isEmpty()){
                            //Toast.makeText(getApplicationContext(), "Email Time!", Toast.LENGTH_LONG).show();
                            textInputLayout.setError("Missing data!");
                        } else {
                            textInputLayout.setError(null);
                            String email = editTextEmail.getText().toString();
                            listener.applyTexts(email);
                        }



                    }
                });

        //editTextStartTime = view.findViewById(R.id.edit_start_time);
        //editTextEndTime = view.findViewById(R.id.edit_end_time);
        editTextEmail = view.findViewById(R.id.TextInputEditTextEmail);

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
        void applyTexts(String email);
    }


}
