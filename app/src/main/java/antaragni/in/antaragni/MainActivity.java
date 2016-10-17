package antaragni.in.antaragni;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,OnFragmentInteractionListener{
  FragmentManager fragmentManager;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    Fragment fragment= new MainFragment();
    fragmentManager=getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .add(R.id.content_main,fragment)
        .addToBackStack(null)
        .commit();
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  @Override
  public void onFragmentInteraction(Uri uri){

  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();
    Drawable image;
    if (id == R.id.nav_competitions) {
      Fragment f= GridFragment.newInstance("comp");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_contact) {
      Fragment f= new MainFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_Home) {
      Fragment f= new MainFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_past_line) {
      Fragment f= GridFragment.newInstance("past");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_register) {


    } else if (id == R.id.nav_sponsors) {
      Fragment f= new MainFragment();
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    }
    else if (id == R.id.nav_map) {
       MapFragment fragment= new MapFragment();
      fragmentManager.beginTransaction()
              .replace(R.id.content_main,fragment)
              .addToBackStack(null).commit();

    }
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
