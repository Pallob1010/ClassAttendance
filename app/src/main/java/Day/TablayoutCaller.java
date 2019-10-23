package Day;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Adapters.PagerAdapter;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import spark.loop.classattendance.MainActivity;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class TablayoutCaller extends Fragment {

    View view;
    TabLayout tabLayout;
    TextView Series,Section,Course,Cycle;
    String series,section,course,cycle;
    ViewPager viewPager;
    PagerAdapter adapter;
    Context context;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    Information information;
    SharedPreference preference;
    int numberofTabs=0;
    int current=0;
    ArrayList<String>title;
    public TablayoutCaller(String series, String section, String course, String cycle, Context context) {
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
        this.context=context;
        aday=new DatabaseAday(context);
        bday=new DatabaseBday(context);
        cday=new DatabaseCday(context);
        dday=new DatabaseDday(context);
        eday=new DatabaseEday(context);
        information=new Information(context);
        preference=new SharedPreference(context);
        title=new ArrayList<>();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.daytablayout,null,false);
        tabLayout=view.findViewById(R.id.TabLayout);
        Course=view.findViewById(R.id.tv1);
        Series=view.findViewById(R.id.tv2);
        Section=view.findViewById(R.id.tv3);
        Cycle=view.findViewById(R.id.tv4);
        viewPager=view.findViewById(R.id.viewpager);

        if (information.getAday(course,series,section,preference.getNumber()).equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("A day"));
            title.add("A day");
            current=aday.getProg(course,series,section,cycle);
            numberofTabs++;

        }
        if (information.getBday(course,series,section,preference.getNumber()).equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("B day"));
            title.add("B day");
            current=current+bday.getProg(course,series,section,cycle);
            numberofTabs++;
        }
        if (information.getCday(course,series,section,preference.getNumber()).equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("C day"));
            title.add("C day");
            current=current+cday.getProg(course,series,section,cycle);
            numberofTabs++;
        }
        if (information.getDday(course,series,section,preference.getNumber()).equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("D day"));
            title.add("D day");
            current=current+dday.getProg(course,series,section,cycle);
            numberofTabs++;
        }
        if (information.getEday(course,series,section,preference.getNumber()).equals("true")){
            tabLayout.addTab(tabLayout.newTab().setText("E day"));
            title.add("E day");
            current=current+eday.getProg(course,series,section,cycle);
            numberofTabs++;
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter=new PagerAdapter(getChildFragmentManager(),getContext(),series,section,course,cycle,title,numberofTabs );
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
       if(current==0){
           viewPager.setCurrentItem(current);
       }else {
           viewPager.setCurrentItem(current-1);
       }



        print();
        return view;
    }


    public void print(){
        Course.setText(course);
        Series.setText(" Series\n"+series);
        Section.setText("Section\n"+section);
        Cycle.setText("Cycle\n"+cycle);

    }
}
