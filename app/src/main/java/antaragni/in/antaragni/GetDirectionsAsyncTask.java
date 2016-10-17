package antaragni.in.antaragni;

/**
 * Created by piyush on 14/10/16.
 */
import java.util.ArrayList;
import java.util.Map;

import org.w3c.dom.Document;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import antaragni.in.antaragni.GetDirectionsAsyncTask;


public class GetDirectionsAsyncTask extends AsyncTask<Map<String, String>, Object, ArrayList<LatLng>> {
    public static final String USER_CURRENT_LAT = "user_current_lat";
    public static final String USER_CURRENT_LONG = "user_current_long";
    public static final String DESTINATION_LAT = "destination_lat";
    public static final String DESTINATION_LONG = "destination_long";
    public static final String DIRECTIONS_MODE = "directions_mode";
    private Exception exception;
    private ProgressDialog progressDialog;
    private Context context;
    private MapFragment mapFragment;
    public  GMapV2Direction md;
    public    Document doc;
    private GoogleMap map;

    public String time_str, time_int, distance_str, distance_int;


    public void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Calculating directions");
        progressDialog.show();
    }

    @Override
    public void onPostExecute(ArrayList result) {
        progressDialog.dismiss();
        if (exception == null) {
            mapFragment.handleGetDirectionsResult(result);

        } else {
            processException();
        }
    }

    @Override
    protected ArrayList<LatLng> doInBackground(Map<String, String>... params) {
        Map<String, String> paramMap = params[0];
        try {
            LatLng fromPosition = new LatLng(Double.valueOf(paramMap.get(USER_CURRENT_LAT)), Double.valueOf(paramMap.get(USER_CURRENT_LONG)));
            LatLng toPosition = new LatLng(Double.valueOf(paramMap.get(DESTINATION_LAT)), Double.valueOf(paramMap.get(DESTINATION_LONG)));
            md = new GMapV2Direction();
            doc = md.getDocument(fromPosition, toPosition, paramMap.get(DIRECTIONS_MODE));

            ArrayList<LatLng> directionPoints = md.getDirection(doc);

            mapFragment.putMarkers(fromPosition, toPosition, paramMap.get("from_place"),paramMap.get("to_place"),md.getDurationText(doc) ,md.getDistanceText(doc));


            return directionPoints;
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    private void processException() {
        Toast.makeText(context, "Error retriving data", Toast.LENGTH_SHORT).show();
    }
}