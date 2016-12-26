package com.example.ll.myapplication5;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by ll on 2016/12/15.
 */
public class Register extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private EditText telephone;
    private EditText code;
    private EditText password,password_again;
    private int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Button code_btn=(Button)findViewById(R.id.code_btn);
        Button register=(Button)findViewById(R.id.register);
        telephone=(EditText)findViewById(R.id.telphone);
        code=(EditText)findViewById(R.id.code);
        password=(EditText)findViewById(R.id.password);
        password_again=(EditText)findViewById(R.id.password_again);


        //获取验证码
        code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!telephone.getText().toString().equals("")) {
                    Random rd = new Random();
                    num = rd.nextInt(899999) + 100000;
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telephone.getText().toString(), null,
                            num + "", null, null);
                }
                else {
                    Toast.makeText(Register.this,"手机号为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(password_again.getText().toString())) {
                    if(code.getText().toString().equals(num+"")) {
                        insertData();
                        Toast.makeText(Register.this,"注册成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Register.this,"验证码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Register.this,"密码错误", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //用户数据插入到数据库里
    private void insertData(){
        dbHelper = new MyDatabaseHelper(this,"Subway.db",null,2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mobile",telephone.getText().toString());
        values.put("password",password.getText().toString());
        db.insert("contact", null , values);
    }

}
