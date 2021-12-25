package com.example.planner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_task";
    public static final String table_user = "tb_user";
    public static final String row_userid = "user_id";
    public static final String row_username = "username";
    public static final String row_password = "password";
    public static final String row_name = "name";
    public static final String row_umur = "umur";
    public static final String row_gender = "gender";

    public static final String table_event = "tb_event";
    public static final String row_eventId = "event_id";
    public static final String row_eventName = "event_name";
    public static final String row_eventLocation = "event_location";
    public static final String row_eventDate = "event_date";
    public static final String row_eventTime = "event_time";

    public static final String table_daily = "tb_daily";
    public static final String row_dailyId = "row_dailyId";
    public static final String row_dailyPlan = "row_dailyPlan";
    public static final String row_startTime = "row_startTime";
    public static final String row_endTime = "row_endTime";
    public static final String row_status = "row_status";

    public static final String table_save = "tb_save";
    public static final String row_saveId = "row_saveId";
    public static final String row_title = "row_title";
    public static final String row_deskripsi = "row_deskripsi";
    public static final String row_tanggal = "row_tanggal";

    public SQLiteDatabase database;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String query = "CREATE TABLE " + table_user + "("
                + row_userid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_username + " TEXT,"
                + row_password + " TEXT,"
                + row_name + " TEXT,"
                + row_umur + " TEXT,"
                + row_gender + " TEXT)";
        database.execSQL(query);
        String queryDaily = "CREATE TABLE " + table_daily + "("
                + row_dailyId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_userid + " INTEGER, "
                + row_dailyPlan + " TEXT, "
                + row_startTime + " TIME,"
                + row_endTime + " TIME,"
                + row_saveId + " INTEGER,"
                + row_tanggal + " DATE,"
                + row_status + " TEXT)";
        database.execSQL(queryDaily);
        String queryEvent = "CREATE TABLE " + table_event + "("
                + row_eventId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_userid + " INTEGER , "
                + row_eventName+ " TEXT,"
                + row_eventLocation + " TEXT,"
                + row_eventDate + " DATE,"
                + row_eventTime + " TEXT)";
        database.execSQL(queryEvent);
        String querySave = "CREATE TABLE " + table_save + "("
                + row_saveId + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + row_userid + " INTEGER , "
                + row_title + " TEXT , "
                + row_deskripsi + " TEXT)";
        database.execSQL(querySave);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + table_user);
        onCreate(database);
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {row_userid};
        SQLiteDatabase database = getReadableDatabase();
        String selection = row_username + "=?" + " and " + row_password + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = database.query(table_user, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        return count > 0;
    }

    public int insertUser(ContentValues values, String username) {

        SQLiteDatabase dbRead = getWritableDatabase();
        SQLiteDatabase dbWrite = getWritableDatabase();
        Cursor checkSameInUser= dbRead.rawQuery("SELECT*FROM tb_user WHERE username = '"+username+"'",null);
        int check = checkSameInUser.getCount();
        if(check == 0) {
            dbWrite.insert(table_user, null, values);
        }
        return check;
    }

    public String selectUser(String user){

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM tb_user WHERE username = '"+user+"'";

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
        }
        return cursor.getString(1);

    }

    public void insertEvent(String id, String userId, String eventPlan, String eventLocation, String eventDate, String evenTime) {

        SQLiteDatabase dbWrite = this.getWritableDatabase();
        SQLiteDatabase dbRead = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(row_eventName, eventPlan);
        values.put(row_eventLocation, eventLocation);
        values.put(row_eventDate, eventDate);
        values.put(row_eventTime, evenTime);
        values.put(row_userid, userId);

        Cursor checkSameInEvent = dbRead.rawQuery("SELECT*FROM tb_event WHERE event_id = "+id,null);
        if(checkSameInEvent.getCount() == 0) {
            dbWrite.insert(table_event, null, values);
        } else {
            checkSameInEvent.moveToLast();
            dbWrite.update(table_event, values, "event_id=?", new String[]{id});
        }

        dbWrite.close();
        dbRead.close();
    }

    public ArrayList<Event> readEvent(int idUser) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_event WHERE user_id = " + idUser, null);

        ArrayList<Event> eventArrayList = new ArrayList<Event>();

        if (cursor.moveToFirst()) {
            do {
                eventArrayList.add(new Event(cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return eventArrayList;
    }

    public ArrayList<DailyPlaner> readDaily(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();

        Calendar myCalendar = Calendar.getInstance();
        String tgl = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalendar.getTime());


        Cursor cursor = db.rawQuery("SELECT * FROM "+table_daily+" WHERE " +row_userid+ "=" +idUser+ " AND "+ row_tanggal +" ='" +tgl+"';", null);

        ArrayList<DailyPlaner> dailyPlanerArrayList = new ArrayList<DailyPlaner>();

        if (cursor.moveToFirst()) {
            do {
                dailyPlanerArrayList.add(new DailyPlaner(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dailyPlanerArrayList;

    }



    public void insertDaily(String getId, Integer userId, String tambahdailyplan, String tambahstarttime, String tambahendtime) {

        SQLiteDatabase dbWrite = this.getWritableDatabase();
        SQLiteDatabase dbRead = getReadableDatabase();
        ContentValues values = new ContentValues();

        Calendar myCalendar = Calendar.getInstance();
//        LocalDateTime now = LocalDateTime.now();
        String tgl = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalendar.getTime());

        values.put(row_userid, userId);
        values.put(row_dailyPlan, tambahdailyplan);
        values.put(row_startTime, tambahstarttime);
        values.put(row_endTime, tambahendtime);
        values.put(row_status, "undone");
//        values.put(row_saveId, null)
        values.put(row_tanggal, tgl);

        Cursor checkSameInDaily = dbRead.rawQuery("SELECT*FROM tb_daily WHERE row_dailyId = "+getId,null);
        if(checkSameInDaily.getCount() == 0)
        {
            dbWrite.insert(table_daily, null, values);
        }
        else
        {
            checkSameInDaily.moveToLast();
            dbWrite.update(table_daily, values, "row_dailyId=?", new String[]{getId});
        }

        dbWrite.close();
        dbRead.close();

    }

    public int checkUserId(String getUsername, String getPassword) {
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor check = dbRead.rawQuery("SELECT*FROM tb_user WHERE username = '"+getUsername+"' AND password = '"+getPassword+"'",null);
        check.moveToFirst();
        return check.getInt(0);
    }


    public int checkFriendId(String getUsername) {
        SQLiteDatabase dbRead = this.getReadableDatabase();
        Cursor check = dbRead.rawQuery("SELECT * FROM tb_user WHERE username = '"+getUsername+"'" ,null);
        check.moveToFirst();
        int x = check.getInt(0);
        return x;
    }

    public void addFriendEvent(int checkFriendId, String eventName, String eventLocation, String eventDate, String eventTime) {
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        SQLiteDatabase dbRead = getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(row_userid, checkFriendId);
        values.put(row_eventName, String.valueOf(eventName));
        values.put(row_eventLocation, String.valueOf(eventLocation));
        values.put(row_eventDate, String.valueOf(eventDate));
        values.put(row_eventTime, String.valueOf(eventTime));


        dbWrite.insert(table_event, null, values);


        dbWrite.close();
        dbRead.close();

    }

    public void delete (int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_event,row_eventId+" =?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteDaily(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_daily,row_dailyId+" =?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void setDone(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(row_status,"done");
//        db.rawQuery("UPDATE tb_daily SET row_status = 'done' where row_dailyId = " +id, null);
        db.update(table_daily, cv, row_dailyId+"=?", new String[]{Integer.toString(id)});
    }

    public void setUnDone(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(row_status,"undone");
        db.update(table_daily, cv, row_dailyId+"=?", new String[]{Integer.toString(id)});

    }

    public long saveDaily(int userId, String input_title, String input_deskripsi) {

        SQLiteDatabase dbWrite = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(row_userid, userId);
        values.put(row_title, String.valueOf(input_title));
        values.put(row_deskripsi, String.valueOf(input_deskripsi));


        long id=dbWrite.insert(table_save, null, values);
        return id;

    }
    public void updateSaveDaily(int id,ArrayList<DailyPlaner> dailyArrList){
        SQLiteDatabase dbWrite = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        for(int i=0;i<dailyArrList.size();i++){
            cv.put(row_saveId,id);
            dbWrite.update(table_daily,cv,row_dailyId+"=?",new String[]{Integer.toString(dailyArrList.get(i).getId())});

        }
    }

    public ArrayList<Repeat> readRepeat(int idUser) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tb_save where user_id =" +idUser, null);

        ArrayList<Repeat> repeatArrayList = new ArrayList<Repeat>();

        if (cursor.moveToFirst()) {
            do {
                repeatArrayList.add(new Repeat(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return repeatArrayList;

    }

    public ArrayList<DailyPlaner> readDailyRepeat(int saveId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Calendar myCalendar = Calendar.getInstance();
        String tgl = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalendar.getTime());


        Cursor cursor = db.rawQuery("SELECT * FROM tb_daily WHERE row_saveId = " +saveId, null);

        ArrayList<DailyPlaner> dailyPlanerArrayList = new ArrayList<DailyPlaner>();

        if (cursor.moveToFirst()) {
            do {
                dailyPlanerArrayList.add(new DailyPlaner(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dailyPlanerArrayList;

    }

    public void insertDifDate(ArrayList<DailyPlaner> values, int saveId, int idUser) {

        SQLiteDatabase dbWrite = this.getWritableDatabase();

        Calendar myCalendar = Calendar.getInstance();
        String tgl = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalendar.getTime());


        for(int i=0;i<values.size();i++){
            DailyPlaner list = values.get(i);

            ContentValues values1 = new ContentValues();

            values1.put(row_userid, idUser);
            values1.put(row_dailyPlan, list.getTambahdailyplaner());
            values1.put(row_startTime, list.getTambahstarttime());
            values1.put(row_endTime, list.getTambahendtime());
            values1.put(row_saveId, saveId);
            values1.put(row_tanggal, tgl);
            values1.put(row_status, "undone");
            dbWrite.insert(table_daily, null, values1);

        }

    }

    public ArrayList<DailyPlaner> readDailyRepeatList() {

        SQLiteDatabase db = this.getReadableDatabase();

        Calendar myCalendar = Calendar.getInstance();
        String tgl = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(myCalendar.getTime());


        Cursor cursor = db.rawQuery("SELECT * FROM tb_daily WHERE row_tanggal ='" +tgl+"';" , null);

        ArrayList<DailyPlaner> dailyPlanerArrayList = new ArrayList<DailyPlaner>();

        if (cursor.moveToFirst()) {
            do {
                dailyPlanerArrayList.add(new DailyPlaner(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dailyPlanerArrayList;

    }
}
