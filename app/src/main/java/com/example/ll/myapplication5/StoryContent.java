package com.example.ll.myapplication5;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;


/**
 * Created by ll on 2016/12/24.
 */
public class StoryContent extends AppCompatActivity implements View.OnClickListener{
    private TextView content;
    private BufferedInputStream bis;
    private int page=-1;
    private byte[][] buffer=new byte[50][1024];
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.story_content); //隐藏标题栏

        /*初始化BmobSDK*/
        Bmob.initialize(this, "87108d997e651c99110828a6ed1b0444");

        /*接受传入参数，获取文件路径*/
        BmobFile bmobfile = (BmobFile) getIntent().getSerializableExtra("path");
        String path=bmobfile.getFileUrl();

        content=(TextView)findViewById(R.id.content);

        /*显示小说名*/
        String name=getIntent().getStringExtra("name");
        setTitle(name);

        /*打开文件*/
        bis=openFile(path);

        /*显示第一页内容*/
        showText(bis);

        /*设置框点击事件*/
        RelativeLayout set_btn = (RelativeLayout) findViewById(R.id.set);
        RelativeLayout light_btn = (RelativeLayout) findViewById(R.id.light);
        set_btn.setOnClickListener(this);
        light_btn.setOnClickListener(this);

        /*滑动条滑动事件*/
        lightChange();
        sizeChange();

        /*改变背景颜色*/
        context=StoryContent.this;
        bgColorChange();
    }

    /*下载缓存数据，获取缓存路径，打开文件*/
    public BufferedInputStream openFile(String path) {
        String chunk="";
        try {
            URL myURL = new URL(path);
            URLConnection ucon = myURL.openConnection();
            InputStream is = ucon.getInputStream();
            bis = new BufferedInputStream(is);
            return bis;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    public void showText(BufferedInputStream bis) {
        try{
            page++;
            int bytesRead = 0;
            if ((bytesRead = bis.read(buffer[page])) != -1) {
                String text = new String(buffer[page], 0, bytesRead);
                content.setText(text.toString());
            }
        } catch (Exception e){
            e.getMessage();
        }
    }

    /*重写屏幕点击事件*/
    public boolean onTouchEvent(MotionEvent event)
    {
        int Action = event.getAction();
        float X = event.getX();
        float Y = event.getY();

        if(Action==MotionEvent.ACTION_DOWN) {
            if (X<250) {  //上一页
                if(page>0) {
                    page--;
                    String text = new String(buffer[page]);
                    content.setText(text.toString());
                }
            }
            else if(X>500) {   //下一页
                showText(bis);
            }
            else {  //显示,隐藏设置栏
                RelativeLayout set=(RelativeLayout)findViewById(R.id.set);
                RelativeLayout module=(RelativeLayout)findViewById(R.id.module);
                RelativeLayout pick_context=(RelativeLayout)findViewById(R.id.pick);
                if(pick_context.getVisibility() == View.VISIBLE) {
                    pick_context.setVisibility(View.GONE);
                }
                else if(module.getVisibility() == View.VISIBLE) {
                    module.setVisibility(View.GONE);
                }
                else if (set.getVisibility() == View.GONE) {
                    set.setVisibility(View.VISIBLE);
                } else {
                    set.setVisibility(View.GONE);
                }
            }
        }

        return true;
    }

    /*点击事件，切换页面*/
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set:
                RelativeLayout module=(RelativeLayout)findViewById(R.id.module);
                module.setVisibility(View.VISIBLE);
                break;
            case R.id.light:
                changeModule(); //切换日夜间模式
                break;
            default:
                break;
        }
    }

    /*切换日夜间模式*/
    public void changeModule() {
        ImageView image=(ImageView)findViewById(R.id.image);
        TextView text=(TextView)findViewById(R.id.text);
        LinearLayout theme=(LinearLayout)findViewById(R.id.theme);
        if(text.getText().toString().equals("夜间模式")) {
            image.setImageResource(R.drawable.day);
            text.setText("日间模式");
            theme.setBackgroundColor(Color.rgb(10,10,10));
            content.setTextColor(Color.rgb(255,255,255));
        }
        else {
            image.setImageResource(R.drawable.night);
            text.setText("夜间模式");
            theme.setBackgroundColor(Color.rgb(212,199,167));
            content.setTextColor(Color.rgb(0,0,0));
        }
    }


    /*调节亮度*/
    public void lightChange() {
        SeekBar lightbar=(SeekBar)findViewById(R.id.lightbar);
        lightbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                changeAppBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*调节字体大小*/
    public void sizeChange() {
        SeekBar textbar=(SeekBar)findViewById(R.id.textbar);
        textbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               content.setTextSize(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /*设置当前屏幕的亮度*/
    public void changeAppBrightness(int brightness) {
        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    /*获取系统亮度*/
    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return systemBrightness;
    }

    /*改变背景颜色*/
    public void bgColorChange() {
        ImageView red=(ImageView)findViewById(R.id.red);
        ImageView white=(ImageView)findViewById(R.id.white);
        ImageView green=(ImageView)findViewById(R.id.green);
        ImageView blue=(ImageView)findViewById(R.id.blue);
        ImageView yellow=(ImageView)findViewById(R.id.yellow);
        ImageView more=(ImageView)findViewById(R.id.more);

        colorClickListener color=new colorClickListener();
        red.setOnClickListener(color);
        white.setOnClickListener(color);
        green.setOnClickListener(color);
        blue.setOnClickListener(color);
        yellow.setOnClickListener(color);

        /*颜色选择器调用事件*/
        more.setOnClickListener(color);
    }

    public class colorClickListener implements View.OnClickListener {
        LinearLayout theme=(LinearLayout)findViewById(R.id.theme);
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.red:
                    theme.setBackgroundColor(Color.RED);
                    break;
                case R.id.white:
                    theme.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.green:
                    theme.setBackgroundColor(Color.GREEN);
                    break;
                case R.id.blue:
                    theme.setBackgroundColor(Color.BLUE);
                    break;
                case R.id.yellow:
                    theme.setBackgroundColor(Color.YELLOW);
                    break;
                case R.id.more:
                    RelativeLayout pick_context=(RelativeLayout)findViewById(R.id.pick);
                    pick_context.setVisibility(View.VISIBLE);
                    ColorPickerView pick = new ColorPickerView(context);
                    pick_context.addView(pick);
                    pick.setOnColorBackListener(new ColorPickerView.OnColorBackListener() {
                        @Override
                        public void onColorBack(int a, int r, int g, int b) {
                            theme.setBackgroundColor(Color.argb(a, r, g, b));
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }








    }
