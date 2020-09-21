package com.example.fyp;

public class appointment {

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getDocusername() {
        return docusername;
    }

    public void setDocusername(String docusername) {
        this.docusername = docusername;
    }

    public String getAppoint_id() {
        return appoint_id;
    }

    public void setAppoint_id(String appoint_id) {
        this.appoint_id = appoint_id;
    }




    public String getAppoint_date() {
        return appoint_date;
    }

    public void setAppoint_date(String appoint_date) {
        this.appoint_date = appoint_date;
    }

    String appoint_date;
    String useremail;
    String docusername;
    String appoint_id;
    String username;
    String userphone;
    String usergender;
    String time;
    String day;
    String doc_name;
    String doc_phone;
    String location;
    String speciality;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUsergender() {
        return usergender;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getPhone() {
        return doc_phone;
    }

    public void setPhone(String phone) {
        this.doc_phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    public appointment(String _useremail,String _docusername,String _appoint_id
            ,String _appoint_date
            ,String _username,String _userphone,String _usergender
    ,String _special,String _name,String _location,String _phone,String _day,String _time){
        useremail=_useremail;
        docusername=_docusername;
        appoint_id=_appoint_id;
        appoint_date=_appoint_date;
        username=_username;
        userphone=_userphone;
        usergender=_usergender;
        doc_name = _name;
        speciality=_special;
        doc_phone=_phone;
        location=_location;
        day=_day;
        time=_time;
    }



}
