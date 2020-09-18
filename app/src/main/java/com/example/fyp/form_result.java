package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class form_result extends AppCompatActivity {


        RecyclerView recyclerView;

        List<clinic> cliniclList = new ArrayList<>();


        UsersAdapter courseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_result);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


            cliniclList
           .add(new clinic
                   ("Ahsan Khan",
                           "ENT Specialist",
                           "medicare hospital 2nd floor"
                           ,"Monday 9am to 6pm"));

        cliniclList
                .add(new clinic
                        ("Ahsan Khan",
                                "ENT Specialist",
                                "medicare hospital 2nd floor"
                                ,"Monday 9am to 6pm"));


        courseAdapter = new UsersAdapter(cliniclList);

        recyclerView.setAdapter(courseAdapter);

    }


    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVh> {

        private List<clinic> clinicList;
        private Context context;

        public UsersAdapter(List<clinic> courseModelList) {
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

            clinic clinic_obj = clinicList.get(position);

            final String _doctorName = clinic_obj.getdoctor_Name();
            final String _location = clinic_obj.getLocation();
            final String _speciality = clinic_obj.getSpeciality();
            final String _time = clinic_obj.getTiming();


            holder._doctor_name.setText(_doctorName);
            holder._location.setText(_location);
            holder._speciality.setText(_speciality);
            holder._timming.setText(_time);


            holder.confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "appointment confirmed", Toast.LENGTH_SHORT).show();

  /*                  AlertDialog.Builder alertadd = new AlertDialog.Builder(MainActivity3.this);
                    LayoutInflater factory = LayoutInflater.from(MainActivity3.this);
                    View imageview = factory.inflate(R.layout.alert_, null);
                    final EditText iv= imageview.findViewById(R.id.edit);
                    Button tv=imageview.findViewById(R.id.b);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.tvPrefix.setText(iv.getText().toString());
                        }
                    });
                    alertadd.setView(imageview);


                    alertadd.setNeutralButton("Close!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dlg, int sumthin) {

                        }
                    });

                    alertadd.show();
*/
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
            TextView _timming;
            Button confirm;

            public UsersAdapterVh(@NonNull View itemView) {
                super(itemView);
                _doctor_name = itemView.findViewById(R.id.dname);
                _speciality = itemView.findViewById(R.id.speciality);
                _location = itemView.findViewById(R.id.location);
                _timming = itemView.findViewById(R.id.timing);
                confirm=itemView.findViewById(R.id.confirm_button);
            }
        }
    }


     class clinic  {

        private String doctor_Name;
        private String speciality;
      private  String location;
         private  String timing;

         public clinic(String dName,String _special,String _loc,String _time) {

            this.doctor_Name = dName;
            speciality=_special;
            location=_loc;
             timing=_time;
         }

         public String getTiming() {
             return timing;
         }

         public void setTiming(String timing) {
             this.timing = timing;
         }

        public String getSpeciality() {
            return speciality;
        }

        public void setSpeciality(String speciality) {
            this.speciality = speciality;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

      public String getdoctor_Name() {
            return doctor_Name;
        }

        public void setDoctor_Name(String cName) {
            this.doctor_Name = cName;
        }
    }
}