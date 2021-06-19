package com.example.grcwgl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateInfoActivity extends AppCompatActivity {
    EditText edt_modid,edt_modpwd,edt_modtype,edt_modemail;
    ImageView imageIcon;
    int iconResourceId=0;
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        Intent intent=getIntent();
        ArrayList<User> list = intent.getParcelableArrayListExtra("loginUser");
        user=list.get(0);
        edt_modid=findViewById(R.id.edt_modid);
        edt_modid.setText(user.getUserId());
        edt_modpwd=findViewById(R.id.edt_modpwd);
        edt_modpwd.setText(user.getUserPwd());
        edt_modemail=findViewById(R.id.edt_modemail);
        edt_modemail.setText(user.getUserEmail());
        imageIcon=findViewById(R.id.img_modicon);
        iconResourceId=user.getUserIcon();
        imageIcon.setImageResource(iconResourceId);
        Button btn_mod_changeicon=findViewById(R.id.btn_mod_changeicon);
        btn_mod_changeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectIconActivity.class);
                intent.putExtra("iconResourceId",iconResourceId);
                startActivityForResult(intent,1);
            }
        });
        Button btn_modsubmit=findViewById(R.id.btn_modsubmit);
        btn_modsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(UpdateInfoActivity.this)
                        .setTitle("更新用户")
                        .setMessage("确认更新用户信息？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                                User user=new User();
                                user.setUserId(edt_modid.getText().toString());
                                user.setUserPwd(edt_modpwd.getText().toString());
                                user.setUserEmail(edt_modemail.getText().toString());
                                user.setUserIcon(iconResourceId);
                                if(dbHelpler.updateUser(user)>0){
                                    Toast.makeText(getApplicationContext(),user.getUserId()+"用户更新成功！",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),user.getUserId()+"用户更新失败！",Toast.LENGTH_SHORT).show();
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
        Button btn_deleteId=findViewById(R.id.btn_deleteId);
        btn_deleteId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog=new AlertDialog.Builder(UpdateInfoActivity.this)
                        .setTitle("销毁账户")
                        .setMessage("确认销毁该账户？该操作不可撤销！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                                dbHelpler.deleteUser(edt_modid.getText().toString());
                                Intent intent1=new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent1);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                iconResourceId= data.getIntExtra("iconResourceId",R.drawable.tx1);
                imageIcon.setImageResource(iconResourceId);
            }
        }
    }
}
