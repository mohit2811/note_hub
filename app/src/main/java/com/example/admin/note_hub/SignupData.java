package com.example.admin.note_hub;

/**
 * Created by Admin on 4/12/2018.
 */

public class SignupData {

    public String name , mobile , department , session ;

    public SignupData()
    {

    }

    public SignupData(String name , String mobile , String department , String session)
    {
        this.name = name;
        this.mobile = mobile;
        this.department = department;

        this.session = session;
    }
}
