package com.jae.radioapp.ui;

import com.jae.radioapp.data.model.Stations;
import com.mhealth.core.mvp.BaseTiView;

/**
 * Created by alex on 6/26/17.
 */

public interface FragmentChannelListView extends BaseTiView{

    void displayStations(Stations stationList);
}
