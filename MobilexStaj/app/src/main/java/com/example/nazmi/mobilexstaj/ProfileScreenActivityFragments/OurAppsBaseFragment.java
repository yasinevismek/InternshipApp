package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nazmi.mobilexstaj.R;

public class OurAppsBaseFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Fragment View
    private View ourAppsView;
    //Todo: Identify View Pager and Section Pager Adapter
    private ViewPager ourAppsViewPager;
    private SectionsPagerAdapter ourAppsSecPagerAdapter;
    //Todo: Identify Buttons
    private Button btNext;
    private Button btBack;

    //Todo: OurApps Fragment to Object Created
    public static OurAppsBaseFragment newInstance() {
        return new OurAppsBaseFragment();
    }

    //Todo: On Create Method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Todo: Fragment View to On Create
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ourAppsView = inflater.inflate(R.layout.profileactivity_ourapps_base_fragment, container, false);
        setupUIViews();
        return ourAppsView;
    }

    //Todo: Declare On Create
    public void setupUIViews() {
        ourAppsSecPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        ourAppsViewPager = ourAppsView.findViewById(R.id.fragOurAppsBase_viewPager);
        ourAppsViewPager.setAdapter(ourAppsSecPagerAdapter);
        btNext = ourAppsView.findViewById(R.id.fragOurAppsBase_btNext);
        btBack = ourAppsView.findViewById((R.id.fragOurAppsBase_btBack));
        btNext.setOnClickListener(this);
        btBack.setOnClickListener(this);
    }

    //Todo: Next&Back Button On Click
    @Override
    public void onClick(View view) {
        if (view.getId() == btNext.getId()) {
            ourAppsViewPager.setCurrentItem(getViewpagerItem(+1), true);
        }
        if (view.getId() == btBack.getId()) {
            //FragmentTransaction ft=getFragmentManager().beginTransaction();
            //ft.replace(R.id.about_viewpager_id,new AboutFragmentSlide2());
            //ft.commit();
            ourAppsViewPager.setCurrentItem(getViewpagerItem(-1), true);
        }
    }

    //Todo: View Pager Counter
    private int getViewpagerItem(int i) {
        return ourAppsViewPager.getCurrentItem() + i;
    }

    //Todo: Section Pager Adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    OurAppsFragmentSlide1 ourslide1 = new OurAppsFragmentSlide1();
                    return ourslide1;
                case 1:
                    OurAppsFragmentSlide2 ourslide2 = new OurAppsFragmentSlide2();
                    return ourslide2;
                case 2:
                    OurAppsFragmentSlide3 ourslide3 = new OurAppsFragmentSlide3();
                    return ourslide3;
                default:
                    return null;
            }
        }

        //Todo: Count Number
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }
    }
}
