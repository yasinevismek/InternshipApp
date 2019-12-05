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

public class ContactBaseFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Adapter and View Pager
    private SectionsPagerAdapter contactBaseSecPagerAdapter;
    private ViewPager contactBaseViewPager;
    //Todo: Identify Button
    private Button btBack;
    private Button btNext;
    //Todo: Identify Fragment View
    private View contactBaseView;

    //Todo: Contact Fragment to Object Created
    public static ContactBaseFragment newInstance() {
        return new ContactBaseFragment();
    }

    //Todo: On Create Method
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Todo: Fragment View to On Create
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contactBaseView = inflater.inflate(R.layout.profileactivity_contact_base_fragment, container, false);
        setupUIViews();
        return contactBaseView;
    }

    //Todo: Declare On Create
    public void setupUIViews() {
        //Todo: Section Pager Adapter and View Pager On Create
        contactBaseSecPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        contactBaseViewPager = contactBaseView.findViewById(R.id.fragContactBase_ViewPager);
        contactBaseViewPager.setAdapter(contactBaseSecPagerAdapter);
        //Todo: Buttons On Create
        btBack = (Button) contactBaseView.findViewById(R.id.fragContactBase_btBack);
        btNext = (Button) contactBaseView.findViewById(R.id.fragContactBase_btNext);
        //Todo: Button Click Listener
        btNext.setOnClickListener(this);
        btBack.setOnClickListener(this);
    }

    //Todo: Next&Back Button On Click
    @Override
    public void onClick(View view) {
        if (view.getId() == btNext.getId()) {
            contactBaseViewPager.setCurrentItem(getViewpagerItem(+1), true);
        }
        if (view.getId() == btBack.getId()) {
            //FragmentTransaction ft=getFragmentManager().beginTransaction();
            //ft.replace(R.id.about_viewpager_id,new AboutFragmentSlide2());
            //ft.commit();
            contactBaseViewPager.setCurrentItem(getViewpagerItem(-1), true);
        }
    }

    //Todo: View Pager Counter
    private int getViewpagerItem(int i) {
        return contactBaseViewPager.getCurrentItem() + i;
    }

    //Todo: Section Pager Adapter
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager mf) {
            super(mf);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    ContactFragmentSendMessage slide1 = new ContactFragmentSendMessage();
                    return slide1;
                case 1:
                    ContactFragmentMap slide2 = new ContactFragmentMap();
                    return slide2;
                default:
                    return null;
            }
        }

        //Todo: Count Number
        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

//    ***********************************GOOGLE MAPS**********************************************
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SupportMapFragment mapFragment=(SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//       map = googleMap;
//       LatLng pp= new LatLng(38.447780, 27.179249);
//        MarkerOptions markerOptions=new MarkerOptions();
//        markerOptions.position(pp).title("MobileX Yazılım");
//        map.addMarker(markerOptions);
//        map.moveCamera(CameraUpdateFactory.newLatLng(pp));
//
//    }
}
