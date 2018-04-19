package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null)
        {

        }

        else {

            Intent i = new Intent(OptionsActivity.this , home_activity.class);

            startActivity(i);
        }

    }

    public void continue_as_user(View view) {


        Intent i = new Intent(OptionsActivity.this , MainActivity.class);

        startActivity(i);

    }


    public void continue_as_admin(View view) {

        Intent i = new Intent(OptionsActivity.this , AdminLogin.class);

        startActivity(i);

    }
}
