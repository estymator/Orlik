package com.example.orlik.ui.games;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.orlik.R;

/**
 * A simple {@link Fragment} subclass.

 */
public class SearchGameFragment extends Fragment {
    private static final String TAG= "SearchGameFragment";
    private GamesViewModel gamesViewModel;
    private Spinner pitchSpinner, rangeSpinner;
    private Button searchButton;
    private TextView addressTextView;


    public SearchGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamesViewModel = new ViewModelProvider(requireActivity(), new GamesViewModelFactory())
                .get(GamesViewModel.class);
        Log.v(TAG,"OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_game, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        pitchSpinner = (Spinner) view.findViewById(R.id.games_pitch_spinner);
        gamesViewModel.setOrganizeSpinner(pitchSpinner,R.array.pitch_choose_array,getContext());

        rangeSpinner = (Spinner) view.findViewById(R.id.games_range_spinner);
        gamesViewModel.setOrganizeSpinner(rangeSpinner, R.array.distance_array, getContext());

        addressTextView = (TextView) view.findViewById(R.id.games_address_textView);

        searchButton = (Button) view.findViewById(R.id.games_search_button);
        //TODO: check if localization get correct, if not ask for manual data
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pitchChoose = pitchSpinner.getSelectedItem().toString();
                String rangeChoose = rangeSpinner.getSelectedItem().toString();
                gamesViewModel.searchGames(pitchChoose, rangeChoose, getContext());
            }

        });

        gamesViewModel.getAddress().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String address) {
                if(address==null){
                    //after logout
                }else
                {
                     addressTextView.setText(address);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Log.v(TAG, "OnAttach");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.v(TAG, "OnDetach");
    }


}