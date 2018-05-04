package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_profile extends AppCompatActivity {

    EditText name_et ;
    EditText email_et;
    EditText session_et;
    EditText department_et;
    EditText phone_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        name_et = findViewById(R.id.name_et);
        email_et = findViewById(R.id.email_et);
        session_et = findViewById(R.id.session_et);
        department_et = findViewById(R.id.department_et);
        phone_et = findViewById(R.id.phone_et);


        get_data();


    }
    public void save_data(View view)

    {



        final String email = email_et.getText().toString();

        final String name = name_et.getText().toString();
        final String mobile = phone_et.getText().toString();

        final String department = department_et.getText().toString();

        final String session = session_et.getText().toString();

        if(name.length() < 4)
        {
            Toast.makeText(user_profile.this, "invalid name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(mobile.length() < 10)
        {
            Toast.makeText(user_profile.this, "invalid mobile number", Toast.LENGTH_SHORT).show();
            return;
        }



        final ProgressDialog progress_bar = new ProgressDialog(user_profile.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Creating account...");
        progress_bar.show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

                    SignupData data = new SignupData(name , mobile , department , session);

                    database.getReference().child("users").child(email).setValue(data);



                    Toast.makeText(user_profile.this , "profile updated successfully" , Toast.LENGTH_SHORT).show();



    }

    public void get_data()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail().replace(".","");

        database.getReference().child("users").child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SignupData data = dataSnapshot.getValue(SignupData.class);

                name_et.setText(data.name);

                email_et.setText(dataSnapshot.getKey());

                session_et.setText(data.session);

                department_et.setText(data.department);

                phone_et.setText(data.mobile);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void finish_activity(View view) {
        finish();
    }
}
