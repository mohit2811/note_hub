package com.example.admin.note_hub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }

    public void back2(View view) {
        finish();
    }
}
