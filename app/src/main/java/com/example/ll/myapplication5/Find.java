package com.example.ll.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ll on 2016/12/22.
 */
public class Find extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        setTitle("发现");

        /*点击事件，切换页面*/
        RelativeLayout story_btn = (RelativeLayout) findViewById(R.id.story);
        RelativeLayout user_btn = (RelativeLayout) findViewById(R.id.user);
        story_btn.setOnClickListener(this);
        user_btn.setOnClickListener(this);

    }
    /*点击事件，切换页面*/
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.story:
                intent = new Intent(Find.this,Logined.class);
                startActivity(intent);
                break;
            case R.id.user:
                intent = new Intent(Find.this,Account.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
