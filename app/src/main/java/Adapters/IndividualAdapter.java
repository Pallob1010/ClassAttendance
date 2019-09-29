package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import Model.Stateholder;
import spark.loop.classattendance.R;

public class IndividualAdapter extends BaseAdapter {

    ArrayList<Stateholder>state;
    private Activity context;
    ArrayList<String>object;

    public IndividualAdapter(Activity context,ArrayList<String>object, ArrayList<Stateholder>state) {

        this.context = context;
        this.state=state;
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
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.indsing, null, true);
        TextView Roll= rowView.findViewById(R.id.rollbox);
        final CheckBox CHBX = rowView.findViewById(R.id.checkBoxselect);

        if(state.get(position).isSelected()){
            CHBX.setChecked(true);
        }else {
            CHBX.setChecked(false);
        }
        Roll.setText(object.get(position));
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
