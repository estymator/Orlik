package com.example.orlik.ui.admin.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orlik.R;
import com.example.orlik.data.adapters.FriendsAdapter;
import com.example.orlik.data.adapters.PitchAdapter;
import com.example.orlik.ui.admin.AdminViewModel;
import com.example.orlik.ui.admin.AdminViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AdminUserListFragment extends Fragment {
    private AdminViewModel adminViewModel;
    private RecyclerView recyclerView;


    public AdminUserListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adminViewModel = new ViewModelProvider(requireActivity(), new AdminViewModelProvider()).get(AdminViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.admin_userList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FriendsAdapter(adminViewModel.getFindUserResult().getValue(), false));
    }
}