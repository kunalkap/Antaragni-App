package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.Adapters.LineupAdapter;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.OnFragmentInteractionListener;
import antaragni.in.antaragni.R;
import antaragni.in.antaragni.serverFields.CurrentLine;
import antaragni.in.antaragni.serverFields.ImageModel;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static antaragni.in.antaragni.Adapters.ImageAdapter.ImageAdapter2;


public class CurrentLineFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  private CompositeSubscription mSubscriptions;
  private DataManager mDataManager;
  private RecyclerView mImageRecycler;
  private RecyclerView.LayoutManager mLayoutManager;
  private LineupAdapter mRecyclerAdapter;
  public EditText mTextView;
  public CurrentLine line;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;

  public CurrentLineFragment() {
    // Required empty public constructor
  }

  public static CurrentLineFragment newInstance(String param1, String param2) {
    CurrentLineFragment fragment = new CurrentLineFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }

    RetrofitAddOn retrofitAddOn= RetrofitAddOn.getInstance(getActivity().getApplicationContext());

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_current_line, container, false);
    WebView myWebView = (WebView) v.findViewById(R.id.webviewcf);
    WebSettings webSettings = myWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    myWebView.loadUrl("https://www.antaragni.in/"+mParam1+"_app");

    return v;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri) {
    if (mListener != null) {
      mListener.onFragmentInteraction(uri);
    }
  }

  public void loadData() {
    mSubscriptions.add(mDataManager.getData(mParam1)
        .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArrayList<CurrentLine>> () {
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
          public void onNext(ArrayList<CurrentLine> list) {
            mRecyclerAdapter.dataList=list;
            mRecyclerAdapter.notifyDataSetChanged();;
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

}
