package com.example.orlik.data.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.orlik.R;
import com.example.orlik.data.model.Game;
import com.example.orlik.data.model.dto.GameDTO;
import com.example.orlik.ui.match.MatchActivity;
import com.example.orlik.ui.pitch.PitchActivity;

import java.util.ArrayList;

public class GamesResultAdapter extends RecyclerView.Adapter<GamesResultAdapter.ViewHolder> {
    private ArrayList<GameDTO> gamesSearchResult;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView pitchTypeTextView, addressTextView, membersTextView, dateTextView, organizerTextView, visibilityTextView;
        private final Button detailsButton, showMatchButton;
        private final RelativeLayout relativeLayout;
        private View view;
        private boolean detailsFlag=false;

        public ViewHolder(View view){
            super(view);
            this.view=view;
            relativeLayout = (RelativeLayout)view.findViewById(R.id.game_result_details_layout);
            pitchTypeTextView = (TextView) view.findViewById(R.id.games_result_pitchType_textView);
            addressTextView = (TextView) view.findViewById(R.id.games_result_address_textView);
            membersTextView = (TextView) view.findViewById(R.id.games_result_members_textView);
            dateTextView = (TextView) view.findViewById(R.id.games_result_date_textView);
            organizerTextView = (TextView) view.findViewById(R.id.games_result_organizer_textView);
            detailsButton = (Button) view.findViewById(R.id.games_result_button);
            showMatchButton = (Button) view.findViewById(R.id.games_result_getMatch_button);
            visibilityTextView = (TextView) view.findViewById(R.id.games_result_visibility_textView);

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

        public TextView getVisibilityTextView() {
            return visibilityTextView;
        }

        public Button getDetailsButton() {
            return detailsButton;
        }

        public Button getShowMatchButton() {
            return showMatchButton;
        }
    }

    public GamesResultAdapter(ArrayList<GameDTO> dataSet) {
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
        String visibility = (gamesSearchResult.get(position).getVisibility() == 1)? "Publiczny": "Prywatny";
        viewHolder.getAddressTextView().setText(gamesSearchResult.get(position).getAddress());
        viewHolder.getDateTextView().setText(gamesSearchResult.get(position).getSchedule());
        viewHolder.getMembersTextView().setText("Zapisani: "+members);
        if(gamesSearchResult.get(position).getOrganiserName()!=null){
            viewHolder.getOrganizerTextView().setText("Organizator: "+gamesSearchResult.get(position).getOrganiserName());
        }else{
            viewHolder.getOrganizerTextView().setVisibility(View.GONE);
        }
        viewHolder.getPitchTypeTextView().setText(gamesSearchResult.get(position).getPitchType());
        viewHolder.getVisibilityTextView().setText("Widoczność: "+visibility);
        viewHolder.setBackground(R.color.gamesAdapterBackground);

        viewHolder.getShowMatchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameDTO game = gamesSearchResult.get(position);

                Intent intent = new Intent(view.getContext(), MatchActivity.class);
                intent.putExtra("match", game);
                view.getContext().startActivity(intent);
            
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesSearchResult.size();
    }



}
