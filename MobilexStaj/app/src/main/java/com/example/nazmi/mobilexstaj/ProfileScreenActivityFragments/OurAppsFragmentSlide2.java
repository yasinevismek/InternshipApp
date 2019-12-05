package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nazmi.mobilexstaj.R;

public class OurAppsFragmentSlide2 extends Fragment {
    //Todo: Identify Fragment View
    private View ourAppsSlide2View;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ourAppsSlide2View = inflater.inflate(R.layout.profileactivity_ourapps_slide2_fragment, container, false);
        return ourAppsSlide2View;
    }
}
