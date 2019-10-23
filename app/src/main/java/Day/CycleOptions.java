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

import java.util.ArrayList;

import Adapters.CycleAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Interfaces.ReverseCaller;
import spark.loop.classattendance.R;


@SuppressLint("ValidFragment")
public class CycleOptions extends Fragment implements AdapterView.OnItemClickListener {

    View view;
    GridView gridView;
    TextView cycleshow;
    CycleAdapter adapter;
    ArrayList<String>cyclelist;
    String series,section,course;
    ReverseCaller caller;
    Context context;
    Information information;
    int progress=0;
    int current=0,current2;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    public CycleOptions(String series, String section, String course, ReverseCaller caller,Context context) {

        this.caller=caller;
        this.section=section;
        this.series=series;
        this.course=course;
        this.context=context;
        cyclelist=new ArrayList<>();
        cyclelist.add("1st");
        cyclelist.add("2nd");
        cyclelist.add("3rd");
        cyclelist.add("4th");
        cyclelist.add("5th");
        cyclelist.add("6th");
        cyclelist.add("7th");
        cyclelist.add("8th");
        cyclelist.add("9th");
        cyclelist.add("10th");
        cyclelist.add("11th");
        cyclelist.add("12th");
        cyclelist.add("13th");
        cyclelist.add("14th");
        aday=new DatabaseAday(context);
        bday=new DatabaseBday(context);
        cday=new DatabaseCday(context);
        dday=new DatabaseDday(context);
        eday=new DatabaseEday(context);

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
        cycleshow=view.findViewById(R.id.cycleshow);
        cycleshow.setText("Series :"+series+"\nSection :"+section+"\n"+course);
        gridView=view.findViewById(R.id.cyclegrid);
        adapter=new CycleAdapter(context,cyclelist,series,section,course,aday,bday,cday,dday,eday);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position>=1) {
            current = aday.getProg(course, series, section, cyclelist.get(position-1))
                    + bday.getProg(course, series, section, cyclelist.get(position-1))
                    + cday.getProg(course, series, section, cyclelist.get(position-1))
                    + dday.getProg(course, series, section, cyclelist.get(position-1))
                    + eday.getProg(course, series, section, cyclelist.get(position-1));
        }else{
            caller.tablayoutcaller(series,section,course,cyclelist.get(position));

        }
        if(current<progress){
        }else {
            caller.tablayoutcaller(series,section,course,cyclelist.get(position));
        }

    }
}
