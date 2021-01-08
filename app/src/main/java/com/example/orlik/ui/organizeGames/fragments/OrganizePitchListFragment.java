package com.example.orlik.ui.organizeGames.fragments;

import android.os.Bundle;

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
import com.example.orlik.data.adapters.GamesResultAdapter;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.ui.organizeGames.OrganizeViewModel;
import com.example.orlik.ui.organizeGames.OrganizeViewModelFactory;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OrganizePitchListFragment extends Fragment {

    private OrganizeViewModel organizeViewModel;
    private RecyclerView pitchRecyclerView;
    public OrganizePitchListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organizeViewModel =  new ViewModelProvider(requireActivity(), new OrganizeViewModelFactory())
                .get(OrganizeViewModel.class);
        organizeViewModel.getPitchListFragmentData().observe(requireActivity(), new Observer<ArrayList<Pitch>>() {
            @Override
            public void onChanged(ArrayList<Pitch> pitches) {
                if(pitches.size()>0){
                    ArrayList<Pitch> adapterData=organizeViewModel.getPitchListAdapterDataSet();
                    adapterData.clear();
                    adapterData.addAll(pitches);
                    organizeViewModel.getPitchAdapter().notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_organize_pitch_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

        pitchRecyclerView = (RecyclerView) view.findViewById(R.id.organize_pitchList_recyclerView);
        pitchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pitchRecyclerView.setAdapter(organizeViewModel.getPitchAdapter());
        organizeViewModel.getPitchListForFragment();

    }
}