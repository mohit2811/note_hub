package com.example.admin.note_hub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class admin_interface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);
    }


    public void view_user(View view) {
        Intent i=new Intent(admin_interface.this , user.class);
        startActivity(i);
    }

    public void feedack_admin(View view) {
        Intent i = new Intent( admin_interface.this , feedback_admin.class);
        startActivity(i);

    }

    public void report(View view)  {
        Intent i = new Intent(admin_interface.this , Report.class);
        startActivity(i);

        }

    public void notes(View view) {
        Intent i = new Intent(admin_interface.this , notes.class);
        startActivity(i);
    }



    public void logout_admin(View view) {

       getSharedPreferences("app_info" , MODE_PRIVATE).edit().clear().commit();

        Intent i = new Intent(admin_interface.this , OptionsActivity.class);

        startActivity(i);

        finish();

    }


}


