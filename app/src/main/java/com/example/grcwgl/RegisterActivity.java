package com.example.grcwgl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_reguserid,edt_reguserpwd,edt_regemail;
    int iconResourceId=R.drawable.tx1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edt_reguserid=findViewById(R.id.edt_reguserid);
        edt_reguserpwd=findViewById(R.id.edt_reguserpwd);
        edt_regemail=findViewById(R.id.edt_regemail);
        imageView =findViewById(R.id.imageView);
        Button btn_registersubmit=findViewById(R.id.btn_registersubmit);
        btn_registersubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setUserId(edt_reguserid.getText().toString());
                user.setUserPwd(edt_reguserpwd.getText().toString());
                user.setUserEmail(edt_regemail.getText().toString());
                user.setUserIcon(iconResourceId);
                DBHelpler dbHelpler=new DBHelpler(getApplicationContext());
                if(dbHelpler.registerUser(user)>0){
                    Intent intent;
                    ArrayList<User> list=new ArrayList<>();
                    list.add(user);
                    String userId=edt_reguserid.getText().toString();
                    if(userId.equals("admin")){
                        Toast.makeText(getApplicationContext(),"不用以admin作为用户账户！",Toast.LENGTH_SHORT).show();
                    }else{
                        intent=new Intent(getApplicationContext(),PersonalMainActivity.class);
                        intent.putParcelableArrayListExtra("loginUser",list);
                        intent.putExtra("userId",userId);
                        startActivity(intent);
                    }
                    Toast.makeText(getApplicationContext(),"用户注册成功！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"用户注册失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button btn_registerreset=findViewById(R.id.btn_registerreset);
        btn_registerreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_reguserid.setText("");
                edt_reguserpwd.setText("");
                edt_regemail.setText("");
                imageView.setImageResource(R.drawable.tx1);
            }
        });
        Button btn_changeIcon=findViewById(R.id.btn_changeIcon);
        btn_changeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SelectIconActivity.class);
                intent.putExtra("iconResourceId",iconResourceId);
                startActivityForResult(intent,1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                iconResourceId= data.getIntExtra("iconResourceId",R.drawable.tx1);
                imageView.setImageResource(iconResourceId);
            }
        }
    }
}
