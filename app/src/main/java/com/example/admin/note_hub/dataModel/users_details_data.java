package com.example.admin.note_hub.dataModel;

/**
 * Created by Admin on 4/30/2018.
 */

public class users_details_data {

    public String name , mobile , email;

    public users_details_data()
    {

    }

    public users_details_data(String name , String mobile )
    {
        this.name = name;
        this.mobile = mobile ;
    }


    public users_details_data(String name , String mobile , String email )
    {
        this.name = name;
        this.mobile = mobile ;
        this.email = email ;

    }




}
