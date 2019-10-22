package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.RemoveAdapter;
import Databases.Information;
import Databases.SharedPreference;
import Interfaces.Backtrack;
import Interfaces.ReverseCaller;
import Model.CourseDetails;
import Model.Stateholder;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class RemoveStudent extends Fragment implements View.OnClickListener {
    View view;
    Information information;
    ListView listView;
    Button Select, Cancel;
    RemoveAdapter adapter;
    ArrayList<Stateholder> state;
    ReverseCaller reverseCaller;
    ArrayList<CourseDetails> object;
    SharedPreference preference;
    public RemoveStudent(Information information, Context context, ReverseCaller reverseCaller) {
        this.information = information;
        this.reverseCaller=reverseCaller;
        state=new ArrayList<>();
        preference=new SharedPreference(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.deletecourse, container, false);
        listView = view.findViewById(R.id.loaderlist);
        Select = view.findViewById(R.id.deleteOk);
        Cancel = view.findViewById(R.id.canceldel);
        Select.setText("Select");
        Select.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        print();

        return view;
    }


    public void print() {
        object = information.getInformation(preference.getNumber());
        for (int i = 0; i < object.size(); i++) {
            state.add(new Stateholder(false));
        }

        adapter = new RemoveAdapter(getActivity(), object, state);
        listView.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.deleteOk:
                selectFunction();
                break;
            case R.id.canceldel:
                reverseCaller.helper();

                break;
        }

    }

    public void selectFunction() {
        boolean var=false;
        for (int i = 0; i < state.size(); i++) {
            if (state.get(i).isSelected()) {
                reverseCaller.callreverse(object.get(i).getSeries(),object.get(i).getSection(),object.get(i).getCourseCode());
               var=true;
            }
        }
        if(!var){
            Toast.makeText(getContext(), "Select a Course First!!!", Toast.LENGTH_SHORT).show();
        }

    }

}
