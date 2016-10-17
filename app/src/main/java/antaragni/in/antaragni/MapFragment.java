package antaragni.in.antaragni;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import antaragni.in.antaragni.Manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import antaragni.in.antaragni.DataHandler.DatabaseAccess;
import antaragni.in.antaragni.DataModels.Venue;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap mMap;
    private int width, height;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLngBounds latLngBounds;
    private Polyline newPolyline;
    private Button button;
    private Spinner fromlist;
    private Spinner tolist;
    private Spinner mode;
    private DatabaseAccess dba;
    private PowerManager mPowerManager;
    private WindowManager mWindowManager;
    public String from_loc, to_loc, Direc_mode;
    public Venue from_venue, to_venue;
    String from_place, to_place;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Float azimut;
    private SensorManager sm;
    Sensor accelerometer;
    Sensor magnetometer;
    private Display mDisplay;


    private OnFragmentInteractionListener mListener;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    final Context context = this.getActivity();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        getSreenDimanstions();


        View view = inflater.inflate(R.layout.fragment_map, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag_map);
        mapFragment.getMapAsync(this);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        buildGoogleApiClient();




        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        button = (Button) view.findViewById(R.id.button);
        fromlist = (Spinner) view.findViewById(R.id.from_dropdown_list);
        tolist = (Spinner) view.findViewById(R.id.to_dropdown_list);
        mode = (Spinner) view.findViewById(R.id.direction_mode);

        String[] from_items = new String[]{"from", "CCD", "MT", "Hall 2", "Hall 5", "Hall 1", "Hall 8", "Hall 7", "Hall 4", "OAT", "CSE Canteen", "Main Auditorium", "LHC", "Informals Stage",
                "Desk Submission", "Pronites Ground", "Adventure zone", "Outreach Auditorium", "Nukkad/Dance Stage", "Swimming pool Parking lot"};
        final String[] to_items = new String[]{"to", "CCD", "MT", "Hall 2", "Hall 5", "Hall 1", "Hall 8", "Hall 7", "Hall 4", "OAT", "CSE Canteen", "Main Auditorium", "LHC", "Informals Stage",
                "Desk Submission", "Pronites Ground", "Adventure zone", "Outreach Auditorium", "Nukkad/Dance Stage", "Swimming pool Parking lot"};
        String[] Mode = new String[]{"mode", "Driving", "Walking"};
        ArrayAdapter<String> from_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, from_items);
        ArrayAdapter<String> to_adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, to_items);
        final ArrayAdapter<String> direc_mod = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Mode);
        fromlist.setAdapter(from_adapter);
        tolist.setAdapter(to_adapter);
        mode.setAdapter(direc_mod);
        fromlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                from_loc = (String) parent.getItemAtPosition(position);

                from_venue = dba.getVenue(from_loc);
                from_place = from_venue.getLocation();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //to do something
            }
        });

        tolist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                to_loc = (String) parent.getItemAtPosition(position);

                to_venue = dba.getVenue(to_loc);
                to_place = to_venue.getLocation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Direc_mode = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //to do something
            }
        });


        //map setup


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Direc_mode != null && from_venue != null && to_venue != null) {
                    findDirections(from_venue.getLatLng().latitude, from_venue.getLatLng().longitude, to_venue.getLatLng().latitude, to_venue.getLatLng().longitude, Direc_mode);

                } else {
                    Toast.makeText(context, "Please select locations and Mode", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setSmallestDisplacement(0.1f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            Toast.makeText(context, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
    }

    @Override

    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));


    }

    public void handleGetDirectionsResult(ArrayList<LatLng> directionPoints) {
        PolylineOptions rectLine = new PolylineOptions().width(5).color(Color.RED);

        for (int i = 0; i < directionPoints.size(); i++) {
            rectLine.add(directionPoints.get(i));
        }
        if (newPolyline != null) {
            newPolyline.remove();
        }
        newPolyline = mMap.addPolyline(rectLine);

        LatLngBounds latlngBounds = createLatLngBoundsObject(from_venue.getLatLng(), to_venue.getLatLng());
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latlngBounds, width, height, 150));

    }

    public void putMarkers(LatLng frm, LatLng t, String frm1, String t1, String time, String dist) {
        Marker frm2 = mMap.addMarker(new MarkerOptions()
                .position(frm)
                .title(frm1).snippet(time + "minutes," + dist + "m Distance")
        );
        frm2.showInfoWindow();

        Marker t2 = mMap.addMarker(new MarkerOptions().position(t).title(t1));
        t2.showInfoWindow();

    }/* map.addMarker(new MarkerOptions()
    .icon(BitmapDescriptorFactory.fromResource(R.drawable.direction_arrow))
            .position(mapCenter)
    .flat(true)
    .rotation(245));

    CameraPosition cameraPosition = CameraPosition.builder()
            .target(mapCenter)
            .zoom(13)
            .bearing(90)
            .build();*/

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng iitk = new LatLng(26.512339, 80.232900);
        mMap.addMarker(new MarkerOptions().position(iitk)
                .title("Map"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(iitk, 15));






        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(context, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }


        CameraPosition temp = mMap.getCameraPosition();


    }

    private LatLngBounds createLatLngBoundsObject(LatLng firstLocation, LatLng secondLocation) {
        if (firstLocation != null && secondLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(firstLocation).include(secondLocation);

            return builder.build();
        }
        return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }// else {
        ////throw new RuntimeException(context.toString()
        //     + " implement OnFragmentInteractionListener");
        //  }
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

    public void findDirections(double fromPositionDoubleLat, double fromPositionDoubleLong, double toPositionDoubleLat, double toPositionDoubleLong, String mode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LAT, String.valueOf(fromPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.USER_CURRENT_LONG, String.valueOf(fromPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DESTINATION_LAT, String.valueOf(toPositionDoubleLat));
        map.put(GetDirectionsAsyncTask.DESTINATION_LONG, String.valueOf(toPositionDoubleLong));
        map.put(GetDirectionsAsyncTask.DIRECTIONS_MODE, mode);

        GetDirectionsAsyncTask asyncTask = new GetDirectionsAsyncTask();
        asyncTask.execute(map);
    }


    private void getSreenDimanstions() {


        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        width = displaymetrics.widthPixels;
        height = displaymetrics.heightPixels;

    }
}
