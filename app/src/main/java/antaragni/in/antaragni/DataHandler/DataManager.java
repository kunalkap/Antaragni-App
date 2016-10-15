package antaragni.in.antaragni.DataHandler;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.DataModels.subEvent;
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

  public Scheduler getScheduler(){
    return mSubscribeScheduler;
  }

  public Observable<subEvent> getEvent(String eventName, Map<String, String> options) {
    return mService.getEvent(eventName);
  }
  public Observable<ArrayList<Category>> allEvents() {
    return mService.allEvents();
  }
  public Observable<ArrayList> allSponsors(Map<String, String> options) {
    return mService.allSponsors(options);
  }
  public Observable<ArrayList> allContacts(Map<String, String> options) {
    return mService.allContacts(options);
  }public Observable<ArrayList> getSchedule(Map<String, String> options) {
    return mService.getSchedule( options);
  }
  public Observable<ArrayList> pastlineup() {
    return mService.pastLineup();
  }
  public Observable<ArrayList> getHomePage(Map<String, String> options) {
    return mService.getHomePage(options);
  }


}
