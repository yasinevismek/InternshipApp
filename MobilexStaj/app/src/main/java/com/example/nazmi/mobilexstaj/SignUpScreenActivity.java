package com.example.nazmi.mobilexstaj;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nazmi.mobilexstaj.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class SignUpScreenActivity extends Activity implements View.OnClickListener {
    //Todo: Identify Views and Progress Dialog
    private Button btSignUp;
    private EditText etEmail;
    private EditText etUserName;
    private EditText etPassword, etConfirmPass;
    private ImageView ivUserPic;
    private ProgressBar progressBar;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    //Todo: String for Model
    String userName, password, confirmPass, email, schoolName, sectionName, internDate, socialInfo;
    private static int PICK_IMAGE = 123;
    Uri imagePath = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                ivUserPic.setImageURI(imagePath);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_signup_screen);
        setupUIViews();
    }

    //Todo: Declare On Create
    private void setupUIViews() {
        //Todo: Views and Button On Create
        btSignUp = findViewById(R.id.activitySignUp_btSignUp);
        etEmail = findViewById(R.id.activitySignUp_etEmail);
        etPassword = findViewById(R.id.activitySignUp_etPassword);
        etConfirmPass = findViewById(R.id.activitySignUp_etPasswordConfirm);
        etUserName = findViewById(R.id.activitySignUp_etUserName);
        ivUserPic = findViewById(R.id.activitySignUp_ivAddImage);

        //Todo: FireBase Authentication On Create
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        //Todo: Progress Bar On Create
        progressBar = findViewById(R.id.activitySignUp_progressBar);

        //Todo: Button Click Listener
        btSignUp.setOnClickListener(this);
        ivUserPic.setOnClickListener(this);
    }

    //Todo: OnclickListener Sign_UP Button
    @Override
    public void onClick(View view) {
        if (view.getId() == btSignUp.getId()) {
            signUpUser();
            // startActivity(new Intent("android.intent.action.Login"));
        }
        if (view.getId() == ivUserPic.getId()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(SignUpScreenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SignUpScreenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    Toast.makeText(SignUpScreenActivity.this, "Permisson Denied", Toast.LENGTH_LONG).show();
                } else {
                    BringImagePicker();
                }
//                Intent intent = new Intent();
//                intent.setType("image/*");//application/* audio/*
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select image"), PICK_IM
            } else {
                BringImagePicker();
            }
        }
    }

    private void BringImagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(SignUpScreenActivity.this);
    }

    //Todo: Registered User
    private void signUpUser() {
        password = etPassword.getText().toString().trim();
        confirmPass = etConfirmPass.getText().toString().trim();
        userName = etUserName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        schoolName = "Okul Bilgileri";
        sectionName = "Bölüm Bilgileri";
        internDate = "Staj Başlangıç Bitiş Tarihi";
        socialInfo = "Youtube - Facebook - Twitter vb.";

        //Todo: Validate of Sign Up Text Box
        if (imagePath == null) {

            Toast.makeText(this, "Please choose User Image", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;
        }
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPass)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(confirmPass)) {
            progressBar.setVisibility(View.VISIBLE);
            //Todo: Send Data to FireBase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //startActivity(new Intent(SignUpScreenActivity.this, MainActivity.class));
//                            Toast.makeText(SignUpScreenActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                sendEmailVerification();
                            } else {
                                // Log.d("TASK", String.valueOf(task.getException()));
                                Toast.makeText(SignUpScreenActivity.this, "Could not Registered,please try again", Toast.LENGTH_SHORT).show();

                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        } else {
            Toast.makeText(this, "Please enter password and confirm password equal", Toast.LENGTH_SHORT).show();
        }
    }

    //Todo: Send Data to FireBase DataBase
    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("Users/" + firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture");
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpScreenActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SignUpScreenActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
            }
        });
        User users = new User(userName, email, schoolName, sectionName, internDate, socialInfo);
        myRef.setValue(users);
//        String key = myRef.push().getKey();
//        DatabaseReference dbRefKey = db.getReference("Users/" + key);
//        dbRefKey.setValue(new User(username, email, password));
    }

    //Todo: Checking Verification Email
    private void sendEmailVerification() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        sendUserData();
                        //DisplayName Kayıt
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                        firebaseUser.updateProfile(profileUpdates);
                        firebaseAuth.signOut();
                        Toast.makeText(SignUpScreenActivity.this, "Succesfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i = new Intent(SignUpScreenActivity.this,LoginScreenActivity.class);
                        startActivity(i);
//                        startActivity(new Intent(SignUpScreenActivity.this, LoginScreenActivity.class));
                    } else {
                        Toast.makeText(SignUpScreenActivity.this, "Verification mail has'nt been sent!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
    }
}









