package com.example.orlik.ui.organizeGames;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.orlik.R;
import com.example.orlik.ui.games.GamesResultFragment;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OrganizeMenuFragment extends Fragment {
    private static final String TAG="OrganizeMenuFragmentTAG";
    private OrganizeViewModel organizeViewModel;
    private Button addGameButton, addPitchButton;

    public OrganizeMenuFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG,"On Create");
        organizeViewModel = new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_organize_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        addGameButton = (Button) view.findViewById(R.id.organize_menu_addGameButton);
        addPitchButton = (Button) view.findViewById(R.id.organize_menu_addPitchButton);

        addGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                organizeViewModel.getFragmentNavigator().setValue("game");
            }
        });
    }
}