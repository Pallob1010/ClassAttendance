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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.IndividualAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Interfaces.Backtrack;
import Model.Stateholder;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class DeleteIndividual extends Fragment implements View.OnClickListener {
    View view;
    TextView display;
    String series, section, course;
    ListView listView;
    Button Remove, Cancel;
    Backtrack backtrack;
    Context context;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    ArrayList<Stateholder> state;
    IndividualAdapter adapter;
    ArrayList<String> object;

    public DeleteIndividual(String series, String section, String course, Backtrack backtrack, Context context) {
        this.series = series;
        this.section = section;
        this.course = course;
        this.backtrack = backtrack;
        this.context = context;
        aday = new DatabaseAday(context);
        bday = new DatabaseBday(context);
        cday = new DatabaseCday(context);
        dday = new DatabaseDday(context);
        eday = new DatabaseEday(context);
        state = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.indmain, container, false);
        display = view.findViewById(R.id.display);
        display.setText(series + " " + section + "\n" + course);
        listView = view.findViewById(R.id.individuallist);
        Remove = view.findViewById(R.id.remove);
        Cancel = view.findViewById(R.id.removecancel);
        Remove.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        print();
        return view;
    }


    public void print() {
        object = aday.getRoll(series, section, course, "1st","A");

        for (int i = 0; i < object.size(); i++) {
            state.add(new Stateholder(false));
        }

        adapter = new IndividualAdapter(getActivity(), object, state);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.remove:
                delete();
                break;
            case R.id.removecancel:
                backtrack.Invisible();
                break;
        }

    }

    public void delete() {

        boolean var = false;

        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).isSelected()) {
                aday.deleteByRoll(series, section, course, object.get(i));
                bday.deleteByRoll(series, section, course, object.get(i));
                cday.deleteByRoll(series, section, course, object.get(i));
                dday.deleteByRoll(series, section, course, object.get(i));
                eday.deleteByRoll(series, section, course, object.get(i));
                var = true;

            }
        }
        if (var) {
            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            backtrack.Invisible();
        } else {
            Toast.makeText(getContext(), "Select atleast a student First!!!", Toast.LENGTH_SHORT).show();
        }


    }
}
