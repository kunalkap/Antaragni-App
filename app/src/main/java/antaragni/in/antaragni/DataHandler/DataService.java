package antaragni.in.antaragni.DataHandler;

import java.util.Map;

import antaragni.in.antaragni.DataModels.Event;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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
  Observable<Event> getEvent(@Path("event") String eventName, @QueryMap Map<String, String> options);

  @GET("database/events")
  Observable allEvents(@QueryMap Map<String, String> options);

  @GET("database/sponsors")
  Observable allSponsors(@QueryMap Map<String, String> options);

  @GET("database/contacts")
  Observable allContacts(@QueryMap Map<String, String> options);

  @GET("database/schedule")
  Observable getSchedule(@QueryMap Map<String, String> options);

  @GET("database/lineup")
  Observable pastLineup(@QueryMap Map<String, String> options);

  @GET("database/mainpage")
  Observable getHomePage(@QueryMap Map<String, String> options);

  @POST("{usertoken}")
  Call<ResponseBody> postNewStream(@Path("usertoken") String usertoken, @Body String body);

}
