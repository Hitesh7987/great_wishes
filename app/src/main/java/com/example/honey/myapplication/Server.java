package com.example.honey.myapplication;

import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class Server {
    HttpClient httpClient;
    HttpPost httppost;

    public Server() {
        httpClient = new DefaultHttpClient();
        httppost = new HttpPost("http://wwwpostformoney.000webhostapp.com/data.php");
    }

    public void sync(String loc) {
        List<NameValuePair> data = new ArrayList<NameValuePair>();
        data.add(new BasicNameValuePair("location", "\n"+loc));
        try {
            HttpEntity entity = new UrlEncodedFormEntity(data, "UTF-8");
            httppost.setEntity(entity);
            httpClient.execute(httppost);
        } catch (Exception e) {
            Log.e("HTTP", "Error in connecttion " + e.toString());
        }
    }
}
