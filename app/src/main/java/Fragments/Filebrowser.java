package Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Adapters.FileAdapter;
import Databases.SharedPreference;
import Interfaces.ReverseCaller;
import Model.ResultHolder;
import spark.loop.classattendance.R;

@SuppressLint("ValidFragment")
public class Filebrowser extends Fragment implements View.OnClickListener {
    int count = 0;
    Context context;
    View view;
    String series, section, course, initialPath, folder, mypath, FileName;
    ArrayList<File> fileobject;
    File test, file, ob;
    int totalclass;
    ArrayList<ResultHolder> object;
    ListView listView;
    Button Home, NewFolder, FileBack, Save, Cancel;
    EditText editText;
    ReverseCaller caller;
    SharedPreference preference;

    public Filebrowser(Context context, String series, String section, String course, int totalclass, ArrayList<ResultHolder> object, ReverseCaller caller) {

        this.context = context;
        this.section = section;
        this.series = series;
        this.totalclass = totalclass;
        this.course = course;
        this.object = object;
        this.caller = caller;
        initialPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        file = new File(initialPath);
        fileobject = new ArrayList<>();
        preference=new SharedPreference(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filebrowser, container, false);
        listView = view.findViewById(R.id.filedirlist);
        Home = view.findViewById(R.id.filehome);
        NewFolder = view.findViewById(R.id.newfolder);
        FileBack = view.findViewById(R.id.fileback);
        Save = view.findViewById(R.id.filesave);
        Cancel = view.findViewById(R.id.filecancel);
        editText = view.findViewById(R.id.filename);
        Home.setOnClickListener(this);
        NewFolder.setOnClickListener(this);
        FileBack.setOnClickListener(this);
        Save.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        fileobject.add(0, file);
        ListViewer(fileobject.get(0));
        FileBack.setEnabled(false);
        return view;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.filehome:
                try {
                    count = 0;
                    File file1 = new File(initialPath);
                    fileobject.clear();
                    fileobject.add(file1);
                    ListViewer(fileobject.get(0));
                } catch (Exception e) {

                }
                break;
            case R.id.newfolder:

                if (count == 0) {
                    NewFolder(file);
                } else {
                    NewFolder(fileobject.get(count));

                }

                break;
            case R.id.fileback:
                try {
                    count--;
                    if (count == 0) {
                        File file = new File(initialPath);
                        ListViewer(file);
                        FileBack.setEnabled(false);
                    } else {
                        ListViewer(fileobject.get(count));
                        fileobject.remove(count);
                        FileBack.setEnabled(true);

                    }
                } catch (Exception e) {

                }
                break;

            case R.id.filesave:

                FileName = editText.getText().toString() + ".pdf";
                if (count == 0) {
                    SaverAsync async = new SaverAsync(file, FileName);
                    async.execute();

                } else {

                    ob = fileobject.get(count);
                    test = new File(ob.getAbsolutePath(), FileName);
                    SaverAsync async = new SaverAsync(test, FileName);
                    async.execute();
                }
                Toast.makeText(context, "Save Successfully", Toast.LENGTH_SHORT).show();
                caller.Back();

                break;

            case R.id.filecancel:
                caller.Back();

                break;
        }

    }


    public void ListViewer(File reciever) {

        final File files[] = reciever.listFiles();
        ArrayList<Integer> DirIcon = new ArrayList<>();
        final ArrayList<String> Directory = new ArrayList<>();
        final ArrayList<String> Directorypath = new ArrayList<>();
        final ArrayList<String> temdir = new ArrayList<>();
        final ArrayList<String> tempfile = new ArrayList<>();
        final ArrayList<String> temdirpath = new ArrayList<>();
        final ArrayList<String> tempfilepath = new ArrayList<>();


        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                temdir.add(files[i].getName());
                temdirpath.add(files[i].getAbsolutePath());


            } else if (files[i].getName().contains(".pdf")) {
                tempfile.add(files[i].getName());
                tempfilepath.add(files[i].getAbsolutePath());
            } else {
                continue;
            }
        }
        for (int i = 0; i < temdir.size(); i++) {
            Directory.add(temdir.get(i));
            DirIcon.add(R.drawable.folder);
            Directorypath.add(temdirpath.get(i));

        }
        for (int i = 0; i < tempfile.size(); i++) {
            Directory.add(tempfile.get(i));
            DirIcon.add(R.drawable.pdffile);
            Directorypath.add(tempfilepath.get(i));

        }


        FileAdapter adapter = new FileAdapter(getContext(), Directory, DirIcon);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (Directory.get(position).contains(".pdf") | Directory.get(position).contains(".doc") | Directory.get(position).contains(".docx") | Directory.get(position).contains(".txt")) {


                } else {
                    try {
                        File f = new File(Directorypath.get(position));
                        FileBack.setEnabled(true);
                        count++;
                        fileobject.add(count, f);
                        ListViewer(f);


                    } catch (Exception e) {

                    }

                }

            }
        });


    }

    public void NewFolder(final File file) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.folderoption);
        dialog.setCancelable(false);
        dialog.show();
        Button create = dialog.findViewById(R.id.foldercreate);
        Button cancel = dialog.findViewById(R.id.foldercancel);
        final EditText foldername = dialog.findViewById(R.id.foldername);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folder = foldername.getText().toString();
                mypath = file.getAbsolutePath();
                mypath = mypath + File.separator + folder;
                final File f = new File(mypath);
                if (!f.exists()) {
                    f.mkdir();
                    ListViewer(file);
                    Toast.makeText(context, "Folder Created", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("\tFollowing Folder name exist.\n\tDo you want to override??");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Override", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            f.delete();
                            f.mkdir();
                            ListViewer(file);
                            Toast.makeText(context, " Folder Created", Toast.LENGTH_SHORT).show();
                            dialog1.cancel();
                            dialog.dismiss();


                        }
                    });
                    builder.setNegativeButton("Rename", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            dialog1.cancel();
                        }
                    });

                    builder.show();

                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    public void PdfSaverMethod(final File fil, String FeName) {
        File f = null;
        Document document;
        Paragraph university,department;
        String[] phraseHead={"Series\n"+series,"Section\n"+section,"Course\n"+course,"Total Classes\n"+String.valueOf(totalclass)};
        String[] phrase={"Roll","Attended","Percentage","Marks"};
        PdfPCell cell,cell1;
        if (fil.getAbsolutePath().endsWith(".pdf")) {
            f = new File(fil.getAbsolutePath());
        } else {
            f = new File(fil.getAbsolutePath(), FeName);
        }
        document = new Document();


        try {

            FileOutputStream fOut = new FileOutputStream(f);
            PdfWriter.getInstance(document, fOut);

            PdfPTable table = new PdfPTable(4);
            table.setWidths(new float[]{1, 1, 1, 1});

            PdfPTable header=new PdfPTable(4);
            header.setWidths(new float[]{1,1,1,1});

            PdfPTable footer=new PdfPTable(3);
            footer.setWidths(new float[]{1,1,1});

            university=new Paragraph("Rajshahi University Of Engineering & Technology");
            university.setAlignment(Paragraph.ALIGN_CENTER);

            department=new Paragraph("Department of Computer Science & Engineering");
            department.setAlignment(Paragraph.ALIGN_CENTER);

            String myst="    ";
            for (int m=0;m<9;m++){
                if(m==3){
                    myst=preference.getUserName()+"\n"+preference.getUserDesignation();
                }else if (m==5){
                    Date date= Calendar.getInstance().getTime();
                    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                    myst=format.format(date);

                }else if(m==6){
                    myst="....................";
                }
                cell=new PdfPCell(new Phrase(myst));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(15);
                cell.setBorder(Rectangle.NO_BORDER);
                footer.addCell(cell);
                myst="     ";

            }





            for(int j=0;j<4;j++){
                cell=new PdfPCell(new Phrase(phraseHead[j]));
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(15);
                cell.setBorder(Rectangle.NO_BORDER);
                header.addCell(cell);

                cell1= new PdfPCell(new Phrase(phrase[j]));
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setPadding(10);
                table.addCell(cell1);

            }

            for (int i = 0; i < object.size(); i++) {



                cell1 = new PdfPCell(new Phrase(object.get(i).getRoll()));
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setPadding(4);
                table.addCell(cell1);

                cell1 = new PdfPCell(new Phrase(String.valueOf(object.get(i).getAttendence())));
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setPadding(4);
                table.addCell(cell1);

                cell1 = new PdfPCell(new Phrase(String.valueOf(object.get(i).getAbsence())));
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setPadding(4);
                table.addCell(cell1);

                if(object.get(i).getAbsence()<60){
                    cell1 = new PdfPCell(new Phrase("x"));
                }else {
                    cell1 = new PdfPCell(new Phrase(String.valueOf(object.get(i).getMarks())));
                }
                cell1.setVerticalAlignment(Element.ALIGN_CENTER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setPadding(4);
                table.addCell(cell1);
            }

            document.open();
            document.add(university);
            document.add(department);
            document.add(header);
            document.add(table);
            document.add(footer);

        } catch (DocumentException de) {


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


    }

    public class SaverAsync extends AsyncTask<String, String, String> {


        File fp;
        String filename;

        public SaverAsync(File f, String filename) {

            this.fp = f;
            this.filename = filename;


        }

        @Override
        protected String doInBackground(String... strings) {
            PdfSaverMethod(fp, filename);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }


}
