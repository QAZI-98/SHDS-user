package com.example.fyp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class appoint_nav extends AppCompatActivity {

    Button b1,b2;
    Intent i;
    SharedPreferences.Editor pref;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_nav);

        b1=findViewById(R.id.coming);
        b2=findViewById(R.id.history);
        pref = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.putInt("flag", 0);
                pref.apply();
                i=new Intent(appoint_nav.this,appointment_page.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.putInt("flag", 1);
                pref.apply();
                i=new Intent(appoint_nav.this,appointment_page.class);
                startActivity(i);
            }
        });


    }
}