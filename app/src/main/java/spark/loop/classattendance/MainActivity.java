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
import android.widget.EditText;
import android.widget.GridView;
import Adapters.CustomGrid;
import Databases.Information;
import Fragments.AddCourse;
import Fragments.AddStudent;
import Fragments.CalculateMarks;
import Fragments.DeleteCourse;
import Fragments.RemoveStudent;
import Interfaces.Backtrack;
public class MainActivity extends AppCompatActivity implements Backtrack {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GridView gridView;
    FragmentManager fragmentManager;
    Information information;
    CustomGrid customGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        information=new Information(this);
        drawerLayout = findViewById(R.id.drawerlayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setGridView();
        setNavigationView();

    }

    public void setGridView() {
        gridView = findViewById(R.id.maingrid);
        customGrid = new CustomGrid(this,information.getInformation());
        gridView.setAdapter(customGrid);


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

    public void addCourse(){
        Backtrack backtrack=this;
        AddCourse addCourse=new AddCourse(backtrack,this,information);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout,addCourse);
        transaction.addToBackStack("addcourse");
        transaction.commit();

    }
    public void addStudent(){
        AddStudent addStudent=new AddStudent(information);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout,addStudent);
        transaction.addToBackStack("addstudent");
        transaction.commit();

    }

    public void deleteCourse(){
        Backtrack backtrack=this;
        DeleteCourse deleteCourse=new DeleteCourse(information,backtrack);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout,deleteCourse);
        transaction.addToBackStack("deletecourse");
        transaction.commit();

    }
    public void removeStudent(){
        Backtrack backtrack=this;
        RemoveStudent removeStudent=new RemoveStudent(information,backtrack);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout,removeStudent);
        transaction.addToBackStack("removestudent");
        transaction.commit();

    }

    public void calculateMarks(){
        CalculateMarks calculateMarks=new CalculateMarks();
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.rootLayout,calculateMarks);
        transaction.addToBackStack("calculateMarks");
        transaction.commit();

    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            Clear();
        }
    }


    public void Clear(){
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

    public static void hideKeyboard( Activity activity ) {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService( Context.INPUT_METHOD_SERVICE );
        View f = activity.getCurrentFocus();
        if( null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom( f.getClass() ) )
            imm.hideSoftInputFromWindow( f.getWindowToken(), 0 );
        else
            activity.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
    }
}
