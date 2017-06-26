package com.jae.radioapp.ui;

import com.jae.radioapp.RadioApplication;
import com.jae.radioapp.data.local.PreferenceHelper;
import com.jae.radioapp.data.model.Area;
import com.jae.radioapp.data.remote.RadikoService;
import com.mhealth.core.mvp.BaseTiPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by alex on 6/26/17.
 */

public class FragmentChannelListPresenter extends BaseTiPresenter<FragmentChannelListView>{

    @Inject
    RadikoService mRadikoService;

    @Inject
    PreferenceHelper mPref;

    public FragmentChannelListPresenter() {
        RadioApplication.getInstance().getAppComponent().inject(this);
    }

    public List<Area> getAreas() {
        return mPref.getAreas();
    }

    // remote

    public void getStations(String areaId) {
        manageSubscription(
                mRadikoService.getStations(areaId)
                .doOnSubscribe(() -> sendToView(view -> view.showLoading()))
                .doAfterTerminate(() -> sendToView(view -> view.hideLoading()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stationList -> {
                    Timber.e("stations=" + stationList.stations.size());
                    sendToView(view -> view.displayStations(stationList));
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }
}
