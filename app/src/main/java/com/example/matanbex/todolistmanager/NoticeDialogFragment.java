package com.example.matanbex.todolistmanager;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

public class NoticeDialogFragment extends DialogFragment {



    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(String answer, String date);
    }

    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View inf = inflater.inflate(R.layout.dialog_add_event, null);
        final EditText answer = (EditText) inf.findViewById(R.id.answer);
        final DatePicker datePicker = (DatePicker) inf.findViewById(R.id.date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        builder.setTitle(R.string.dialog_title).setView(inf)
                .setPositiveButton(R.string.positive_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(' ');
                        stringBuilder.append(datePicker.getDayOfMonth());
                        stringBuilder.append('/');
                        stringBuilder.append(datePicker.getMonth() + 1);
                        stringBuilder.append('/');
                        stringBuilder.append(datePicker.getYear());

                        mListener.onDialogPositiveClick(answer.getText().toString(), stringBuilder.toString());
                    }
                })
                .setNegativeButton(R.string.negative_dialog, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onCancel(dialog);
                    }
                });
        return builder.create();
    }
}