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
import spark.loop.classattendance.R;

public class CycleAdapter extends BaseAdapter {

    ArrayList<String> cyclelist;
    Context context;
    String course,series,section;
    Information information;
    int progress=0;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    int current=0;
    public CycleAdapter(Context context, ArrayList<String> cyclelist, String series, String section, String course, DatabaseAday aday, DatabaseBday bday, DatabaseCday cday, DatabaseDday dday, DatabaseEday eday) {
        this.context = context;
        this.cyclelist = cyclelist;
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

    }

    @Override
    public int getCount() {
        return cyclelist.size();
    }

    @Override
    public Object getItem(int position) {
        return cyclelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    class ViewHolder {

        TextView block,Running;

        public ViewHolder(View v) {

            block = v.findViewById(R.id.cycleid);
            Running=v.findViewById(R.id.runningindicator);
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


        current = aday.getProg(course, series, section, cyclelist.get(position))
                + bday.getProg(course, series, section, cyclelist.get(position))
                + cday.getProg(course, series, section, cyclelist.get(position))
                + dday.getProg(course, series, section, cyclelist.get(position))
                + eday.getProg(course, series, section, cyclelist.get(position));

        holder.block.setText(cyclelist.get(position));
           if(current<progress){
            holder.Running.setBackgroundResource(R.drawable.green_circle);

        }else {
               holder.Running.setBackgroundResource(R.drawable.red_circle);
           }
        return row;
    }

}
