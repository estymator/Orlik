package com.example.orlik.data.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orlik.R;
import com.example.orlik.data.model.Notification;
import com.example.orlik.data.model.dto.NotificationDTO;
import com.example.orlik.ui.main.MainViewModel;
import com.example.orlik.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private ArrayList<NotificationDTO> dataSet;
    private MainViewModel mainViewModel;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView descriptionTextView;
        private final Button confirmButton, dismissButton;
        public ViewHolder(View view){
            super(view);
            descriptionTextView = (TextView) view.findViewById(R.id.notification_item_textView);
            confirmButton = (Button) view.findViewById(R.id.notification_item_confirm_button);
            dismissButton = (Button) view.findViewById(R.id.notification_item_dismiss_button);

        }

        public TextView getDescriptionTextView() {
            return descriptionTextView;
        }

        public Button getConfirmButton() {
            return confirmButton;
        }

        public Button getDismissButton() {
            return dismissButton;
        }
    }

    public NotificationAdapter(ArrayList<NotificationDTO> dataSet){
        this.dataSet=dataSet;
    }

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        if(dataSet.get(position).getType()==1){
            String name=dataSet.get(position).getUser().getName()+" "+dataSet.get(position).getUser().getSurname();
            holder.getDescriptionTextView().setText(name+" Zaproszenie do znajomych");
        }
        holder.getDismissButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.deleteNotification(dataSet.get(position).getId());
            }
        });

        holder.getConfirmButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataSet.get(position).getType()==1){
                    Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                    intent.putExtra("user", dataSet.get(position).getUser());
                    intent.putExtra("friend",false);
                    view.getContext().startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount(){
        return dataSet.size();
    }


}
