package Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import Databases.Information;
import Interfaces.Backtrack;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class RemoveStudent extends Fragment {
    View view;
    Information information;
    Backtrack backtrack;
    ListView listView;
    public RemoveStudent(Information information, Backtrack backtrack) {
        this.information=information;
        this.backtrack=backtrack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.deletecourse,container,false);
        listView=view.findViewById(R.id.loaderlist);
        return view;
    }
}
