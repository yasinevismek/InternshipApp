package com.example.nazmi.mobilexstaj;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordScreenActivity extends AppCompatActivity implements View.OnClickListener {
    //Todo: Identify Views and Button
    private EditText etEmailPass;
    private Button btResetPass;
    //Todo: Identify FireBase Tools
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_screen);
        //Todo: Views and Button On Create
        etEmailPass = findViewById(R.id.activityPassword_etEmail);
        btResetPass = findViewById(R.id.activityPassword_btResPass);
        //Todo: FireBase Authentication on Create
        firebaseAuth = FirebaseAuth.getInstance();
        //Todo: Created On Click Listener for Reset Password Button
        btResetPass.setOnClickListener(this);
    }

    //Todo: On Click Listener for Reset Password Button
    @Override
    public void onClick(View view) {
        String userEmail = etEmailPass.getText().toString().trim();

        if (userEmail.equals("")) {
            Toast.makeText(PasswordScreenActivity.this, "Please enter your registered email ID", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(PasswordScreenActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(PasswordScreenActivity.this, LoginScreenActivity.class));
                    } else {
                        Toast.makeText(PasswordScreenActivity.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
