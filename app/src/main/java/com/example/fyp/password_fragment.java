package com.example.fyp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class password_fragment extends Fragment {



    EditText email, cpass, npass;
    Button update;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String _email, _cpass, _npass,rdb_pass;
    user person;
    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_password_fragment, container, false);

        progressBar = view.findViewById(R.id.progressBar1);

        email = view.findViewById(R.id.email);
        cpass = view.findViewById(R.id.old_pass);
        npass = view.findViewById(R.id.new_pass);
        update = view.findViewById(R.id.update);
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        _email = auth.getCurrentUser().getEmail();
        email.setText(_email);

        update.setEnabled(true);
                progressBar.setVisibility(View.INVISIBLE);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                progressBar.setVisibility(View.VISIBLE);

                _cpass = cpass.getText().toString().trim();
                _npass = npass.getText().toString().trim();

                if (_cpass.isEmpty() || _npass.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Snackbar.make(view, "Enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else {

                    AuthCredential authCredential = EmailAuthProvider.getCredential(_email, _cpass);
                    firebaseUser.reauthenticate(authCredential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        firebaseUser.updatePassword(_npass)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                            reference = FirebaseDatabase.getInstance()
                                                                    .getReference("users")
                                                                    .child(_email);
                                                            reference.child("name").setValue(rdb_pass);


                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Snackbar.make(view, "Password updated", Snackbar.LENGTH_LONG)
                                                                    .setAction("Close", new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View view) {

                                                                        }
                                                                    })
                                                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                                    .show();

                                                        } else {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Snackbar.make(view, "something went wrong try again!", Snackbar.LENGTH_LONG)
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
                                    } else {

                                        progressBar.setVisibility(View.INVISIBLE);
                                        Snackbar.make(view, "Authentication failed!", Snackbar.LENGTH_LONG)
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

        return view;
    }

}
