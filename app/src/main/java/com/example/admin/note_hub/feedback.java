package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.note_hub.dataModel.feedback_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class feedback extends AppCompatActivity {
EditText feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feedback =findViewById(R.id.feedback_user);
    }


    public void feedback_user(View view) {

        String feedbacks =feedback.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth fauth=FirebaseAuth.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String currentDateandTime = sdf.format(new Date());

        feedback_data data = new feedback_data(feedbacks , currentDateandTime);

        String email= fauth.getCurrentUser().getEmail().replace(".","");
        database.getReference().child("feedback").child(email).setValue(data);
        Toast.makeText(feedback.this, "done", Toast.LENGTH_SHORT).show();

        finish();
    }


    public void backbtn(View view) {
        finish();
    }

    public void send(View view) {
        finish();
    }

    public void feedback(View view)
    {

    }
}
