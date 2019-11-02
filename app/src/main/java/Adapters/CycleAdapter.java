package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import spark.loop.classattendance.R;

public class CycleAdapter extends BaseAdapter {
    Context context;
    String course,series,section;
    Information information;
    int progress=0,current=0;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    SharedPreference preference;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};
    public CycleAdapter(Context context,String series, String section, String course, DatabaseAday aday, DatabaseBday bday, DatabaseCday cday, DatabaseDday dday, DatabaseEday eday) {
        this.context = context;
        this.course=course;
        this.series=series;
        this.section=section;
        this.aday=aday;
        this.bday =bday;
        this.cday =cday;
        this.dday =dday;
        this.eday =eday;
        information=new Information(context);
        progress= information.activeDay(course,series,section);
        preference=new SharedPreference(context);


    }

    @Override
    public int getCount() {
        return cycles.length;
    }

    @Override
    public Object getItem(int position) {
        return cycles[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    class ViewHolder {

        TextView block,Running;
        ProgressBar progressBar;

        public ViewHolder(View v) {

            block = v.findViewById(R.id.cycleid);
            Running=v.findViewById(R.id.runningindicator);
            progressBar=v.findViewById(R.id.dayprogress);
            progressBar.setMax(progress);
        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.cycleblock, null);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.block.setText(cycles[position]);

        current=aday.getProg(course,series,section,cycles[position])+
                bday.getProg(course,series,section,cycles[position])+
                cday.getProg(course,series,section,cycles[position])+
                dday.getProg(course,series,section,cycles[position])+
                eday.getProg(course,series,section,cycles[position]);

        if(current==progress){
            holder.Running.setBackgroundResource(R.drawable.red_circle);
            holder.progressBar.setProgress(current);
        }else if(current>0) {
            holder.Running.setBackgroundResource(R.drawable.green_circle);
            holder.progressBar.setProgress(current);
            preference.saveRunningCycle(course,series,section,cycles[position]);

        }else {
            holder.progressBar.setProgress(current);
        }




        return row;
    }

}
