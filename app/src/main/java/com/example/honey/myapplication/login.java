package com.example.honey.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HONEY on 4/12/2018.
 */

public class login extends AppCompatActivity {
    HttpEntity entity;
    HttpPost httppost;
    HttpClient httpclient;
    HttpResponse response;
    EditText e1,e2;
    Button b1,b2;
    StringBuilder sb;
    InputStream is;
    String sp;
    ProgressBar pro;
    @Override
     public void onCreate(Bundle savedinstance){
        super.onCreate(savedinstance);
        setContentView(R.layout.login);
        b1=(Button)findViewById(R.id.login);
        b2=(Button)findViewById(R.id.register);
        e1=(EditText)findViewById(R.id.edit1);
        e2=(EditText)findViewById(R.id.edit2);
        httpclient=new DefaultHttpClient();
        httppost=new HttpPost("http://wwwpostformoney.000webhostapp.com/login.php");
        }
     public void login1(View view){
         List<NameValuePair> info=new ArrayList<NameValuePair>();
         info.add(new BasicNameValuePair("username",e1.getText().toString()));
         info.add(new BasicNameValuePair("password",e2.getText().toString()));
         Dialog dialog=new Dialog(view.getContext());
         dialog.setContentView(R.layout.progress);
         dialog.setTitle("Please wait...");
         pro=(ProgressBar)dialog.findViewById(R.id.pro);
         pro.setProgress(1);
         dialog.show();
         Intent intent = new Intent(login.this, MainActivity.class);
         startActivity(intent);
      /*   Runnable runnable=new Runnable() {
             @Override
             public void run() {
                 try {
                     httppost.setEntity(new UrlEncodedFormEntity(info, "UTF_8"));
                     response = httpclient.execute(httppost);
                     entity = response.getEntity();
                     is = entity.getContent();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                     sb = new StringBuilder();
                     String line = null;
                     while ((line = reader.readLine()) != null) {
                         sb.append(line);
                     }
                     sp=sb.toString();
                     is.close();
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Intent intent = new Intent(login.this, MainActivity.class);
                            if ((sb.toString()).equals("login failed")) {
                                 e1.setText("");
                                 e2.setText("");
                                 Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                             }
                             if (e1.getText().toString().equals(sp)) {
                                 startActivity(intent);
                                 Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
                                 }
                             }
                         });
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         };
             Thread t=new Thread(runnable);
             t.start();
             */
    }
     public void register2(View view){
         Intent intent=new Intent(login.this,register.class);
         startActivity(intent);
     }
}

