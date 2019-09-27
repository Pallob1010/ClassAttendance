package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.CourseDetails;
import spark.loop.classattendance.R;


public class CustomGrid extends BaseAdapter{
    private Context mContext;
    private ArrayList<CourseDetails>object;

    public CustomGrid(Context c,ArrayList<CourseDetails>object) {
        mContext = c;
        this.object=object;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return object.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    class ViewHolder{

        TextView CourseCode,Series,Section;
        public ViewHolder(View v){
            CourseCode=v.findViewById(R.id.singlegridcourseCode);
            Series=v.findViewById(R.id.singlegridseries);
            Section=v.findViewById(R.id.singlegridsection);

        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row=convertView;
        ViewHolder holder=null;


        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.singlegrid, null);
            holder=new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder)row.getTag();
        }

        holder.CourseCode.setText(object.get(position).getCourseCode().toUpperCase());
        holder.Series.setText("Series: "+object.get(position).getSeries());
        holder.Section.setText("Section: "+object.get(position).getSection().toUpperCase());
        return row;
    }
}
