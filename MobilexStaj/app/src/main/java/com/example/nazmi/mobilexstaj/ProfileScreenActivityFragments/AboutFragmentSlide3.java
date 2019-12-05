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

public class AboutFragmentSlide3 extends Fragment {
    //Todo: Identify Fragment View
    private View aboutSlide3View;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        aboutSlide3View = inflater.inflate(R.layout.profileactivity_about_slide3_fragment, container, false);
        return aboutSlide3View;
    }
}
