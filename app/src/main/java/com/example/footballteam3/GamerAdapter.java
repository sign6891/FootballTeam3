package com.example.footballteam3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.footballteam3.databinding.GamerViewItemBinding;

import java.util.ArrayList;

public class GamerAdapter extends RecyclerView.Adapter<GamerAdapter.GamerViewHolder> {

    ArrayList<Gamer> gamerArrayList = new ArrayList<>();
    private MainActivity mainActivity;

    public void setGamerArrayList(ArrayList<Gamer> gamerArrayList) {
        this.gamerArrayList = gamerArrayList;
        notifyDataSetChanged();
    }

    public GamerAdapter(ArrayList<Gamer> gamerArrayList, MainActivity mainActivity) {
        this.gamerArrayList = gamerArrayList;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public GamerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        GamerViewItemBinding gamerViewItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.gamer_view_item,
                        parent,
                false);
        return new GamerViewHolder(gamerViewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GamerViewHolder holder, final int position) {

        final Gamer gamer = gamerArrayList.get(position);

        holder.gamerViewItemBinding.setGamer(gamer);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.addAndEditGamer(true, gamer, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamerArrayList.size();
    }

    public class GamerViewHolder extends RecyclerView.ViewHolder {

        private GamerViewItemBinding gamerViewItemBinding;
        public GamerViewHolder(@NonNull GamerViewItemBinding gamerViewItemBinding) {
            super(gamerViewItemBinding.getRoot());
            this.gamerViewItemBinding = gamerViewItemBinding;
        }
    }
}
