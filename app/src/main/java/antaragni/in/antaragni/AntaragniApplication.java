package antaragni.in.antaragni;

import android.app.Application;

import antaragni.in.antaragni.DataHandler.DataManager;

/**
 * Created by varun on 15/10/16.
 */

public class AntaragniApplication extends Application {
  DataManager mDataManager;
  @Override
  public void onCreate(){
    super.onCreate();
    mDataManager=new DataManager(getApplicationContext());
  }
}
