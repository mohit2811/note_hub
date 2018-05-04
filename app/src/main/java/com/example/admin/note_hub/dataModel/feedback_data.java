package com.example.admin.note_hub.dataModel;

/**
 * Created by Admin on 4/30/2018.
 */

public class feedback_data {

    public String message , date , email ;

    public feedback_data()
    {

    }

    public feedback_data(String message , String date )
    {
        this.message = message ;

        this.date = date ;

    }

    public feedback_data(String message , String date , String email )
    {
        this.message = message ;

        this.date = date ;

        this.email = email ;

    }
}
