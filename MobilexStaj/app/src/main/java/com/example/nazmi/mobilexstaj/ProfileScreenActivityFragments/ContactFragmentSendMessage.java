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

public class ContactFragmentSendMessage extends Fragment {
    //Todo: Initializations Fragment View
    private View contactMessageView;

    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contactMessageView = inflater.inflate(R.layout.profileactivity_contact_message_fragment, container, false);





        return contactMessageView;
    }
}
