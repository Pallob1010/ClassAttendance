package Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Databases.DatabaseAday;
import Databases.DatabaseDday;
import spark.loop.classattendance.R;


public class CustomListD extends ArrayAdapter<String> {


    ArrayList<String> object;
    ArrayList<String> State;

    Context context;
    DatabaseDday day;
    String series, section, course, cycle;

    public CustomListD(Context context, ArrayList<String> object,ArrayList<String>State, String series, String section, String course, String cycle) {
        super(context, R.layout.singlerecycler, object);
        day = new DatabaseDday(context);
        this.object = object;
        this.series = series;
        this.section = section;
        this.course = course;
        this.cycle = cycle;
        this.State=State;
        this.context=context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.singlerecycler, null, true);
        final CheckBox checkBox1 = convertView.findViewById(R.id.chbx_p);
        TextView textView = convertView.findViewById(R.id.roll_id);

        String S = "";
        textView.setText(object.get(position));

        try {
            S = State.get(position);
        } catch (Exception e) {
        }

        if (S.equals("false")) {

            checkBox1.setChecked(true);
        } else {
            checkBox1.setChecked(false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (State.get(position).equals("true")) {
                    checkBox1.setChecked(true);
                    day.updateState(course, series, section, cycle, "D", object.get(position), "false");
                    StateUpdate();
                } else {
                    checkBox1.setChecked(false);
                    day.updateState(course, series, section, cycle, "D", object.get(position), "true");
                    StateUpdate();
                }


            }
        });
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    day.updateState(course, series, section, cycle, "D", object.get(position), "false");
                    StateUpdate();

                }else {
                    day.updateState(course, series, section, cycle, "D", object.get(position), "true");
                    StateUpdate();
                }
            }
        });
        return convertView;
    }


    public void StateUpdate() {
        State=day.getState(series,section,course,cycle,"D");
    }
}
