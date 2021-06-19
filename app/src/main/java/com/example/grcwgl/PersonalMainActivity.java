package com.example.grcwgl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonalMainActivity extends AppCompatActivity {
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_main);
        Intent intent =getIntent();
        final ArrayList<User> list =intent.getParcelableArrayListExtra("loginUser");
        user=list.get(0);
        String result="欢迎你,"+user.getUserId();
        TextView textView=findViewById(R.id.textView);
        textView.setText(result);
        Button sz_btn = findViewById(R.id.sz_btn);
        sz_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        Button btn_lookinfo=findViewById(R.id.btn_lookinfo);
        btn_lookinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateInfoActivity.class);
                intent.putParcelableArrayListExtra("loginUser",list);
                startActivity(intent);
            }
        });
    }
}
