package com.jae.radioapp.ui.adapter;

import android.databinding.DataBindingUtil;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.jae.radioapp.data.Callback;
import com.jae.radioapp.data.evenbus.PlayStatusEvent;
import com.jae.radioapp.data.model.Station;
import com.jae.radioapp.databinding.ItemChannelBinding;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 6/26/17.
 */

public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    implements View.OnClickListener{

    Callback<Station> stationCallback;
    List<Station> mStations;

    Station selectedStation;
    PlayStatusEvent.PlayStatus playStatus = PlayStatusEvent.PlayStatus.PLAYING;

    public ChannelAdapter(Callback<Station> stationCallback) {
        this.stationCallback = stationCallback;
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

        if (selectedStation != null && selectedStation.id.equals(station.id)) {
            if (playStatus != null && playStatus.equals(PlayStatusEvent.PlayStatus.PLAYING)) {
                AnimationDrawable animation = (AnimationDrawable)
                        ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.ic_equalizer_white_36dp);
                DrawableCompat.setTintList(animation, null);
                animation.start();
                binding.imgPlayStatus.setImageDrawable(animation);
            } else {
                Drawable playDrawable = ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.ic_equalizer1_white_36dp);
                DrawableCompat.setTintList(playDrawable, null);
                binding.imgPlayStatus.setImageDrawable(playDrawable);
            }
        } else {
            Drawable playDrawable = ContextCompat.getDrawable(binding.getRoot().getContext(), R.drawable.ic_play);
            DrawableCompat.setTintList(playDrawable, null);
            binding.imgPlayStatus.setImageDrawable(playDrawable);
        }

        binding.getRoot().setTag(station);
        binding.getRoot().setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    // ---------- FUNCTIONS ---------- //

    public void setStations(List<Station> stations) {
        mStations.clear();
        mStations.addAll(stations);
        notifyDataSetChanged();
    }

    public void setPlayStatus(PlayStatusEvent.PlayStatus playStatus) {
        this.playStatus = playStatus;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Station station = (Station) v.getTag();
        stationCallback.onCallback(station);

        selectedStation = station;
        v.postDelayed(() -> notifyDataSetChanged(), 200);

    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {

        ItemChannelBinding binding;

        public ChannelViewHolder(ItemChannelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
