package com.duymanh.btl.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.duymanh.btl.model.Schedule;
import com.duymanh.btl.model.User;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Schedule2.db";
    private static int DATABASE_VERSION = 1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE schedules("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,category TEXT,time TEXT,date TEXT)";

        String sql2 = "CREATE TABLE users("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "username TEXT,password TEXT, email TEXT)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //get all va sap xep order by theo thoi gian
    public List<Schedule> getAll(){
        List<Schedule> list = new ArrayList<>();

        SQLiteDatabase st = getReadableDatabase();
        String order ="date DESC";
        Cursor rs = st.query("schedules",null,null,null,null,null,order);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Schedule(id,title,category,time,date));
        }
        return list;
    }

    public List<User> getAllUser(){
        List<User> list = new ArrayList<>();

        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("users",null,null,null,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String username = rs.getString(1);
            String password = rs.getString(2);
            String email = rs.getString(3);
            list.add(new User(id,username,password,email));
        }
        return list;
    }

    //them 1 schedule
    public long addSchedule(Schedule s){
        ContentValues values = new ContentValues();
        values.put("title",s.getTitle());
        values.put("category",s.getCategory());
        values.put("time",s.getTime());
        values.put("date",s.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("schedules", null, values);
    }

    //add 1 user
    public long addUser(User u){

        ContentValues values = new ContentValues();
        values.put("username",u.getUsername());
        values.put("password",u.getPassword());
        values.put("email",u.getEmail());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        System.out.println("da add user"+u.getUsername()+" thanh cong");
        return sqLiteDatabase.insert("users", null, values);
    }


    //Lay cac schedle theo date
    public List<Schedule> getByDate(String date){
        List<Schedule> list = new ArrayList<>();
        String whereClause = "date like ?";
        String[] whereArgs = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("schedules", null,whereClause,whereArgs,null,null,null);

        while(rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String time = rs.getString(3);
            list.add(new Schedule(id,title,category,time,date));
        }

        return list;
    }



    //Update
    public int update(Schedule s){
        ContentValues values = new ContentValues();
        values.put("title",s.getTitle());
        values.put("category",s.getCategory());
        values.put("time",s.getTime());
        values.put("date",s.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(s.getId())};
        return sqLiteDatabase.update("schedules", values,whereClause,whereArgs);
    }
    //delete
    public int delete(int id){
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("schedules",whereClause,whereArgs);
    }


    public boolean checkUserSigup(String username){
        List<User> list = getAllUser();
        for(User x:list){
            if(x.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        return true;
    }

    public boolean checkUser(User u){
        List<User> list = getAllUser();
        for(User x:list){
            if(x.getUsername().equalsIgnoreCase(u.getUsername()) && x.getPassword().equalsIgnoreCase(u.getPassword())){
                return true;
            }
        }
        return false;
    }

    public List<Schedule> searchByTitle(String key){
        List<Schedule> list = new ArrayList<>();
        String whereClause = "title like ?";
        String[] whereArgs = {"%"+key+"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("schedules",null,whereClause,whereArgs,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Schedule(id,title,category,time,date));
        }
        return list;
    }

    public List<Schedule> searchByCategory(String category){
        List<Schedule> list = new ArrayList<>();
        String whereClause = "category like ?";
        String[] whereArgs = {category};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("schedules",null,whereClause,whereArgs,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Schedule(id,title,category,time,date));
        }
        return list;
    }

    public List<Schedule> searchByDateFromTo(String from, String to){
        List<Schedule> list = new ArrayList<>();
        String whereClause = "date BETWEEN ? AND ?";
        String[] whereArgs = {from.trim(),to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("schedules",null,whereClause,whereArgs,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String time = rs.getString(3);
            String date = rs.getString(4);
            list.add(new Schedule(id,title,category,time,date));
        }
        return list;
    }
    public List<Schedule> searchByTitleandDate(String key,String date){
        List<Schedule> list = new ArrayList<>();
        String whereClause = "title like ? AND date like ?";
        String[] whereArgs = {"%"+key+"%",date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("schedules",null,whereClause,whereArgs,null,null,null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String time = rs.getString(3);
            list.add(new Schedule(id,title,category,time,date));
        }
        return list;
    }
}
