package com.kim.jiance.utils;

import android.content.Context;
import android.util.Log;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

/**
 * Created by 伟阳 on 2015/11/21.
 */
public class HttpUtil {

    public static String get(Context context, String url, String object) throws IOException, ResourceException {
        String str = "null";
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context);
        String ipAndPort = mySharedPreferences.readIPAndPort();
        String urlStr = "http://";
        if (ipAndPort != null && !ipAndPort.equals(":")) {
            urlStr = urlStr + ipAndPort + url;
        } else {
            urlStr = urlStr + "192.168.74.15:8888" + url;
        }
        Log.d("HttpUtil", urlStr);
        ClientResource client = new ClientResource(urlStr);
        Representation result = null;
        result = client.post(object);
        str = result.getText().trim();
        return str;
    }
}
