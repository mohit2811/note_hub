package com.example.admin.note_hub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.note_hub.dataModel.favourite_note_data;
import com.example.admin.note_hub.dataModel.notes_details_data;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class favourite_notes extends AppCompatActivity {

    RecyclerView recyclerView;
    boolean check=true;
    ArrayList<NotesData> notes_list;
    ArrayList<String> fav_notes_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_notes);

        notes_list = new ArrayList<>();
        fav_notes_list = new ArrayList<>();
        recyclerView = findViewById(R.id.notes_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(favourite_notes.this, LinearLayoutManager.VERTICAL, false));

    }

    private void get_data_from_firebase(String department_name) {


        notes_list.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = auth.getCurrentUser().getEmail();

        database.getReference().child("notes").child(department_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    for (DataSnapshot snap2 : snap.getChildren()) {

                        notes_details_data data = snap2.getValue(notes_details_data.class);


                            if (fav_notes_list.contains(snap2.getKey()))
                            {
                                NotesData data_with_time = new NotesData(data.title, data.subject, data.department, data.session, data.type, snap2.getKey());
                                notes_list.add(data_with_time);
                            }


                    }
                }

                recyclerView.setAdapter(new Adapter());

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
    public void get_fav_notes()
    {
        fav_notes_list.clear();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();

        database.getReference().child("favourites").child(email.replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren() )
                {

                    
                    fav_notes_list.add(data.getKey());
                }

                get_department();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void get_department()
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email =  auth.getCurrentUser().getEmail();

        database.getReference().child("users").child(email.replace(".","")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String departmentSession =  dataSnapshot.child("department").getValue().toString()+"_"+dataSnapshot.child("session").getValue().toString();

                get_data_from_firebase(departmentSession);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void back_btn(View view) {

        finish();
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


    public class Adapter extends RecyclerView.Adapter<favourite_notes.view_holder>
    {

        @Override
        public favourite_notes.view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(favourite_notes.this).inflate(R.layout.notes_cell , parent , false));
        }



        @Override
        public void onBindViewHolder(final favourite_notes.view_holder holder, int position)
        {


            final NotesData data = notes_list.get(position);

            holder.title.setText(data.title);

            holder.subject.setText(data.subject);

            holder.department.setText(data.department);

            holder.session.setText(data.session);

          holder.fav_icon.setVisibility(View.GONE);


            holder.cell_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.type.equals("Images")) {

                        Intent i = new Intent(favourite_notes.this, Show_images_notes.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                    if(data.type.equals("Pdf"))
                    {
                        Intent i = new Intent(favourite_notes.this, ShowPdfActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Videos"))
                    {
                        Intent i = new Intent(favourite_notes.this, ShowVideoActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Audio"))
                    {
                        Intent i = new Intent(favourite_notes.this, PlayAudioActivity.class);

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

    public String convertTime(long yourmilliseconds)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        Date resultdate = new Date(yourmilliseconds);
        System.out.println(sdf.format(resultdate));

        return String.valueOf(sdf.format(resultdate));
    }

    @Override
    protected void onResume() {
        super.onResume();

        get_fav_notes();
    }
}
