package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private Spinner department_spinner , session_spinner  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        department_spinner = (Spinner) findViewById(R.id.department_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.departments, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        department_spinner.setAdapter(adapter);



        session_spinner = (Spinner) findViewById(R.id.session_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.session, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        session_spinner.setAdapter(adapter2);


    }

            public void Sign_up (View view){

            final EditText email_et = findViewById(R.id.et2);
            EditText password_et = findViewById(R.id.et4);

            EditText renter_password_et = findViewById(R.id.et5);

            String renter_password = renter_password_et.getText().toString();

            EditText name_et = findViewById(R.id.et1);
            EditText mobile_et = findViewById(R.id.et3);

            final String email = email_et.getText().toString();
            String password = password_et.getText().toString();
            final String name = name_et.getText().toString();
            final String mobile = mobile_et.getText().toString();

            final String department = department_spinner.getSelectedItem().toString();

            final String session = session_spinner.getSelectedItem().toString();

            if(name.length() < 4)
            {
                Toast.makeText(sign_up.this, "invalid name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(mobile.length() < 10)
            {
                Toast.makeText(sign_up.this, "invalid mobile number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            } else {
                Toast.makeText(sign_up.this, "invalid email syntax", Toast.LENGTH_SHORT).show();
                return;

            }

            if (password.length() >= 8 && password.length() < 33)

            {
            } else {
                Toast.makeText(sign_up.this, "password must be between 8 to 32 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if( ! password.equals(renter_password))
            {
                Toast.makeText(sign_up.this, "password do not match with renter password", Toast.LENGTH_SHORT).show();
                return;
            }

            final ProgressDialog progress_bar = new ProgressDialog(sign_up.this);
            progress_bar.setTitle("please wait");
            progress_bar.setMessage("Creating account...");
            progress_bar.show();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progress_bar.hide();
                    if (task.isSuccessful()) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        SignupData data = new SignupData(name , mobile , department , session);

                        database.getReference().child("users").child(email.replace(".","")).setValue(data);

                        Toast.makeText(sign_up.this, "done", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(sign_up.this , MainActivity.class);
                        startActivity(i);


                    } else

                    {
                        Toast.makeText(sign_up.this, "error try again", Toast.LENGTH_SHORT).show();

                    }

                }


            };
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(listener);

        }
        }


