package com.example.grcwgl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectIconActivity extends AppCompatActivity {
    ListView listViewIcons;
    int iconResourceId=R.drawable.tx1;
    int[] icons={R.drawable.tx1,R.drawable.tx2,R.drawable.tx3,R.drawable.tx4,R.drawable.tx5,R.drawable.tx6,R.drawable.tx7,R.drawable.tx8};
    ImageView imageView2;

    ArrayList<Map<String,String>> getData(){
        ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
        for(int i=0;i<8;i++){
            Map<String,String> map=new HashMap<>();
            map.put("icon",String.valueOf(icons[i]));
            list.add(map);
        }
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_icon);
        Intent intent=getIntent();
        iconResourceId=intent.getIntExtra("iconResourceId",R.drawable.tx1);
        imageView2=findViewById(R.id.imageView2);
        imageView2.setImageResource(iconResourceId);
        listViewIcons=findViewById(R.id.listviewicons);
        SimpleAdapter simpleAdapter=new SimpleAdapter(
                getApplicationContext(),
                getData(),
                R.layout.listtiemlayout,
                new String[]{"icon"},
                new int[]{R.id.icon}
        );
        listViewIcons.setAdapter(simpleAdapter);
        listViewIcons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                iconResourceId=icons[position];
                imageView2.setImageResource(iconResourceId);
            }
        });
        Button btn_setIconOK=findViewById(R.id.btn_setIconOK);
        btn_setIconOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("iconResourceId",iconResourceId);
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
        Button btn_selectCancel=findViewById(R.id.btn_selectCancel);
        btn_selectCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
