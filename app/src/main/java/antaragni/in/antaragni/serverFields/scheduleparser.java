package antaragni.in.antaragni.serverFields;

/**
 * Created by saransh on 18/10/16.
 */

public class scheduleparser {
    public String EventName;
    public String StartTime;
    public String EndTime;
    public String Venue;
    public int Day;

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        this.Day = day;
    }

    public String getVenue() {

        return Venue;
    }

    public void setVenue(String venue) {
        this.Venue = venue;
    }

    public String getEndtime() {

        return EndTime;
    }

    public void setEndtime(String endtime) {
        this.EndTime = endtime;
    }

    public String getStarttime() {

        return StartTime;
    }

    public void setStarttime(String starttime) {
        this.StartTime = starttime;
    }

    public String getEventname() {

        return EventName;
    }

    public void setEventname(String eventname) {
        this.EventName = eventname;
    }
}
