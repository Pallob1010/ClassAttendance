package Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Interfaces.Backtrack;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class AddStudent extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    ArrayAdapter<String> adapter1, adapter2, adapter3;
    ArrayList<String> Series, Section, Course;
    EditText RollInput;
    View view;
    Information information;
    String series = "", section = "", course = "";
    Spinner seriesSpinner, sectionSpinner, courseSpinner;
    Button Confirm, Cancel;
    Backtrack backtrack;
    Context context;
    int count = 0;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    Dialog dialog;

    public AddStudent(Information information, Backtrack backtrack, Context context) {
        this.information = information;
        this.context = context;
        aday = new DatabaseAday(context);
        bday = new DatabaseBday(context);
        cday = new DatabaseCday(context);
        dday = new DatabaseDday(context);
        eday = new DatabaseEday(context);
        Series = new ArrayList<>();
        Section = new ArrayList<>();
        Course = new ArrayList<>();
        Series = information.getSeries();
        Section = information.getSection();
        Course = information.getCourse();
        if (Series.size() != 0) {
            series = Series.get(0);
            section = Section.get(0);
            course = Course.get(0);
        }


        this.backtrack = backtrack;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addstudent, container, false);
        Cancel = view.findViewById(R.id.cancelNewStudent);
        Confirm = view.findViewById(R.id.confirmstudent);
        Cancel.setOnClickListener(this);
        Confirm.setOnClickListener(this);
        RollInput = view.findViewById(R.id.rollinputId);
        dialog = new Dialog(context);
        spinnerData();


        return view;
    }

    public void spinnerData() {
        seriesSpinner = view.findViewById(R.id.seriesspinner);
        sectionSpinner = view.findViewById(R.id.sectionspinner);
        courseSpinner = view.findViewById(R.id.coursespinner);
        adapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Series);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Section);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3 = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, Course);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesSpinner.setAdapter(adapter1);
        seriesSpinner.setOnItemSelectedListener(this);
        sectionSpinner.setAdapter(adapter2);
        sectionSpinner.setOnItemSelectedListener(this);
        courseSpinner.setAdapter(adapter3);
        courseSpinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.confirmstudent:

                if (series.equals("") | section.equals("") | course.equals("")) {
                    Toast.makeText(getContext(), "No Running Series !!! ", Toast.LENGTH_SHORT).show();
                } else {
                    if (RollInput.getText().toString().trim().isEmpty()) {
                        Toast.makeText(context, "Insert Roll Number", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.progressdialog);
                        dialog.setCancelable(false);
                        dialog.show();
                        addStudents(RollInput.getText().toString().trim());
                    }
                }
                dialog.dismiss();
                backtrack.Helper();

                break;
            case R.id.cancelNewStudent:
                backtrack.Helper();
                break;

        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (v.getId()) {
            case R.id.seriesspinner:
                series = Series.get(position);
                break;
            case R.id.sectionspinner:
                section = Section.get(position);
                break;
            case R.id.coursespinner:
                course = Course.get(position);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addStudents(String Roll) {

        for (int i = 0; i < 14; i++) {
            count = aday.insertRollStateAday(course.toUpperCase(), series, section.toUpperCase(), cycles[i], "A", Roll, "false");
            count = bday.insertRollStateBday(course.toUpperCase(), series, section.toUpperCase(), cycles[i], "A", Roll, "false");
            count = cday.insertRollStateCDay(course.toUpperCase(), series, section.toUpperCase(), cycles[i], "A", Roll, "false");
            count = dday.insertRollStateDDay(course.toUpperCase(), series, section.toUpperCase(), cycles[i], "A", Roll, "false");
            count = eday.insertRollStateEDay(course.toUpperCase(), series, section.toUpperCase(), cycles[i], "A", Roll, "false");

        }

    }

}
