package antaragni.in.antaragni.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.json.JSONObject;

import java.net.URL;

import antaragni.in.antaragni.Fragments.Competitions;
import antaragni.in.antaragni.Fragments.GridFragment;
import antaragni.in.antaragni.Fragments.MainFragment;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {
  FragmentManager fragmentManager;
  CircularImageView headerImage;
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

    final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    headerImage=(CircularImageView)(navigationView.getHeaderView(0).findViewById(R.id.imageView));
    Bundle params = new Bundle();
    params.putString("fields", "id,email,gender,cover,picture.type(large)");
    new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
        new GraphRequest.Callback() {
          @Override
          public void onCompleted(GraphResponse response) {
            if (response != null) {
              try {
                JSONObject data = response.getJSONObject();
                if (data.has("picture")) {
                  String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                  RetrievePic conn=new RetrievePic();
                  conn.execute(profilePicUrl);
                }
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
          }
        }).executeAsync();
  }

  class RetrievePic extends AsyncTask<String, Void, Bitmap> {

    private Exception exception;

    protected Bitmap doInBackground(String... urls) {
      Bitmap bitmap;
      try {
        URL facebookProfileURL= new URL(urls[0]);
        bitmap = BitmapFactory.decodeStream(facebookProfileURL.openConnection().getInputStream());
      } catch (Exception e) {
        this.exception = e;
        bitmap=null;
      }
      return bitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
      headerImage.setImageBitmap(bitmap);
    }
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
      Fragment f= Competitions.newInstance("comp","competitions");
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
      Fragment f= GridFragment.newInstance("past","pastline");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    } else if (id == R.id.nav_sponsors) {
      Fragment f= GridFragment.newInstance("sponsors","sponsors");
      fragmentManager.beginTransaction()
          .replace(R.id.content_main,f)
          .addToBackStack(null).commit();

    }else if (id== R.id.nav_log)
    {
      LoginManager.getInstance().logOut();
      Intent t = new Intent(MainActivity.this, Login.class);
      startActivity(t);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

}
