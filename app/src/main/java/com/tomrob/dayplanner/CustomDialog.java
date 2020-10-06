package com.tomrob.dayplanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private TextInputEditText editTextEmail;
    private Spinner spinnerTimeSlotType;
    private CustomDialogListener listener;
    private TextInputLayout textInputLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        textInputLayout = view.findViewById(R.id.TextInputLayoutEmail);
        editTextEmail = view.findViewById(R.id.TextInputEditTextEmail);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                validateEditText(editable);
            }
        });





        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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


    @Override
    public void onResume() {
        super.onResume();

        AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    public interface CustomDialogListener{
        void applyTexts(String email);
    }

    private void validateEditText(Editable s) {
        AlertDialog dialog = (AlertDialog) getDialog();
        if (!TextUtils.isEmpty(s)) {
           // layoutEdtPhone.setError(null);
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
        }
        else{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
           // layoutEdtPhone.setError(getString(R.string.ui_no_password_toast));
        }
    }



}
