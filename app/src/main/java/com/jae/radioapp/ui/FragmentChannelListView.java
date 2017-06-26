package com.jae.radioapp.ui;

import com.jae.radioapp.data.model.StationList;
import com.mhealth.core.mvp.BaseTiView;

/**
 * Created by alex on 6/26/17.
 */

public interface FragmentChannelListView extends BaseTiView{

    void displayStations(StationList stationList);
}
