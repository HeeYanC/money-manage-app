package com.example.grcwgl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminMainActivity extends AppCompatActivity {
    String userId;
    String selectId="";
    String[] allId;
    User user=null;

    ArrayList<Map<String,String>> getListData(){
        ArrayList<Map<String,String>> list=new ArrayList<>();
        ArrayList<User> userList=(new DBHelpler(getApplicationContext())).getAllUser();
        allId=new String[userList.size()];
        for(int i=0;i<userList.size();i++){
            User user=userList.get(i);
            allId[i]=user.getUserId();
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",user.getUserId());
            map.put("pwd",user.getUserPwd());
            map.put("email",user.getUserEmail());
            map.put("icon",String.valueOf(user.getUserIcon()));
            list.add(map);
        }
        return list;
    }
    void refreshListView(){
        final ListView listView=findViewById(R.id.listviewallusers);
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),
                getListData(),
                R.layout.alluserinfoitemlayout,
                new String[]{"id","pwd","email","icon"},
                new int[]{R.id.tv_uid,R.id.tv_upwd,R.id.tv_uemail,R.id.img_uicon});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectId=allId[position];
                TextView tv_selectUserId=findViewById(R.id.tv_selectUserId);
                tv_selectUserId.setText(selectId);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Intent intent=getIntent();
        ArrayList<User> list =intent.getParcelableArrayListExtra("loginUser");
        user=list.get(0);
        String result="这是管理员主页，欢迎你，"+user.getUserId();
        TextView tv_adminmain=findViewById(R.id.tv_adminmain);
        tv_adminmain.setText(result);
        refreshListView();
        Button btn_adminresetpwd=findViewById(R.id.btn_adminresetpwd);
        btn_adminresetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(AdminMainActivity.this).setTitle("重置密码").setMessage("确认重置该用户密码？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                                dbHelpler.resetUserPwd(selectId,"112233");
                                refreshListView();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).create();
                dialog.show();
            }
        });
        Button btn_admin_deleteuser=findViewById(R.id.btn_admin_deleteuser);
        btn_admin_deleteuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(AdminMainActivity.this).setTitle("删除用户").setMessage("确认删除该用户？该操作不可撤销！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                                dbHelpler.deleteUser(selectId);
                                refreshListView();
                                TextView tv_selectUserId=findViewById(R.id.tv_selectUserId);
                                tv_selectUserId.setText("");
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
