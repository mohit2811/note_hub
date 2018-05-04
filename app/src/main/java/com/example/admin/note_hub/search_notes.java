package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class search_notes extends AppCompatActivity {

    private SearchView searchView ;
    

    RecyclerView recyclerView;

    ArrayList<NotesData> notes_list;

    ArrayList<NotesData> search_notes_list;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_notes);

        pd = new ProgressDialog(search_notes.this);

        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.show();

        search_notes_list = new ArrayList<>();

        notes_list = new ArrayList<>();

        recyclerView = findViewById(R.id.notes_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(search_notes.this , LinearLayoutManager.VERTICAL , false));

        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.equals(""))
                {
                    search_notes_list.clear();
                    recyclerView.setAdapter(new Adapter());

                    return true;
                }

                search(newText);

                return true;
            }
        });


        get_department();

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

                                pd.hide();


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

    public void go_back(View view) {

        finish();
    }


    public class view_holder extends RecyclerView.ViewHolder {

        public TextView title , subject , department , session;

        public LinearLayout cell_layout;

        public view_holder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            subject = itemView.findViewById(R.id.subject);
            department = itemView.findViewById(R.id.deparment);
            session = itemView.findViewById(R.id.session);
            cell_layout = itemView.findViewById(R.id.cell_layout);


        }



    }
    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(search_notes.this).inflate(R.layout.notes_cell , parent ,false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {


            final NotesData data = search_notes_list.get(position);

            holder.title.setText(data.title);

            holder.subject.setText(data.subject);

            holder.department.setText(data.department);

            holder.session.setText(data.session);


            holder.cell_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(data.type.equals("Images")) {

                        Intent i = new Intent(search_notes.this, Show_images_notes.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                    if(data.type.equals("Pdf"))
                    {
                        Intent i = new Intent(search_notes.this, ShowPdfActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }

                    if(data.type.equals("Videos"))
                    {
                        Intent i = new Intent(search_notes.this, ShowVideoActivity.class);

                        i.putExtra("images_key", data.time);

                        startActivity(i);
                    }
                }
            });

        }




        @Override
        public int getItemCount() {
            return search_notes_list.size();
        }
    }


    private void search( String s )
    {
        search_notes_list.clear();

        for(NotesData data : notes_list)
        {
            if(data.subject.contains(s))
            {
                search_notes_list.add(data);
            }
        }

        recyclerView.setAdapter(new Adapter());
    }

}
