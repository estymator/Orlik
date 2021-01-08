package com.example.orlik.ui.organizeGames.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.orlik.R;
import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private OrganizeViewModel organizeViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        organizeViewModel= new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        final Calendar c = Calendar.getInstance(TimeZone.getTimeZone("Poland"));
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.my_dialog_theme,this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        return timePickerDialog;


    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
        TextView timeTextView = getActivity().findViewById(R.id.organize_gameTime_TextView);
        String gameTime;
        String minutesS =String.valueOf(minutes);
        if(minutes<10)
        {
            minutesS="0"+minutes;
        }
        if(hourOfDay<10){
           gameTime="0"+hourOfDay+":"+minutesS;
        }else{
           gameTime = hourOfDay+":"+minutesS;
        }
        timeTextView.setText(gameTime);
        organizeViewModel.setGameTime(gameTime);
    }
}
