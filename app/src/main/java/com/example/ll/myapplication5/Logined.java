package com.example.ll.myapplication5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by ll on 2016/12/15.
 */
public class Logined extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private StoryAdapter storyAdapter;
    private List<Story> list = new ArrayList<Story>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logined);
        setTitle("我的书架"); //标题

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        /*可以在主线程中访问网络*/
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());  


        /*初始化BmobSDK*/
        Bmob.initialize(this, "87108d997e651c99110828a6ed1b0444");

        /*查询服务器，初始化我的书架*/
        listView = (ListView) findViewById(R.id.story_list);
        queryData();

        /*点击事件，切换页面*/
        RelativeLayout find_btn = (RelativeLayout) findViewById(R.id.find);
        RelativeLayout user_btn = (RelativeLayout) findViewById(R.id.user);
        find_btn.setOnClickListener(this);
        user_btn.setOnClickListener(this);
    }

    /**
     * 查询数据
     */
    public void queryData(){

        BmobQuery query =new BmobQuery("Story");
        query.setLimit(100);
        query.order("createdAt");
        //v3.5.0版本提供`findObjectsByTable`方法查询自定义表名的数据
        query.findObjectsByTable(new QueryListener<JSONArray>() {
            @Override
            public void done(JSONArray ary, BmobException e) {
                if(e==null){
//                    Log.i("bmob","查询成功："+ary.toString());
                    try {
                        for (int i=0;i<ary.length();i++){
                            JSONObject object = ary.getJSONObject(i); //解析json格式

                            /*下载远程图片*/
                            JSONObject image=object.getJSONObject("image");
                            String image_url=image.getString("url"); //获取图片路径
                            Bitmap bitmap=returnBitmap(image_url); //加载图片,获取图片btmap格式

                            /*获取文件路径*/
                            JSONObject novel=object.getJSONObject("story");
                            String novel_name=novel.getString("filename");
                            String story_path=novel.getString("url"); //获取小说路径
                            BmobFile bmobfile =new BmobFile(novel_name,"",story_path); //创建BOMB文件对象

                            /*获取小说的其它相关信息*/
                            String story_name=object.getString("story_name");
                            String author=object.getString("author");
                            String noPage=object.getString("nopage");
                            String upPage=object.getString("uppage");
                            Story story=new Story(bitmap,bmobfile,story_name,author,noPage,upPage);
                            list.add(story);
                        }
                    }
                    catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    showView(); //显示listView
                }else{
                    Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }
    /**
     * 根据URL获取Bitmap
     * */
    private Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    public void showView() {
        /*显示listView*/
        storyAdapter = new StoryAdapter(Logined.this, R.layout.story,list);
        listView.setAdapter(storyAdapter);

        /*点击小说阅读小说内容，切换页面*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Logined.this,StoryContent.class);
                Story story=list.get(position);
                intent.putExtra("path", story.getStoryPath()); //传递路径
                intent.putExtra("name", story.getStoryName()); //传递路径
                startActivity(intent);
            }
        });
    }

    /*点击事件，切换页面*/
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.find:
                intent = new Intent(Logined.this,Find.class);
                startActivity(intent);
                break;
            case R.id.user:
                intent = new Intent(Logined.this,Account.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
