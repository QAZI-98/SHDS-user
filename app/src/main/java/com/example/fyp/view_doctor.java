package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class view_doctor extends AppCompatActivity {


    RecyclerView recyclerView;

    List<doctor> cliniclList = new ArrayList<>();
    DatabaseReference doc_reference,appointment_reference,user_reference;
    String loc,phone,name,special,day,time,key,doctors_username,
            useremail,users_name,users_phone,users_gender;
    UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        recyclerView = findViewById(R.id.dlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doc_reference=FirebaseDatabase.getInstance().getReference("doctor");
        doc_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    loc = ds.child("location").getValue(String.class);
                    phone = ds.child("phone").getValue(String.class);
                    name = ds.child("name").getValue(String.class);
                    special = ds.child("speciality").getValue(String.class);
                    day= ds.child("day").getValue(String.class);
                    time= ds.child("time").getValue(String.class);
                    doctors_username= ds.child("username").getValue(String.class);

                    cliniclList.add(new doctor
                            (special,name,loc,phone,day,time,doctors_username));
                }
                courseAdapter = new UsersAdapter(cliniclList);

                recyclerView.setAdapter(courseAdapter);
                linearLayoutManager = new LinearLayoutManager(view_doctor.this);
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        courseAdapter = new UsersAdapter(cliniclList);

        recyclerView.setAdapter(courseAdapter);
    }

    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVh> {

        private List<doctor> clinicList;
        private Context context;

        public UsersAdapter(List<doctor> courseModelList) {
            this.clinicList = courseModelList;

        }

        @NonNull
        @Override
        public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

            return new UsersAdapter.UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.doclist_item,null));
        }

        @Override
        public void onBindViewHolder(@NonNull UsersAdapterVh holder, int position) {

            doctor clinic_obj = clinicList.get(position);

            final String _doctorName = clinic_obj.getName();
            final String _location = clinic_obj.getLocation();
            final String _speciality = clinic_obj.getSpeciality();
            final String _time = clinic_obj.getTime();
            final String _day = clinic_obj.getDay();
            final String phone = clinic_obj.getPhone();
            final String _username = clinic_obj.getUsername();

            holder._doctor_name.setText(_doctorName);
            holder._location.setText(_location);
            holder._speciality.setText(_speciality);
            holder._day.setText( _day+" "+_time);
            holder._number.setText(phone);





        }

        @Override
        public int getItemCount() {
            return clinicList.size();
        }


        public class UsersAdapterVh extends RecyclerView.ViewHolder {

            TextView _doctor_name;
            TextView _speciality;
            TextView _location;
            TextView _day;
            TextView _number;

            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _doctor_name = itemView.findViewById(R.id.dname);
                _speciality = itemView.findViewById(R.id.speciality);
                _location = itemView.findViewById(R.id.location);
                _day = itemView.findViewById(R.id.day);
                _number = itemView.findViewById(R.id.phone);
                confirm=itemView.findViewById(R.id.confirm_button);
            }
        }
    }

}