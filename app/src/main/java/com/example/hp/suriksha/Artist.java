package com.example.hp.suriksha;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Artist {
    FirebaseAuth mAuth;
    private String ID;
    private String Persons_count;
    private String From;
    private String To;
    private String Time;
  private String Phone_no;
  private String mail_id;

    public Artist(String Persons_count, String From, String To, String Time, String Phone_no,String mail_id) {

        this.Persons_count = Persons_count;
        this.From = From;
        this.To = To;
        this.Time = Time;
        this.Phone_no = Phone_no;
this.mail_id=mail_id;
    }


    public String getPersons_count() {
        return Persons_count;
    }

    public String getTo() {
        return To;
    }
    public String getFrom() {
        return From;
    }
    public String getTime() {
        return Time;
    }

    public String getMail_id() {
        return mail_id;
    }
    public String getPhone_no() { return Phone_no; }
}
