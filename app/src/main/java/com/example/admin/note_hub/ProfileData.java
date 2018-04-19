package com.example.admin.note_hub;

/**
 * Created by Admin on 3/22/2018.
 */

public class ProfileData {
    public String name , address ,gender;
    public  int age;
    ProfileData() {
    }
        ProfileData(String name , String address, String gender , int age)
        {
            this.name = name;
            this.address = address;
            this.gender = gender;
            this.age = age;
        }
}
