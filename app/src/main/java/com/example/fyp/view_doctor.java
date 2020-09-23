package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    DatabaseReference doc_reference;
    String loc,phone,name,special,day,time,key,doctors_username,
            useremail,users_name,users_phone,users_gender;
    form_result.UsersAdapter courseAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doctor);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doc_reference= FirebaseDatabase.getInstance().getReference("doctor");

        doc_reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        public void onBindViewHolder(@NonNull UsersAdapterVh holder, int position) {
            appointment clinic_obj = clinicList.get(position);

            final String _doctorName = clinic_obj.getDoc_name();
            final String _location = clinic_obj.getLocation();
            final String _speciality = clinic_obj.getSpeciality();
            final String _time = clinic_obj.getTime();
            final String _day = clinic_obj.getDay();
            final String phone = clinic_obj.getPhone();
            final String _date = clinic_obj.getAppoint_date();

            holder._doctor_name.setText(_doctorName);
            holder._location.setText(_location);
            holder._speciality.setText(_speciality);
            holder._day.setText( _day+" "+_time);
            holder._number.setText(phone);
            holder._date.setText(_date);
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
            }
        }
    }



}