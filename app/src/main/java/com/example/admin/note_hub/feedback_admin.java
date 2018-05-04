package com.example.admin.note_hub;

import android.app.ProgressDialog;
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

import com.example.admin.note_hub.dataModel.feedback_data;
import com.example.admin.note_hub.dataModel.users_details_data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class feedback_admin extends AppCompatActivity {



    public void back1(View view) {
        finish();
    }


    ArrayList<feedback_data> feedback_list;

    RecyclerView recyclerView;

    ProgressDialog pd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_admin);

        pd = new ProgressDialog(feedback_admin.this);

        pd.setTitle("Loading");
        pd.setMessage("Please Wait..");
        pd.show();

        feedback_list = new ArrayList<>();

        recyclerView = findViewById(R.id.feedback_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(feedback_admin.this , LinearLayoutManager.VERTICAL , false));



    }

    private void get_data_from_firebase( )
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        database.getReference().child("feedback").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                feedback_list.clear();

                for(DataSnapshot snap0 : dataSnapshot.getChildren()) {


                    feedback_data data = snap0.getValue(feedback_data.class);

                    feedback_data data_ = new feedback_data(data.message, data.date,  snap0.getKey());

                    feedback_list.add(data_);
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

        TextView name , date , message  ;



        public view_holder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.username);

            date = itemView.findViewById(R.id.date);

            message = itemView.findViewById(R.id.message);
        }
    }


    public class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(feedback_admin.this).inflate(R.layout.feedback_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {


            final feedback_data data = feedback_list.get(position);

            holder.name.setText(data.email);

            holder.date.setText(data.date);

            holder.message.setText(data.message);





        }

        @Override
        public int getItemCount() {
            return feedback_list.size();
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

