package com.example.fyp;

import android.util.Log;

//purpose of this class is to remove charetors that can not be in root elements in firebase
public class replacer {

    static String _email;

    public static String fireproof_string(String input)
    {
        _email=input;
        _email=_email.replace(".","");
        _email=_email.replace("#","");
        _email=_email.replace("$","");
        _email=_email.replace("[","");
        _email=_email.replace("}","");


        Log.i("btag",_email);
        return  _email;
    }



}
