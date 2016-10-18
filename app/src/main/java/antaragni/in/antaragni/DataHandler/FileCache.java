package antaragni.in.antaragni.DataHandler;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * Created by varun on 17/10/16.
 */

public class FileCache {
  private File cacheDir;

  public FileCache(Context context){


      // if checking on simulator the create cache dir in your application context
      cacheDir=context.getCacheDir();


    if(!cacheDir.exists()){
      // create cache dir in your application context
      cacheDir.mkdirs();

    }
  }

  public File getFile(String url){
    //Identify images by hashcode or encode by URLEncoder.encode.
    String filename=String.valueOf(url.hashCode());

    File f = new File(cacheDir, filename);
    try {
      f.createNewFile();
    }
    catch(IOException e){

    }
    return f;

  }

  public void clear(){
    // list all files inside cache directory
    File[] files=cacheDir.listFiles();
    if(files==null)
      return;
    //delete all cache directory files
    for(File f:files)
      f.delete();
  }
}
