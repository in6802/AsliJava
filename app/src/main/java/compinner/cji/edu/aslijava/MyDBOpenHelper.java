package compinner.cji.edu.aslijava;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ScaleGestureDetector;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDBOpenHelper extends SQLiteOpenHelper {

    private static MyDBOpenHelper INSTANCE;
    private static SQLiteDatabase mDB;

    public static MyDBOpenHelper getInstance(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        if(INSTANCE == null) {
            INSTANCE = new MyDBOpenHelper(context, name, factory, version);
            mDB = INSTANCE.getWritableDatabase();
        }
        return INSTANCE;
    }

    public MyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table menu_table " +
            "(menu_id text primary key , " +
            " menu_name text, " +
            " menu_cost  text);");
        db.execSQL("create table order_table " +
                "(order_pkid text primary key , " +
                " order_id text, " +
                " menu_name text, " +
                " menu_count text, " +
                " menu_cost text, " +
                " closed_flag  text);");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table menu_table;");
        db.execSQL("drop table order_table;");

        db.execSQL("create table menu_table " +
                "(menu_id text primary key , " +
                " menu_name text, " +
                " menu_cost  text);");
        db.execSQL("create table order_table " +
                "(order_pkid text primary key , " +
                " order_id text, " +
                " menu_name text, " +
                " menu_count text, " +
                " menu_cost text, " +
                " closed_flag  text);");

    }

    //all
    public String readOrder() {

        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = mDB.rawQuery("select * from order_table order by order_id desc", null);
        while (cursor.moveToNext()) {
            stringBuffer.append(cursor.getString(0)).append(": ");
            stringBuffer.append(cursor.getString(1)).append(" - ");
            stringBuffer.append(cursor.getString(2)).append(" - ");
            stringBuffer.append(cursor.getString(3)).append(": ");
            stringBuffer.append(cursor.getString(4)).append(" - ");
            stringBuffer.append(cursor.getString(5)).append("\n");
        }
        return stringBuffer.toString();
    }

    public void insertOrder(String order_pkid, String order_id, String menu_name,
                            String menu_count, String menu_cost, String closed_flag) {

        mDB.execSQL("insert into order_table " +
                        "values (?,?,?,?,?,?)",
                new Object[] {order_pkid, order_id, menu_name, menu_count,menu_cost, closed_flag});
    }

    public void insertMenu(String menu_id, String menu_name, String menu_cost) {
        mDB.execSQL("insert into menu_table " +
                        "values (?,?,?)",
                new Object[] {menu_id, menu_name, menu_cost});
    }
    public void updateMenu(String menu_id, String menu_name, String menu_cost) {
        String id = "";
        Cursor cursor = mDB.rawQuery("select menu_name, menu_cost " +
                "from menu_table where menu_id=?", new String[] {menu_id});
        if(cursor.moveToNext()) {
            menu_name = cursor.getString(0);
            menu_cost = cursor.getString(1);
        }

        mDB.execSQL("update menu_table " +
                        "set menu_name=?, menu_cost=? " +
                        "where menu_id=?",
                new Object[] {menu_name, menu_cost, menu_id});
    }
}

