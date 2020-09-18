package com.example.fyp;

public class feedback_form {

    String email;
    String text;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;


    String name;

    public feedback_form(String _email,String _name,String _text,String _date){
        email=_email;
        text=_text;
        name=_name;
        date=_date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }




}
