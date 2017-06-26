package com.jae.radioapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.jae.radioapp.data.evenbus.OpenStationEvent;
import com.jae.radioapp.data.evenbus.PlayStatusEvent;
import com.jae.radioapp.data.model.Station;
import com.jae.radioapp.databinding.FragmentPlayerBottomBinding;
import com.mhealth.core.mvp.BaseTiFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by alex on 6/27/17.
 */

public class FragmentPlayerBottom extends BaseTiFragment<FragmentPlayerBottomPresenter, FragmentPlayerBottomView>
    implements FragmentPlayerBottomView{

    public static FragmentPlayerBottom getInstance() {
        return new FragmentPlayerBottom();
    }

    @NonNull
    @Override
    public FragmentPlayerBottomPresenter providePresenter() {
        return new FragmentPlayerBottomPresenter();
    }

    FragmentPlayerBottomBinding mBinding;
    boolean isPlaying = false;

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_bottom, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mBinding.imgPlayStatus.setOnClickListener(v -> {
            if (isPlaying) {
                mBinding.imgPlayStatus.setImageResource(R.drawable.ic_play);
                isPlaying = false;
                EventBus.getDefault().post(new PlayStatusEvent(PlayStatusEvent.PlayStatus.PAUSE));
            } else {
                mBinding.imgPlayStatus.setImageResource(R.drawable.ic_pause);
                isPlaying = true;
                EventBus.getDefault().post(new PlayStatusEvent(PlayStatusEvent.PlayStatus.PLAYING));
            }
        });
    }

    @Subscribe
    public void onOpenStation(OpenStationEvent event) {
        Station station = event.station;
        mBinding.imgLogo.setImageURI(station.logoUrl);
        mBinding.tvStationName.setText(station.name);
        mBinding.tvStationNameAscii.setText(station.asciiName);

        EventBus.getDefault().post(new PlayStatusEvent(PlayStatusEvent.PlayStatus.PLAYING));
        mBinding.imgPlayStatus.setImageResource(R.drawable.ic_pause);
        isPlaying = true;
    }

}
