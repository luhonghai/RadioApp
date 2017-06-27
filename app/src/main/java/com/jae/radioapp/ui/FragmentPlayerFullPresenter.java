package com.jae.radioapp.ui;

import com.jae.radioapp.RadioApplication;
import com.jae.radioapp.data.model.Program;
import com.jae.radioapp.data.model.Station;
import com.jae.radioapp.data.remote.RadikoService;
import com.mhealth.core.mvp.BaseTiPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by alex on 6/27/17.
 */

public class FragmentPlayerFullPresenter extends BaseTiPresenter<FragmentPlayerFullView> {

    @Inject
    RadikoService mRadikoService;

    Station mStation;

    public FragmentPlayerFullPresenter(Station station) {
        RadioApplication.getInstance().getAppComponent().inject(this);
        mStation = station;
    }

    public Station getStation() {
        return mStation;
    }

    public void getPrograms() {
        manageSubscription(
                mRadikoService.getPrograms(mStation.areaId)
                .doOnSubscribe(() -> sendToView(view -> view.showLoading()))
                .doAfterTerminate(() -> sendToView(view -> view.hideLoading()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(radikoPrograms -> {

                    for (int i = 0; i < radikoPrograms.stationList.stations.size(); i++) {
                        Station station = radikoPrograms.stationList.stations.get(i);

                        if (station.idInProgramsAPI.equals(mStation.id)) {
                            List<Program> programs = radikoPrograms.stationList.stations.get(i).scd.programs.programs;
                            sendToView(view -> view.displayPrograms(programs));
                            break;
                        }
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                })
        );
    }

}
