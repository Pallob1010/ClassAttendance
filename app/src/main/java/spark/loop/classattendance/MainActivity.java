package spark.loop.classattendance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.GridView;
import java.util.ArrayList;
import Adapters.CustomGrid;
import Databases.Information;
import Fragments.AddCourse;
import Fragments.AddStudent;
import Fragments.CalculateMarks;
import Day.CycleOptions;
import Fragments.DeleteCourse;
import Fragments.DeleteIndividual;
import Fragments.RemoveStudent;
import Day.TablayoutCaller;
import Interfaces.Backtrack;
import Interfaces.ReverseCaller;
import Model.CourseDetails;


public class MainActivity extends AppCompatActivity implements Backtrack, ReverseCaller, AdapterView.OnItemClickListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GridView gridView;
    FragmentManager fragmentManager;
    Information information;
    CustomGrid customGrid;
    ArrayList<CourseDetails> object;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        information = new Information(this);
        drawerLayout = findViewById(R.id.drawerlayout);
        object=new ArrayList<>();
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setGridView();
        setNavigationView();

    }

    public void setGridView() {
        object = information.getInformation();
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
        dialog.getWindow().setGravity(Gravity.CENTER);
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
        AddStudent addStudent = new AddStudent(information,backtrack,this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, addStudent);
        transaction.addToBackStack("addstudent");
        transaction.commit();

    }

    public void deleteCourse() {
        Backtrack backtrack = this;
        DeleteCourse deleteCourse = new DeleteCourse(information, backtrack,this);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, deleteCourse);
        transaction.addToBackStack("deletecourse");
        transaction.commit();

    }

    public void removeStudent() {
        ReverseCaller reverseCaller = this;
        RemoveStudent removeStudent = new RemoveStudent(information, reverseCaller);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, removeStudent);
        transaction.addToBackStack("removestudent");
        transaction.commit();

    }

    public void calculateMarks() {
        CalculateMarks calculateMarks = new CalculateMarks();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, calculateMarks);
        transaction.addToBackStack("calculateMarks");
        transaction.commit();

    }

    public void cycleOptions(String series, String section, String course) {
        ReverseCaller caller=this;
        CycleOptions cycleOptions = new CycleOptions(series, section, course,caller);
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
    public void callreverse(String series, String section, String course) {
        Backtrack backtrack = this;
        DeleteIndividual individual = new DeleteIndividual(series, section, course, backtrack,this);
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
        TablayoutCaller tablayoutCaller=new TablayoutCaller(series,section,course,cycle);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout, tablayoutCaller);
        transaction.addToBackStack("tablayoutcaller");
        transaction.commit();


    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

}
