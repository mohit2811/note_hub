package com.example.admin.note_hub.dataModel;

/**
 * Created by Harmeet kaur on 11-04-2018.
 */

public class notes_details_data {

  public    String title , subject , department , session , type , time , status , email ;


  public notes_details_data()
    {

    }

    public notes_details_data(String title , String subject , String department , String session , String type , String status)
    {
        this.title = title;

        this.subject = subject;

        this.department = department;

        this.session = session ;

        this.type = type ;

        this.status = status;
    }

    public notes_details_data(String title , String subject , String department , String session , String type , String time , String status)
    {
        this.title = title;

        this.subject = subject;

        this.department = department;

        this.session = session ;

        this.type = type ;

        this.time = time ;

        this.status = status;
    }

    public notes_details_data(String title , String subject , String department , String session , String type , String time , String status , String email)
    {
        this.title = title;

        this.subject = subject;

        this.department = department;

        this.session = session ;

        this.type = type ;

        this.time = time ;

        this.status = status;

        this.email = email ;
    }
}
