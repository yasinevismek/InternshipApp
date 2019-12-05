package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nazmi.mobilexstaj.R;
import com.example.nazmi.mobilexstaj.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class UserProfileFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Fragment View
    private View userView;
    //Todo: Identify Views
    private ImageView ivUserPho,ivSettings;
    private TextView tvUserName, tvUserSchoolName, tvUserEmail, tvUserSectionInfo, tvUserInternDate, tvUserSocialInfo;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private FirebaseUser firebaseUser;

    public static Context contextOfApplication;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        int PICK_IMAGE = 123;
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
//            imagePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contextOfApplication.getContentResolver(), imagePath);
//                ivUserPho.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                imagePath = result.getUri();
//                ivUserPho.setImageURI(imagePath);
//
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }


    //Todo: Fragment View to On Create
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userView = inflater.inflate(R.layout.profileactivity_user_profile_fragment, container, false);
        contextOfApplication = getContext();
        setupUIViews();
        retrieveStorage();
        retrieveData();
        return userView;
    }

    private void setupUIViews() {
        //Todo: Views On Create
        ivSettings = userView.findViewById(R.id.fragUserProfile_ivEditIcon);
        ivUserPho = userView.findViewById(R.id.fragUserProfile_ivUser);
        tvUserName = userView.findViewById(R.id.fragUserProfile_tvInfoUserFullName);
        tvUserSchoolName = userView.findViewById(R.id.fragUserProfile_tvInfoSchoolName);
        tvUserEmail = userView.findViewById(R.id.fragUserProfile_tvInfoEmail);
        tvUserSectionInfo = userView.findViewById(R.id.fragUserProfile_tvInfoSection);
        tvUserInternDate = userView.findViewById(R.id.fragUserProfile_tvInfoInternDate);
        tvUserSocialInfo = userView.findViewById(R.id.fragUserProfile_tvInfoSocial);

        //Todo: FireBase Authentication and DataBase On Create
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        ivSettings.setOnClickListener(this);

    }

    private void retrieveStorage() {
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseUser.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(ivUserPho);
            }
        });
    }

    private void retrieveData() {
        //Todo: DataBase Reference equal FireBase User ID
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users/" + firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                assert userProfile != null;
                tvUserName.setText(userProfile.getUserName());
                tvUserEmail.setText(userProfile.getEmail());
                tvUserSchoolName.setText(userProfile.getSchoolName());
                tvUserSectionInfo.setText(userProfile.getSectionName());
                tvUserInternDate.setText(userProfile.getInternDate());
                tvUserSocialInfo.setText(userProfile.getSocialInfo());
                retrieveStorage();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void changeFragment() {
        if (getFragmentManager() == null) throw new AssertionError();
        if (getActivity() == null) throw new AssertionError();
        getFragmentManager().beginTransaction().replace(R.id.profile_main, new UserEditFragment()).addToBackStack(null).commit();
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == ivSettings.getId()){
            changeFragment();
        }
    }
}
