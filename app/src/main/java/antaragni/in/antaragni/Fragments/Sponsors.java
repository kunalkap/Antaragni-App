package antaragni.in.antaragni.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import antaragni.in.antaragni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Sponsors extends Fragment {


  public Sponsors() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v=inflater.inflate(R.layout.fragment_sponsors, container, false);
    WebView myWebView = (WebView) v.findViewById(R.id.webview);
    myWebView.loadUrl("https://www.antaragni.in/sponsors_app");
    myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
    myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    return v;
  }

}
