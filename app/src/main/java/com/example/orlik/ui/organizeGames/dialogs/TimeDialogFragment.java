package com.example.orlik.ui.organizeGames.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import com.example.orlik.R;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Poland"));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.my_dialog_theme,this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        TextView timeTextView = getActivity().findViewById(R.id.organize_gameTime_TextView);
        if(hourOfDay<10){
            timeTextView.setText("0"+hourOfDay+":"+minutes);
        }else{
            timeTextView.setText(hourOfDay+":"+minutes);
        }

    }
}
