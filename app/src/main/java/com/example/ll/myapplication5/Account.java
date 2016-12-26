package com.example.ll.myapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by ll on 2016/12/21.
 */
public class Account extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        setTitle("个人资料");

        /*点击事件，切换页面*/
        RelativeLayout find_btn = (RelativeLayout) findViewById(R.id.find);
        RelativeLayout story_btn = (RelativeLayout) findViewById(R.id.story);
        find_btn.setOnClickListener(this);
        story_btn.setOnClickListener(this);

    }
    /*点击事件，切换页面*/
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.find:
                intent = new Intent(Account.this,Find.class);
                startActivity(intent);
                break;
            case R.id.story:
                intent = new Intent(Account.this,Logined.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
