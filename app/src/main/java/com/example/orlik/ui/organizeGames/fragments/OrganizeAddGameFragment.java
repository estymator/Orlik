package com.example.orlik.ui.organizeGames.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.orlik.R;
import com.example.orlik.data.location.LocationGetter;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;
import com.example.orlik.ui.organizeGames.dialogs.DateDialagFragment;
import com.example.orlik.ui.organizeGames.dialogs.TimeDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.

 */
public class OrganizeAddGameFragment extends Fragment {
    private static final String TAG = "OrganizeAddGameFragment";
    private OrganizeViewModel organizeViewModel;
    private Spinner maxPlayersSpinner, minPlayersSpinner, visibilitySpinner, pitchSpinner;
    private TextView gameDate, gameTime;
    private CheckBox isOrganizerPlaying;
    Button addGameButton;
    private Calendar date = Calendar.getInstance();
    private  int day=date.get(Calendar.DAY_OF_MONTH), month=date.get(Calendar.MONTH), year=date.get(Calendar.YEAR);
    private int hour=(date.get(Calendar.HOUR_OF_DAY)==24)? 00:(date.get(Calendar.HOUR)+1);

    public OrganizeAddGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        organizeViewModel = new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_organize_add_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        maxPlayersSpinner=(Spinner) view.findViewById(R.id.organize_max_players_spinner);
        organizeViewModel.setOrganizeSpinner(maxPlayersSpinner,R.array.players_number_array);
        maxPlayersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                organizeViewModel.setMaxPlayersNumber(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        minPlayersSpinner=(Spinner) view.findViewById(R.id.organize_min_players_spinner);
        organizeViewModel.setOrganizeSpinner(minPlayersSpinner,R.array.players_number_array);
        minPlayersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                organizeViewModel.setMinPlayersNumber(Integer.valueOf(adapterView.getItemAtPosition(i).toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        visibilitySpinner=(Spinner) view.findViewById(R.id.organize_visibility_spinner);
        organizeViewModel.setOrganizeSpinner(visibilitySpinner,R.array.visibility_array);
        visibilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                organizeViewModel.setVisibility(adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pitchSpinner=(Spinner) view.findViewById(R.id.organize_pitch_spinner);
//        after receive list of pitch set result to display on spinner
        organizeViewModel.getPitchList().observe(requireActivity(), new Observer<ArrayList<Pitch>>() {
            @Override
            public void onChanged(ArrayList<Pitch> pitches) {
                if(pitches!=null){
                    organizeViewModel.setPitchesAdapter(pitchSpinner);
                }
            }
        });
        pitchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v(TAG,(adapterView.getItemAtPosition(i).toString()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        organizeViewModel.requestPitchList();

        gameDate = (TextView) view.findViewById(R.id.organize_gameDate_TextView);
        gameDate.setText((day+"/"+month+"/"+year));
        gameDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateDialagFragment dateDialagFragment = new DateDialagFragment();
                dateDialagFragment.show(getParentFragmentManager(), "DateDialog");
            }
        });

        gameTime = (TextView) view.findViewById(R.id.organize_gameTime_TextView);
        gameTime.setText((hour+":00"));
        gameTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeDialogFragment timeDialogFragment = new TimeDialogFragment();
                timeDialogFragment.show(getParentFragmentManager(), "TimeDialog");
            }
        });

        isOrganizerPlaying = (CheckBox) view.findViewById(R.id.organize_join_checkBox);
        isOrganizerPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organizeViewModel.setOrganizerPlay(((CheckBox)view).isChecked());
            }
        });

        addGameButton = (Button) view.findViewById(R.id.organize_addMatch_button);
        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organizeViewModel.addGame();
            }
        });
    }


}