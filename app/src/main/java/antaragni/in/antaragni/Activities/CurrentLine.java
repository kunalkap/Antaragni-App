package antaragni.in.antaragni.Activities;

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
import android.webkit.WebSettings;
import android.webkit.WebView;

import antaragni.in.antaragni.Fragments.CurrentLineFragment;
import antaragni.in.antaragni.Fragments.GridFragment;
import antaragni.in.antaragni.Fragments.MainFragment;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;

public class CurrentLine extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

  FragmentManager fragmentManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_current_line);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    WebView myWebView = (WebView) findViewById(R.id.webview);
    myWebView.loadUrl("https://www.antaragni.in/currentLineUp_app");
    WebSettings webSettings = myWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);

    fragmentManager=getSupportFragmentManager();
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
    getMenuInflater().inflate(R.menu.current_line, menu);
    return true;
  }


  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_india_haat) {
      Fragment f= CurrentLineFragment.newInstance("indiaHaat","haat");
      fragmentManager.beginTransaction()
          .replace(R.id.content_current_line,f)
          .commit();
    } else if (id == R.id.nav_india_inspired) {
      Fragment f= CurrentLineFragment.newInstance("indiaInspired","out");
      fragmentManager.beginTransaction()
          .replace(R.id.content_current_line,f)
          .commit();

    } else if (id == R.id.nav_classical) {
      Fragment f= CurrentLineFragment.newInstance("classical","classical");
      fragmentManager.beginTransaction()
          .replace(R.id.content_current_line,f)
          .commit();
    } else if (id == R.id.nav_international) {
      Fragment f= CurrentLineFragment.newInstance("carnival","inter");
      fragmentManager.beginTransaction()
          .replace(R.id.content_current_line,f)
          .commit();
    } else if (id == R.id.nav_kavi) {
      Fragment f= CurrentLineFragment.newInstance("kavi","kavi");
      fragmentManager.beginTransaction()
          .replace(R.id.content_current_line,f)
          .commit();
    }else if (id == R.id.nav_Home) {
      Fragment f= new MainFragment();
      fragmentManager.beginTransaction()
              .replace(R.id.content_main,f)
              .commit();

    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
  @Override
  public void onFragmentInteraction(Uri uri){

  }
}
