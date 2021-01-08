package com.example.orlik.data.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.orlik.R;
import com.example.orlik.data.model.Pitch;
import com.example.orlik.ui.pitch.PitchActivity;

import java.util.ArrayList;

public class PitchAdapter extends RecyclerView.Adapter<PitchAdapter.ViewHolder> {
    public ArrayList<Pitch> dataSet;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView typeTextView, addressTextView;
        private final Button detailsButton;
        public ViewHolder(View view) {
            super(view);
            typeTextView=(TextView) view.findViewById(R.id.pitchItem_type_textView);
            addressTextView=(TextView) view.findViewById(R.id.pitchItem_address_textView);
            detailsButton=(Button) view.findViewById(R.id.pitchItem_details_Button);
        }

        public TextView getTypeTextView() {
            return typeTextView;
        }

        public TextView getAddressTextView() {
            return addressTextView;
        }

        public Button getDetailsButton() {
            return detailsButton;
        }
    }

    public PitchAdapter(ArrayList<Pitch> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pitch_result_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.getAddressTextView().setText(dataSet.get(position).getAdress());
        holder.getTypeTextView().setText(dataSet.get(position).getType());
        holder.getDetailsButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PitchActivity.class);
                intent.putExtra("pitch", dataSet.get(position));
                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
