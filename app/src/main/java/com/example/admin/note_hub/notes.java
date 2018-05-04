package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.note_hub.dataModel.notes_details_data;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class notes extends AppCompatActivity {

    public void back3(View view) {
        finish();
    }


    RecyclerView recyclerView;

    ArrayList<notes_details_data> notes_list;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        pd = new ProgressDialog(notes.this);

        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.show();

        notes_list = new ArrayList<>();

        recyclerView = findViewById(R.id.notes_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(notes.this , LinearLayoutManager.VERTICAL , false));



    }

    private void get_data_from_firebase( )
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        database.getReference().child("notes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notes_list.clear();

                for(DataSnapshot snap0 : dataSnapshot.getChildren()) {

                    for (DataSnapshot snap : snap0.getChildren()) {
                        for (DataSnapshot snap2 : snap.getChildren()) {
                            notes_details_data data = snap2.getValue(notes_details_data.class);

                            notes_details_data data_with_time = new notes_details_data(data.title, data.subject, data.department, data.session, data.type, snap2.getKey() , data.status , snap.getKey());
                            notes_list.add(data_with_time);
                        }
                    }
                }

                pd.hide();

                recyclerView.setAdapter(new Adapter());

            }

            @Override

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }









    public class view_holder extends RecyclerView.ViewHolder
    {

        TextView notes_title , notes_description , time , department , session , user_email ;

        Button approve_notes ;

        public view_holder(View itemView) {
            super(itemView);

            notes_title = itemView.findViewById(R.id.notes_title);

            notes_description = itemView.findViewById(R.id.notes_description);

            time = itemView.findViewById(R.id.notes_date);

            department = itemView.findViewById(R.id.notes_department);

            session = itemView.findViewById(R.id.notes_session);

            approve_notes = itemView.findViewById(R.id.approve_btn);

            user_email = itemView.findViewById(R.id.user_email);
        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(notes.this).inflate(R.layout.single_notes_cell_admin , parent , false));
        }

        @Override
        public void onBindViewHolder(final notes.view_holder holder, int position) {


            final notes_details_data data = notes_list.get(position);

            holder.notes_title.setText(data.title);

            holder.notes_description.setText(data.subject);

            holder.time.setText(convertTime(Long.parseLong(data.time)));

            holder.department.setText(data.department);

            holder.session.setText(data.session);

            holder.user_email.setText(data.email);


            holder.notes_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.type.equals("Images")) {

                        Intent i = new Intent(notes.this, Show_images_notes.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                    if(data.type.equals("Pdf"))
                    {
                        Intent i = new Intent(notes.this, ShowPdfActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Videos"))
                    {
                        Intent i = new Intent(notes.this, ShowVideoActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Audio"))
                    {
                        Intent i = new Intent(notes.this, PlayAudioActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                }
            });

            holder.approve_notes.setText("DELETE");


            holder.approve_notes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        database.getReference().child("notes").child(data.department + "_" + data.session).child(data.email.replace(".", "")).child(data.time).setValue(null);

                        Toast.makeText(notes.this, "notes deleted", Toast.LENGTH_SHORT).show();



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

        get_data_from_firebase();
    }
}
