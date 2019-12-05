package com.example.nazmi.mobilexstaj;
/**
 * Created by Nazmican GÖKBULUT
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Todo: Identify Button and View
    private Button btLogIn;
    private Button btSignUp;
    private TextView tvDate;
    //Todo: On Create Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        setupUIViews();
    }

    //Todo: Declare On Create
    public void setupUIViews() {
        //Todo: System Date Look On Create
        tvDate = findViewById(R.id.activityWelcome_tvDate);
        Date time = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        tvDate.setText(simpleDateFormat.format(time));
        //Todo: Buttons On Create
        btLogIn = findViewById(R.id.activityWelcome_btLogIn);
        btLogIn.setOnClickListener(this);
        btSignUp = findViewById(R.id.activityWelcome_btSignUp);
        btSignUp.setOnClickListener(this);
    }

    //Todo: Log In&Sign Up Button Click Listener
    @Override
    public void onClick(View view) {
        if (view.getId() == btLogIn.getId()) {
            startActivity(new Intent(MainActivity.this,LoginScreenActivity.class));
        }
        if (view.getId() == btSignUp.getId()) {
            startActivity(new Intent(MainActivity.this,SignUpScreenActivity.class));
        }
    }
}










