package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Notes extends AppCompatActivity {

    private EditText title_et , subject_et , department_et , session_et ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__notes);

        title_et = findViewById(R.id.title_et);

        subject_et = findViewById(R.id.subject_et) ;

        department_et = findViewById(R.id.department_et);

        session_et = findViewById(R.id.session_et);

        get_department();

    }

    private void get_department()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();



        database.getReference().child("users").child(email.replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SignupData data = dataSnapshot.getValue(SignupData.class);

                department_et.setText(data.department);

                session_et.setText(data.session);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void next(View view) {
        Intent next = new Intent(Add_Notes.this , home_activity.class);
        startActivity(next);
    }

    public void open_upload_activity(View view) {


        String title = title_et.getText().toString();

        String subject = subject_et.getText().toString();

        String department = department_et.getText().toString();

        String session = session_et.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        NotesData data = new NotesData(title , subject , department ,session);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();

        long current_time = System.currentTimeMillis();

        database.getReference().child("notes").child(department+"_"+session).child(email.replace("." , "")).child(String.valueOf(current_time)).setValue(data);



        Intent i = new Intent(Add_Notes.this , UploadImagesActivity.class);

        startActivity(i);
    }


}
