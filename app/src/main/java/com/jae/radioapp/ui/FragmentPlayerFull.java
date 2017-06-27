package com.jae.radioapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.jae.radioapp.data.model.Program;
import com.jae.radioapp.data.model.Station;
import com.jae.radioapp.databinding.FragmentPlayerFullBinding;
import com.jae.radioapp.ui.adapter.ProgramAdapter;
import com.mhealth.core.mvp.BaseTiFragment;

import java.util.List;

/**
 * Created by alex on 6/27/17.
 */

public class FragmentPlayerFull extends BaseTiFragment<FragmentPlayerFullPresenter, FragmentPlayerFullView>
    implements FragmentPlayerFullView{

    public static FragmentPlayerFull getInstance(Station station) {
        FragmentPlayerFull fragmentPlayerFull = new FragmentPlayerFull();
        fragmentPlayerFull.station = station;
        return fragmentPlayerFull;
    }

    @NonNull
    @Override
    public FragmentPlayerFullPresenter providePresenter() {
        return new FragmentPlayerFullPresenter(station);
    }

    FragmentPlayerFullBinding mBinding;
    Station station;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_player_full, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        getPresenter().getPrograms();
    }

    private void initUI() {
        mBinding.toolBar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        mBinding.tvStationName.setText(getPresenter().getStation().name);
        mBinding.tvStationNameAscii.setText(getPresenter().getStation().asciiName);

        mBinding.rvPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));

        mBinding.btnPlayPause.setOnClickListener(v -> {});
        mBinding.btnRecord.setOnClickListener(v -> {});
        mBinding.btnSleepTimer.setOnClickListener(v -> {});
    }

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
    public void displayPrograms(List<Program> programs) {
        mBinding.rvPrograms.setAdapter(new ProgramAdapter(programs));
    }
}
