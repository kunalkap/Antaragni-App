package antaragni.in.antaragni.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.InputStream;
import java.io.OutputStream;

import antaragni.in.antaragni.R;

/**
 * Created by varun on 17/10/16.
 */

public class utils {
  public static void CopyStream(InputStream is, OutputStream os)
  {
    final int buffer_size=1024;
    try
    {

      byte[] bytes=new byte[buffer_size];
      for(;;)
      {
        //Read byte from input stream

        int count=is.read(bytes, 0, buffer_size);
        if(count==-1)
          break;

        //Write byte from output stream
        os.write(bytes, 0, count);
      }
    }
    catch(Exception ex){}
  }

  public static void slide_down(Context ctx, View v) {

    Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide);
    if (a != null) {
      a.reset();
      if (v != null) {
        v.clearAnimation();
        v.startAnimation(a);
      }
    }
  }


  public static boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      return connectivityManager.getActiveNetworkInfo() != null;
    }
}
