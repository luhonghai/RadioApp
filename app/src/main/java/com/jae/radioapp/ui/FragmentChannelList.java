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
import com.jae.radioapp.R;
import com.jae.radioapp.data.model.Area;
import com.jae.radioapp.data.model.StationList;
import com.jae.radioapp.databinding.FragmentChannelListBinding;
import com.jae.radioapp.ui.adapter.ChannelAdapter;
import com.mhealth.core.mvp.BaseTiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 6/26/17.
 */

public class FragmentChannelList extends BaseTiFragment<FragmentChannelListPresenter, FragmentChannelListView>
    implements FragmentChannelListView{

    public static FragmentChannelList getInstance() {
        return new FragmentChannelList();
    }

    @NonNull
    @Override
    public FragmentChannelListPresenter providePresenter() {
        return new FragmentChannelListPresenter();
    }

    FragmentChannelListBinding mBinding;

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
    }

    private void initUI() {
        // list channel
        mBinding.rvChannel.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.rvChannel.setAdapter(new ChannelAdapter());

        mBinding.btnSelectArea.setOnClickListener(v -> openAreaOptions());

        // pick first
        onSelectArea(getPresenter().getAreas().get(0));
    }

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

    public void onSelectArea(Area area) {
        mBinding.btnSelectArea.setText("Current Area:" + area.name);
        getPresenter().getStations(area.areaId);
    }

    // ---------- API CALLBACK ---------- //
    // ---------- API CALLBACK ---------- //
    // ---------- API CALLBACK ---------- //


    @Override
    public void displayStations(StationList stationList) {
        ((ChannelAdapter)mBinding.rvChannel.getAdapter()).setStations(stationList.stations);
    }
}
