package antaragni.in.antaragni.serverFields;

/**
 * Created by varun on 18/10/16.
 */

public class ContactSchema {

  public String heading;
  public ImagE images;
  public String text;
  public String email;
  public String link;

  public String number;


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String gettext() {
    return text;
  }

  public void settext(String text) {
    this.text = text;
  }

  public ImagE getImages() {

    return images;
  }

  public void setImages(ImagE images) {
    this.images = images;
  }


  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getHeading() {

    return heading;
  }

  public void setHeading(String heading) {
    this.heading = heading;
  }

  public String getLink() {

    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

}
