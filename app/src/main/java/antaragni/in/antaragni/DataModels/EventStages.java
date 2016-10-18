package antaragni.in.antaragni.DataModels;

import java.util.GregorianCalendar;

/**
 * Created by varun on 15/10/16.
 */

public class EventStages {
  public subEvent msubEvent;
  public String name;                    //name of the event

  public GregorianCalendar start_time;   //start time of the event
  public GregorianCalendar end_time;     //end time of the event
  public int day;                        //day of ant. i.e. day 1,2,3 or 4
  public Venue venue;

  public String getName()
  {
    return name;
  }

  public GregorianCalendar getStart_time()
  {
    return start_time;
  }

  public GregorianCalendar getEnd_time()
  {
    return end_time;
  }

  public int getDay()
  {
    return day;
  }

  public Venue getVenue()
  {
    return venue;
  }
}
