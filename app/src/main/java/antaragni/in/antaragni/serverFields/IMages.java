package antaragni.in.antaragni.serverFields;

import java.util.ArrayList;

/**
 * Created by varun on 18/10/16.
 */

public class IMages {

  public String name;
  public String text;
  public ImagE image;
  public ArrayList<ImagE> images;
  public ArrayList<link> videos;


  public String getname(){
    return name;
  }
  public void setname(String name){
    this.name=name;
  }

  public String gettext(){
    return text;
  }
  public void settext(String img){
    this.text=img;
  }

  public ImagE getimage(){
    return image;
  }
  public void setimage(ImagE img){
    this.image=img;
  }
  public ArrayList<ImagE> getImages(){
    return images;
  }
  public void setimageS(ArrayList<ImagE> img){
    this.images=img;
  }
  public ArrayList<link> getvideos(){
    return videos;
  }
  public void setlink(ArrayList<link> img){
    this.videos=img;
  }

}
