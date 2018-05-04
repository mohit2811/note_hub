package com.example.admin.note_hub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.note_hub.dataModel.notes_details_data;
import com.example.admin.note_hub.dataModel.users_details_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class user extends AppCompatActivity {



    public void backkkk(View view) {
        finish();
    }
    RecyclerView recyclerView;

    ArrayList<users_details_data> users_list;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        pd = new ProgressDialog(user.this);

        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.show();

        users_list = new ArrayList<>();

        recyclerView = findViewById(R.id.user_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(user.this , LinearLayoutManager.VERTICAL , false));



    }

    private void get_data_from_firebase( )
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        database.getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                users_list.clear();

                for(DataSnapshot snap0 : dataSnapshot.getChildren()) {


                            users_details_data data = snap0.getValue(users_details_data.class);

                            users_details_data data_with_time = new users_details_data(data.name, data.mobile,  snap0.getKey());
                            users_list.add(data_with_time);
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

        TextView name , mobile  ;

        Button delete ;

        public view_holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_txt);

            mobile = itemView.findViewById(R.id.mobile_txt);

            delete = itemView.findViewById(R.id.delete_user);
        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(user.this).inflate(R.layout.user_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {


            final users_details_data data = users_list.get(position);

            holder.name.setText(data.name);


            holder.mobile.setText(data.mobile);




            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    database.getReference().child("users").child(data.email).setValue(null);

                    Toast.makeText(user.this, "users deleted", Toast.LENGTH_SHORT).show();



                }
            });
        }

        @Override
        public int getItemCount() {
            return users_list.size();
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
