package com.jae.radioapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.mhealth.core.ui.BaseFragment;

/**
 * Created by alex on 6/26/17.
 */

public class FragmentLeftMenu extends BaseFragment {

    public static FragmentLeftMenu getInstance() {
        return new FragmentLeftMenu();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_left_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {


    }


}
