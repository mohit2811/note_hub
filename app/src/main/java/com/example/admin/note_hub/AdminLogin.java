package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }


    public void login_1(View view) {
        Intent i = new Intent(AdminLogin.this,Admin_signup.class);
        startActivity(i);
    }
}
