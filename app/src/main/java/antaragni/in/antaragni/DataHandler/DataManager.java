package antaragni.in.antaragni.DataHandler;

import android.content.Context;

import java.util.ArrayList;
import java.util.Map;

import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.serverFields.CurrentLine;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.DataModels.subEvent;
import rx.Observable;
import rx.Scheduler;

/**
 * Created by varun on 15/10/16.
 */

public class DataManager {
  protected Scheduler mSubscribeScheduler;
  public DataService mService;
  public Context mContext;
  public DataManager mDataManager;
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
  public Observable<ArrayList<ImageModel>> allSponsors() {
    return mService.allSponsors();
  }
  public Observable<ArrayList> allContacts() {
    return mService.allContacts();
  }public Observable<ArrayList> getSchedule() {
    return mService.getSchedule();
  }
  public Observable<ArrayList<ImageModel>> pastlineup() {
    return mService.pastLineup();
  }
  public Observable<ArrayList<ImageModel>> getHomePage() {
    return mService.getHomePage();
  }
  public Observable<ArrayList<CurrentLine>> getData(String type) {
    return mService.getData(type);
  }
}
