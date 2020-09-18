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

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class infoFragment extends Fragment {


    EditText email, name;
    Button update;
    FirebaseAuth auth;
    DatabaseReference reference,user_node_ref;
    String _email,_name;
    user person;
    ProgressBar progressBar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_info,container,false);

        progressBar=view.findViewById(R.id.progressBar1);

        email = view.findViewById(R.id.email);
        name = view.findViewById(R.id.name);
        update = view.findViewById(R.id.update);
        auth = FirebaseAuth.getInstance();

        _email = auth.getCurrentUser().getEmail();

        email.setText(_email);
        _email=replacer.fireproof_string(_email);

        reference = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(_email);



        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.INVISIBLE);
                _name = snapshot.child("name").getValue(String.class);
                name.setText(_name);
                update.setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                reference = FirebaseDatabase.getInstance()
                        .getReference("users")
                        .child(_email);


                _name=name.getText().toString().trim();
                if (_name.isEmpty()){
                    progressBar.setVisibility(View.INVISIBLE);
                    name.setError("required");
                    Snackbar.make(view, "enter all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Close", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }

                else{
                    progressBar.setVisibility(View.INVISIBLE);
                    reference.child("name").setValue(_name);

                    Snackbar.make(view, "Updated!", Snackbar.LENGTH_LONG)
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


        return view;
    }
}
