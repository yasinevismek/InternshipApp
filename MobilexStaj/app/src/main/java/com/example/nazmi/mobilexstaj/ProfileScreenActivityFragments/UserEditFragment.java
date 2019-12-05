package com.example.nazmi.mobilexstaj.ProfileScreenActivityFragments;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nazmi.mobilexstaj.R;
import com.example.nazmi.mobilexstaj.SignUpScreenActivity;
import com.example.nazmi.mobilexstaj.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class UserEditFragment extends Fragment implements View.OnClickListener {
    //Todo: Identify Fragment View
    private View userEditView;
    //Todo: Identify Button, TextView, EditText, ImageView
    private Button btSave;
    private ImageView ivEditPho;
    private TextView tvPhotoChange;
    private EditText etEditName, etEditSchoolName, etEditEmail, etEditSectionInfo, etEditInternDate, etEditSocialInfo;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    //Todo: String for Model
    String userName, email, schoolName, sectionName, internDate, socialInfo;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    public static Context contextOfApplication;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
//            imagePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
//                ivUserPic.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                imagePath = result.getUri();
                ivEditPho.setImageURI(imagePath);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    //Inflate
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userEditView = inflater.inflate(R.layout.profileactivity_user_edit_fragment, container, false);
        contextOfApplication = getContext();
        setupUIViews();
        retrieveData();
        return userEditView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void setupUIViews() {
        //Todo: TextView and ImageView On Create


        //Todo: EditText On Create
        etEditName = userEditView.findViewById(R.id.fragUserEdit_etEditUserFullName);
        etEditSchoolName = userEditView.findViewById(R.id.fragUserEdit_etEditSchoolName);
        etEditEmail = userEditView.findViewById(R.id.fragUserEdit_etEditEmail);
        etEditSectionInfo = userEditView.findViewById(R.id.fragUserEdit_etEditSection);
        etEditInternDate = userEditView.findViewById(R.id.fragUserEdit_etEditInternDate);
        etEditSocialInfo = userEditView.findViewById(R.id.fragUserEdit_etEditSocial);
        ivEditPho = userEditView.findViewById(R.id.fragUserEdit_ivEditUser);

        //Todo: FireBase Authentication and DataBase On Create
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    private void retrieveData() {
        //Todo: DataBase Reference equal FireBase User ID
        databaseReference = firebaseDatabase.getReference("Users/" + firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User editProfile = dataSnapshot.getValue(User.class);
                assert editProfile != null;
                etEditName.setText(editProfile.getUserName());
                etEditSchoolName.setText(editProfile.getSchoolName());
                etEditEmail.setText(editProfile.getEmail());
                etEditSectionInfo.setText(editProfile.getSectionName());
                etEditInternDate.setText(editProfile.getInternDate());
                etEditSocialInfo.setText(editProfile.getSocialInfo());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
        retrieveStorage();
    }

    private void retrieveStorage() {
        storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseUser.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(ivEditPho);
            }
        });
    }


    private void initView(View userEditView) {
        ivEditPho = userEditView.findViewById(R.id.fragUserEdit_ivEditUser);
        tvPhotoChange = userEditView.findViewById(R.id.fragUserEdit_tvPhotoChange);
        btSave = userEditView.findViewById(R.id.fragUserEdit_btSave);
        btSave.setOnClickListener(this);
        tvPhotoChange.setOnClickListener(this);
    }

    private void updateUserData() {
        userName = etEditName.getText().toString().trim();
        email = etEditEmail.getText().toString().trim();
        schoolName = etEditSchoolName.getText().toString().trim();
        sectionName = etEditSectionInfo.getText().toString().trim();
        internDate = etEditInternDate.getText().toString().trim();
        socialInfo = etEditSocialInfo.getText().toString().trim();

        User userProfile = new User(userName, email, schoolName, sectionName, internDate, socialInfo);
        databaseReference.setValue(userProfile);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
        firebaseUser.updateProfile(profileUpdates);

        if (imagePath != null) {
            StorageReference imageReference = storageReference.child(firebaseUser.getUid()).child("Images").child("Profile Picture");
            UploadTask uploadTask = imageReference.putFile(imagePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(contextOfApplication, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(contextOfApplication, "Upload Successful", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void changeFragment() {
        assert getFragmentManager() != null;
        assert getActivity() != null;
        getActivity().getSupportFragmentManager().popBackStack();
        ((Activity) contextOfApplication).isFinishing();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == btSave.getId()) {
            updateUserData();
            changeFragment();
        }
        if (view.getId() == tvPhotoChange.getId()) {
            BringImagePicker();
//            Intent intent = new Intent();
//            intent.setType("image/*");//application/* audio/*
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IMAGE);
        }
    }
    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(contextOfApplication,UserEditFragment.this);
    }
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == btSave.getId()) {
//            signUpUser();
//            startActivity(new Intent(getContext(), ProfileScreenActivity.class));
//        }
//    }
//
//    //String userFullNameEs, schoolNameEs, ınternDateEs, uniSectionInfoEs, socialInfoEs;
//    private void signUpUser() {
//        userFullNameEs = etEditName.getText().toString().trim();
//        schoolNameEs = etEditSchoolName.getText().toString().trim();
//        uniSectionInfoEs = etEditSectionInfo.getText().toString().trim();
//        ınternDateEs = etEditInternDate.getText().toString().trim();
//        socialInfoEs = etEditSocialInfo.getText().toString().trim();
//        //Todo: Validate of Sign Up Text Box
//        if (TextUtils.isEmpty(userFullNameEs)) {
//            Toast.makeText(getContext(), "Please enter full name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(schoolNameEs)) {
//            //email is empty
//            Toast.makeText(getContext(), "Please enter school name", Toast.LENGTH_SHORT).show();
//            //stopping the function execution further
//            return;
//        }
//        if (TextUtils.isEmpty(uniSectionInfoEs)) {
//            Toast.makeText(getContext(), "Please enter section name", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(ınternDateEs)) {
//            Toast.makeText(getContext(), "Please enter intern date", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(socialInfoEs)) {
//            Toast.makeText(getContext(), "Please enter social info", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        firebaseAuth.updateCurrentUser()
//        UserRegisteredFireBase();
//
//    }
//    //Todo: Send Data to FireBase DataBase
//    private void UserRegisteredFireBase() {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
//        UserSettings userSettings= new UserSettings(userFullNameEs,schoolNameEs,uniSectionInfoEs,ınternDateEs,socialInfoEs);
//        myRef.setValue(userSettings);
////        String key = myRef.push().getKey();
////        DatabaseReference dbRefKey = db.getReference("Users/" + key);
////        dbRefKey.setValue(new User(username, email, password));
//    }
}


