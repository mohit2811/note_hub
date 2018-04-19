package com.example.admin.note_hub;

/**
 * Created by Admin on 4/18/2018.
 */

public class NotesData {

    public String title , subject , department , session;

    public NotesData()
    {

    }
    public NotesData (String title , String subject , String department , String session)
    {
        this.title = title;
        this.subject = subject;
        this.department = department;
        this.session = session;
    }
}
