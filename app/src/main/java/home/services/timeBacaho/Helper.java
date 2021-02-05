package home.services.timeBacaho;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import home.services.timeBacaho.Models.categories;

public class Helper {



public  static  String user_id(Context context){
    DatabaseHelper db1;
    db1 = new DatabaseHelper(context);
    Cursor res = db1.getAllData();
    String id = null;
    while (res.moveToNext()) {

        id = res.getString(2);

    }

    return  id;
}


}
