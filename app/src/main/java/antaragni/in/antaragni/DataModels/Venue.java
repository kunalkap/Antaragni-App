package antaragni.in.antaragni.DataModels;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by varun on 3/10/16.
 */

public class Venue {
  private final String location;
  private LatLng latLng;

  public Venue(String location, LatLng latLng)
  {
    this.location = location;
    this.latLng = latLng;
  }

  public String getLocation()
  {
    return location;
  }

  public LatLng getLatLng()
  {
    return latLng;
  }

}
