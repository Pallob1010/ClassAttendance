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

import java.util.ArrayList;

import Adapters.CustomListA;
import Adapters.CustomListE;
import Databases.DatabaseEday;
import Model.RollState;
import spark.loop.classattendance.R;


@SuppressLint("ValidFragment")
public class Eday extends Fragment {

    View view;
    Context context;
    ListView listView;
    String series,section,course,cycle;
    ArrayList<String> object;
    CustomListE adapter;
    DatabaseEday eday;
    public Eday(Context context, String series, String section, String course, String cycle) {
        this.context=context;
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
        object=new ArrayList<>();
        eday=new DatabaseEday(context);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.eday, null, false);
        listView=view.findViewById(R.id.edaylist);
        print();
        return view;
    }

    public void print(){
        object=eday.getRoll(series,section,course,cycle);
        adapter=new CustomListE(getActivity(),object,eday.getState(series,section,course,cycle),eday,series,section,course,cycle);
        listView.setAdapter(adapter);

    }
}