package antaragni.in.antaragni.serverFields;

import java.util.ArrayList;

/**
 * Created by varun on 18/10/16.
 */

public class CurrentLine {
  public String maintext;
  public ArrayList<IMages> images;
  public String getMaintext(){
    return maintext;
  }
  public void setMaintext(String url1){
    this.maintext=url1;
  }

  public ArrayList<IMages> getimages(){
    return images;
  }
  public void setimage(ArrayList<IMages> img){
    this.images=img;
  }
}
