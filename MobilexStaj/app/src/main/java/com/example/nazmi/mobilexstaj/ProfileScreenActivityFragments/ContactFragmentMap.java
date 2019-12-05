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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactFragmentMap extends Fragment implements OnMapReadyCallback {
    //Todo: Identify Google Map
    private GoogleMap map;
    //Todo: Identify Fragment View
    private View contactMapsView;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactMapsView = inflater.inflate(R.layout.profileactivity_contact_map_fragment, container, false);
        setupUIViews();
        return contactMapsView;
    }

    //Todo: Map Fragment Create
    public void setupUIViews() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragContactMaps);
        mapFragment.getMapAsync(this);
    }

    //Todo: Map GPS Position
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng pp = new LatLng(38.447780, 27.179249);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(pp).title("MobileX Yazılım");
        map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(pp));
    }
}
