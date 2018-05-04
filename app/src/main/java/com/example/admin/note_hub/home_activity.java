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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.note_hub.dataModel.favourite_note_data;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

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
        startActivity(i);
    }

    public void logout(View view) {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();

        getSharedPreferences("app_info" , MODE_PRIVATE).edit().clear().commit();

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

                if(dataSnapshot.hasChildren()) {
                    SignupData data = dataSnapshot.getValue(SignupData.class);


                    database.getReference().child("notes").child(data.department + "_" + data.session).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChildren()) {
                                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                                    for (DataSnapshot snap1 : snap.getChildren()) {
                                        NotesData data = snap1.getValue(NotesData.class);

                                        NotesData data_with_time = new NotesData(data.title, data.subject, data.department, data.session, data.type, snap1.getKey());
                                        notes_list.add(data_with_time);

                                    }
                                }

                                recyclerView.setAdapter(new Adapter());
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public class view_holder extends RecyclerView.ViewHolder {

        public TextView title , subject , department , session;

        public LinearLayout cell_layout;

        public MaterialFavoriteButton fav_icon ;

        public view_holder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subject = itemView.findViewById(R.id.subject);
            department = itemView.findViewById(R.id.deparment);
            session = itemView.findViewById(R.id.session);
            cell_layout = itemView.findViewById(R.id.cell_layout);

            fav_icon = itemView.findViewById(R.id.fav_icon);


        }

    }
    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(home_activity.this).inflate(R.layout.notes_cell , parent ,false));
        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {


            final NotesData data = notes_list.get(position);

            holder.title.setText(data.title);

            holder.subject.setText(data.subject);

            holder.department.setText(data.department);

            holder.session.setText(data.session);

            holder.fav_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.fav_icon.setFavorite(true , true);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    FirebaseAuth auth = FirebaseAuth.getInstance();

                    String email = auth.getCurrentUser().getEmail().replace("." ,"");

                    favourite_note_data fav_data = new favourite_note_data(data.title);

                    database.getReference().child("favourites").child(email).child(data.time).setValue(fav_data);

                }
            });


            holder.cell_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.type.equals("Images")) {

                        Intent i = new Intent(home_activity.this, Show_images_notes.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                    if(data.type.equals("Pdf"))
                    {
                        Intent i = new Intent(home_activity.this, ShowPdfActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Videos"))
                    {
                        Intent i = new Intent(home_activity.this, ShowVideoActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Audio"))
                    {
                        Intent i = new Intent(home_activity.this, PlayAudioActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                }
            });

        }




        @Override
        public int getItemCount() {
            return notes_list.size();
        }
    }












}
