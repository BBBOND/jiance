package com.kim.jiance.login;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kim.jiance.content.Common;
import com.kim.jiance.content.MyURL;
import com.kim.jiance.utils.HttpUtil;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 伟阳 on 2015/11/16.
 */
public class Login {

    private Context context;
    private String username;
    private String password;

    public Login(Context context, String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    public void login(final GetLoginCallBack callBack) {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                callBack.getLoginResult((String) msg.obj);
                return true;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("username", username);
                map.put("password", password);
                try {
                    String str = HttpUtil.get(context, MyURL.LOGIN, JSON.toJSONString(map));
                    Message message = Message.obtain();
                    message.obj = str;
                    handler.sendMessage(message);
                } catch (ResourceException e) {
                    e.printStackTrace();
                    Log.d("Login", "连接失败！");
                    Message message = Message.obtain();
                    message.obj = Common.CONNECTFAILED;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("Login", "获取失败！");
                    Message message = Message.obtain();
                    message.obj = Common.GETINFORMATIONFAILED;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    public interface GetLoginCallBack {
        void getLoginResult(String result);
    }
}
