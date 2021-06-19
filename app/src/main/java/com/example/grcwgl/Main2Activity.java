package com.example.grcwgl;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    EditText edt_type,edt_pricr,edt_date,edt_bz;
    ImageView iv_date;
    int mYear,mMonth,mDay;
    DatePicker datePicker;
    Calendar currentDate;
    String[] months={"01","02","03","04","05","06","07", "08","09", "10", "11", "12"};
    private String contents[]={"支出","收入"};
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
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        edt_type=findViewById(R.id.edt_type);
        edt_pricr=findViewById(R.id.edt_pricr);
        edt_date=findViewById(R.id.edt_date);
        edt_bz=findViewById(R.id.edt_bz);
        currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        if(mMonth >= 0 && mMonth <= 8){
            if(mDay >= 0 && mDay <= 9){
                edt_date.setText(mYear+"-0"+(mMonth+1)+"-0"+mDay);
            }else{
                edt_date.setText(mYear+"-0"+(mMonth+1)+"-"+mDay);
            }
        }else{
            edt_date.setText(mYear+"-"+(mMonth+1)+"-"+mDay);
        }
        ImageView imageView3=findViewById(R.id.imageView3);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(Main2Activity.this,  0, edt_date, currentDate);;
            }
        });
        final RadioButton rad_sr=findViewById(R.id.rad_sr);
        final RadioButton rad_zc=findViewById(R.id.rad_zc);
        Button btn_add=findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account account = new Account();
                account.setAccountCategory(edt_type.getText().toString());
                account.setAccountMoney(edt_pricr.getText().toString());
                account.setAccountDate(edt_date.getText().toString());
                account.setAccountRemark(edt_bz.getText().toString());
                if(edt_type.getText().toString().isEmpty() || edt_pricr.getText().toString().isEmpty() || edt_date.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "请确认信息完整性", Toast.LENGTH_SHORT).show();
                }else {
                    if (rad_sr.isChecked()) {
                        Income income = new Income(getApplicationContext());
                        income.addAcount(account);
                        Intent intent;
                        ArrayList<Account> list = new ArrayList<>();
                        list.add(account);
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else if (rad_zc.isChecked()) {
                        Expenditure expenditure = new Expenditure(getApplicationContext());
                        expenditure.addAcount(account);
                        Intent intent;
                        ArrayList<Account> list = new ArrayList<>();
                        list.add(account);
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "请选择收支类型", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
