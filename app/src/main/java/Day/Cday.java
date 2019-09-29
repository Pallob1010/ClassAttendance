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

import java.util.ArrayList;

import Adapters.CustomListA;
import Adapters.CustomListC;
import Databases.DatabaseAday;
import Databases.DatabaseCday;
import Model.RollState;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class Cday extends Fragment {
    View view;
    Context context;
    ListView listView;
    String series,section,course,cycle;
    ArrayList<String> object;
    CustomListC adapter;
    DatabaseCday aday;
    public Cday(Context context, String series, String section, String course, String cycle) {
        this.context=context;
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
        object=new ArrayList<>();
        aday=new DatabaseCday(context);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cday, null, false);
        listView=view.findViewById(R.id.cdaylist);
        print();
        return view;
    }

    public void print(){
        object=aday.getRoll(series,section,course,cycle);
        adapter=new CustomListC(getActivity(),object,aday.getState(series,section,course,cycle),aday,series,section,course,cycle);
        listView.setAdapter(adapter);

    }
}