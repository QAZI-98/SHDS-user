package com.example.fyp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class appointment_page extends AppCompatActivity {


    RecyclerView recyclerView;

    List<appointment> cliniclList = new ArrayList<>();
    DatabaseReference appointment_reference;
    String loc,phone,name,special,day,time,useremail,key,apoint_date,temp_email;
    UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;
    FirebaseUser current_user;
    SimpleDateFormat sdf;
    long diffindays;
    Date firstDate;
    Date secondDate;
    int flag;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_page);
        prefs = getSharedPreferences(appoint_nav.MY_PREFS_NAME, MODE_PRIVATE);
        flag = prefs.getInt("flag", 3);
        sdf=new SimpleDateFormat("yyyy-MM-dd");
        String date_toaday = sdf.format(new Date());

        try {
            secondDate = sdf.parse(date_toaday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        appointment_reference= FirebaseDatabase.getInstance().getReference("appointment");
        recyclerView = findViewById(R.id.aplist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        current_user= FirebaseAuth.getInstance().getCurrentUser();
        useremail= current_user.getEmail();

        appointment_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    try {
                        firstDate = sdf.parse(ds.child("appoint_date").getValue(String.class));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    diffindays = firstDate.getTime() - secondDate.getTime();
                    diffindays = diffindays / (24 * 60 * 60 * 1000);
                    Log.e("mycheck",Long.toString(diffindays));
                    Log.e("abc","abcd");

                    loc = ds.child("location").getValue(String.class);
                    phone = ds.child("phone").getValue(String.class);
                    name = ds.child("doc_name").getValue(String.class);
                    special = ds.child("speciality").getValue(String.class);
                    day = ds.child("day").getValue(String.class);
                    time = ds.child("time").getValue(String.class);
                    key = ds.child("appoint_id").getValue(String.class);
                    apoint_date = ds.child("appoint_date").getValue(String.class);


                    if (flag==0 && ds.child("useremail").getValue(String.class).equals(useremail) && diffindays>=0) {
                        Log.i("abc",ds.child("location").getValue(String.class));
                        cliniclList.add(new appointment
                                (key, apoint_date, special, name, loc, phone, day, time));
                    }
                    else if(flag==1 && ds.child("useremail").getValue(String.class).equals(useremail) && diffindays<0) {

                        Log.i("abc",ds.child("location").getValue(String.class));
                        cliniclList.add(new appointment
                                (key, apoint_date, special, name, loc, phone, day, time));
                    }


                }
                courseAdapter = new UsersAdapter(cliniclList);
                recyclerView.setAdapter(courseAdapter);
                linearLayoutManager = new LinearLayoutManager(appointment_page.this);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVh> {

        private List<appointment> clinicList;
        private Context context;

        public UsersAdapter(List<appointment> courseModelList) {
            this.clinicList = courseModelList;

        }

        @NonNull
        @Override
        public UsersAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();

            return new UsersAdapter.UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.appointment_list,null));

        }

        @Override
        public void onBindViewHolder(@NonNull final UsersAdapter.UsersAdapterVh holder, int position) {

            appointment clinic_obj = clinicList.get(position);

            final String _doctorName = clinic_obj.getDoc_name();
            final String _location = clinic_obj.getLocation();
            final String _speciality = clinic_obj.getSpeciality();
            final String _time = clinic_obj.getTime();
            final String _day = clinic_obj.getDay();
            final String phone = clinic_obj.getPhone();
            final String _date = clinic_obj.getAppoint_date();
            final String _aid = clinic_obj.getAppoint_id();


            holder._doctor_name.setText(_doctorName);
            holder._location.setText(_location);
            holder._speciality.setText(_speciality);
            holder._day.setText( _day+" "+_time);
            holder._number.setText(phone);
            holder._date.setText(_date);


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    appointment_reference.child(_aid).removeValue();
                    Intent i=new Intent(appointment_page.this,appointment_page.class);
                    finish();
                    startActivity(i);

                    Toast.makeText(context, "Appointment canceled", Toast.LENGTH_SHORT).show();
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
            TextView _date;

            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _doctor_name = itemView.findViewById(R.id.dname);
                _speciality = itemView.findViewById(R.id.speciality);
                _location = itemView.findViewById(R.id.location);
                _day = itemView.findViewById(R.id.day);
                _number = itemView.findViewById(R.id.phone);
                confirm=itemView.findViewById(R.id.confirm_button);
                _date=itemView.findViewById(R.id.date);
                if (flag==1)
                    confirm.setVisibility(View.INVISIBLE);
            }
        }
    }



}