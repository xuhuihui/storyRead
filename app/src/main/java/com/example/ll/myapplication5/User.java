package com.example.ll.myapplication5;

/**
 * Created by ll on 2016/12/15.
 */
public class User {
    private String mobile;
    private String password;


    public User(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }
}
