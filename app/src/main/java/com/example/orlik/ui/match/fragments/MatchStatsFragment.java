package com.example.orlik.ui.match.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.orlik.R;
import com.example.orlik.data.model.PlayerStatistics;
import com.example.orlik.ui.match.MatchViewModel;
import com.example.orlik.ui.match.MatchViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MatchStatsFragment extends Fragment {
    private MatchViewModel matchViewModel;
    private Button addStatsButton;
    private EditText goalsEditText, assistsEditText;

    public MatchStatsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matchViewModel = new ViewModelProvider(requireActivity(), new MatchViewModelProvider()).get(MatchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match_stats, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addStatsButton = view.findViewById(R.id.match_addStats_button);
        goalsEditText = view.findViewById(R.id.match_addStats_goals_editText);
        assistsEditText = view.findViewById(R.id.match_addStats_assists_editText);
        addStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(goalsEditText.getText().toString().length()>0&&assistsEditText.getText().toString().length()>0){
                    matchViewModel.addStats(Integer.valueOf(goalsEditText.getText().toString()),Integer.valueOf(assistsEditText.getText().toString()));
                }

            }
        });

    }
}