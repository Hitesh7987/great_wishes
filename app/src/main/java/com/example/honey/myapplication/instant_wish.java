package com.example.honey.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by HONEY on 4/5/2018.
 */

public class instant_wish extends Dialog{
    Button button4,button3;
    ListView wishes;
    String[] wish={"Happy birthday ...","Wish a very happy birthday..","Happy marriage anniversary..."};
    String str="Happy this special day";
    Context context;
    Dialog dialog;
    /*  public instant_wish(Context contex){
              }
      @Override
      protected void onCreate(Bundle savedinstance){
          super.onCreate(savedinstance);
          setContentView(R.layout.instant_dialog);
          sms();
      }*/
    public instant_wish(Context contex){
        super(contex);
        this.context=contex;
       // dialog=new Dialog(contex);
    }

    public void sms(final Context context1,final String str1){
        dialog=new Dialog(context1);
        dialog.setContentView(R.layout.instant_dialog);
        dialog.setTitle("Send via SMS...");
        wishes=(ListView)dialog.findViewById(R.id.wishes);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(context1,R.layout.wishes,R.id.wish_text,wish);
        wishes.setAdapter(arrayAdapter);
        // addlist();
        button4=(Button)dialog.findViewById(R.id.button4);
        button3=(Button)dialog.findViewById(R.id.button3);
        final PendingIntent sentPI=PendingIntent.getBroadcast(context1,0,new Intent("SMS_SENT"),0);
        final PendingIntent delieverPI=PendingIntent.getBroadcast(context1,0,new Intent("" +
                ""),0);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   finish();
                dialog.dismiss();

            }
        });
        dialog.show();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(str1,null,str,sentPI,delieverPI);
            }
        });
        wishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                str=wish[i];
            }
        });
    }
    public void social(){

    }

}
