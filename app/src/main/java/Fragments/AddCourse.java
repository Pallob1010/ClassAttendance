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
    LinearLayout layout1;
    ConstraintLayout layout2;
    ProgressBar progressBar;
    TextView Displaypercentage;
    int count = 0;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};

    public AddCourse(Backtrack backtrack, Context context, Information information) {
        this.backtrack = backtrack;
        this.context = context;
        this.information = information;
        databaseCreator = new DatabaseCreator();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addcourse, container, false);
        layout1 = view.findViewById(R.id.withoutprogresslayout);
        layout2 = view.findViewById(R.id.progresslayout);
        progressBar = view.findViewById(R.id.progressbar1);
        Displaypercentage = view.findViewById(R.id.creationpercentage);
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


        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.create:
                if (inputfield()) {
                    if (information.insertValues(Series, Section.toUpperCase(), Course.toUpperCase(), FirstRoll, TotalStudents, Attendance)) {
                        databaseCreator.execute();
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
        Series = Ser.getText().toString().trim();
        Section = Sec.getText().toString().trim();
        Course = Cou.getText().toString().trim();
        FirstRoll = Fis.getText().toString().trim();
        TotalStudents = Tot.getText().toString().trim();
        Attendance = Att.getText().toString().trim();

        if (Series.isEmpty() | Section.isEmpty() | Course.isEmpty() | FirstRoll.isEmpty() | TotalStudents.isEmpty() | Attendance.isEmpty()) {
            return false;
        } else {

            return true;
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

                    count += aday.insertRollStateAday(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "A", String.valueOf(j), "false");
                    publishProgress(String.valueOf(count));
                    count += bday.insertRollStateBday(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "B", String.valueOf(j), "false");
                    publishProgress(String.valueOf(count));
                    count += cday.insertRollStateCDay(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "C", String.valueOf(j), "false");
                    publishProgress(String.valueOf(count));
                    count += dday.insertRollStateDDay(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "D", String.valueOf(j), "false");
                    publishProgress(String.valueOf(count));
                    count += eday.insertRollStateEDay(Course.toUpperCase(), Series, Section.toUpperCase(), cycles[i], "E", String.valueOf(j), "false");
                    publishProgress(String.valueOf(count));


                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            int x=Integer.parseInt(values[0]);
            progressBar.setProgress(x);
            Displaypercentage.setText(String.valueOf((x*100)/4200)+" %");
        }


        @Override
        protected void onPostExecute(String s) {
            backtrack.Helper();
        }


    }


}
