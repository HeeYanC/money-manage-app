package com.example.grcwgl;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Main3Activity extends MainActivity {
    EditText editText3,editText4,editText5,editText6;
    TextView tv_szlx;
    int selectID =-1;
    Account account=null;
    int mYear,mMonth,mDay;
    DatePicker datePicker;
    Calendar currentDate;
    public static void showDatePickerDialog(Activity activity, int themeResId, final EditText tv, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity , themeResId,new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器(How the parent is notified that the date is set.)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        final Intent intent =getIntent();
        final String sz=intent.getStringExtra("sz");
        final String sid=intent.getStringExtra("sid");
        String category=intent.getStringExtra("category");
        String money=intent.getStringExtra("money");
        String date=intent.getStringExtra("date");
        String remark=intent.getStringExtra("remark");
        tv_szlx=findViewById(R.id.tv_szlx);
        tv_szlx.setText(sz);
        editText3=findViewById(R.id.editText3);
        editText3.setText(category);
        editText4=findViewById(R.id.editText4);
        editText4.setText(money);
        editText5=findViewById(R.id.editText5);
        editText5.setText(date);
        editText6=findViewById(R.id.editText6);
        editText6.setText(remark);
        selectID=Integer.parseInt(sid);
        currentDate = Calendar.getInstance();
        ImageView imageView3=findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(Main3Activity.this,  0, editText5, currentDate);;
            }
        });
        Button button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenditure expenditure=new Expenditure(getApplicationContext());
                Income income=new Income(getApplicationContext());
                Account account=new Account();
                account.setAccountId(selectID);
                account.setAccountMoney(editText4.getText().toString());
                account.setAccountCategory(editText3.getText().toString());
                account.setAccountDate(editText5.getText().toString());
                account.setAccountRemark(editText6.getText().toString());
                if(editText4.getText().toString().isEmpty() || editText3.getText().toString().isEmpty() || editText5.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请确认信息完整性", Toast.LENGTH_SHORT).show();
                }else {
                    if (sz.equals("收入")) {
                        income.updateACCOUNT(account);
                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        expenditure.updateACCOUNT(account);
                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Button button2=findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenditure expenditure=new Expenditure(getApplicationContext());
                Income income=new Income(getApplicationContext());
                if (sz.equals("收入")){
                    income.deleteAccount(sid);
                }else {
                    expenditure.deleteAccount(sid);
                }
                Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
                Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
