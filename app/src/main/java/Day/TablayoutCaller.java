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

import java.util.ArrayList;

import Adapters.PagerAdapter;
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
    public TablayoutCaller(String series, String section, String course, String cycle) {
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
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
        tabLayout.addTab(tabLayout.newTab().setText("A day"));
        tabLayout.addTab(tabLayout.newTab().setText("B day"));
        tabLayout.addTab(tabLayout.newTab().setText("C day"));
        tabLayout.addTab(tabLayout.newTab().setText("D day"));
        tabLayout.addTab(tabLayout.newTab().setText("E day"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter=new PagerAdapter(getChildFragmentManager(),getContext(),series,section,course,cycle);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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
