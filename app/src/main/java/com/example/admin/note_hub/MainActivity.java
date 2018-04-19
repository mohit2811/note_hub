package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Sign_up(View view) {
        Intent i = new Intent(MainActivity.this,sign_up.class);
        startActivity(i);
    }

    public void login_1(View view) {

        EditText email = findViewById(R.id.et1);
        EditText password = findViewById(R.id.et2);
        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

        } else {
            Toast.makeText(MainActivity.this, "incorrect email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() >= 8 && password.length() < 33)
        {


    }
    else {
            Toast.makeText(MainActivity.this , "password is incorrect" , Toast.LENGTH_SHORT).show();
            return;
        }
        final ProgressDialog progress_bar = new ProgressDialog(MainActivity.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Creating account...");
        progress_bar.show();
        FirebaseAuth sign = FirebaseAuth.getInstance();
        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this , home_activity.class);
                    startActivity(i);
                    Intent profile1 = new Intent(MainActivity.this , home_activity.class);
                    startActivity(profile1);

                }
                else

                {
                    Toast.makeText(MainActivity.this, "error try again", Toast.LENGTH_SHORT).show();

                }

            }


        };
       sign.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(listener);
        }


    public void finish_activity(View view) {

        finish();
    }
}