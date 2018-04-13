package com.example.ananya.careapp;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatientAppointment extends Fragment {

    EditText docid,adate,atime;
    Button submit;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref= database.getReference("docappoint");
    public PatientAppointment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View patApp = inflater.inflate(R.layout.fragment_patient_appointment, container, false);
        docid = patApp.findViewById(R.id.docappid);
        adate = patApp.findViewById(R.id.docappdate);
        atime = patApp.findViewById(R.id.docapptime);
        submit = patApp.findViewById(R.id.docappsubmit);
        adate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    adate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                }
                }
        });
        atime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    atime.setText(hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String did = docid.getText().toString();
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                final String pname= user.getDisplayName();
                final String pdate=adate.getText().toString();
                final String ptime=atime.getText().toString();
                final String pid = ((MyApp) getActivity().getApplication()).getId();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.hasChild(did)) {
                            long count = (long)snapshot.child(did).child("appointcount").getValue();
                            count=count+1;
                            ref.child(did).child("appointcount").setValue(count);
                            AppointmentModel d = new AppointmentModel(pid,pname,pdate,ptime);
                            ref.child(did).child("appointment").child(String.valueOf(count)).setValue(d);

                        }
                        else {
                            Toast.makeText(getActivity(),"Patient Not Found",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(), "Error Occured", Toast.LENGTH_SHORT).show();
                    }
                });
                ref.keepSynced(true);
            }
        });
        return patApp;
    }

}
