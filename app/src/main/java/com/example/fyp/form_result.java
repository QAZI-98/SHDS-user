package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class form_result extends AppCompatActivity {


        RecyclerView recyclerView;

        List<doctor> cliniclList = new ArrayList<>();
    DatabaseReference doc_reference,appointment_reference,user_reference;
    String loc,phone,name,special,day,time,key,doctors_username,
            useremail,users_name,users_phone,users_gender;
        UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;
    FirebaseUser current_user;
    boolean already_booked;
    int no_of_appointments=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_result);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        doc_reference= FirebaseDatabase.getInstance().getReference("doctor");
        appointment_reference= FirebaseDatabase.getInstance().getReference("appointment");

        current_user= FirebaseAuth.getInstance().getCurrentUser();
        useremail= current_user.getEmail();
        user_reference= FirebaseDatabase.getInstance().getReference("users").child(replacer.fireproof_string(useremail));

        user_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users_name= snapshot.child("name").getValue(String.class);
                users_phone= snapshot.child("phone").getValue(String.class);
                users_gender= snapshot.child("gender").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        doc_reference.addValueEventListener(new ValueEventListener() {
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
                linearLayoutManager = new LinearLayoutManager(form_result.this);
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

            return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.items,null));
        }

        @Override
        public void onBindViewHolder(@NonNull final UsersAdapter.UsersAdapterVh holder, int position) {

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



            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {





                    appointment_reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            already_booked = false;
                             no_of_appointments=0;
                            for (DataSnapshot ds : snapshot.getChildren()) {


                                if (ds.child("docusername").getValue(String.class).equals(_username)) {
                                    no_of_appointments++;
                                    if (ds.child("useremail").getValue(String.class).equals(useremail)) {
                                        already_booked = true;
                                    }

                                }


                            }

                            if (already_booked) {

                                Snackbar.make(view, "Appointment already booked!", Snackbar.LENGTH_LONG)
                                        .setAction("Close", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                            }
                                        })
                                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                        .show();

                            } else {

                                if(no_of_appointments<2){

                                    LocalDate ldt = null;
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        ldt = LocalDate.now();

                                        switch (_day)
                                        {
                                            case "Monday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
                                                break;
                                            case "Tuesday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
                                                break;
                                            case "Wednesday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
                                                break;
                                            case "Thursday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
                                                break;
                                            case "Friday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
                                                break;
                                            case "Saturday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                                                break;
                                            case "Sunday":
                                                ldt = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
                                                break;

                                        }
                                        key=appointment_reference.push().getKey();
                                        appointment_reference.child(key)
                                                .setValue(new appointment(useremail,_username,key,ldt.toString(),users_name,users_phone,users_gender,_speciality,_doctorName,_location,phone,_day,_time));
                                        Snackbar.make(view, "Appointment Confirmed!", Snackbar.LENGTH_LONG)
                                                .setAction("Close", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                    }
                                                })
                                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                                .show();

                                    }

                                    else {
                                        Toast.makeText(context, "Issue with android version", Toast.LENGTH_SHORT).show();
                                    }


                                }
                                else{
                                    Snackbar.make(view, "Booking full!", Snackbar.LENGTH_LONG)
                                            .setAction("Close", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                }
                                            })
                                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                            .show();      }

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            });

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