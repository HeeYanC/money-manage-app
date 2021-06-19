package com.example.grcwgl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Income extends SQLiteOpenHelper {
    public static final String DB_NAME="income.db";
    public static final String TABLE_NAME="incomeinfo";
    public static final String COLUMN_INCOMEID="id";
    public static final String COLUMN_INCOMECATEGORY="category";
    public static final String COLUMN_INCOMEMONEY="money";
    public static final String COLUMN_INCOMEDATE="date";
    public static final String COLUMN_INCOMEREMARK="remark";
    private static final String CREATE_TABLE= "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_INCOMEID + " INTEGER not null primary key autoincrement ,"
            + COLUMN_INCOMECATEGORY + " text not null ,"
            + COLUMN_INCOMEMONEY + " decimal(10,2) not null," +" text,"
            + COLUMN_INCOMEDATE +" date not null,"
            +COLUMN_INCOMEREMARK+" integer)";

    public Income(Context context){
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public ArrayList<Account> getAllAcount(){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,
                new String[]{COLUMN_INCOMEID,COLUMN_INCOMECATEGORY,COLUMN_INCOMEMONEY,COLUMN_INCOMEDATE,COLUMN_INCOMEREMARK},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public long addAcount(Account account){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_INCOMECATEGORY,account.getAccountCategory());
        contentValues.put(COLUMN_INCOMEMONEY,account.getAccountMoney());
        contentValues.put(COLUMN_INCOMEDATE,account.getAccountDate());
        contentValues.put(COLUMN_INCOMEREMARK,account.getAccountRemark());
        return db.insert(TABLE_NAME,null,contentValues);
    }
    public void deleteAccount(String accountId){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_INCOMEID+"=?",new String[]{accountId});
    }
    public long updateACCOUNT(Account account){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_INCOMEID,account.getAccountId());
        contentValues.put(COLUMN_INCOMECATEGORY,account.getAccountCategory());
        contentValues.put(COLUMN_INCOMEMONEY,account.getAccountMoney());
        contentValues.put(COLUMN_INCOMEDATE,account.getAccountDate());
        contentValues.put(COLUMN_INCOMEREMARK,account.getAccountRemark());
        return db.update(TABLE_NAME,contentValues,COLUMN_INCOMEID+"=?",new String[]{String.valueOf(account.getAccountId())});
    }
    public ArrayList<Account> getSerchCategoryAcount(String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public ArrayList<Account> getSerchDateAcount(String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where date = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public ArrayList<Account> getSerchAllAcount(String accountCategory,String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where category = ? and date = ? ", new String[]{accountCategory,accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo ",null);
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    public String getSerchCategoryPrice(String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where category = ? ",new String[]{accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    public String getSerchDatePrice(String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where date = ? ",new String[]{accountDate});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    public String getSerchAllPrice(String accountCategory,String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where category = ? and date = ? ",new String[]{accountCategory,accountDate});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询本月数据*/
    public ArrayList<Account> getSerchThisMonthAcount(){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') ", null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisMonthPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') ",null);
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询本周数据*/
    public ArrayList<Account> getSerchThisWeekAcount(){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) ", null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisWeekPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) ",null);
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询指定时间段数据*/
    public ArrayList<Account> getSerchZdsjdAcount(String accountDate1,String accountDate2){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where date>= ? and date <= ? ", new String[]{accountDate1,accountDate2});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchZdsjdPrice(String accountDate1,String accountDate2){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where date>= ? and date <= ? ",new String[]{accountDate1,accountDate2});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询某周数据*/
    public ArrayList<Account> getSerchWeekAcount(String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%w',date) = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchWeekPrice(String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%w',date) = ? ",new String[]{accountDate});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询某月数据*/
    public ArrayList<Account> getSerchMonthAcount(String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%m',date) = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchMonthPrice(String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%m',date) = ? ",new String[]{accountDate});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询指定时间段及类别数据*/
    public ArrayList<Account> getSerchZdsjdlbAcount(String accountDate1,String accountDate2,String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where date>= ? and date <= ? and category = ? ", new String[]{accountDate1,accountDate2,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchZdsjdlbPrice(String accountDate1,String accountDate2,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where date>= ? and date <= ? and category = ? ", new String[]{accountDate1,accountDate2,accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询本月及类别数据*/
    public ArrayList<Account> getSerchThisMonthlbAcount(String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') and category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisMonthlbPrice(String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') and category = ? ", new String[]{accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询本周及类别数据*/
    public ArrayList<Account> getSerchThisWeeklbAcount(String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) and category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisWeeklbPrice(String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) and category = ? ", new String[]{accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询某周及类别数据*/
    public ArrayList<Account> getSerchWeeklbAcount(String accountDate,String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%w',date) = ? and category = ? ", new String[]{accountDate,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchWeeklbPrice(String accountDate,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%w',date) = ? and category = ? ",new String[]{accountDate,accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }
    /*查询某月及类别数据*/
    public ArrayList<Account> getSerchMonthlbAcount(String accountDate,String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from incomeinfo where strftime('%m',date) = ? and category = ? ", new String[]{accountDate,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_INCOMEID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_INCOMEREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchMonthlbPrice(String accountDate,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from incomeinfo where strftime('%m',date) = ? and category = ? ",new String[]{accountDate,accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }


}
