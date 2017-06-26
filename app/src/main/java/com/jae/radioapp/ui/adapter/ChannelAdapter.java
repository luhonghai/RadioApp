package com.jae.radioapp.ui.adapter;

import android.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.jae.radioapp.data.model.Area;
import com.jae.radioapp.data.model.Station;
import com.jae.radioapp.data.model.StationList;
import com.jae.radioapp.databinding.ItemChannelBinding;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 6/26/17.
 */

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Station> mStations;

    public ChannelAdapter() {
        mStations = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChannelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_channel, parent, false);
        return new ChannelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemChannelBinding binding = ((ChannelViewHolder)viewHolder).binding;
        Station station = mStations.get(position);

        binding.imgLogo.setImageURI(station.logoUrl);
        binding.tvStationName.setText(station.name);
        binding.tvStationNameAscii.setText(station.asciiName);
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    // local
    public void setStations(List<Station> stations) {
        mStations.clear();
        mStations.addAll(stations);
        notifyDataSetChanged();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        ItemChannelBinding binding;

        public ChannelViewHolder(ItemChannelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
