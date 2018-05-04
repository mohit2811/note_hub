package com.example.admin.note_hub;

/**
 * Created by Admin on 4/18/2018.
 */

public class NotesData {

    public    String title , subject , department , session , type , time ;


    public   NotesData()
    {

    }

    public NotesData(String title , String description , String department , String session , String type)
    {
        this.title = title;

        this.subject = description;

        this.department = department;

        this.session = session ;

        this.type = type ;
    }

    public NotesData(String title , String description , String department , String session , String type , String time)
    {
        this.title = title;

        this.subject = description;

        this.department = department;

        this.session = session ;

        this.type = type ;

        this.time = time ;
    }
}
