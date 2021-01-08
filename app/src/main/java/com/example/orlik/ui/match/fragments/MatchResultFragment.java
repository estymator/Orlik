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
import com.example.orlik.ui.match.MatchViewModel;
import com.example.orlik.ui.match.MatchViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MatchResultFragment extends Fragment {

    private MatchViewModel matchViewModel;
    private Button sendButton;
    private EditText team1EditText, team2EditText;
    public MatchResultFragment() {
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
        return inflater.inflate(R.layout.fragment_match_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sendButton= view.findViewById(R.id.matchResult_send_button);
        team1EditText= view.findViewById(R.id.matchResult_team1_editText);
        team2EditText= view.findViewById(R.id.matchResult_team2_editText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchViewModel.sendResult(team1EditText.getText().toString()+" : "+team2EditText.getText().toString());
            }
        });
    }
}