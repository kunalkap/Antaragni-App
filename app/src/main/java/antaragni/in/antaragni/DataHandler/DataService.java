package antaragni.in.antaragni.DataHandler;

import java.util.ArrayList;
import java.util.Map;

import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.DataModels.subEvent;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
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
  Observable allSponsors(@QueryMap Map<String, String> options);

  @GET("database/contacts")
  Observable allContacts(@QueryMap Map<String, String> options);

  @GET("database/schedule")
  Observable getSchedule(@QueryMap Map<String, String> options);

  @GET("database/lineup")
  Observable<ArrayList> pastLineup();

  @GET("database/mainpage")
  Observable getHomePage(@QueryMap Map<String, String> options);

  @POST("{usertoken}")
  Call<ResponseBody> postNewStream(@Path("usertoken") String usertoken, @Body String body);

}
