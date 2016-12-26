package com.example.ll.myapplication5;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*点击事件，登录*/
        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        /*点击事件，注册*/
        Button register=(Button)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });
    }




    //登录
    private void login(){
        int flag=0;
        dbHelper = new MyDatabaseHelper(this,"Subway.db",null,2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询AddressBook表中所有的数据
        Cursor cursor = db.query("contact",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                //遍历Cursor对象，取出数据并打印
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                EditText _telphone=(EditText)findViewById(R.id.telphone);
                EditText _password=(EditText)findViewById(R.id.password);
                if((_telphone.getText().toString().equals(mobile))&&(_password.getText().toString().equals(password))) {
                    flag=1;
                }
            }while(cursor.moveToNext());
        }
        cursor.close();

        if(flag==1) {
            Intent intent = new Intent(MainActivity.this,Logined.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(MainActivity.this,"登录失败", Toast.LENGTH_SHORT).show();
        }
    }

}
