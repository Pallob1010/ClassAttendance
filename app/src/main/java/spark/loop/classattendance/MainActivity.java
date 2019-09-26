package spark.loop.classattendance;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import java.util.ArrayList;
import Adapters.CustomGrid;
import Model.CourseDetails;

public class MainActivity extends AppCompatActivity {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        ArrayList<CourseDetails> details = new ArrayList<>();
        details.add(new CourseDetails("CSE-107", "12", "A"));
        details.add(new CourseDetails("CSE-107", "12", "B"));
        details.add(new CourseDetails("CSE-107", "12", "C"));
        CustomGrid customGrid = new CustomGrid(this, details);
        gridView.setAdapter(customGrid);

    }

    public void setNavigationView() {
        navigationView = findViewById(R.id.navigatonview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.addcourse:
                        break;
                    case R.id.addstudent:
                        break;
                    case R.id.removestudent:
                        break;
                    case R.id.deletecourse:
                        break;
                    case R.id.calculatemarks:
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


}
