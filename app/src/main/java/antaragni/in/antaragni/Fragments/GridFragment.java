package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class GridFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";
  private static final String TAB_PARAM = "param2";
  private String mParam1;
  private OnFragmentInteractionListener mListener;
  private String tab;
  private Drawable mDrawable;
  private CompositeSubscription mSubscriptions;
  private DataManager mDataManager;
  public ImageAdapter recylclerAdapter;
  public ArrayList<ImageModel> eventlist;


  public static GridFragment newInstance(String param1,String param2) {
    GridFragment fragment = new GridFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(TAB_PARAM,param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      tab=getArguments().getString(TAB_PARAM);
    }
    mSubscriptions=new CompositeSubscription();
    RetrofitAddOn retrofitAddOn= RetrofitAddOn.getInstance(getActivity().getApplicationContext());
    mDataManager=new DataManager(getActivity().getApplicationContext());
    mDataManager.mService=retrofitAddOn.newUserService();
    loadData(mParam1);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_grid, container, false);
    GridView gridview = (GridView) v.findViewById(R.id.grid_photos);
    setImage(mParam1);
    recylclerAdapter=new ImageAdapter(null,getActivity(),tab);
    gridview.setAdapter(recylclerAdapter);
    return v;
  }
  private void setImage(String type){
    if (type.equals("past"))
      mDrawable=getResources().getDrawable(R.drawable.suni);
    else if(type.equals("comp"))
      mDrawable=getResources().getDrawable(R.drawable.comp1);
  }
  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  public void loadData(String type) {
   if (type.equals("past"))
      mSubscriptions.add(mDataManager.pastlineup()
          .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<ArrayList<ImageModel>>() {
            @Override
            public void onCompleted() {
              Log.v("heloo","get is successssssss@@@@@@   past");
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
            public void onNext(ArrayList<ImageModel> list) {
             eventlist=list;
              recylclerAdapter.imageLinks=eventlist;
              Log.v("helllooooo",""+list.get(0).url.get(0).image.filename+" this is the name");
              recylclerAdapter.notifyDataSetChanged();
            }
          }));

  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnFragmentInteractionListener) {
      mListener = (OnFragmentInteractionListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  @Override
  public void onDestroy() {
    this.mSubscriptions.unsubscribe();
    super.onDestroy();
  }

}
