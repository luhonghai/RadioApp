package com.jae.radioapp.ui;

import com.google.gson.Gson;
import com.halosolutions.library.RadioToken;
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

    public void getAreaId(Double lat, Double lng) {
        manageSubscription(
                new RadioToken(RadioApplication.getInstance()).request(lat, lng)
                        .doOnSubscribe(() -> sendToView(view -> view.showLoading()))
                        .doAfterTerminate(() -> sendToView(view -> view.hideLoading()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(authToken -> {
                            if (authToken.getRaw().toUpperCase().equals("OUT")) {
                                sendToView(view -> view.onGetTokenError());
                            } else {
                                mPref.saveString(PreferenceHelper.KEY_AUTH_TOKEN, new Gson().toJson(authToken));
                                getStations(authToken.getAreaId());
                            }
                        }, throwable -> {
                            throwable.printStackTrace();
                            sendToView(view -> view.onGetTokenError());
                        })
        );
    }

    public void getStations(String areaId) {
        manageSubscription(
                mRadikoService.getStations(areaId)
                .doOnSubscribe(() -> sendToView(view -> view.showLoading()))
                .doAfterTerminate(() -> sendToView(view -> view.hideLoading()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stationList -> {
                    for (int i = 0; i < stationList.stations.size(); i++) {
                        stationList.stations.get(i).areaId = areaId;
                    }
                    sendToView(view -> view.displayStations(stationList));
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

}
