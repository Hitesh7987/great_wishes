package com.example.honey.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;

import static com.example.honey.myapplication.R.id.texts;

public class activity2 extends AppCompatActivity {
    private TextView text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);
       text=(TextView)findViewById(R.id.texts);
        Intent intent=getIntent();
        String message=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        text.setText(message);
         Toast.makeText(getApplicationContext(),"Going to send email", Toast.LENGTH_SHORT).show();
         Intent email=new Intent(Intent.ACTION_SEND);
           email.setType("message/rfc822");
          email.putExtra(Intent.EXTRA_EMAIL,new String[] {message});
           email.putExtra(Intent.EXTRA_TEXT,"Wishing a very happy birthday...");
           Intent chooser=Intent.createChooser(email,"Send with..");
         if(email.resolveActivity(getPackageManager())!=null){
             startActivity(chooser);
         }

                }

}
