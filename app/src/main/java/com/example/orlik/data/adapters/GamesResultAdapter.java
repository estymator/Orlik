package com.example.orlik.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.orlik.R;
import com.example.orlik.data.model.Game;

import java.util.ArrayList;

public class GamesResultAdapter extends RecyclerView.Adapter<GamesResultAdapter.ViewHolder> {
    private ArrayList<Game> gamesSearchResult;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView pitchTypeTextView, addressTextView, membersTextView, dateTextView, organizerTextView;
        private final Button detailsButton;
        private final RelativeLayout relativeLayout;
        private View view;
        private boolean detailsFlag=false;

        public ViewHolder(View view){
            super(view);
            this.view=view;
            relativeLayout = (RelativeLayout)view.findViewById(R.id.game_result_details_layout);
            pitchTypeTextView = (TextView) view.findViewById(R.id.games_result_pitchType_textView);
            addressTextView = (TextView) view.findViewById(R.id.games_result_date_textView);
            membersTextView = (TextView) view.findViewById(R.id.games_result_members_textView);
            dateTextView = (TextView) view.findViewById(R.id.games_result_date_textView);
            organizerTextView = (TextView) view.findViewById(R.id.games_result_organizer_textView);
            detailsButton = (Button) view.findViewById(R.id.games_result_button);

            detailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailsFlag){
                        relativeLayout.setVisibility(View.GONE);
                        detailsButton.setText("Więcej");
                    }else{
                        relativeLayout.setVisibility(View.VISIBLE);
                        detailsButton.setText("Zwiń");
                    }
                    detailsFlag=!detailsFlag;
                }
            });


        }

        public void setBackground(int c){
            view.setBackgroundColor(c);
        }

        public TextView getPitchTypeTextView() {
            return pitchTypeTextView;
        }

        public TextView getAddressTextView() {
            return addressTextView;
        }

        public TextView getMembersTextView() {
            return membersTextView;
        }

        public TextView getDateTextView() {
            return dateTextView;
        }

        public TextView getOrganizerTextView() {
            return organizerTextView;
        }

        public Button getDetailsButton() {
            return detailsButton;
        }
    }

    public GamesResultAdapter(ArrayList<Game> dataSet) {
        gamesSearchResult = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.games_result_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        String members = gamesSearchResult.get(position).getPlayers()+"/"+gamesSearchResult.get(position).getMaxPlayersNumber();
        viewHolder.getAddressTextView().setText("Adressssssssssssssssss");
        viewHolder.getDateTextView().setText(gamesSearchResult.get(position).getSchedule());
        viewHolder.getMembersTextView().setText(members);
        viewHolder.getOrganizerTextView().setText(gamesSearchResult.get(position).getOrganizerLogin());
        viewHolder.getPitchTypeTextView().setText("Typ Boiska");
        if(position%2==0){
            viewHolder.setBackground(R.color.barBackground);
        }else{
            viewHolder.setBackground(R.color.colorPrimaryDark);
        }
    }

    @Override
    public int getItemCount() {
        return gamesSearchResult.size();
    }



}
