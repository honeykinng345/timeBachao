package home.services.timeBacaho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "order.db";
    public final static String TABLE_NAME = "order_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "pid";
    public static final String COL_3 = "item_name";
    public static final String COL_4 = "Item_Price_each";
    public static final String COL_5 = "Item_Price";
    public static final String COL_6 = "Item_Quantity";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,pid TEXT,item_name TEXT,Item_Price_each TEXT,Item_Price TEXT" +
                ",Item_Quantity TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String pid, String name, String itemPriceEach, String itemPrice, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, pid);
        cv.put(COL_3, name);
        cv.put(COL_4,itemPriceEach);
        cv.put(COL_5,itemPrice);
        cv.put(COL_6,quantity);



        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) return false;
        else return true;
    }


    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query="SELECT * FROM "+TABLE_NAME+" WHERE ID='"+id+"'";
        Cursor  cursor = db.rawQuery(query,null);
        return cursor;
    }

    public boolean updateData(String id, String name, String surname, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, surname);
        contentValues.put(COL_4, marks);
        db.update(TABLE_NAME, contentValues, "ID=?", new String[]{id});
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return res;
    }

    public void removeAll()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();



    }
}