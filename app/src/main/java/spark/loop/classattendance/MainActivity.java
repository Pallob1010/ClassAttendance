package spark.loop.classattendance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.CustomGrid;
import Adapters.DialogGrid;
import Databases.DatabaseAday;
import Databases.DatabaseBday;
import Databases.DatabaseCday;
import Databases.DatabaseDday;
import Databases.DatabaseEday;
import Databases.Information;
import Databases.SharedPreference;
import Day.CycleOptions;
import Day.TablayoutCaller;
import Fragments.AddCourse;
import Fragments.AddStudent;
import Fragments.CalculateMarks;
import Fragments.DeleteCourse;
import Fragments.DeleteIndividual;
import Fragments.Filebrowser;
import Fragments.RemoveStudent;
import Fragments.Result;
import Interfaces.Backtrack;
import Interfaces.ReverseCaller;
import Model.CourseDetails;
import Model.Restore;
import Model.ResultHolder;
import Networking.ApiClient;
import Networking.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements Backtrack, ReverseCaller, AdapterView.OnItemClickListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GridView gridView;
    FragmentManager fragmentManager;
    Information information;
    CustomGrid customGrid;
    ArrayList<CourseDetails> object;
    String fseries, fsection, fcourse;
    ArrayList<ResultHolder> fobject = new ArrayList<>();
    int total;
    SharedPreference preference;
    Dialog dialog;
    TextView Updateprogress;
    DatabaseAday aday;
    DatabaseBday bday;
    DatabaseCday cday;
    DatabaseDday dday;
    DatabaseEday eday;
    TextView SyncTime;
    String[] cycles = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th"};

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        information = new Information(this);
        preference = new SharedPreference(this);
        preference.saveData("Zaman","01745914445","1234","gs");
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerlayout);
        object = new ArrayList<>();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setGridView();
        setNavigationView();

    }

    public void setGridView() {
        object = information.getInformation(preference.getNumber());
        gridView = findViewById(R.id.maingrid);
        customGrid = new CustomGrid(this, object);
        gridView.setAdapter(customGrid);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cycleOptions(object.get(position).getSeries(), object.get(position).getSection(), object.get(position).getCourseCode());
    }

    public void setNavigationView() {
        navigationView = findViewById(R.id.navigatonview);
        final SharedPreference preference = new SharedPreference(this);
        View headerlayout = navigationView.getHeaderView(0);
        Button Backup = headerlayout.findViewById(R.id.syncButton);
        TextView Name = headerlayout.findViewById(R.id.name);
        SyncTime= headerlayout.findViewById(R.id.synctime);
        Button Restore = headerlayout.findViewById(R.id.restoreButton);
        Name.setText("Welcome\n" + preference.getUserName());
        SyncTime.setText("Last Sync\n"+preference.getLastSyncTime());

        Backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (information.hasData()) {
                   // BackSync backSync = new BackSync();
                    //backSync.execute();
                } else {
                    Toast.makeText(MainActivity.this, "Nothing to sync!!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Restore(preference.getNumber());
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addcourse:
                        addCourse();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.addstudent:
                        addStudent();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.removestudent:
                        removeStudent();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.deletecourse:
                        deleteCourse();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.calculatemarks:
                        calculateMarks();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.aboutme:
                        aboutMe();
                        break;
                    case R.id.exit:
                        finish();
                        break;
                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void aboutMe() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setGravity(Gravity.BOTTOM | Gravity.LEFT);
        dialog.setContentView(R.layout.about);
        dialog.show();

    }

    public void addCourse() {
        Backtrack backtrack = this;
        AddCourse addCourse = new AddCourse(backtrack, this, information);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, addCourse);
        transaction.addToBackStack("addcourse");
        transaction.commit();

    }

    public void addStudent() {
        Backtrack backtrack = this;
        AddStudent addStudent = new AddStudent(information, backtrack, this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, addStudent);
        transaction.addToBackStack("addstudent");
        transaction.commit();

    }

    public void deleteCourse() {
        Backtrack backtrack = this;
        DeleteCourse deleteCourse = new DeleteCourse(information, backtrack, this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, deleteCourse);
        transaction.addToBackStack("deletecourse");
        transaction.commit();

    }

    public void removeStudent() {
        ReverseCaller reverseCaller = this;
        RemoveStudent removeStudent = new RemoveStudent(information, this,reverseCaller);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, removeStudent);
        transaction.addToBackStack("removestudent");
        transaction.commit();

    }

    public void calculateMarks() {
        Backtrack backtrack = this;
        CalculateMarks calculateMarks = new CalculateMarks(information, backtrack, this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, calculateMarks);
        transaction.addToBackStack("calculateMarks");
        transaction.commit();

    }

    public void cycleOptions(String series, String section, String course) {
        ReverseCaller caller = this;
        CycleOptions cycleOptions = new CycleOptions(series, section, course, caller);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, cycleOptions);
        transaction.addToBackStack("CycleOptions");
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public void Clear() {
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void Helper() {
        setGridView();
        hideKeyboard(this);
        Clear();

    }

    @Override
    public void Invisible() {

        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount() - 1; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void result(String series, String section, String course, int marks) {
        Clear();
        Backtrack backtrack = this;
        Result result = new Result(series, section, course, marks, this, backtrack);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, result);
        transaction.addToBackStack("result");
        transaction.commit();
    }

    @Override
    public void filebrowser(String series, String section, String course, ArrayList<ResultHolder> object, int totalclass) {

        this.fseries = series;
        this.fsection = section;
        this.fcourse = course;
        this.fobject = object;
        this.total = totalclass;

        if (canRead() & canWrite()) {
            myfunc(series, section, course, object, totalclass);

        } else if (!canRead() & canWrite()) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE}, 1);
        } else if (canRead() & !canWrite()) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, 3);
        }

    }

    @Override
    public void Back() {
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void callreverse(String series, String section, String course) {
        Backtrack backtrack = this;
        DeleteIndividual individual = new DeleteIndividual(series, section, course, backtrack, this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, individual);
        transaction.addToBackStack("individual");
        transaction.commit();

    }

    @Override
    public void helper() {
        setGridView();
        Clear();
    }

    @Override
    public void tablayoutcaller(String series, String section, String course, String cycle) {
        TablayoutCaller tablayoutCaller = new TablayoutCaller(series, section, course, cycle);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, tablayoutCaller);
        transaction.addToBackStack("tablayoutcaller");
        transaction.commit();


    }

    public boolean canRead() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    public boolean canWrite() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myfunc(fseries, fsection, fcourse, fobject, total);
                }
                break;
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myfunc(fseries, fsection, fcourse, fobject, total);
                }

                break;
            case 3:
                boolean read = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean write = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (read && write) {
                    myfunc(fseries, fsection, fcourse, fobject, total);
                } else {
                    Toast.makeText(this, "Required All Permissions", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void myfunc(String series, String section, String course, ArrayList<ResultHolder> object, int totalclass) {
        ReverseCaller caller = this;
        Filebrowser filebrowser = new Filebrowser(this, series, section, course, totalclass, object, caller);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, filebrowser);
        transaction.addToBackStack("filebrowser");
        transaction.commit();
    }

    public void Restore(String number) {


        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<ArrayList<Restore>> call = service.restoreData(number);
        call.enqueue(new Callback<ArrayList<Restore>>() {
            @Override
            public void onResponse(Call<ArrayList<Restore>> call, Response<ArrayList<Restore>> response) {
                BackupDialog(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Restore>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check Internet Connection!!!", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void BackupDialog(final ArrayList<Restore> ob) {
        final Dialog mydialog = new Dialog(this);
        mydialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mydialog.setContentView(R.layout.dialoggrid);
        mydialog.show();
        GridView gridView1 = mydialog.findViewById(R.id.maingriddialog);
        DialogGrid dialogGrid = new DialogGrid(this, ob);
        gridView1.setAdapter(dialogGrid);

        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s1, s2, s3, s4, s5, s6, s7;
                s1 = ob.get(position).getSeries();
                s2 = ob.get(position).getSection();
                s3 = ob.get(position).getCourse();
                s4 = ob.get(position).getFirstRoll();
                s5 = ob.get(position).getTotalStudents();
                s6 = ob.get(position).getAttendanceMarks();
                s7 = ob.get(position).getNumber();
             //   information.insertValues(s1, s2, s3, s4, s5, s6,s7);
                mydialog.dismiss();
                setGridView();
                callWeb(s3 + s1 + s2 + s7);

            }
        });


    }

    public void callWeb(String Id) {
        Toast.makeText(this, Id, Toast.LENGTH_SHORT).show();

    }

    public void send(JsonArray Id) {

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<String> call = service.sendToweb(Id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              if (response.isSuccessful()){
                  Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_LONG).show();
                  preference.saveSynctime(response.body());
                  SyncTime.setText("Last Sync\n"+preference.getLastSyncTime());
              }
              else
              {
                  Toast.makeText(MainActivity.this, "failure", Toast.LENGTH_SHORT).show();
              }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });



    }

    public void SendInformation(JsonArray array){

        ApiService service = ApiClient.getClient().create(ApiService.class);
        Call<String>call=service.InformationSend(array);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }

    public class BackSync extends AsyncTask<String, String, String> {
        Information informationob;
        JsonArray All,inform;

        @Override
        protected void onPreExecute() {
            aday = new DatabaseAday(MainActivity.this);
            bday = new DatabaseBday(MainActivity.this);
            cday = new DatabaseCday(MainActivity.this);
            dday = new DatabaseDday(MainActivity.this);
            eday = new DatabaseEday(MainActivity.this);
            informationob = new Information(MainActivity.this);
            All=new JsonArray();
            inform=new JsonArray();
        }

        @Override
        protected String doInBackground(String... strings) {

            All.add(aday.getAllId());
            All.add(aday.getAllRoll());
            All.add(aday.getAllCycle());
            All.add(aday.getAllState());
            All.add(bday.getAllState());
            All.add(cday.getAllState());
            All.add(dday.getAllState());
            All.add(eday.getAllState());
            inform=informationob.getinfo();
            SendInformation(inform);
            send(All);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }


}
