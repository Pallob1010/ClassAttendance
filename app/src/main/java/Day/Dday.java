package Day;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.CustomListA;
import Adapters.CustomListD;
import Databases.DatabaseAday;
import Databases.DatabaseDday;
import Databases.Information;
import Databases.SharedPreference;
import Model.RollState;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")

public class Dday extends Fragment {
    View view;
    Context context;
    ListView listView;
    String series,section,course,cycle;
    ArrayList<String> object;
    CustomListD adapter;
    DatabaseDday day;
    Information information;
    SharedPreference preference;
    String condition="";
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    public Dday(Context context, String series, String section, String course, String cycle) {
        this.context=context;
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
        object=new ArrayList<>();
        day=new DatabaseDday(context);
        preference=new SharedPreference(context);
        information=new Information(context);
        condition=information.getDday(course,series,section,preference.getNumber());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dday, null, false);
        listView=view.findViewById(R.id.ddaylist);
        if(condition.equals("true")){
            print();
            day.update(course,series,section,cycle,"D");
        }
        return view;
    }

    public void print(){
        object=day.getRoll(series,section,course,cycle,"D");
        adapter=new CustomListD(getActivity(),object,day.getState(series,section,course,cycle,"D"),day,series,section,course,cycle);
        listView.setAdapter(adapter);

    }
}