package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_activity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private ArrayList<NotesData> notes_list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);

        notes_list = new ArrayList<>();

        recyclerView = findViewById(R.id.notes_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(home_activity.this , LinearLayoutManager.VERTICAL , false));

        get_department();


    }


    public void profile1(View view) {

        Intent i = new Intent(home_activity.this , user_profile.class);

        startActivity(i);
    }
    public void backbtn (View view){
        Intent i = new Intent(home_activity.this , feedback.class);
        startActivity(i);

    }

    public void search(View view) {
        Intent i = new Intent(home_activity.this , search_notes.class);
        startActivity(i);
    }

    public void feedback(View view) {
        Intent i = new Intent(home_activity.this , feedback.class);
        startActivity(i);
    }

    public void favnotes(View view) {
        Intent i = new Intent(home_activity.this , favourite_notes.class);
    }

    public void logout(View view) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();

        Intent i = new Intent(home_activity.this , OptionsActivity.class);

        startActivity(i);

        finish();

    }

    public void open_drawer(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.d1);
        drawerLayout.openDrawer(Gravity.LEFT);

    }

    public void addnotes(View view) {
        Intent add_notes = new Intent(home_activity.this ,Add_Notes.class);
        startActivity(add_notes);
    }


    private void get_department()
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();



        database.getReference().child("users").child(email.replace(".","")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                SignupData data = dataSnapshot.getValue(SignupData.class);


                database.getReference().child("notes").child(data.department+"_"+data.session).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snap : dataSnapshot.getChildren())
                        {
                            for (DataSnapshot snap1 : snap.getChildren())
                            {
                                NotesData data = snap1.getValue(NotesData.class);

                                notes_list.add(data);

                            }
                        }

                        recyclerView.setAdapter(new Adapter());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public class view_holder extends RecyclerView.ViewHolder {

        public TextView title , subject , department , session;

        public view_holder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subject = itemView.findViewById(R.id.subject);
            department = itemView.findViewById(R.id.deparment);
            session = itemView.findViewById(R.id.session);

        }



    }
    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(home_activity.this).inflate(R.layout.notes_cell , parent ,false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            NotesData data = notes_list.get(position);

            holder.title.setText(data.title);

            holder.subject.setText(data.subject);

            holder.department.setText(data.department);

            holder.session.setText(data.session);

        }




        @Override
        public int getItemCount() {
            return notes_list.size();
        }
    }












}
