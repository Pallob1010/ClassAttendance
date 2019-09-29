package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import Model.CourseDetails;
import Model.Stateholder;
import spark.loop.classattendance.R;

public class RemoveAdapter extends BaseAdapter {
    ArrayList<CourseDetails> object;
    ArrayList<Stateholder>state;
    private Activity context;

    public RemoveAdapter(Activity context, ArrayList<CourseDetails> object, ArrayList<Stateholder>state) {
        this.object = object;
        this.context = context;
        this.state=state;
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
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.deletesingle, null, true);
        TextView Course= rowView.findViewById(R.id.cou);
        TextView Series = rowView.findViewById(R.id.ser);
        TextView Section = rowView.findViewById(R.id.sec);
        final CheckBox CHBX = rowView.findViewById(R.id.selectbox);

        if(state.get(position).isSelected()){
            CHBX.setChecked(true);
        }else {
            CHBX.setChecked(false);
        }

        Course.setText(object.get(position).getCourseCode().toUpperCase());
        Series.setText(object.get(position).getSeries());
        Section.setText(object.get(position).getSection().toUpperCase());

        CHBX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {

                    for (int i=0;i<state.size();i++){
                            state.get(i).setSelected(false);
                    }
                    state.get(position).setSelected(true);
                    CHBX.setChecked(true);
                    notifyDataSetChanged();

                }
            }
        });


        return rowView;
    }
}
