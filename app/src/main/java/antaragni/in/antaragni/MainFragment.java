package antaragni.in.antaragni;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;


public class MainFragment extends Fragment {


  private OnFragmentInteractionListener mListener;
  private ListView mImageRecycler;
  private ImageAdapter mRecyclerAdapter;
  public MainFragment() {

  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v= inflater.inflate(R.layout.fragment_main, container, false);
    mImageRecycler = (ListView) v.findViewById(R.id.imageView);
    mRecyclerAdapter = new ImageAdapter(getResources().getDrawable(R.drawable.antaragni_logo),getActivity());
    mImageRecycler.setAdapter(mRecyclerAdapter);
    return v;
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
