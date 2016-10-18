package antaragni.in.antaragni.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.InputStream;
import java.io.OutputStream;

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


    public static boolean isNetworkAvailable(Context context) {
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      return connectivityManager.getActiveNetworkInfo() != null;
    }
}
