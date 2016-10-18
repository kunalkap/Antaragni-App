package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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


public class MainFragment extends Fragment {

  private ListView mImageRecycler;
  private ImageAdapter mRecyclerAdapter;
  private OnFragmentInteractionListener mListener;
  private CompositeSubscription mSubscriptions;
  private DataManager mDataManager;
  public MainFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
    View v= inflater.inflate(R.layout.fragment_main, container, false);
    mImageRecycler = (ListView) v.findViewById(R.id.imageView);
    mRecyclerAdapter = new ImageAdapter(null,getActivity(),"home");
    mImageRecycler.setAdapter(mRecyclerAdapter);
    return v;
  }

  public void loadData() {
      mSubscriptions.add(mDataManager.getHomePage()
          .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<ArrayList<ImageModel>>() {
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
            public void onNext(ArrayList<ImageModel> list) {
              mRecyclerAdapter.imageLinks=list;
              Log.v(".yohohoho",""+list.get(0).url.get(0).image.filename+"kjdfhfloefskkefgfds;pdfkcsadukk;fxlkxcj");
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
  @Override
  public void onDestroy() {
    this.mSubscriptions.unsubscribe();
    super.onDestroy();
  }

}
