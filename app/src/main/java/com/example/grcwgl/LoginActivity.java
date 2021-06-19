package com.example.grcwgl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class LoginActivity extends AppCompatActivity {
    EditText edt_loginuserid,edt_loginpwd;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SQLiteStudioService.instance().start(this);
        edt_loginuserid=findViewById(R.id.edt_loginuserid);
        edt_loginpwd=findViewById(R.id.edt_loginpwd);
        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String userId=edt_loginuserid.getText().toString();
                    String userPwd=edt_loginpwd.getText().toString();
                    DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                    User user=dbHelpler.userLogin(userId,userPwd);
                    if(user!=null){
                        Intent intent;
                        ArrayList<User> list=new ArrayList<>();
                        list.add(user);
                        if(userId.equals("admin")){
                            intent=new Intent(getApplicationContext(),AdminMainActivity.class);
                            intent.putParcelableArrayListExtra("loginUser",list);
                            startActivity(intent);
                        }else{
                            intent=new Intent(getApplicationContext(),PersonalMainActivity.class);
                            intent.putParcelableArrayListExtra("loginUser",list);
                            startActivity(intent);
                        }
                        Toast.makeText(getApplicationContext(),user.getUserId()+"登录成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"账号或密码错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"数据库访问异常！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
