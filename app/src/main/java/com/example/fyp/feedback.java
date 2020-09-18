package com.example.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class feedback extends AppCompatActivity {

    EditText feed;
    Button send;
    String txt,date;
    SimpleDateFormat sdf;
    DatabaseReference feedbackReference,userReference;

String key, name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        feed = findViewById(R.id.richtext);
        send = findViewById(R.id.submit);

        feedbackReference = FirebaseDatabase.getInstance().getReference("feedback");

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
     userReference = FirebaseDatabase.getInstance().getReference().child("users").child(replacer.fireproof_string(email));
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = feed.getText().toString().trim();

                if (txt.isEmpty()) {
                    feed.setError("field cannot be blank");
                } else {

                    sdf = new SimpleDateFormat("d/MM/yy");
                    date = sdf.format(new Date());
                    key=feedbackReference.push().getKey();
                    feedbackReference.child(key).setValue(new feedback_form(email,name,txt,date));
                    Toast.makeText(feedback.this, "feedback submitted", Toast.LENGTH_SHORT).show();
                    feed.setText("");
                }
            }
        });


    }
}