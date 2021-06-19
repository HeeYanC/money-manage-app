package com.example.grcwgl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Expenditure extends SQLiteOpenHelper {
    public static final String DB_NAME="expenditure.db";
    public static final String TABLE_NAME="expenditureeinfo";
    public static final String COLUMN_EXPENDITUREID="id";
    public static final String COLUMN_EXPENDITURECATEGORY="category";
    public static final String COLUMN_EXPENDITUREMONEY="money";
    public static final String COLUMN_EXPENDITUREEDATE="date";
    public static final String COLUMN_EXPENDITUREREMARK="remark";
    private static final String CREATE_TABLE= "create table if not exists "
            + TABLE_NAME + "(" + COLUMN_EXPENDITUREID + " INTEGER not null primary key autoincrement ,"
            + COLUMN_EXPENDITURECATEGORY + " text not null ,"
            + COLUMN_EXPENDITUREMONEY + " DECIMAL(10,2) not null ,"
            + COLUMN_EXPENDITUREEDATE +" date not null,"
            +COLUMN_EXPENDITUREREMARK+" text)";

    public Expenditure(Context context){
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
                new String[]{COLUMN_EXPENDITUREID,COLUMN_EXPENDITURECATEGORY,COLUMN_EXPENDITUREMONEY,COLUMN_EXPENDITUREEDATE,COLUMN_EXPENDITUREREMARK},
                null,
                null,
                null,
                null,
                null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public long addAcount(Account account){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_EXPENDITURECATEGORY,account.getAccountCategory());
        contentValues.put(COLUMN_EXPENDITUREMONEY,account.getAccountMoney());
        contentValues.put(COLUMN_EXPENDITUREEDATE,account.getAccountDate());
        contentValues.put(COLUMN_EXPENDITUREREMARK,account.getAccountRemark());
        return db.insert(TABLE_NAME,null,contentValues);
    }
    public void deleteAccount(String accountId){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(TABLE_NAME,COLUMN_EXPENDITUREID+"=?",new String[]{accountId});
    }
    public long updateACCOUNT(Account account){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COLUMN_EXPENDITUREID,account.getAccountId());
        contentValues.put(COLUMN_EXPENDITURECATEGORY,account.getAccountCategory());
        contentValues.put(COLUMN_EXPENDITUREMONEY,account.getAccountMoney());
        contentValues.put(COLUMN_EXPENDITUREEDATE,account.getAccountDate());
        contentValues.put(COLUMN_EXPENDITUREREMARK,account.getAccountRemark());
        return db.update(TABLE_NAME,contentValues,COLUMN_EXPENDITUREID+"=?",new String[]{String.valueOf(account.getAccountId())});
    }
    public ArrayList<Account> getSerchCategoryAcount(String accountCategory){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public ArrayList<Account> getSerchDateAcount(String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where date = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public ArrayList<Account> getSerchAllAcount(String accountCategory,String accountDate){
        ArrayList<Account> list=new ArrayList<Account>();
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where category = ? and date = ? ", new String[]{accountCategory,accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo ",null);
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
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where category = ? ",new String[]{accountCategory});
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
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where date = ? ",new String[]{accountDate});
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
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where category = ? and date = ? ",new String[]{accountCategory,accountDate});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') ", null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisMonthPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') ",null);
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) ", null);
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisWeekPrice(){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) ",null);
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where date>= ? and date <= ? ", new String[]{accountDate1,accountDate2});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchZdsjdPrice(String accountDate1,String accountDate2){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where date>= ? and date <= ? ",new String[]{accountDate1,accountDate2});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%w',date) = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchWeekPrice(String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%w',date) = ? ",new String[]{accountDate});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%m',date) = ? ", new String[]{accountDate});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchMonthPrice(String accountDate){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%m',date) = ? ",new String[]{accountDate});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where date>= ? and date <= ? and category = ? ", new String[]{accountDate1,accountDate2,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchZdsjdlbPrice(String accountDate1,String accountDate2,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where date>= ? and date <= ? and category = ? ", new String[]{accountDate1,accountDate2,accountCategory});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') and category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisMonthlbPrice(String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%Y',date) = strftime('%Y','now') and strftime('%m',date) = strftime('%m','now') and category = ? ", new String[]{accountCategory});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) and category = ? ", new String[]{accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchThisWeeklbPrice(String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where date>=date(datetime('now','start of day','-7 day','weekday 1')) and date < date(datetime('now','start of day','+0 day','weekday 1')) and category = ? ", new String[]{accountCategory});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%w',date) = ? and category = ? ", new String[]{accountDate,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchWeeklbPrice(String accountDate,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%w',date) = ? and category = ? ",new String[]{accountDate,accountCategory});
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
        Cursor cursor=db.rawQuery("select * from expenditureeinfo where strftime('%m',date) = ? and category = ? ", new String[]{accountDate,accountCategory});
        while(cursor.moveToNext()){
            Account account =new Account();
            account.setAccountId(cursor.getInt(cursor.getColumnIndex(COLUMN_EXPENDITUREID)));
            account.setAccountCategory(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITURECATEGORY)));
            account.setAccountMoney(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREMONEY)));
            account.setAccountDate(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREEDATE)));
            account.setAccountRemark(cursor.getString(cursor.getColumnIndex(COLUMN_EXPENDITUREREMARK)));
            list.add(account);
        }
        return list;
    }
    public String getSerchMonthlbPrice(String accountDate,String accountCategory){
        SQLiteDatabase db=getReadableDatabase();
        String price="";
        Cursor cursor = db.rawQuery("select sum(money) from expenditureeinfo where strftime('%m',date) = ? and category = ? ",new String[]{accountDate,accountCategory});
        if(cursor.moveToNext()){
            price=cursor.getString(0);
        }
        if(cursor.getString(0)==null){
            price="0";
        }
        return price;
    }



}
