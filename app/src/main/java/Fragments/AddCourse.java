package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Databases.Information;
import Interfaces.Backtrack;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class AddCourse extends Fragment implements View.OnClickListener {
    View view;
    Context context;
    EditText Series, Section, Course, FirstRoll, TotalStudents, Attendance;
    Button Ok, Cancel;
    Backtrack backtrack;
    Information information;
    String Ser, Sec, Cou, Fis, Tot, Att;

    public AddCourse(Backtrack backtrack, Context context, Information information) {
        this.backtrack = backtrack;
        this.context = context;
        this.information = information;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addcourse, container, false);
        Ok = view.findViewById(R.id.create);
        Ok.setOnClickListener(this);
        Cancel = view.findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);
        Series = view.findViewById(R.id.seriesid);
        Section = view.findViewById(R.id.sectionid);
        Course = view.findViewById(R.id.courseid);
        FirstRoll = view.findViewById(R.id.firstrollid);
        TotalStudents = view.findViewById(R.id.totalstudentsid);
        Attendance = view.findViewById(R.id.attendancemarks);


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.create:
                if (inputfield()) {
                    if (information.insertValues(Ser, Sec.toUpperCase(), Cou.toUpperCase(), Fis, Tot, Att)) {
                        backtrack.Helper();
                    } else {
                        Toast.makeText(context, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Fill All The Field", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.cancel:
                backtrack.Helper();
                break;

        }


    }


    public boolean inputfield() {
        Ser = Series.getText().toString();
        Sec = Section.getText().toString();
        Cou = Course.getText().toString();
        Fis = FirstRoll.getText().toString();
        Tot = TotalStudents.getText().toString();
        Att = Attendance.getText().toString();

        if (Ser.isEmpty() | Sec.isEmpty() | Cou.isEmpty() | Fis.isEmpty() | Tot.isEmpty() | Att.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}
