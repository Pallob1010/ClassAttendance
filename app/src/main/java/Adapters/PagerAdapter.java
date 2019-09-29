package Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import Day.Aday;
import Day.Bday;
import Day.Cday;
import Day.Dday;
import Day.Eday;

public class PagerAdapter extends FragmentStatePagerAdapter {


    Context context;
    String Title[] = {"A day", "B day", " C day", "D day", "E day"};
    String series,section,course,cycle;
    public PagerAdapter(FragmentManager fragmentManager, Context context, String series, String section, String course, String cycle) {
        super(fragmentManager);
        this.context = context;
        this.series=series;
        this.section=section;
        this.course=course;
        this.cycle=cycle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Aday aday = new Aday(context,series,section,course,cycle);
                return aday;

            case 1:
                Bday bday = new Bday(context,series,section,course,cycle);
                return bday;

            case 2:
                Cday cday = new Cday(context,series,section,course,cycle);
                return cday;

            case 3:
                Dday dday = new Dday(context,series,section,course,cycle);
                return dday;
            case 4:

                Eday eday = new Eday(context,series,section,course,cycle);

                return eday;
            default:
                return null;


        }


    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Title[position];
    }

}
