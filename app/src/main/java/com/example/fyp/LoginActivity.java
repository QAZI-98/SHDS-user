package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    EditText email, pass;
    Button signin;
    TextView sign;
    FirebaseAuth auth;
    String _email, _pass;
    ProgressBar pb1;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            if (firebaseUser.isEmailVerified()) {
                Intent ir=new Intent(this,dashboard.class);
                finish();
                startActivity(ir);
            }
        }

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        signin = findViewById(R.id.email_sign_in_button);
        sign = findViewById(R.id.signup);
        auth = FirebaseAuth.getInstance();
        pb1 = findViewById(R.id.progressBar1);


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                pb1.setVisibility(View.VISIBLE);
                _email = email.getText().toString().trim();
                _pass = pass.getText().toString().trim();

                if (_email.isEmpty() || _pass.isEmpty()) {

                    pb1.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();

                } else {

                    auth.signInWithEmailAndPassword(_email, _pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pb1.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful()) {
                                        if (auth.getCurrentUser().isEmailVerified()) {
                                            try {


                                                Intent i = new Intent(LoginActivity.this, dashboard.class);
                                                finish();
                                                startActivity(i);
                                            } catch (Exception e) {
                                                Log.i("taggg", e.getMessage());

                                            }

                                            Snackbar.make(view, "Login successful!", Snackbar.LENGTH_LONG)
                                                    .setAction("Close", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                        }
                                                    })
                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                    .show();

                                        } else {

                                            Snackbar.make(view, "Verify your email address!", Snackbar.LENGTH_LONG)
                                                    .setAction("Close", new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                        }
                                                    })
                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                    .show();
                                        }
                                    } else {
                                        Snackbar.make(view, "Invalid ID or password!", Snackbar.LENGTH_LONG)
                                                .setAction("Close", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                .show();

                                    }


                                }
                            });


                }


            }
        });


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(LoginActivity.this, signup.class);
                finish();
                startActivity(i);


            }
        });


    }
}
