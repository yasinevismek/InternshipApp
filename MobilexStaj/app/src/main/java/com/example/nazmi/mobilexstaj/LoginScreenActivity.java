package com.example.nazmi.mobilexstaj;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreenActivity extends Activity implements View.OnClickListener {
    //Todo: Identify Button, Views and Progress Dialog
    private Button btLogIn;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvResetPassword;
    private ProgressDialog progressDialog;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;


    //Todo: On Create Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        setupUIViews();
    }

    //Todo: Declare On Crate
    private void setupUIViews() {
        //Todo: FireBase Authentication on Create
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //Todo: Views and Button On Create
        btLogIn = findViewById(R.id.activityLogIn_btLogIn);
        etEmail = findViewById(R.id.activityLogIn_etEmail);
        etPassword = findViewById(R.id.activityLogIn_etPassword);
        tvResetPassword = findViewById(R.id.activityLogIn_tvForgotPass);
        //Todo: Created On Click Listener for Login Button and Forgot Text
        tvResetPassword.setOnClickListener(this);
        btLogIn.setOnClickListener(this);
        //Todo: Progress Dialog Create
        progressDialog = new ProgressDialog(this);
//        if(user != null){
//            finish();
//            startActivity(new Intent(LoginScreenActivity.this, ProfileScreenActivity.class));
//        }
    }

    //Todo: Login User
    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        //Todo: Validate of Login Text Box
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        //Todo: FireBase Authentication Control
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start the profile activity
//                            finish();
//                            startActivity(new Intent(LoginScreenActivity.this, ProfileScreenActivity.class));
                            checkEmailVerification();
                        } else {
                            Toast.makeText(LoginScreenActivity.this, "Could not login,please try again", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    //Todo: On Click Listener for Login Button
    @Override
    public void onClick(View view) {
        if (view == btLogIn) {
            userLogin();
            etEmail.setText("");
            etPassword.setText("");
        } else if (view == tvResetPassword) {
            startActivity(new Intent(LoginScreenActivity.this, PasswordScreenActivity.class));
        }
    }

    //Todo: Checking Verification Email
    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();
//        startActivity(new Intent(LoginScreenActivity.this, ProfileScreenActivity.class));
        if (emailFlag) {
            finish();
            Intent i = new Intent(LoginScreenActivity.this,ProfileScreenActivity.class);
            startActivity(i);
//            startActivity(new Intent(LoginScreenActivity.this, ProfileScreenActivity.class));
            Toast.makeText(LoginScreenActivity.this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Verify your email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }


}
