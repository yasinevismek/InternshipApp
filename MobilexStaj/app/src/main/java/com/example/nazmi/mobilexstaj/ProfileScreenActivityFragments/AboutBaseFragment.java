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

public class AboutBaseFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Adapter and View Pager
    private SectionsPagerAdapter aboutBaseSecPagerAdapter;
    private ViewPager aboutBaseViewPager;
    //Todo: Identify Button
    private Button btBack;
    private Button btNext;
    //Todo: Identify Fragment View
    private View aboutBaseView;

    //Todo: About Fragment to Object Created
    public static AboutBaseFragment newInstance() {
        return new AboutBaseFragment();
    }

    //Todo: On Create Method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Todo: Fragment View to On Create
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        aboutBaseView = inflater.inflate(R.layout.profileactivity_about_base_fragment, container, false);
        setupUIViews();
        return aboutBaseView;
    }

    //Todo: Declare On Create
    public void setupUIViews() {
        //Todo: Section Pager Adapter and View Pager On Create
        aboutBaseSecPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        aboutBaseViewPager = aboutBaseView.findViewById(R.id.fragAboutBase_ViewPager);
        aboutBaseViewPager.setAdapter(aboutBaseSecPagerAdapter);
        //Todo: Buttons On Create
        btBack = aboutBaseView.findViewById(R.id.fragAboutBase_btBack);
        btNext = aboutBaseView.findViewById(R.id.fragAboutBase_btNext);
        //Todo: Button Click Listener
        btNext.setOnClickListener(this);
        btBack.setOnClickListener(this);
    }

    //Todo: Next&Back Button On Click
    @Override
    public void onClick(View view) {
        if (view.getId() == btNext.getId()) {
            aboutBaseViewPager.setCurrentItem(getViewpagerItem(+1), true);
        }
        if (view.getId() == btBack.getId()) {
            //FragmentTransaction ft=getFragmentManager().beginTransaction();
            //ft.replace(R.id.about_viewpager_id,new AboutFragmentSlide2());
            //ft.commit();
            aboutBaseViewPager.setCurrentItem(getViewpagerItem(-1), true);
        }
    }

    //Todo: View Pager Counter
    private int getViewpagerItem(int i) {
        return aboutBaseViewPager.getCurrentItem() + i;
    }

    //Todo: Section Pager Adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    AboutFragmentSlide1 slide1 = new AboutFragmentSlide1();
                    return slide1;
                case 1:
                    AboutFragmentSlide2 slide2 = new AboutFragmentSlide2();
                    return slide2;
                case 2:
                    AboutFragmentSlide3 slide3 = new AboutFragmentSlide3();
                    return slide3;
                default:
                    return null;
            }
        }
        //Todo: Count Number
        @Override
        public int getCount() {
            return 3;
        }
    }
}
