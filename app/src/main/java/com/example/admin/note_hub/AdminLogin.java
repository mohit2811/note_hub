package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AdminLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
    }






    public void login_1(View view) {
        EditText email = findViewById(R.id.et1);
        EditText password = findViewById(R.id.et2);



        if (Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

        } else {
            Toast.makeText(AdminLogin.this, "incorrect email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() >= 8 && password.length() < 33)
        {


        }
        else {
            Toast.makeText(AdminLogin.this , "password is incorrect" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(email.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("12345678"))
        {

            SharedPreferences.Editor sp = getSharedPreferences("app_info" , MODE_PRIVATE).edit();

            sp.putString("type" , "admin");

            sp.commit();

            Toast.makeText(AdminLogin.this, "done", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(AdminLogin.this , admin_interface.class);
            startActivity(i);

            finish();


        }



       /* final ProgressDialog progress_bar = new ProgressDialog(AdminLogin.this);
        progress_bar.setTitle("please wait");
        progress_bar.setMessage("Creating account...");
        progress_bar.show();
        FirebaseAuth sign = FirebaseAuth.getInstance();
        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progress_bar.hide();
                if (task.isSuccessful()) {
                    Toast.makeText(AdminLogin.this, "done", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdminLogin.this , admin_interface.class);
                    startActivity(i);
                    Intent profile1 = new Intent(AdminLogin.this , admin_interface.class);
                    startActivity(profile1);

                }
                else

                {
                    Toast.makeText(AdminLogin.this, "error try again", Toast.LENGTH_SHORT).show();

                }

            }


        };
        sign.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(listener);
*/

    }

    }

