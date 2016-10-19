package antaragni.in.antaragni.DataHandler;

import java.util.ArrayList;

import antaragni.in.antaragni.DataModels.Category;
import antaragni.in.antaragni.serverFields.ContactSchema;
import antaragni.in.antaragni.serverFields.CurrentLine;
import antaragni.in.antaragni.serverFields.ImageModel;
import antaragni.in.antaragni.DataModels.subEvent;
import antaragni.in.antaragni.serverFields.link;
import antaragni.in.antaragni.serverFields.scheduleparser;
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

  @GET("database/schedule/{type}")
  Observable<ArrayList<scheduleparser>> getSchedule(@Path("type") String type);

  @GET("database/sponsors")
  Observable<ArrayList<ImageModel>> allSponsors();

  @GET("database/contacts")
  Observable allContacts();

  @GET("database/lineup")
  Observable<ArrayList<ImageModel>> pastLineup();

  @GET("database/mainpage")
  Observable<ArrayList<ImageModel>> getHomePage();

  @POST("{usertoken}")
  Call<ResponseBody> postNewStream(@Path("usertoken") String usertoken, @Body String body);

  @GET("database/contacts")
  Observable<ArrayList<ContactSchema>> getContacts();

  @GET("database/about")
  Observable<link> getAbout();



  @GET("database/{type}")
  Observable<ArrayList<CurrentLine>> getData(@Path("type") String type );



}
