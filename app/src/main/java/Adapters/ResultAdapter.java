package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import Model.ResultHolder;
import spark.loop.classattendance.R;


public class ResultAdapter extends BaseAdapter {
    private Activity context;

TextView roll,present,absent,marks;

   ArrayList<ResultHolder>object;

    public ResultAdapter(Activity context,ArrayList<ResultHolder>object) {
        this.context = context;
        this.object=object;

    }

    @Override
    public int getCount() {
        return object.size();
    }

    @Override
    public Object getItem(int position) {
        return object.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.singleresult, null, true);
        roll=rowView.findViewById(R.id.resultroll);
        present=rowView.findViewById(R.id.resultattendance);
        absent=rowView.findViewById(R.id.resultabsence);
        marks=rowView.findViewById(R.id.resultmarks);
        roll.setText(object.get(position).getRoll());
        present.setText(String.valueOf(object.get(position).getAttendence()));
        absent.setText(String.valueOf(object.get(position).getAbsence())+" %");
        if (object.get(position).getAbsence()<60){
            marks.setText("x");
        }else {
            marks.setText(String.valueOf(object.get(position).getMarks()));
        }


        return rowView;
    }
}
