package Fragments;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import Interfaces.Backtrack;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class DeleteIndividual extends Fragment implements View.OnClickListener {
    View view;
    TextView display;
    String series,section,course;
    ListView listView;
    Button Remove,Cancel;
    Backtrack backtrack;
    public DeleteIndividual(String series, String section, String course, Backtrack backtrack) {
        this.series=series;
        this.section=section;
        this.course=course;
        this.backtrack=backtrack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.indmain,container,false);
        display=view.findViewById(R.id.display);
        display.setText(series+" "+section+"\n"+course);
        listView=view.findViewById(R.id.individuallist);
        Remove=view.findViewById(R.id.remove);
        Cancel=view.findViewById(R.id.removecancel);
        Remove.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        print();
        return view;
    }


    public void print(){


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.remove:
               backtrack.Invisible();
                break;
            case R.id.removecancel:
                backtrack.Invisible();
                break;
        }

    }

}
