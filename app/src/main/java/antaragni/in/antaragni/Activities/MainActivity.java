package antaragni.in.antaragni.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.siyamed.shapeimageview.CircularImageView;

import antaragni.in.antaragni.Fragments.About_fragment;
import antaragni.in.antaragni.Fragments.Competitions;
import antaragni.in.antaragni.Fragments.ContactFragment;
import antaragni.in.antaragni.Fragments.GridFragment;
import antaragni.in.antaragni.Fragments.MainFragment;
import antaragni.in.antaragni.Fragments.Sponsors;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import rx.Scheduler;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
  FragmentManager fragmentManager;
  CircularImageView headerImage;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Intent i= getIntent();

    super.onCreate(savedInstanceState);
    fragmentManager=getSupportFragmentManager();
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if(i.getExtras()!=null) {
      String s = i.getExtras().get("starter").toString();
      if(s.equals("current")) {
        Fragment f = new ContactFragment();
        fragmentManager.beginTransaction()
            .add(R.id.content_main, f)
            .commit();
      }else {
        Fragment fragment= new MainFragment();
        fragmentManager.beginTransaction()
            .add(R.id.content_main,fragment)
            .addToBackStack(null)
            .commit();
      }

    }else {
      Fragment fragment= new MainFragment();
      fragmentManager.beginTransaction()
          .add(R.id.content_main,fragment)
          .addToBackStack(null)
          .commit();
    }

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Fragment f = new ContactFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, f)
                .addToBackStack(null).commit();

      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    Fragment f= new MainFragment();
    fragmentManager.beginTransaction()
            .replace(R.id.content_main,f)
            .commit();

  }

  @Override
  public void onFragmentInteraction(Uri uri){

  }

  public void openFragment(int id){
    if (id == R.id.nav_competitions) {
      Fragment f= Competitions.newInstance("comp","competitions");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .commit();

    } else if (id == R.id.nav_contact) {
      Fragment f= new ContactFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
         .commit();

    } else if (id == R.id.nav_Home) {
      Fragment f= new MainFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
              .commit();

    } else if (id == R.id.nav_schedule){
      Intent t = new Intent(MainActivity.this, SchedulerActivity.class);
      startActivity(t);
    }
    else if (id == R.id.nav_past_line) {
      Fragment f= GridFragment.newInstance("past","pastline");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
         .commit();

    } else if (id == R.id.nav_sponsors) {
      Fragment f= new Sponsors();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
.commit();

    }else if (id== R.id.nav_about)
    {    Fragment f= new About_fragment();
      fragmentManager.beginTransaction()
              .replace(R.id.content_main,f)
              .commit();


    }
    else if (id==R.id.nav_current_line){
      Intent t = new Intent(MainActivity.this, CurrentLine.class);
      startActivity(t);
    }
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    Drawable image;
    openFragment(id);
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

}
