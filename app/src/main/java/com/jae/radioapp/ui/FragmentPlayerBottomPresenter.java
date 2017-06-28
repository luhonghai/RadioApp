package com.jae.radioapp.ui;

import android.os.Handler;

import com.mhealth.core.mvp.BaseTiPresenter;

/**
 * Created by alex on 6/27/17.
 */

public class FragmentPlayerBottomPresenter extends BaseTiPresenter<FragmentPlayerBottomView> {

    public void getStreamUrl() {
        sendToView(view -> view.showLoading());
        new Handler().postDelayed(() -> {
//            sendToView(view -> view.hideLoading());
            sendToView(view -> view.onStreamUrlLoaded("http://techslides.com/demos/sample-videos/small.mp4"));
        }, 5000);
    }


}
