package antaragni.in.antaragni.Fragments;


import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.AntaragniApplication;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.DataService;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Competitions extends Fragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String TAB_PARAM = "param2";
  private String mParam1;
  private OnFragmentInteractionListener mListener;
  private String tab;
  private Drawable mDrawable;
  private CompositeSubscription mSubscriptions;
  private DataManager mDataManager;
  public ListView listview;
  public ArrayList<Category> eventsList;
  public ImageAdapter recylclerAdapter;
  public static Competitions newInstance(String param1,String param2) {
    Competitions fragment = new Competitions();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(TAB_PARAM,param2);
    fragment.setArguments(args);
    return fragment;
  }

  public Competitions() {
    // Required empty public constructor
  }
  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      tab=getArguments().getString(TAB_PARAM);
    }
    mSubscriptions=new CompositeSubscription();
    RetrofitAddOn retrofitAddOn= RetrofitAddOn.getInstance(getActivity().getApplicationContext());
    mDataManager=new DataManager(getActivity().getApplicationContext());
    mDataManager.mService=retrofitAddOn.newUserService();
    loadData();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_competitions, container, false);
    ListView listview = (ListView) v.findViewById(R.id.category);
    recylclerAdapter=new ImageAdapter(mDrawable,eventsList,getActivity(),"competitions");
    listview.setAdapter(recylclerAdapter);
    setImage();
    return v;
  }
  private void setImage(){
      mDrawable=getResources().getDrawable(R.drawable.comp1);
  }
  public void loadData() {
    mSubscriptions.add(mDataManager.allEvents()
        .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArrayList<Category>>() {
          @Override
          public void onCompleted() {
            Log.v("heloo","get is successssssss@@@@@@@@@@@@2");
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
          public void onNext(ArrayList<Category> list) {
            eventsList=list;
            Log.v("heloo",""+eventsList.get(0).getSubevents().get(0).getDisplayName()+" yohoooooo");
            recylclerAdapter.dataList=eventsList;
            recylclerAdapter.mDrawable=mDrawable;
            recylclerAdapter.notifyDataSetChanged();
          }
        }));
  }
  @Override
  public void onDestroy() {
    this.mSubscriptions.unsubscribe();
    super.onDestroy();
  }
}
