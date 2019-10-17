package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import spark.loop.classattendance.R;


public class FileAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String>fileDirectory;
    ArrayList<Integer>dirIcon;

    public FileAdapter(@NonNull Context context, ArrayList<String>fileDirectory,ArrayList<Integer>dirIcon) {
        super(context, R.layout.filedirrow,fileDirectory);
        this.context=context;
        this.fileDirectory=fileDirectory;
        this.dirIcon=dirIcon;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=layoutInflater.inflate(R.layout.filedirrow,null,true);
        ImageView imageView=convertView.findViewById(R.id.img);
        TextView textView=convertView.findViewById(R.id.foldertext);
        imageView.setBackgroundResource(dirIcon.get(position));
        textView.setText(fileDirectory.get(position));

        return convertView;
    }
}
