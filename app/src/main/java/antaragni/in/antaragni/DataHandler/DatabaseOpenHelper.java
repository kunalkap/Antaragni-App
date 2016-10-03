package antaragni.in.antaragni.DataHandler;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
/**
 * Created by varun on 3/10/16.
 */

public class DatabaseOpenHelper extends SQLiteAssetHelper
  {

    private static final int DATABASE_Version = 1;
    private static final String DATABASE_Name = "events";

    public DatabaseOpenHelper(Context context)
    {
      super(context, DATABASE_Name, null, DATABASE_Version);
    }

  }
