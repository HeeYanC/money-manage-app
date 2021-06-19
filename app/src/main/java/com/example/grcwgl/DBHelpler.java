package com.example.grcwgl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelpler extends SQLiteOpenHelper {
    public static final String DB_NAME="Test.db";
    public static final String TABLE_NAME="userinfo";
    public static final String COLUMN_USERID="id";
    public static final String COLUMN_USERPWD="pwd";
    public static final String COLUMN_USERICON="icon";
    public static final String COLUMN_USEREMAIL="email";
    private static final String CREATE_TABLE= "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_USERID + " text not null primary key,"
            + COLUMN_USERPWD + " text not null,"
            + COLUMN_USEREMAIL +" text,"
            +COLUMN_USERICON+" integer)";

    public DBHelpler(Context context){
        super(context,DB_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            db.execSQL("insert into "+TABLE_NAME+" values('admin','admin','admin@qq.com',0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public ArrayList<User> getAllUser(){
        ArrayList<User> list=new ArrayList<User>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,
                new String[]{COLUMN_USERID,COLUMN_USERPWD,COLUMN_USERICON,COLUMN_USEREMAIL},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            User user=new User();
            user.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USERID)));
            user.setUserPwd(cursor.getString(cursor.getColumnIndex(COLUMN_USERPWD)));
            user.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USEREMAIL)));
            user.setUserIcon(cursor.getInt(cursor.getColumnIndex(COLUMN_USERICON)));
            list.add(user);
        }
        return list;
    }
    public User userLogin(String userId,String userPwd){
        User user=null;
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,
                new String[]{COLUMN_USERID,COLUMN_USERPWD,COLUMN_USERICON,COLUMN_USEREMAIL},
                COLUMN_USERID+"=? and "+COLUMN_USERPWD+"=?",
                new String[]{userId,userPwd},
                null,
                null,
                null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            user=new User();
            user.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USERID)));
            user.setUserPwd(cursor.getString(cursor.getColumnIndex(COLUMN_USERPWD)));
            user.setUserEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USEREMAIL)));
            user.setUserIcon(cursor.getInt(cursor.getColumnIndex(COLUMN_USERICON)));
        }
        return user;
    }
    public long registerUser(User user){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_USERID,user.getUserId());
        contentValues.put(COLUMN_USERPWD,user.getUserPwd());
        contentValues.put(COLUMN_USEREMAIL,user.getUserEmail());
        contentValues.put(COLUMN_USERICON,user.getUserIcon());
        return db.insert(TABLE_NAME,null,contentValues);
    }
    public void resetUserPwd(String userId,String userPwd){
        SQLiteDatabase db=getWritableDatabase();
        String sql="update "+TABLE_NAME+" set "+COLUMN_USERPWD+" ='"+userPwd+"' where "
                +COLUMN_USERID+" ='"+userId+"'";
        db.execSQL(sql);
    }
    public void deleteUser(String userId){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_USERID+"=?",new String[]{userId});
    }
    public long updateUser(User user){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_USERID,user.getUserId());
        contentValues.put(COLUMN_USERPWD,user.getUserPwd());
        contentValues.put(COLUMN_USEREMAIL,user.getUserEmail());
        contentValues.put(COLUMN_USERICON,user.getUserIcon());
        return db.update(TABLE_NAME,contentValues,COLUMN_USERID+"=?",new String[]{user.getUserId()});
    }
}
