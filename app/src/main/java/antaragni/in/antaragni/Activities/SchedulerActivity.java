package antaragni.in.antaragni.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import antaragni.in.antaragni.Adapters.MyAdapter;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.Fragments.About_fragment;
import antaragni.in.antaragni.Fragments.Competitions;
import antaragni.in.antaragni.Fragments.ContactFragment;
import antaragni.in.antaragni.Fragments.GridFragment;
import antaragni.in.antaragni.Fragments.MainFragment;
import antaragni.in.antaragni.Fragments.Sponsors;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import antaragni.in.antaragni.serverFields.scheduleparser;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SchedulerActivity extends AppCompatActivity {


  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;
  FragmentManager fragmentManager;
  private CompositeSubscription mSubscriptions;
  public HashMap<String,ArrayList<scheduleparser>> mDataSet;
  private DataManager mDataManager;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    setContentView(R.layout.activity_scheduler);
    fragmentManager=getSupportFragmentManager();
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
    tabLayout.setupWithViewPager(mViewPager);
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mViewPager.setAdapter(mSectionsPagerAdapter);
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent t = new Intent(SchedulerActivity.this, MainActivity.class);
        t.putExtra("starter","current");
        startActivity(t);
      }
    });

  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // handle arrow click here
    if (item.getItemId() == android.R.id.home) {
      Intent i = new Intent(SchedulerActivity.this,MainActivity.class);
      startActivity(i);
      // close this activity and return to preview activity (if there is any)
    }

    return super.onOptionsItemSelected(item);
  }
  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private CompositeSubscription mSubscriptions;
    public ArrayList<scheduleparser> mDataset;
    public int day;
    private DataManager mDataManager;

    public PlaceholderFragment() {
    }

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      if (getArguments() != null)
        day = getArguments().getInt(ARG_SECTION_NUMBER);

      Log.v("helllo", "my day is " + day);
      mAdapter = new MyAdapter(null);
      RetrofitAddOn retrofitAddOn = RetrofitAddOn.getInstance(getActivity().getApplicationContext());
      mDataManager = new DataManager(getActivity().getApplicationContext());
      mSubscriptions=new CompositeSubscription();
      mDataManager.mService = retrofitAddOn.newUserService();
      loadData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_current_line, container, false);


      mRecyclerView = (RecyclerView) rootView.findViewById(R.id.current_line_recycler);

      mRecyclerView.setHasFixedSize(true);

      // use a linear layout manager
      mLayoutManager = new LinearLayoutManager(getActivity());
      mRecyclerView.setLayoutManager(mLayoutManager);
      mRecyclerView.setAdapter(mAdapter);
      return rootView;
    }

    public void loadData() {
      mSubscriptions.add(mDataManager.getSchedule(""+day)
          .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<ArrayList<scheduleparser>>() {
            @Override
            public void onCompleted() {
              Log.v("heloo", "get is successssssss@@@@@@@@@@@@2");
            }

            @Override
            public void onError(Throwable e) {
              // cast to retrofit.HttpException to get the response code
              if (e instanceof HttpException) {
                HttpException response = (HttpException) e;
                int code = response.code();
              }
            }

            @Override
            public void onNext(ArrayList<scheduleparser> list) {
              mDataset=list;
              mAdapter.mDataset=mDataset;
              mAdapter.notifyDataSetChanged();
            }
          }));
    }
    @Override
    public void onDestroy() {
      this.mSubscriptions.unsubscribe();
      super.onDestroy();
    }

  }

  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
      // Show 4 total pages.
      return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      switch (position) {
        case 0:
          return "Day 1";
        case 1:
          return "Day 2";
        case 2:
          return "Day 3";
        case 3:
          return "Day 4";
      }
      return null;
    }
  }
}
