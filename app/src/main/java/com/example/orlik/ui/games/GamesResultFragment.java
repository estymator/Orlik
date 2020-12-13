package com.example.orlik.ui.games;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orlik.R;
import com.example.orlik.data.model.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GamesResultFragment extends Fragment {
    private static final String TAG="GamesResultFragmentTAG";
    private GamesViewModel gamesViewModel;
    private RecyclerView recyclerView;
    private String[] gamesResult;
    public GamesResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamesViewModel = new ViewModelProvider(requireActivity(), new GamesViewModelFactory())
                .get(GamesViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.games_result_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(gamesViewModel.getGamesList().getValue().size()>0){
            recyclerView.setAdapter(new GamesResultAdapter(gamesViewModel.getGamesList().getValue()));
        }


    }
}