package antaragni.in.antaragni.DataHandler;

import android.icu.util.Currency;

import java.util.ArrayList;

import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.serverFields.CurrentLine;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.DataModels.subEvent;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by varun on 15/10/16.
 */

public interface DataService {
  @GET("app/{event}")
  Observable<subEvent> getEvent(@Path("event") String eventName);

  @GET("database/events")
  Observable<ArrayList<Category>> allEvents();

  @GET("database/sponsors")
  Observable<ArrayList<ImageModel>> allSponsors();

  @GET("database/contacts")
  Observable allContacts();

  @GET("database/schedule")
  Observable getSchedule();

  @GET("database/lineup")
  Observable<ArrayList<ImageModel>> pastLineup();

  @GET("database/mainpage")
  Observable<ArrayList<ImageModel>> getHomePage();

  @POST("{usertoken}")
  Call<ResponseBody> postNewStream(@Path("usertoken") String usertoken, @Body String body);


  @GET("database/{type}")
  Observable<ArrayList<CurrentLine>> getData(@Path("type") String type );

}
