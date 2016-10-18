package antaragni.in.antaragni.serverFields;

import android.media.Image;

import java.util.ArrayList;

import antaragni.in.antaragni.serverFields.ImageURL;

/**
 * Created by varun on 17/10/16.
 */

public class ImageModel {
  public ArrayList<ImageURL> url;
  public ImagE images;
  public String text;
  public String link;

  public ArrayList<ImageURL> getUrl(){
    return url;
  }
  public void setUrl(ArrayList<ImageURL> url1){
    this.url=url;
  }

  public ImagE getimage(){
    return images;
  }
  public void setimage(ImagE img){
    this.images=img;
  }
}
