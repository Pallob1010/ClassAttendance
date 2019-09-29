package Day;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;

import Adapters.CycleAdapter;
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
    public CycleOptions(String series, String section, String course, ReverseCaller caller) {

        this.caller=caller;
        this.section=section;
        this.series=series;
        this.course=course;

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

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.cycleview,null,false);
        print();
        return view;
    }

    public void print(){
        cycleshow=view.findViewById(R.id.cycleshow);
        cycleshow.setText("Series :"+series+"\nSection :"+section+"\n"+course);
        gridView=view.findViewById(R.id.cyclegrid);
        adapter=new CycleAdapter(getContext(),cyclelist);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        caller.tablayoutcaller(series,section,course,cyclelist.get(position));
    }
}
