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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {




    EditText email, pass, name,phone;
    Button signup_button;
    TextView signin;
    FirebaseAuth auth;
    String _email, _pass, _name,gender="male",_phone;
    user person;
    DatabaseReference databaseReference;
    ProgressBar pb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone_num);
        signup_button = findViewById(R.id.signup_button);
        signin = findViewById(R.id.signin);
        auth = FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        pb1=findViewById(R.id.progressBar1);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                pb1.setVisibility(View.VISIBLE);
                _email=email.getText().toString().trim();
                _pass=pass.getText().toString().trim();
                _name=name.getText().toString().trim();
                _phone=phone.getText().toString().trim();

                if (_email.isEmpty() || _pass.isEmpty() || _name.isEmpty() || _phone.isEmpty())
                {
                    pb1.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                            .show();

                }

                else{

                    auth.createUserWithEmailAndPassword(_email,_pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    pb1.setVisibility(View.INVISIBLE);
                                    if (task.isSuccessful())
                                    {
                                        auth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {

                                                            person=new user(_name,_email,_pass,_phone,gender);

                                                            //to remove charected that are not allowed in Base Path Strings so we can set email as path instead to key char
                                                            _email=replacer.fireproof_string(_email);

                                                            databaseReference.child(_email).setValue(person);
                                                            Snackbar.make(view,"Account created check your email!", Snackbar.LENGTH_LONG)
                                                                    .setAction("Close", new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                        }
                                                                    })
                                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                                    .show();

                                                            Intent i=new Intent(signup.this,LoginActivity.class);
                                                            finish();
                                                            startActivity(i);

                                                        }

                                                        else{

                                                            Snackbar.make(view,task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                                                    .setAction("Close", new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                        }
                                                                    })
                                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                                    .show();
                                                        }

                                                    }
                                                });
                                    }
                                    else{
                                        Snackbar.make(view,task.getException().getMessage(), Snackbar.LENGTH_LONG)
                                                .setAction("Close", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                                                .show();

                                    }

                                }

                            });

                }


            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(signup.this,LoginActivity.class);
                finish();
                startActivity(i);

            }
        });


    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

         switch(view.getId()) {
            case R.id.male:
                if (checked)
                    gender="male";
                    break;
            case R.id.female:
                if (checked)
                    gender="female";
                break;
        }
    }
}