package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.AntaragniApplication;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
    mDataManager=((AntaragniApplication)(getActivity().getApplicationContext())).getDataManager();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_grid, container, false);
    GridView gridview = (GridView) v.findViewById(R.id.grid_photos);
    setImage(mParam1);
    ImageAdapter recylclerAdapter=new ImageAdapter(mDrawable,null,getActivity(),tab);
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
  /*
  private void loadData(String type) {

    mSubscriptions.add(mDataManager.pastlineup()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(mDataManager.getScheduler())
        .subscribe(new Subscriber<ArrayList<StreamItem>>() {
                     @Override
                     public void onCompleted() {
                       Log.wtf(TAG, "c");
                     }

                     @Override
                     public void onError(Throwable e) {
                       Log.wtf(TAG, e.getMessage());
                       switch (request_type) {
                         case RELOAD:
                           refreshLayout.setRefreshing(false);
                           showSnackBar(getActivity().getString(R.string.error_internet), Snackbar.LENGTH_SHORT);
                           break;
                         case FIRST_LOAD:
                           showHideOfflineLayout(true);
                           //setErrorShown(e.getMessage());
                           break;
                         case AFTER_LOAD:
                           if (e.getMessage().contains("404"))
                             showSnackBar(getActivity().getString(R.string.error_stream_not_found), Snackbar.LENGTH_SHORT);
                           else if (e.getMessage().contains("length=0; index=-1"))
                             showSnackBar(getActivity().getString(R.string.end_stream), Snackbar.LENGTH_SHORT);
                           else
                             showSnackBar(getActivity().getString(R.string.error_internet), Snackbar.LENGTH_SHORT);
                           break;
                       }
                     }

                     @Override
                     public void onNext(ArrayList<StreamItem> streamItemList) {
                       switch (request_type) {
                         case RELOAD:
                           refreshLayout.setRefreshing(false);
                           fragmentModel.addData(streamItemList);
                           setAdapter(streamItemList);
                           break;
                         case FIRST_LOAD:
                           fragmentModel.addData(streamItemList);
                           updateAdapter(streamItemList);
                           setListShown();//if user is loading the stream for the first time then show the stream list.
                           break;
                         case AFTER_LOAD:
                           fragmentModel.UpdateData(streamItemList);
                           updateAdapter(streamItemList);
                           dismissSnackBar();//if user is loading new items then dismiss the snackbar on items loaded.
                           mStreamView.smoothScrollBy(0, 60);
                           break;
                       }
                       lastStreamItem = (streamItemList.get(streamItemList.size() - 1)).id;
                     }
                   }
        ));
  }*/
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

}
