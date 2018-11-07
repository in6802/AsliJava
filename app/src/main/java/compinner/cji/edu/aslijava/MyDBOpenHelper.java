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
        //create table menu_table (menu_id text primary key, menu_name text, menu_cost text);
        String query ="create table menu_table " +
                "(menu_id text primary key, " +
                "menu_name text, " +
                "menu_cost integer);";
        db.execSQL(query);

        //
        query ="create table order_table " +
                "(order_pkid text primary key," +
                " order_id text, " +
                "menu_id text," +
                " menu_name text, " +
                " menu_count integer, " +
                " menu_cost integer, " +
                " closed_flag  integer);";
        db.execSQL(query);
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table menu_table;");
        db.execSQL("drop table order_table;");
        onCreate(db);
    }

    //all
    public String readOrder() {

        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = mDB.rawQuery("select * from order_table order by order_id desc", null);
        while (cursor.moveToNext()) {
 //           stringBuffer.append(cursor.getString(0)).append(": ");
            stringBuffer.append(cursor.getString(1)).append(" - ");
            stringBuffer.append(cursor.getString(2)).append(" - ");
            stringBuffer.append(cursor.getString(3)).append(": ");
            stringBuffer.append(cursor.getInt(4)).append(" - ");
            stringBuffer.append(cursor.getInt(5)).append(" - ");
            stringBuffer.append(cursor.getInt(6)).append("\n");
        }
        return stringBuffer.toString();
    }
    //Today
    public String readOrderToday(String today) {

        StringBuffer stringBuffer = new StringBuffer();
        //       String query = "select distinct * from order_table where order_I like 'today%';
        String query = "select distinct * from order_table " +
                "where order_id like '"+ today +"%';";
        Cursor cursor = mDB.rawQuery(query, null);
        while (cursor.moveToNext()) {
//            stringBuffer.append(cursor.getString(0)).append(": ");
            stringBuffer.append(cursor.getString(1)).append(" - ");
            stringBuffer.append(cursor.getString(2)).append(" - ");
            stringBuffer.append(cursor.getString(3)).append(": ");
            stringBuffer.append(cursor.getInt(4)).append(" - ");
            stringBuffer.append(cursor.getInt(5)).append(" - ");
            stringBuffer.append(cursor.getInt(6)).append("\n");
        }
        return stringBuffer.toString();
    }
    //Today
    public int countOrderDay(String today) {

        StringBuffer stringBuffer = new StringBuffer();
        //       String query = "select distinct * from order_table where order_I like 'today%';
//        String query = "select distinct * from order_table " +
//                "where order_id like '" + today + "%';";
        String query = "select distinct order_id from order_table ;" ;
        Cursor cursor = mDB.rawQuery(query, null);
        int count = cursor.getCount();
        return count;
    }

    public void insertOrder(String order_pkid, String order_id, String menu_ID, String menu_name,
                            int menu_count, int menu_cost, int closed_flag) {

        mDB.execSQL("insert into order_table " +
                        "values (?,?,?,?,?,?,?)",
                new Object[] {order_pkid, order_id, menu_ID, menu_name, menu_count,menu_cost, closed_flag});
    }

    public void insertMenu(String menu_id, String menu_name, int menu_cost) {
        mDB.execSQL("insert into menu_table " +
                        "values (?,?,?)",
                new Object[] {menu_id, menu_name, menu_cost});
    }
    public void updateMenu(String menu_id, String menu_name, int menu_cost) {
        String id = "";
        Cursor cursor = mDB.rawQuery("select menu_name, menu_cost " +
                "from menu_table where menu_id=?", new String[] {menu_id});
        if(cursor.moveToNext()) {
            menu_name = cursor.getString(0);
            menu_cost = cursor.getInt(1);
        }

        mDB.execSQL("update menu_table " +
                        "set menu_name=?, menu_cost=? " +
                        "where menu_id=?",
                new Object[] {menu_name, menu_cost, menu_id});
    }
}

