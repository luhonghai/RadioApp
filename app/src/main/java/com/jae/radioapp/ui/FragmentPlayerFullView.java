package com.jae.radioapp.ui;

import com.jae.radioapp.data.model.Program;
import com.mhealth.core.mvp.BaseTiView;

import java.util.List;

/**
 * Created by alex on 6/27/17.
 */

public interface FragmentPlayerFullView extends BaseTiView{

    void displayPrograms(List<Program> programs);

}
