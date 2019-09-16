package com.example.honey.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HONEY on 4/13/2018.
 */

public class register extends AppCompatActivity {
    StringBuilder sb;
    InputStream is;
    HttpClient httpclient;
    HttpResponse response;
    HttpPost httppost;
    EditText username,password,date,emailID;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        date=(EditText)findViewById(R.id.date);
        emailID=(EditText)findViewById(R.id.emailID);
        button=(Button)findViewById(R.id.button);
        httpclient=new DefaultHttpClient();
        httppost=new HttpPost("http://wwwpostformoney.000webhostapp.com/Db_connect.php");
    }
    public void register1(View view){
        final List<NameValuePair> record=new ArrayList<NameValuePair>();
        record.add(new BasicNameValuePair("username",username.getText().toString()));
        record.add(new BasicNameValuePair("password",password.getText().toString()));
        record.add(new BasicNameValuePair("doj",date.getText().toString()));
        record.add(new BasicNameValuePair("email",emailID.getText().toString()));
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(record,"UTF-8"));
                    response=httpclient.execute(httppost);
                    HttpEntity httpEntity=response.getEntity();
                    is=httpEntity.getContent();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(is,"UTF-8"));
                    sb=new StringBuilder();
                    String line=null;
                    while((line=reader.readLine())!=null){
                        sb.append(line +"\n");
                    }
                    is.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),sb.toString(),Toast.LENGTH_LONG).show();
                        }
                    });
                    //message(sb.toString());
                } catch (Exception e) {}
            }
        };
        Thread t1=new Thread(runnable);
        t1.start();
    }
    public void message(String st){
        Toast.makeText(this.getApplicationContext(),st,Toast.LENGTH_LONG).show();
    }
}
