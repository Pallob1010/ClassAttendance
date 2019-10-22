package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.DeleteAdapter;
import Adapters.IndividualAdapter;
import Adapters.RemoveAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import Interfaces.Backtrack;
import Model.CourseDetails;
import Model.Stateholder;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class CalculateMarks extends Fragment implements View.OnClickListener {
    View view;
    Information information;
    ListView listView;
    Button Ok, Cancel;
    RemoveAdapter adapter;
    ArrayList<Stateholder> state;
    Backtrack backtrack;
    ArrayList<CourseDetails> object;
    Context context;

    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    SharedPreference preference;
    public CalculateMarks(Information information, Backtrack backtrack, Context context) {
        this.information = information;
        state = new ArrayList<>();
        this.backtrack = backtrack;
        this.context=context;
        aday = new DatabaseAday(context);
        bday = new DatabaseBday(context);
        cday = new DatabaseCday(context);
        dday = new DatabaseDday(context);
        eday = new DatabaseEday(context);
        preference=new SharedPreference(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.deletecourse, container, false);
        listView = view.findViewById(R.id.loaderlist);
        Ok = view.findViewById(R.id.deleteOk);
        Ok.setOnClickListener(this);
        Ok.setText("Select");
        Cancel = view.findViewById(R.id.canceldel);
        Cancel.setOnClickListener(this);
        print();

        return view;
    }


    public void print() {

        object = information.getInformation(preference.getNumber());
        for (int i = 0; i < object.size(); i++) {
            state.add(new Stateholder(false));
        }
        adapter = new RemoveAdapter(getActivity(),object,state);
        listView.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.deleteOk:
                delete();

                break;
            case R.id.canceldel:
                backtrack.Helper();
                break;

        }
    }

    public void delete() {

        boolean var=false;

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).isSelected()) {
                backtrack.result(object.get(i).getSeries(),object.get(i).getSection(),object.get(i).getCourseCode(),object.get(i).getMarks());
                var=true;
            }
        }
        if(!var){
            Toast.makeText(getContext(), "Select a Course First!!!", Toast.LENGTH_SHORT).show();
        }


    }
}
