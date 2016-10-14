package antaragni.in.antaragni.DataHandler;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import antaragni.in.antaragni.DataModels.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by varun on 15/10/16.
 */

public class DataManager {
  protected Scheduler mSubscribeScheduler;
  protected DataService mService;
  public Context mContext;
  public DataManager(Context context){
    mContext=context;
  }

  public Observable<Event> getEvent(String eventName, Map<String, String> options) {
    return mService.getEvent(eventName, options);
  }
  public Observable<ArrayList> allEvents(Map<String, String> options) {
    return mService.allEvents(options);
  }
  public Observable<ArrayList> allSponsors(Map<String, String> options) {
    return mService.allSponsors(options);
  }
  public Observable<ArrayList> allContacts(Map<String, String> options) {
    return mService.allContacts(options);
  }public Observable<ArrayList> getSchedule(Map<String, String> options) {
    return mService.getSchedule( options);
  }
  public Observable<ArrayList> pastlineup( Map<String, String> options) {
    return mService.pastLineup(options);
  }
  public Observable<ArrayList> getHomePage(Map<String, String> options) {
    return mService.getHomePage(options);
  }


}
