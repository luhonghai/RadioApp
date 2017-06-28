package com.jae.radioapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.exoplayer2.ExoPlayer;
import com.jae.radioapp.R;
import com.jae.radioapp.data.evenbus.MediaPlayerStateChangeEvent;
import com.jae.radioapp.data.evenbus.OpenStationEvent;
import com.jae.radioapp.data.model.Area;
import com.jae.radioapp.data.model.Stations;
import com.jae.radioapp.databinding.FragmentChannelListBinding;
import com.jae.radioapp.ui.adapter.ChannelAdapter;
import com.mhealth.core.mvp.BaseTiFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by alex on 6/26/17.
 */

public class FragmentChannelList extends BaseTiFragment<FragmentChannelListPresenter, FragmentChannelListView>
    implements FragmentChannelListView{

    public static FragmentChannelList getInstance() {
        FragmentChannelList fragmentChannelList = new FragmentChannelList();
        return fragmentChannelList;
    }

    @NonNull
    @Override
    public FragmentChannelListPresenter providePresenter() {
        return new FragmentChannelListPresenter();
    }

    FragmentChannelListBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_channel_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        getAreaId();
    }

    private void initUI() {
        // list channel
        mBinding.rvChannel.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rvChannel.setAdapter(new ChannelAdapter(station -> {
            EventBus.getDefault().post(new OpenStationEvent(station));
        }));

        mBinding.btnSelectArea.setOnClickListener(v -> openAreaOptions());
    }

    // Test purpose
    private void openAreaOptions() {
        List<Area> areas = getPresenter().getAreas();
        ArrayList<String> areaNames = new ArrayList<>();
        for (int i = 0; i < areas.size(); i++) {
            areaNames.add(areas.get(i).name);
        }
        new MaterialDialog.Builder(getActivity())
                .items(areaNames)
                .itemsCallbackSingleChoice(-1, (dialog, itemView, which, text) -> {
                    dialog.dismiss();
                    onSelectArea(areas.get(which));
                    return false;
                }).build().show();
    }

    // Test purpose
    private void onSelectArea(Area area) {
        mBinding.btnSelectArea.setText("Current Area:" + area.name);
        getPresenter().getStations(area.areaId);
    }

    public void getAreaId() {
        mBinding.layoutRetry.setVisibility(View.GONE);
        // TODO: pretend to get location here, then pass location to API call
        getPresenter().getAreaId(36.473144, 138.970151);
    }


    // ---------- OVERRIDE FUNCTIONS ---------- //
    // ---------- OVERRIDE FUNCTIONS ---------- //
    // ---------- OVERRIDE FUNCTIONS ---------- //

    @Override
    public void showLoading() {
//        super.showLoading();
        mBinding.pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
//        super.hideLoading();
        mBinding.pbLoading.setVisibility(View.GONE);
    }

    // ---------- API CALLBACK ---------- //
    // ---------- API CALLBACK ---------- //
    // ---------- API CALLBACK ---------- //

    @Override
    public void onGetTokenError() {
        mBinding.layoutRetry.setVisibility(View.VISIBLE);
        mBinding.btnRetry.setOnClickListener(v -> getAreaId());
    }

    @Override
    public void displayStations(Stations stationList) {
        ((ChannelAdapter)mBinding.rvChannel.getAdapter()).setStations(stationList.stations);
    }

    // ---------- EVENT BUS ---------- //
    // ---------- EVENT BUS ---------- //
    // ---------- EVENT BUS ---------- //

    @Subscribe
    public void onMediaPlayerStateChange(MediaPlayerStateChangeEvent event) {
        Timber.e("onMediaPlayerStateChange=" + event.state);
        if (event.state == ExoPlayer.STATE_READY) {
            ((ChannelAdapter)mBinding.rvChannel.getAdapter()).setIsPlaying(true);
        } else {
            ((ChannelAdapter)mBinding.rvChannel.getAdapter()).setIsPlaying(false);
        }
    }

}
