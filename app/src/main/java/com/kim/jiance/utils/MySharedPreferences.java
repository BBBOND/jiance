package com.kim.jiance.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 伟阳 on 2015/11/17.
 */
public class MySharedPreferences {
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    public boolean saveUsernameAndPassword(Map<String, Object> map) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", String.valueOf(map.get("username")));
        editor.putString("password", String.valueOf(map.get("password")));
        flag = editor.commit();
        return flag;
    }

    public Map<String, Object> readUsernameAndPassword() {
        Map<String, Object> map = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        if (!username.equals("") && !password.equals("")) {
            map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
        }
        return map;
    }

    public boolean saveIPAndPort(String ip, String port) {
        boolean flag = false;
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipconfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ip", ip);
        editor.putString("port", port);
        flag = editor.commit();
        return flag;
    }

    public String readIPAndPort() {
        String ipAndPort = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences("ipconfig", Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString("ip", "");
        String port = sharedPreferences.getString("port", "");
        ipAndPort = ip + ":" + port;
        return ipAndPort;
    }
}
