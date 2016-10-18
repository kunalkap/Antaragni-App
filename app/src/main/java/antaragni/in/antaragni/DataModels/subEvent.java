package antaragni.in.antaragni.DataModels;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import antaragni.in.antaragni.serialisation.ExcludeSerialization;

/**
 * Created by varun on 3/10/16.
 */

public class subEvent {
  public Category category;               //category of the event

  public Maintext MainText;             //description of the event, must be more than 50 works
  public String linkToReg;
  public String display_name;
  public String name;
  public ArrayList<EventStages> parts;

  public Category getCategory()
  {
    return category;
  }


  //Getters and setters
  public Maintext getMainText() {
    return MainText;
  }

  public void setMainText(Maintext maintext) {
    this.MainText = maintext;
  }

  public String getLink() {
    return linkToReg;
  }

  public void setLink(String link) {
    this.linkToReg = link;
  }

  public String getDisplayName() {
    return display_name;
  }

  public void setDisplayName(String display) {
    this.display_name = display;
  }

  public String getname() {
    return name;
  }

  public void setname(String name1) {
    this.name = name1;
  }

  public class Maintext{
    public String type;
    public String filename;
    public void setType(String type1) {
      this.type = type1;
    }

    public String getType() {
      return type;
    }

    public void setFileName(String file_name) {
      this.filename = file_name;
    }

    public String getFileName() {
      return filename;
    }

  }

}
