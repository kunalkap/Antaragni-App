package antaragni.in.antaragni.serverFields;

/**
 * Created by saransh on 18/10/16.
 */

public class scheduleparser {
    public String eventname;
    public String starttime;
    public String endtime;
    public String venue;
    public String day;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getVenue() {

        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEndtime() {

        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStarttime() {

        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEventname() {

        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }
}
