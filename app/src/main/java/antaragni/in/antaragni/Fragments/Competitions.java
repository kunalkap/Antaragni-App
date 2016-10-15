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
import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
  public ArrayList<Category> eventsList;
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
    mDataManager=new DataManager(getActivity().getApplicationContext());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_competitions, container, false);
    ListView listview = (ListView) v.findViewById(R.id.category);
    setImage();
    ImageAdapter recylclerAdapter=new ImageAdapter(null,eventsList,getActivity(),"competitions");
    listview.setAdapter(recylclerAdapter);
    return v;
  }
  private void setImage(){
      mDrawable=getResources().getDrawable(R.drawable.comp1);
  }

}
