package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class dashboard extends AppCompatActivity {

    ImageView viewdoc, form, profiles, feedback, about, appointment, sign_out;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        viewdoc = findViewById(R.id.view_doctor);
        form = findViewById(R.id.search_d);
        profiles = findViewById(R.id.profile);
        sign_out = findViewById(R.id.signout);
        feedback = findViewById(R.id.feedbac);
        about = findViewById(R.id.aboutu);
        appointment = findViewById(R.id.appointment);


        viewdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, view_doctor.class);
                startActivity(i);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, appoint_nav.class);
                startActivity(i);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, about.class);
                startActivity(i);

            }
        });


        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, feedback.class);
                startActivity(i);

            }
        });

        profiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, profile.class);
                startActivity(i);
            }
        });

        form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(dashboard.this, symptom_form.class);
                startActivity(i);
            }
        });


        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "confirm sign out?", Snackbar.LENGTH_LONG)
                        .setAction("sign out", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseAuth.getInstance().signOut();
                                i = new Intent(dashboard.this, LoginActivity.class);
                                finish();
                                startActivity(i);
                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });


    }
}