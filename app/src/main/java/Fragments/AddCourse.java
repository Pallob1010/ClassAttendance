package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import Interfaces.Backtrack;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class AddCourse extends Fragment implements View.OnClickListener {
    View view;
    Context context;
    String Series, Section, Course, FirstRoll, TotalStudents, Attendance;
    Button Ok, Cancel;
    Backtrack backtrack;
    Information information;
    EditText Ser, Sec, Cou, Fis, Tot, Att;
    DatabaseCreator databaseCreator;
    ConstraintLayout layout2,layout1;
    ProgressBar progressBar;
    TextView Displaypercentage;
    int count = 0;
    SharedPreference preference;
    String ad="",bd="",cd="",dd="",ed="";
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    CheckBox adaybox,bdaybox,cdaybox,ddaybox,edaybox;
    public AddCourse(Backtrack backtrack, Context context, Information information) {
        this.backtrack = backtrack;
        this.context = context;
        this.information = information;
        databaseCreator = new DatabaseCreator();
        preference = new SharedPreference(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addcourse, container, false);
        layout1 = view.findViewById(R.id.withoutprogresslayout);
        layout2 = view.findViewById(R.id.progresslayout);
        progressBar = view.findViewById(R.id.progressbar1);
        Displaypercentage = view.findViewById(R.id.resultpercentage);
        progressBar.setMax(4200);
        Ok = view.findViewById(R.id.create);
        Ok.setOnClickListener(this);
        Cancel = view.findViewById(R.id.cancel);
        Cancel.setOnClickListener(this);
        Ser = view.findViewById(R.id.seriesid);
        Sec = view.findViewById(R.id.sectionid);
        Cou = view.findViewById(R.id.courseid);
        Fis = view.findViewById(R.id.firstrollid);
        Tot = view.findViewById(R.id.totalstudentsid);
        Att = view.findViewById(R.id.attendancemarks);
        adaybox=view.findViewById(R.id.adaycheckbox);
        bdaybox=view.findViewById(R.id.bdaycheckbox);
        cdaybox=view.findViewById(R.id.cdaycheckbox);
        ddaybox=view.findViewById(R.id.ddaycheckbox);
        edaybox=view.findViewById(R.id.edaycheckbox);

        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.create:
                if (inputfield()) {
                    if (information.insertValues(Series, Section.toUpperCase(), Course.toUpperCase(), FirstRoll, TotalStudents, Attendance,preference.getNumber(),ad,bd,cd,dd,ed)) {
                        databaseCreator.execute();
                    } else {
                        Toast.makeText(context, "Unsuccessfull", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.cancel:
                backtrack.Helper();
                break;

        }


    }


    public boolean inputfield() {
        Series = Ser.getText().toString().trim();
        Section = Sec.getText().toString().trim();
        Course = Cou.getText().toString().trim();
        FirstRoll = Fis.getText().toString().trim();
        TotalStudents = Tot.getText().toString().trim();
        Attendance = Att.getText().toString().trim();

        if (!Series.isEmpty() ){
            if(! Section.isEmpty()){

                if(! FirstRoll.isEmpty()){

                    if(! TotalStudents.isEmpty()){

                        if(! Attendance.isEmpty()){

                            if(!adaybox.isChecked() & !bdaybox.isChecked() & !cdaybox.isChecked() & !ddaybox.isChecked() & !edaybox.isChecked()){

                                Toast.makeText(context, "Select Atleast a Day", Toast.LENGTH_SHORT).show();
                                return false;

                            }else {
                                if (adaybox.isChecked()){
                                    ad="true";
                                }
                                if (bdaybox.isChecked()){
                                    bd="true";
                                }
                                if (cdaybox.isChecked()){
                                    cd="true";
                                }
                                if (ddaybox.isChecked()){
                                    dd="true";
                                }
                                if (edaybox.isChecked()){
                                    ed="true";
                                }
                                return true;
                            }


                        }else {
                            Toast.makeText(context, "Input Attendance Marks", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                    }else {
                        Toast.makeText(context, "Input TotalStudents", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {
                    Toast.makeText(context, "Input FirstRoll", Toast.LENGTH_SHORT).show();
                    return false;
                }


            }else {
                Toast.makeText(context, "Input Section", Toast.LENGTH_SHORT).show();
                return false;
            }


        }else {
            Toast.makeText(context, "Input Series", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public class DatabaseCreator extends AsyncTask<String, String, String> {

        DatabaseAday aday;
        DatabaseBday bday;
        DatabaseCday cday;
        DatabaseDday dday;
        DatabaseEday eday;
        int firstroll, lastroll;

        @Override
        protected void onPreExecute() {
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            firstroll = Integer.parseInt(FirstRoll);
            lastroll = Integer.parseInt(TotalStudents) + firstroll;
            aday = new DatabaseAday(context);
            bday = new DatabaseBday(context);
            cday = new DatabaseCday(context);
            dday = new DatabaseDday(context);
            eday = new DatabaseEday(context);

        }

        @Override
        protected String doInBackground(String... strings) {


            for (int i = 0; i < 14; i++) {

                for (int j = firstroll; j < lastroll; j++) {

                    count += aday.inserts(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "A", String.valueOf(j),ad,0);
                    publishProgress(String.valueOf(count));
                    count += bday.inserts(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "B", String.valueOf(j),bd,0);
                    publishProgress(String.valueOf(count));
                    count += cday.inserts(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "C", String.valueOf(j),cd,0);
                    publishProgress(String.valueOf(count));
                    count += dday.inserts(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "D", String.valueOf(j),dd,0);
                    publishProgress(String.valueOf(count));
                    count += eday.inserts(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "E", String.valueOf(j),ed,0);
                    publishProgress(String.valueOf(count));


                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            int x = Integer.parseInt(values[0]);
            progressBar.setProgress(x);
            Displaypercentage.setText(String.valueOf((x * 100) / 4200) + " %");
        }


        @Override
        protected void onPostExecute(String s) {
            backtrack.Helper();
        }


    }


}
