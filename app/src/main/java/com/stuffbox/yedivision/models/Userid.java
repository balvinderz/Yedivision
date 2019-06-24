package com.stuffbox.yedivision.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * Created by jasbe on 25-07-2018.
 */

public class Userid {
    private String Userid;
    private  String Password;
    private String Name;
    private  int batchode;

    public Userid(String userid,String password,String name,int batch) {
        Userid = userid;
        Password=password;
        Name=name;
        batchode=batch;
    }
    public Userid()
    {

    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getBatchode() {
        return batchode;
    }

    public void setBatchode(int batchode) {
        this.batchode = batchode;
    }
}
