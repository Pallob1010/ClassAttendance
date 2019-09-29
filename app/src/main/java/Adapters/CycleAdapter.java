package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import spark.loop.classattendance.R;

public class CycleAdapter extends BaseAdapter {

    ArrayList<String> cyclelist;
    Context context;

    public CycleAdapter(Context context, ArrayList<String> cyclelist) {
        this.context = context;
        this.cyclelist = cyclelist;
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

        TextView block;

        public ViewHolder(View v) {

            block = v.findViewById(R.id.cycleid);
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

        holder.block.setText(cyclelist.get(position));
        return row;
    }

}
