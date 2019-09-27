package Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import Databases.Information;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class AddStudent extends Fragment {

    ArrayAdapter<String> adapter1,adapter2,adapter3;
    ArrayList<String>Series,Section,Course;
    View view;
    Information information;
    Spinner seriesSpinner,sectionSpinner,courseSpinner;
    public AddStudent(Information information) {
        this.information=information;
        Series=new ArrayList<>();
        Section=new ArrayList<>();
        Course=new ArrayList<>();
        Series=information.getSeries();
        Section=information.getSection();
        Course=information.getCourse();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.addstudent,container,false);
        spinnerData();



        return view;
    }

    public void spinnerData(){
        seriesSpinner=view.findViewById(R.id.seriesspinner);
        sectionSpinner=view.findViewById(R.id.sectionspinner);
        courseSpinner=view.findViewById(R.id.coursespinner);
        adapter1= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,Series);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,Section);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3= new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,Course);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesSpinner.setAdapter(adapter1);
        sectionSpinner.setAdapter(adapter2);
        courseSpinner.setAdapter(adapter3);

    }
}
