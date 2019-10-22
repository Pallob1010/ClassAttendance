package Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.ResultAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Interfaces.Backtrack;
import Model.ResultHolder;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class Result extends Fragment implements View.OnClickListener {


    View view;
    ListView listView;
    String series, section, course;
    TextView Course, Series, Section, TotalClass, UpdateProgress;
    ResultCalculation calculation;
    ArrayList<ResultHolder> object;
    ResultAdapter adapter;
    Dialog dialog;
    Context context;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    ArrayList<String> stateA, stateB, stateC, stateD, stateE;
    int numberofStudents;
    int TotalPresence = 0;
    int marks;
    int RunningTime = 0;
    int counter=0;
    ArrayList<String> Rolls;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    int present[];
    int Presence;
    Backtrack backtrack;
    ProgressBar progressBar;
    FloatingActionButton actionButton;
    public Result(String series, String section, String course, int marks, Context context, Backtrack backtrack) {
        this.series = series;
        this.section = section;
        this.course = course;
        this.backtrack=backtrack;
        this.marks = marks;
        object = new ArrayList<>();
        calculation = new ResultCalculation();
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progressdialog);
        dialog.setCancelable(false);
        dialog.getWindow().setGravity(Gravity.CENTER);
        UpdateProgress = dialog.findViewById(R.id.updateprogress);
        progressBar=dialog.findViewById(R.id.pro);
        dialog.show();
        calculation.execute();



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.result, container, false);
        actionButton=view.findViewById(R.id.fab);
        actionButton.setOnClickListener(this);
        listView = view.findViewById(R.id.resultlist);
        Course = view.findViewById(R.id.resco);
        Series = view.findViewById(R.id.resse);
        Section = view.findViewById(R.id.ressec);
        TotalClass = view.findViewById(R.id.totalclass);
        Course.setText(course);
        Series.setText("Series\n"+series);
        Section.setText("Section\n"+section);
         print();

        return view;
    }


    public void print() {
        adapter = new ResultAdapter(getActivity(), object);
        listView.setAdapter(adapter);
        TotalClass.setText("Total Class\n" + String.valueOf(TotalPresence));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
            backtrack.filebrowser(series,section,course,object,TotalPresence);
    }


    public class ResultCalculation extends AsyncTask<String, String, String> {
        int c1, c2, c3, c4, c5;
        String adaycounter,bdaycounter,cdaycounter,ddaycounter,edaycounter;


        @Override
        protected void onPreExecute() {

            aday = new DatabaseAday(context);
            bday = new DatabaseBday(context);
            cday = new DatabaseCday(context);
            dday = new DatabaseDday(context);
            eday = new DatabaseEday(context);
            Rolls = aday.getRoll(series, section, course, "1st","A");
            numberofStudents = Rolls.size();
            present = new int[numberofStudents +1];
            RunningTime = numberofStudents*6;
            progressBar.setMax(RunningTime);
        }

        @Override
        protected String doInBackground(String... strings) {

               for (int i=0; i<14;i++){

                   c1 = 0;
                   c3 = 0;
                   c5 = 0;
                   c2 = 0;
                   c4 = 0;
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));
                   stateA = aday.getState(series, section, course, cycles[i],"A");
                   adaycounter=aday.getTotalcount(course,series,section,Rolls.get(0),cycles[i],"A");
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));
                   stateB = bday.getState(series, section, course, cycles[i],"B");
                   bdaycounter=bday.getTotalcount(course,series,section,Rolls.get(0),cycles[i],"B");
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));
                   stateC = cday.getState(series, section, course, cycles[i],"C");
                   cdaycounter=cday.getTotalcount(course,series,section,Rolls.get(0),cycles[i],"C");
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));
                   stateD = dday.getState(series, section, course, cycles[i],"D");
                   ddaycounter=dday.getTotalcount(course,series,section,Rolls.get(0),cycles[i],"D");
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));
                   stateE = eday.getState(series, section, course, cycles[i],"E");
                   edaycounter=eday.getTotalcount(course,series,section,Rolls.get(0),cycles[i],"E");
                   counter++;
                   progressBar.setProgress(counter);
                   publishProgress(String.valueOf(counter));

                   for (int j = 0; j < numberofStudents; j++) {
                       if (stateA.get(j).equals("true") && adaycounter.equals("1")) {
                           c1 = 1;
                           present[j] = present[j] + 1;

                       }
                       if (stateB.get(j).equals("true") && bdaycounter.equals("1")) {
                           c2 = 1;
                           present[j] = present[j] + 1;

                       }
                       if (stateC.get(j).equals("true") && cdaycounter.equals("1")) {
                           c3 = 1;
                           present[j] = present[j] + 1;
                       }
                       if (stateD.get(j).equals("true") && ddaycounter.equals("1")) {
                           c4 = 1;
                           present[j] = present[j] + 1;
                       }
                       if (stateE.get(j).equals("true") && edaycounter.equals("1")) {
                           c5 = 1;
                           present[j] = present[j] + 1;
                       }

                       counter++;
                       progressBar.setProgress(counter);
                       publishProgress(String.valueOf(counter));

                   }

                   TotalPresence = TotalPresence + c1 + c2 + c3 + c4 + c5;
               }


            for (int i = 0; i < numberofStudents; i++) {
                Presence = present[i];
                try {
                    object.add(new ResultHolder(Rolls.get(i), Presence, (Presence*100)/TotalPresence, (marks * Presence) / TotalPresence));
                }catch (Exception e){

                }
                counter++;
                publishProgress(String.valueOf(counter));
            }


            return null;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            int x=Integer.parseInt(values[0]);
            UpdateProgress.setText(" "+String.valueOf((x*100)/RunningTime)+" %");
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(context, String.valueOf(present[0]), Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            print();
        }

    }
}
