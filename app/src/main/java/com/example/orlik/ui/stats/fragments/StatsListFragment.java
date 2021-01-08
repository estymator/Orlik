package com.example.orlik.ui.stats.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orlik.R;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.ui.stats.StatsViewModel;
import com.example.orlik.ui.stats.StatsViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class StatsListFragment extends Fragment {
    private StatsViewModel statsViewModel;
    RecyclerView checkedRecyclerView, finishedRecyclerView;

    public StatsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsViewModel = new ViewModelProvider(requireActivity(), new StatsViewModelFactory()).get(StatsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finishedRecyclerView = view.findViewById(R.id.stats_addStat_recyclerView);
        finishedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        finishedRecyclerView.setAdapter(statsViewModel.getFinishedAdapter());

        checkedRecyclerView = view.findViewById(R.id.stats_complete_recycylerView);
        checkedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        checkedRecyclerView.setAdapter(statsViewModel.getCheckedAdapter());

        statsViewModel.getGetCheckedGamesResult().observe(requireActivity(), new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> gameDTOS) {
                if (gameDTOS != null) {
                    if(gameDTOS.size()>0){
                        statsViewModel.getCheckedAdapterDataSet().clear();
                        statsViewModel.getCheckedAdapterDataSet().addAll(gameDTOS);
                        statsViewModel.getCheckedAdapter().notifyDataSetChanged();
                    }
                }
            }
        });

        statsViewModel.getGetFinishedGamesResult().observe(requireActivity(), new Observer<ArrayList<GameDTO>>() {
            @Override
            public void onChanged(ArrayList<GameDTO> gameDTOS) {
                if (gameDTOS != null) {
                    if(gameDTOS.size()>0){
                        statsViewModel.getFinishedAdapterDataSet().clear();
                        statsViewModel.getFinishedAdapterDataSet().addAll(gameDTOS);
                        statsViewModel.getFinishedAdapter().notifyDataSetChanged();
                    }
                }
            }
        });

        statsViewModel.getListOfGames();

    }
}