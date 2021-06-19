package com.example.grcwgl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String contents[]={"","本月","本周","1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月","星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
    TextView textView;
    String selectcontents;
    String AccountCategory,IncomePrice,ExpenditurePrice;
    String[] allCategory;
    int mYear,mMonth,mDay;
    DatePicker datePicker;
    Calendar currentDate;
    public static int DateCompare(String startDate, String endDate){
        int i =0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = simpleDateFormat.parse(startDate);//开始时间
            Date date2 = simpleDateFormat.parse(endDate);//结束时间
            if (date2.getTime() < date1.getTime()) {
                //结束时间小于开始时间
                i = 1;
            } else if (date2.getTime() == date1.getTime()) {
                //开始时间与结束时间相同
                i = 2;
            } else if (date2.getTime() > date1.getTime()) {
                //结束时间大于开始时间
                i = 3;
            }
        }catch (Exception e) {
        }
        return  i;
    }


    public static void showDatePickerDialog(Activity activity, int themeResId, final EditText tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year,  int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                if(monthOfYear >= 0 && monthOfYear <= 8){
                    if(dayOfMonth >= 0 && dayOfMonth <= 9){
                        tv.setText(year+"-0"+(monthOfYear+1)+"-0"+dayOfMonth);
                    }else{
                        tv.setText(year+"-0"+(monthOfYear+1)+"-"+dayOfMonth);
                    }
                }else{
                    tv.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                }
            }

        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    /*收入*/
    ArrayList<Map<String,String>> getListIncomeData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getAllAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshListView(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getListIncomeData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入类别查询*/
    ArrayList<Map<String,String>> getSerchCategoryIncomeData(String category){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchCategoryAcount(category);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchCategoryListView(String category){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchCategoryIncomeData(category),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入时间查询*/
    ArrayList<Map<String,String>> getSerchDateIncomeData(String date){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchDateAcount(date);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchDateListView(String date){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchDateIncomeData(date),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入类别及指定日期查询*/
    ArrayList<Map<String,String>> getSerchAllIncomeData(String category,String date){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchAllAcount(category,date);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchAllListView(String category,String date){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchAllIncomeData(category,date),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入本月查询*/
    ArrayList<Map<String,String>> getSerchThisMonthIncomeData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchThisMonthAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisMonthListView(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisMonthIncomeData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入本周查询*/
    ArrayList<Map<String,String>> getSerchThisWeekIncomeData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchThisWeekAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisWeekListView(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisWeekIncomeData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入某月查询*/
    ArrayList<Map<String,String>> getSerchMonthIncomeData(String accountDate){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchMonthAcount(accountDate);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchMonthListView(String accountDate){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchMonthIncomeData(accountDate),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入某周查询*/
    ArrayList<Map<String,String>> getSerchWeekIncomeData(String accountDate){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchWeekAcount(accountDate);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchWeekListView(String accountDate){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchWeekIncomeData(accountDate),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入指定时间段查询*/
    ArrayList<Map<String,String>> getSerchZdsjdIncomeData(String accountDate1,String accountDate2){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchZdsjdAcount(accountDate1,accountDate2);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchZdsjdListView(String accountDate1,String accountDate2){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchZdsjdIncomeData(accountDate1,accountDate2),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入指定时间段及类别查询*/
    ArrayList<Map<String,String>> getSerchZdsjdlbIncomeData(String accountDate1,String accountDate2,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchZdsjdlbAcount(accountDate1,accountDate2,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchZdsjdlbListView(String accountDate1,String accountDate2,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchZdsjdlbIncomeData(accountDate1,accountDate2,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入本月及类别查询*/
    ArrayList<Map<String,String>> getSerchThisMonthlbIncomeData(String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchThisMonthlbAcount(accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisMonthlbListView(String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisMonthlbIncomeData(accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入本周及类别查询*/
    ArrayList<Map<String,String>> getSerchThisWeeklbIncomeData(String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchThisWeeklbAcount(accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisWeeklbListView(String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisWeeklbIncomeData(accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
   /*收入某月及类别查询*/
    ArrayList<Map<String,String>> getSerchMonthlbIncomeData(String accountDate,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchMonthlbAcount(accountDate,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchMonthlbListView(String accountDate,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchMonthlbIncomeData(accountDate,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*收入某周及类别查询*/
    ArrayList<Map<String,String>> getSerchWeeklbIncomeData(String accountDate,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Income(getApplicationContext())).getSerchWeeklbAcount(accountDate,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchWeeklbListView(String accountDate,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchWeeklbIncomeData(accountDate,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="收入";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出*/
    ArrayList<Map<String,String>> getListExpenditureData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getAllAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshList1View(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getListExpenditureData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出类别查询*/
    ArrayList<Map<String,String>> getSerchCategoryExpenditureData(String category){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchCategoryAcount(category);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchCategoryList1View(String category){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchCategoryExpenditureData(category),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出时间查询*/
    ArrayList<Map<String,String>> getSerchDateExpenditureData(String date){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchDateAcount(date);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchDateList1View(String date){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchDateExpenditureData(date),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出类别及指定日期查询*/
    ArrayList<Map<String,String>> getSerchAllExpenditureData(String category,String date){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchAllAcount(category,date);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchAllList1View(String category,String date){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchAllExpenditureData(category,date),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出本月查询*/
    ArrayList<Map<String,String>> getSerchThisMonthExpenditureData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchThisMonthAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisMonthList1View(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisMonthExpenditureData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出本周查询*/
    ArrayList<Map<String,String>> getSerchThisWeekExpenditureData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchThisWeekAcount();
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisWeekList1View(){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisWeekExpenditureData(),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });    }
    /*支出某月查询*/
    ArrayList<Map<String,String>> getSerchMonthExpenditureData(String accountDate){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchMonthAcount(accountDate);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchMonthList1View(String accountDate){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchMonthExpenditureData(accountDate),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出某周查询*/
    ArrayList<Map<String,String>> getSerchWeekExpenditureData(String accountDate){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchWeekAcount(accountDate);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchWeekList1View(String accountDate){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchWeekExpenditureData(accountDate),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出指定时间段查询*/
    ArrayList<Map<String,String>> getSerchZdsjdExpenditureData(String accountDate1,String accountDate2){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchZdsjdAcount(accountDate1,accountDate2);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchZdsjdList1View(String accountDate1,String accountDate2){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchZdsjdExpenditureData(accountDate1,accountDate2),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出指定时间段及类别查询*/
    ArrayList<Map<String,String>> getSerchZdsjdlbExpenditureData(String accountDate1,String accountDate2,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchZdsjdlbAcount(accountDate1,accountDate2,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchZdsjdlbList1View(String accountDate1,String accountDate2,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchZdsjdlbExpenditureData(accountDate1,accountDate2,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出本月及类别查询*/
    ArrayList<Map<String,String>> getSerchThisMonthlbExpenditureData(String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchThisMonthlbAcount(accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisMonthlbList1View(String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisMonthlbExpenditureData(accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出本周及类别查询*/
    ArrayList<Map<String,String>> getSerchThisWeeklbExpenditureData(String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchThisWeeklbAcount(accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchThisWeeklbList1View(String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchThisWeeklbExpenditureData(accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });    }
    /*支出某月及类别查询*/
    ArrayList<Map<String,String>> getSerchMonthlbExpenditureData(String accountDate,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchMonthlbAcount(accountDate,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchMonthlbList1View(String accountDate,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchMonthlbExpenditureData(accountDate,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    /*支出某周及类别查询*/
    ArrayList<Map<String,String>> getSerchWeeklbExpenditureData(String accountDate,String accountCategory){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<Account> accountList=(new Expenditure(getApplicationContext())).getSerchWeeklbAcount(accountDate,accountCategory);
        allCategory=new String[accountList.size()];
        for(int i=0;i<accountList.size();i++){
            Account account=accountList.get(i);
            allCategory[i]=account.getAccountCategory();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",String.valueOf(account.getAccountId()));
            map.put("category",account.getAccountCategory());
            map.put("money",String.valueOf(account.getAccountMoney()));
            map.put("date",account.getAccountDate());
            map.put("remark",account.getAccountRemark());
            list.add(map);
        }
        return list;
    }
    void refreshSerchWeeklbList1View(String accountDate,String accountCategory){
        final ListView listView=findViewById(R.id.listviewinfo);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getSerchWeeklbExpenditureData(accountDate,accountCategory),
                R.layout.itemlayoutinfo,
                new String[]{"id","category","money","date","remark"},
                new int[]{R.id.tv_id,R.id.tv_zmlx,R.id.tv_price,R.id.tv_date,R.id.tv_bz});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView templist=(ListView) parent;
                View view1=templist.getChildAt(position);
                TextView tv_id =view1.findViewById(R.id.tv_id);
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                HashMap<String, String> map = (HashMap<String, String>) listView.getItemAtPosition(position);
                String sid =tv_id.getText().toString();
                String category = map.get("category");
                String money = map.get("money");
                String date = map.get("date");
                String remark = map.get("remark");
                String sz="支出";
                intent.putExtra("sid",sid);
                intent.putExtra("sz",sz);
                intent.putExtra("category",category);
                intent.putExtra("money",money);
                intent.putExtra("date",date);
                intent.putExtra("remark",remark);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Expenditure expenditure=new Expenditure(getApplicationContext());
        final Income income=new Income(getApplicationContext());
        IncomePrice=income.getSerchPrice();
        ExpenditurePrice=expenditure.getSerchPrice();
        textView=findViewById(R.id.textView13);
        textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
        refreshListView();
        Button btn_sr=findViewById(R.id.btn_sr);
        btn_sr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncomePrice=income.getSerchPrice();
                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                refreshListView();
            }
        });
        Button btn_zc=findViewById(R.id.btn_zc);
        btn_zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpenditurePrice=expenditure.getSerchPrice();
                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                refreshList1View();
            }
        });
        Button btn_add =findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
            }
        });
        Button btn_select=findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "注:时间查询仅可选择一项", Toast.LENGTH_SHORT).show();
                View view=getLayoutInflater().inflate(R.layout.serch,null);
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item,contents);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                final Spinner spn_othersj=view.findViewById(R.id.spn_othersj);
                final EditText edit_lb=view.findViewById(R.id.edit_lb);
                final EditText editText2=view.findViewById(R.id.editText2);
                final EditText editText=view.findViewById(R.id.editText);
                final EditText editText7=view.findViewById(R.id.editText7);
                final ImageView imageView3=view.findViewById(R.id.imageView3);
                final ImageView imageView4=view.findViewById(R.id.imageView4);
                final ImageView imageView5=view.findViewById(R.id.imageView5);
                final RadioButton rad_sr=view.findViewById(R.id.rad_sr);
                final RadioButton rad_zc=view.findViewById(R.id.rad_zc);
                currentDate = Calendar.getInstance();
                mYear = currentDate.get(Calendar.YEAR);
                mMonth = currentDate.get(Calendar.MONTH);
                mDay = currentDate.get(Calendar.DAY_OF_MONTH);
                imageView3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(MainActivity.this,  0, editText2, currentDate);;
                    }
                });
                imageView4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(MainActivity.this,  0, editText, currentDate);;
                    }
                });
                imageView5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(MainActivity.this,  0, editText7, currentDate);;
                    }
                });
                spn_othersj.setAdapter(adapter);
                spn_othersj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectcontents=contents[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                AlertDialog dialog=new AlertDialog.Builder(MainActivity.this).setTitle("收支查询（以下查询可单项或多项）")
                        .setView(view)
                        .setPositiveButton("查询", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String lb=edit_lb.getText().toString();
                                String sj=editText2.getText().toString();
                                String sj1=editText.getText().toString();
                                String sj2=editText7.getText().toString();
                                int i = DateCompare(sj1,sj2);
                                if(rad_sr.isChecked()){
                                    if(lb.isEmpty()){
                                        if(sj.isEmpty()){
                                            if(sj1.isEmpty() && sj2.isEmpty()){
                                                if(selectcontents.isEmpty()){
                                                    Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    switch (selectcontents){
                                                        case "本月":
                                                            IncomePrice=income.getSerchThisMonthPrice();
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisMonthListView();
                                                            break;
                                                        case "本周":
                                                            IncomePrice=income.getSerchThisWeekPrice();
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisWeekListView();
                                                            break;
                                                        case "1月":
                                                            IncomePrice=income.getSerchMonthPrice("01");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("01");
                                                            break;
                                                        case "2月":
                                                            IncomePrice=income.getSerchMonthPrice("02");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("02");
                                                            break;
                                                        case "3月":
                                                            IncomePrice=income.getSerchMonthPrice("03");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("03");
                                                            break;
                                                        case "4月":
                                                            IncomePrice=income.getSerchMonthPrice("04");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("04");
                                                            break;
                                                        case "5月":
                                                            IncomePrice=income.getSerchMonthPrice("05");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("05");
                                                            break;
                                                        case "6月":
                                                            IncomePrice=income.getSerchMonthPrice("06");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("06");
                                                            break;
                                                        case "7月":
                                                            IncomePrice=income.getSerchMonthPrice("07");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("07");
                                                            break;
                                                        case "8月":
                                                            IncomePrice=income.getSerchMonthPrice("08");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("08");
                                                            break;
                                                        case "9月":
                                                            IncomePrice=income.getSerchMonthPrice("09");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("09");
                                                            break;
                                                        case "10月":
                                                            IncomePrice=income.getSerchMonthPrice("10");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("10");
                                                            break;
                                                        case "11月":
                                                            IncomePrice=income.getSerchMonthPrice("11");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("11");
                                                            break;
                                                        case "12月":
                                                            IncomePrice=income.getSerchMonthPrice("12");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthListView("12");
                                                            break;
                                                        case "星期一":
                                                            IncomePrice=income.getSerchWeekPrice("1");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("1");
                                                            break;
                                                        case "星期二":
                                                            IncomePrice=income.getSerchWeekPrice("2");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("2");
                                                            break;
                                                        case "星期三":
                                                            IncomePrice=income.getSerchWeekPrice("3");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("3");
                                                            break;
                                                        case "星期四":
                                                            IncomePrice=income.getSerchWeekPrice("4");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("4");
                                                            break;
                                                        case "星期五":
                                                            IncomePrice=income.getSerchWeekPrice("5");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("5");
                                                            break;
                                                        case "星期六":
                                                            IncomePrice=income.getSerchWeekPrice("6");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("6");
                                                            break;
                                                        case "星期日":
                                                            IncomePrice=income.getSerchWeekPrice("0");
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeekListView("0");
                                                            break;
                                                    }
                                                }
                                            }else if(sj1.isEmpty() || sj2.isEmpty()){
                                                Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(i == 1){
                                                    Toast.makeText(MainActivity.this, "结束日期不能小于起始日期", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    IncomePrice=income.getSerchZdsjdPrice(sj1,sj2);
                                                    textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                    refreshSerchZdsjdListView(sj1,sj2);
                                                }
                                            }
                                        }else{
                                            IncomePrice=income.getSerchDatePrice(sj);
                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                            refreshSerchDateListView(sj);
                                        }
                                    }else{
                                        if(sj.isEmpty()) {
                                            if (sj1.isEmpty() && sj2.isEmpty()) {
                                                if (selectcontents.isEmpty()) {
                                                    IncomePrice = income.getSerchCategoryPrice(lb);
                                                    textView.setText("收入 ￥" + IncomePrice + "\n" + "支出 ￥" + ExpenditurePrice);
                                                    refreshSerchCategoryListView(lb);
                                                } else {
                                                    switch (selectcontents){
                                                        case "本月":
                                                            IncomePrice=income.getSerchThisMonthlbPrice(lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisMonthlbListView(lb);
                                                            break;
                                                        case "本周":
                                                            IncomePrice=income.getSerchThisWeeklbPrice(lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisWeeklbListView(lb);
                                                            break;
                                                        case "1月":
                                                            IncomePrice=income.getSerchMonthlbPrice("01",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("01",lb);
                                                            break;
                                                        case "2月":
                                                            IncomePrice=income.getSerchMonthlbPrice("02",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("02",lb);
                                                            break;
                                                        case "3月":
                                                            IncomePrice=income.getSerchMonthlbPrice("03",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("03",lb);
                                                            break;
                                                        case "4月":
                                                            IncomePrice=income.getSerchMonthlbPrice("04",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("04",lb);
                                                            break;
                                                        case "5月":
                                                            IncomePrice=income.getSerchMonthlbPrice("05",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("05",lb);
                                                            break;
                                                        case "6月":
                                                            IncomePrice=income.getSerchMonthlbPrice("06",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("06",lb);
                                                            break;
                                                        case "7月":
                                                            IncomePrice=income.getSerchMonthlbPrice("07",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("07",lb);
                                                            break;
                                                        case "8月":
                                                            IncomePrice=income.getSerchMonthlbPrice("08",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("08",lb);
                                                            break;
                                                        case "9月":
                                                            IncomePrice=income.getSerchMonthlbPrice("09",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("09",lb);
                                                            break;
                                                        case "10月":
                                                            IncomePrice=income.getSerchMonthlbPrice("10",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("10",lb);
                                                            break;
                                                        case "11月":
                                                            IncomePrice=income.getSerchMonthlbPrice("11",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("11",lb);
                                                            break;
                                                        case "12月":
                                                            IncomePrice=income.getSerchMonthlbPrice("12",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbListView("12",lb);
                                                            break;
                                                        case "星期一":
                                                            IncomePrice=income.getSerchWeeklbPrice("1",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("1",lb);
                                                            break;
                                                        case "星期二":
                                                            IncomePrice=income.getSerchWeeklbPrice("2",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("2",lb);
                                                            break;
                                                        case "星期三":
                                                            IncomePrice=income.getSerchWeeklbPrice("3",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("3",lb);
                                                            break;
                                                        case "星期四":
                                                            IncomePrice=income.getSerchWeeklbPrice("4",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("4",lb);
                                                            break;
                                                        case "星期五":
                                                            IncomePrice=income.getSerchWeeklbPrice("5",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("5",lb);
                                                            break;
                                                        case "星期六":
                                                            IncomePrice=income.getSerchWeeklbPrice("6",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("6",lb);
                                                            break;
                                                        case "星期日":
                                                            IncomePrice=income.getSerchWeeklbPrice("0",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbListView("0",lb);
                                                            break;
                                                    }
                                                }
                                            }else if (sj1.isEmpty() || sj2.isEmpty()){
                                                Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(i ==1){
                                                    Toast.makeText(MainActivity.this, "结束日期不能小于起始日期", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    IncomePrice=income.getSerchZdsjdlbPrice(sj1,sj2,lb);
                                                    textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                    refreshSerchZdsjdlbListView(sj1,sj2,lb);
                                                }
                                            }
                                        }else{
                                            IncomePrice=income.getSerchAllPrice(lb,sj);
                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                            refreshSerchAllListView(lb,sj);
                                        }
                                    }
                                }else if(rad_zc.isChecked()){
                                    if(lb.isEmpty()){
                                        if(sj.isEmpty()){
                                            if(sj1.isEmpty() && sj2.isEmpty()){
                                                    if(selectcontents.isEmpty()){
                                                        Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        switch (selectcontents){
                                                            case "本月":
                                                                ExpenditurePrice=expenditure.getSerchThisMonthPrice();
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchThisMonthList1View();
                                                                break;
                                                            case "本周":
                                                                ExpenditurePrice=expenditure.getSerchThisWeekPrice();
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchThisWeekList1View();
                                                                break;
                                                            case "1月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("01");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("01");
                                                                break;
                                                            case "2月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("02");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("02");
                                                                break;
                                                            case "3月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("03");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("03");
                                                                break;
                                                            case "4月":
                                                                ExpenditurePrice=income.getSerchMonthPrice("04");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("04");
                                                                break;
                                                            case "5月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("05");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("05");
                                                                break;
                                                            case "6月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("06");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("06");
                                                                break;
                                                            case "7月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("07");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("07");
                                                                break;
                                                            case "8月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("08");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("08");
                                                                break;
                                                            case "9月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("09");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("09");
                                                                break;
                                                            case "10月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("10");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("10");
                                                                break;
                                                            case "11月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("11");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("11");
                                                                break;
                                                            case "12月":
                                                                ExpenditurePrice=expenditure.getSerchMonthPrice("12");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchMonthList1View("12");
                                                                break;
                                                            case "星期一":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("1");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("1");
                                                                break;
                                                            case "星期二":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("2");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("2");
                                                                break;
                                                            case "星期三":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("3");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("3");
                                                                break;
                                                            case "星期四":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("4");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("4");
                                                                break;
                                                            case "星期五":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("5");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("5");
                                                                break;
                                                            case "星期六":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("6");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("6");
                                                                break;
                                                            case "星期日":
                                                                ExpenditurePrice=expenditure.getSerchWeekPrice("0");
                                                                textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                                refreshSerchWeekList1View("0");
                                                                break;
                                                        }
                                                    }
                                            }else if(sj1.isEmpty() || sj2.isEmpty()){
                                                Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(i == 1){
                                                    Toast.makeText(MainActivity.this, "结束日期不能小于起始日期", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    ExpenditurePrice=expenditure.getSerchZdsjdPrice(sj1,sj2);
                                                    textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                    refreshSerchZdsjdList1View(sj1,sj2);
                                                }
                                            }
                                        }else{
                                            ExpenditurePrice=expenditure.getSerchDatePrice(sj);
                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                            refreshSerchDateList1View(sj);
                                        }
                                    }else{
                                        if(sj.isEmpty()) {
                                            if (sj1.isEmpty() && sj2.isEmpty()) {
                                                if (selectcontents.isEmpty()) {
                                                    ExpenditurePrice = expenditure.getSerchCategoryPrice(lb);
                                                    textView.setText("收入 ￥" + IncomePrice + "\n" + "支出 ￥" + ExpenditurePrice);
                                                    refreshSerchCategoryList1View(lb);
                                                } else {
                                                    switch (selectcontents){
                                                        case "本月":
                                                            ExpenditurePrice=expenditure.getSerchThisMonthlbPrice(lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisMonthlbList1View(lb);
                                                            break;
                                                        case "本周":
                                                            ExpenditurePrice=expenditure.getSerchThisWeeklbPrice(lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchThisWeeklbList1View(lb);
                                                            break;
                                                        case "1月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("01",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("01",lb);
                                                            break;
                                                        case "2月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("02",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("02",lb);
                                                            break;
                                                        case "3月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("03",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("03",lb);
                                                            break;
                                                        case "4月":
                                                            ExpenditurePrice=income.getSerchMonthlbPrice("04",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("04",lb);
                                                            break;
                                                        case "5月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("05",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("05",lb);
                                                            break;
                                                        case "6月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("06",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("06",lb);
                                                            break;
                                                        case "7月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("07",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("07",lb);
                                                            break;
                                                        case "8月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("08",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("08",lb);
                                                            break;
                                                        case "9月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("09",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("09",lb);
                                                            break;
                                                        case "10月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("10",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("10",lb);
                                                            break;
                                                        case "11月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("11",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("11",lb);
                                                            break;
                                                        case "12月":
                                                            ExpenditurePrice=expenditure.getSerchMonthlbPrice("12",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchMonthlbList1View("12",lb);
                                                            break;
                                                        case "星期一":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("1",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("1",lb);
                                                            break;
                                                        case "星期二":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("2",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("2",lb);
                                                            break;
                                                        case "星期三":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("3",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("3",lb);
                                                            break;
                                                        case "星期四":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("4",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("4",lb);
                                                            break;
                                                        case "星期五":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("5",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("5",lb);
                                                            break;
                                                        case "星期六":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("6",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("6",lb);
                                                            break;
                                                        case "星期日":
                                                            ExpenditurePrice=expenditure.getSerchWeeklbPrice("0",lb);
                                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                            refreshSerchWeeklbList1View("0",lb);
                                                            break;
                                                    }
                                                }
                                            }else if (sj1.isEmpty() || sj2.isEmpty()){
                                                Toast.makeText(MainActivity.this, "请确认信息完整性", Toast.LENGTH_SHORT).show();
                                            }else{
                                                if(i ==1){
                                                    Toast.makeText(MainActivity.this, "结束日期不能小于起始日期", Toast.LENGTH_SHORT).show();
                                                }else{
                                                    ExpenditurePrice=expenditure.getSerchZdsjdlbPrice(sj1,sj2,lb);
                                                    textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                                    refreshSerchZdsjdlbList1View(sj1,sj2,lb);
                                                }
                                            }
                                        }else{
                                            ExpenditurePrice=expenditure.getSerchAllPrice(lb,sj);
                                            textView.setText("收入 ￥"+IncomePrice+"\n"+"支出 ￥"+ExpenditurePrice);
                                            refreshSerchAllList1View(lb,sj);
                                        }
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "请选择收支类型", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).create();
                dialog.show();
            }
        });
    }
}
