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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import Adapters.CycleAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import Interfaces.ReverseCaller;
import spark.loop.classattendance.R;


@SuppressLint("ValidFragment")
public class CycleOptions extends Fragment implements AdapterView.OnItemClickListener {

    View view;
    GridView gridView;
    TextView cycleshow,CurrentCycle;
    CycleAdapter adapter;
    String series,section,course;
    ReverseCaller caller;
    Context context;
    Information information;
    int progress=0,current=0;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    SharedPreference preference;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    public CycleOptions(String series, String section, String course, ReverseCaller caller,Context context) {

        this.caller=caller;
        this.section=section;
        this.series=series;
        this.course=course;
        this.context=context;
        aday=new DatabaseAday(context);
        bday=new DatabaseBday(context);
        cday=new DatabaseCday(context);
        dday=new DatabaseDday(context);
        eday=new DatabaseEday(context);
        preference=new SharedPreference(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.cycleview,null,false);
        print();
        information=new Information(context);
        progress=information.activeDay(course,series,section);
        return view;
    }

    public void print(){
        cycleshow=view.findViewById(R.id.totalShow);
        CurrentCycle=view.findViewById(R.id.currentCycle);
        cycleshow.setText("Series   : "+series+"\nSection : "+section+"\nCourse  : "+course);
        CurrentCycle.setText(preference.getRunningCycle(course,series,section));
        gridView=view.findViewById(R.id.cyclegrid);
        adapter=new CycleAdapter(context,series,section,course,aday,bday,cday,dday,eday);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        preference.saveRunningCycle(course,series,section,cycles[position]);
        if(position==0){
          caller.tablayoutcaller(series,section,course,cycles[position]);
        }else {
            current=aday.getProg(course,series,section,cycles[position-1])+
                    bday.getProg(course,series,section,cycles[position-1])+
                    cday.getProg(course,series,section,cycles[position-1])+
                    dday.getProg(course,series,section,cycles[position-1])+
                    eday.getProg(course,series,section,cycles[position-1]);
                if(current==progress){
                    caller.tablayoutcaller(series,section,course,cycles[position]);
                }else {
                    Toast.makeText(context, "Previous cycle is not completed yet!!!", Toast.LENGTH_SHORT).show();
                }

        }
    }
}
