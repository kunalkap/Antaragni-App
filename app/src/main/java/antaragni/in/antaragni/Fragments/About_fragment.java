package antaragni.in.antaragni.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import antaragni.in.antaragni.Adapters.ImageAdapter;
import antaragni.in.antaragni.DataHandler.DataManager;
import antaragni.in.antaragni.DataHandler.RetrofitAddOn;
import antaragni.in.antaragni.R;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.serverFields.link;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link About_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link About_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class About_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private CompositeSubscription mSubscriptions;
    public ImageAdapter recylclerAdapter;
    private DataManager mDataManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView tv;
    private String string;

    private OnFragmentInteractionListener mListener;

    public About_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment About_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static About_fragment newInstance(String param1, String param2) {
        About_fragment fragment = new About_fragment();
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
        mSubscriptions=new CompositeSubscription();
        RetrofitAddOn retrofitAddOn= RetrofitAddOn.getInstance(getActivity().getApplicationContext());
        mDataManager=new DataManager(getActivity().getApplicationContext());
        mDataManager.mService=retrofitAddOn.newUserService();
        loadData();
    }

    public void loadData() {
            mSubscriptions.add(mDataManager.getAbout()
                    .subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<link>() {
                        @Override
                        public void onCompleted() {
                            Log.v("heloo","get is successssssss@@@@@@   past");
                            tv.setText(Html.fromHtml(string));
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
                        public void onNext(link list) {
                          string=list.getlink();
                        }
                    }));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view = inflater.inflate(R.layout.fragment_about, container, false);
     tv = (TextView) view.findViewById(R.id.tv);

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
