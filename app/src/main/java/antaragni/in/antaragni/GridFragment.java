package antaragni.in.antaragni;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


public class GridFragment extends Fragment {

  private static final String ARG_PARAM1 = "param1";
  private String mParam1;
  private OnFragmentInteractionListener mListener;
  private Drawable mDrawable;
  private GridView mGridView;
  public static GridFragment newInstance(String param1) {
    GridFragment fragment = new GridFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_grid, container, false);
    GridView gridview = (GridView) v.findViewById(R.id.grid_photos);
    setImage(mParam1);
    ImageAdapter recylclerAdapter=new ImageAdapter(mDrawable,getActivity());
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
