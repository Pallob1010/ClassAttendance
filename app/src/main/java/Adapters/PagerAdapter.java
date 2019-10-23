package Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import Day.Aday;
import Day.Bday;
import Day.Cday;
import Day.Dday;
import Day.Eday;

public class PagerAdapter extends FragmentStatePagerAdapter {


    Context context;
    ArrayList<String> title;
    String series, section, course, cycle;
    int numberofTabs;

    public PagerAdapter(FragmentManager fragmentManager, Context context, String series, String section, String course, String cycle, ArrayList<String> title, int numberofTabs) {
        super(fragmentManager);
        this.context = context;
        this.series = series;
        this.section = section;
        this.course = course;
        this.cycle = cycle;
        this.title = title;
        this.numberofTabs = numberofTabs;
    }

    @Override
    public Fragment getItem(int position) {

        if (title.get(position).equals("A day")) {
            Aday aday = new Aday(context, series, section, course, cycle);
            return aday;
        } else if (title.get(position).equals("B day")) {
            Bday bday = new Bday(context, series, section, course, cycle);
            return bday;

        } else if (title.get(position).equals("C day")) {
            Cday cday = new Cday(context, series, section, course, cycle);
            return cday;
        } else if (title.get(position).equals("D day")) {
            Dday dday = new Dday(context, series, section, course, cycle);
            return dday;
        } else if (title.get(position).equals("E day")) {
            Eday eday = new Eday(context, series, section, course, cycle);
            return eday;
        } else {
            return null;
        }


    }

    @Override
    public int getCount() {
        return numberofTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

}
