package antaragni.in.antaragni.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import antaragni.in.antaragni.Activities.EventScheduler;
import antaragni.in.antaragni.R;

/**
 * Created by varun on 14/10/16.
 */

public class PlaceHolderFragment extends Fragment {
  /**
   * The fragment argument representing the section number for this
   * fragment.
   */
  private static final String ARG_SECTION_NUMBER = "section_number";

  public static PlaceHolderFragment newInstance(int sectionNumber) {
    PlaceHolderFragment fragment = new PlaceHolderFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_SECTION_NUMBER, sectionNumber);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_event_scheduler, container, false);
    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
    return rootView;
  }
}
