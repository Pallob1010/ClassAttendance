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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Adapters.CustomListA;
import Adapters.CustomListC;
import Adapters.CustomListE;
import Databases.DatabaseAday;
import Databases.DatabaseCday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import Model.RollState;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class Cday extends Fragment {
    View view;
    Context context;
    ListView listView;
    String series,section,course,cycle;
    ArrayList<String>object;
    CustomListC adapter;
    DatabaseCday day;
    Information information;
    SharedPreference preference;
    long currenttimes,storedtimes;
    public Cday(Context context, String series, String section, String course, String cycle) {
        this.context=context;
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
        object=new ArrayList<>();
        day=new DatabaseCday(context);
        preference=new SharedPreference(context);
        currenttimes=Calendar.getInstance().getTimeInMillis();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cday, null, false);
        listView=view.findViewById(R.id.cdaylist);
        storedtimes=Long.parseLong(preference.getDate(course,series,section));

        if((currenttimes-storedtimes)>=3000){
            day.update(course,series,section,cycle,"C");
            preference.saveDay(course,series,section);
        }
        print();
        return view;
    }

    public void print(){

        object=day.getRoll(series,section,course,cycle,"C");
        adapter=new CustomListC(context,object,day.getState(series,section,course,cycle,"C"),series,section,course,cycle);
        listView.setAdapter(adapter);


    }
}
