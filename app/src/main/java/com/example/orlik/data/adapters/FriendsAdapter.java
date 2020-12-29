package com.example.orlik.data.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.orlik.R;
import com.example.orlik.data.model.User;
import com.example.orlik.ui.profile.ProfileActivity;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>  {
    private ArrayList<User> users;
    private boolean isFriend;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final static String TAG = "FriendsViewHolderTAG";
        private TextView nameTextView;
        private Button ProfileButton;

        public ViewHolder(View view){
            super(view);
            this.nameTextView= view.findViewById(R.id.main_friendsItem_textView);
            this.ProfileButton=view.findViewById(R.id.main_friendsItem_button);

        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public Button getProfileButton() {
            return ProfileButton;
        }


    }

    public FriendsAdapter(ArrayList<User> dataset, boolean isFriend){

        this.users=dataset;
        this.isFriend=isFriend;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.main_friends_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){ //TODO once get nullpointerExc when launch app, try to setText on null
        viewHolder.getNameTextView().setText(users.get(position).getName()+" "+users.get(position).getSurname());

        viewHolder.getProfileButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                intent.putExtra("user", users.get(position));
                intent.putExtra("friend",isFriend);
                view.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount(){
        return users.size();
    }
}
