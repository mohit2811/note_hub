package com.example.admin.note_hub;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class user_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }
    public void save_data(View view)
    {

        ProfileData data = new ProfileData("shinchan", "nohara house","male",5);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("simran1020@gmail.com").setValue(data);



    }
}
