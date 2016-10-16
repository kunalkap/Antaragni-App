package antaragni.in.antaragni.DataHandler;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

import antaragni.in.antaragni.serialisation.ExcludeSerialization;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by varun on 15/10/16.
 */

public class RetrofitAddOn {
  private static RetrofitAddOn mInstance;
  OkHttpClient okHttpClient;
  HttpLoggingInterceptor interceptor;
  Retrofit mRetrofit;

  private RetrofitAddOn(final Context context) {
    interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
    okHttpClientBuilder
        .addInterceptor(interceptor)
        .addNetworkInterceptor(new StethoInterceptor())
        .cache(new Cache(context.getCacheDir(), 10 * 1024 * 1024)) // 10 MB
        .build();

    ExclusionStrategy exclusionStrategy = new ExcludeFromSerializationExclusionStrategy();
    Gson gson = new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).create();
    okHttpClient = okHttpClientBuilder.build();
    mRetrofit = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl("http://www.antaragni.in/")
        .client(okHttpClient)
        .build();
  }

  //this class needs to be singleton so that we have only one okHttpClient and CookieManager
  public static synchronized RetrofitAddOn getInstance(Context context) {
    if (mInstance == null) {
      mInstance = new RetrofitAddOn(context);
    }
    return mInstance;
  }

  public DataService newUserService() {
    return mRetrofit.create(DataService.class);
  }

  // Excludes any field (or class) that is tagged with an "@ExcludeSerialization"
  private static class ExcludeFromSerializationExclusionStrategy implements ExclusionStrategy {
    public boolean shouldSkipClass(Class<?> clazz) {
      return clazz.getAnnotation(ExcludeSerialization.class) != null;
    }

    public boolean shouldSkipField(FieldAttributes f) {
      return f.getAnnotation(ExcludeSerialization.class) != null;
    }
  }
}
