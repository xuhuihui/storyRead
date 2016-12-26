package com.example.ll.myapplication5;

import android.graphics.Bitmap;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by ll on 2016/12/22.
 */
public class Story {
    private Bitmap imagePath;  //存放图片路径
    private BmobFile storyPath;  //存放小说路径
    private String storyName; //小说名字
    private String author;  //作者
    private String noTime;  //阅读页数
    private String upPage;  //更新页数

    public Story(Bitmap imagePath, BmobFile storyPath, String storyName, String author, String noTime, String upPage) {
        this.imagePath = imagePath;
        this.storyPath = storyPath;
        this.storyName = storyName;
        this.author = author;
        this.noTime = noTime;
        this.upPage = upPage;
    }

    public void setImagePath(Bitmap imagePath) {
        this.imagePath = imagePath;
    }

    public Bitmap getImagePath() {
        return imagePath;
    }

    public BmobFile getStoryPath() {
        return storyPath;
    }

    public String getStoryName() {
        return storyName;
    }

    public String getAuthor() {
        return author;
    }

    public String getNoTime() {
        return noTime;
    }

    public String getUpPage() {
        return upPage;
    }
}
