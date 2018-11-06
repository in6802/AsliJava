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
        db.execSQL("create table awe_country " +
                "(_id integer primary key autoincrement, " +
                " country text, " +
                " city text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("create table temp_awe_country as select * from awe_country;");
        db.execSQL("drop table awe_country;");

        db.execSQL("create table awe_country (pkid text primary key, country text, city text);");
        db.execSQL("create table awe_country_visitedcount (fkid text);");
//        db.execSQL("insert into awe_country (pkid, country, city) " +
//                "select _id, country, city from temp_awe_country;");
    }

    public void insertRecord(String country, String city) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datetime = format.format(new Date());

        mDB.execSQL("insert into awe_country values (?,?,?)", new Object[] {datetime, country, city});
    }

    public void deleteRecord(String country) {
        String id = "";

        Cursor cursor = mDB.rawQuery("select pkid from awe_country where country=?", new String[] {country});
        if(cursor.moveToNext()) id = cursor.getString(0);

        mDB.execSQL("delete from awe_country where pkid=?", new Object[] {id});
    }

    public void updateRecord(String country, String city) {
        String id = "";
        Cursor cursor = mDB.rawQuery("select pkid from awe_country where country=?", new String[] {country});
        if(cursor.moveToNext()) id = cursor.getString(0);

        mDB.execSQL("update awe_country set country=?, city=? where pkid=?", new Object[] {country, city, id});
    }

    public String read() {
        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = mDB.rawQuery("select * from awe_country order by pkid desc", null);
        while (cursor.moveToNext()) {
            stringBuffer.append(cursor.getString(0)).append(": ");
            stringBuffer.append(cursor.getString(1)).append(" - ");
            stringBuffer.append(cursor.getString(2)).append("\n");
        }
        return stringBuffer.toString();
    }

    public String[] search(String pkid) {
        String[] results = new String[4];
//        String sql = "select pkid, country, city, ifnull(visitedcount,0) visitedcount\n" +
//                "from (select * from awe_country where country=?) a \n" +
//                "left join \n" +
//                "(select fkid, count(*) visitedcount\n" +
//                "from awe_country_visitedcount\n" +
//                "group by fkid) b\n" +
//                "on a.pkid = b.fkid;";
        String sql = "select pkid, country, city, count(fkid) count\n" +
                "        from awe_country\n" +
                "        left join awe_country_visitedcount\n" +
                "        on pkid=fkid and pkid =?";

        Cursor cursor = mDB.rawQuery(sql, new String[] {pkid});
        if (cursor.moveToNext()) {
            results[0] = cursor.getString(0);
            results[1] = cursor.getString(1);
            results[2] = cursor.getString(2);
            results[3] = cursor.getString(3);
        }
        return results;
    }

    public void insertCount(String pkid) {
        mDB.execSQL("insert into awe_country_visitedcount values (?)", new Object[] {pkid});
    }
}

