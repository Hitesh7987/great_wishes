package com.example.honey.myapplication;

 import android.Manifest;
 import android.app.Activity;
 import android.app.AlertDialog;
 import android.content.BroadcastReceiver;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.IntentFilter;
 import android.content.pm.PackageManager;
 import android.database.Cursor;
 import android.hardware.input.InputManager;
 import android.location.Location;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.media.Image;
 import android.net.Uri;
 import android.os.Build;
 import android.os.Bundle;
 import android.os.IBinder;
 import android.os.SystemClock;
 import android.provider.ContactsContract;
 import android.provider.Settings;
 import android.support.annotation.RequiresApi;
 import android.support.v4.content.ContextCompat;
 import android.support.v4.view.InputDeviceCompat;
 import android.support.v7.app.AppCompatActivity;
 import android.telephony.SmsManager;
 import android.util.Log;
 import android.view.InputDevice;
 import android.view.InputEvent;
 import android.view.KeyEvent;
 import android.view.MotionEvent;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageButton;
 import android.widget.ListView;
 import android.widget.ProgressBar;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity implements LocationListener{
    private Cursor cursor=null;
     LocationManager lm;
        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private EditText name;
   private TextView text,textView2;
    private Button button;
    private ImageButton select,wish_sms,wish_phone,wish_social;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        textView2 = (TextView) findViewById(R.id.textView2);
        select = (ImageButton) findViewById(R.id.select);
        name = (EditText) findViewById(R.id.name);
        wish_sms = (ImageButton) findViewById(R.id.wish_sms);
        wish_phone = (ImageButton) findViewById(R.id.wish_phone);
        wish_social = (ImageButton) findViewById(R.id.wish_social);
         reciever();
    }
  @Override
  public void onBackPressed(){
        new AlertDialog.Builder(this).setTitle("Are you sure about it ?").setCancelable(false).setMessage("This app is going to close.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
      public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
  }
   public void addmember(View view){
        Intent nextact=new Intent(view.getContext(), member.class);
        startActivity(nextact);
   }
   public void buttonlistener(View view){
               String st = name.getText().toString();
               Toast.makeText(getApplicationContext(), st, Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(view.getContext(), activity2.class);
               intent.putExtra(EXTRA_MESSAGE,st);
               startActivity(intent);
           }

    public void location(){
    if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
        lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Toast.makeText(getApplicationContext(),"LOCATING...",Toast.LENGTH_SHORT).show();
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
        }
     }
@Override
    public void onLocationChanged(Location location){
   String msg="New lattitude: "+location.getLatitude()+"\nNew longitidude: "+location.getLongitude();
    // name.setText(location.getLatitude()+" "+location.getLongitude());
    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
}
@Override
    public void onProviderDisabled(String s){
        new AlertDialog.Builder(this).setCancelable(true).setMessage("For this you need to turn on your location.").setTitle("Location Required").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"GPS is turned off ",Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).show();
            }
@Override
    public void onProviderEnabled(String s){
    Log.e("GPS","GPS turned on");
        Toast.makeText(getApplicationContext(),"GPS is turned on ",Toast.LENGTH_SHORT).show();
}
@Override
    public void onStatusChanged(String s, int status , Bundle extra){}

    public void synclocation(View v){
         location();
          Runnable runn=new Runnable(){
            @Override
            public void run(){
                Server ob=new Server();
                ob.sync(name.getText().toString());
            }
        };
        Thread serverthread = new Thread(runn);
        serverthread.start();
    }
  public void instant(View view){
  instant_wish ob=new instant_wish(view.getContext());
  switch(view.getId()){
      case R.id.wish_sms:
         ob.sms(view.getContext(),textView2.getText().toString());
      break;
      case  R.id.wish_phone:Intent intent=new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:"+textView2.getText().toString()));
                            if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED) {
                                startActivity(intent);
                            }
      break;
      case R.id.wish_social:
      break;
  }
  }
 public void select_contact(View view){
     Intent intent=new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
     startActivityForResult(intent,123);

 }

    @Override
    public void onActivityResult(int req_code, int result,Intent data){
        super.onActivityResult(req_code,result,data);
        Log.d("ACTIVITY","ONACTIVITY RESULT");
        if(result==RESULT_OK){
            switch (req_code){
                case (123):
                    Uri uri=data.getData();
                    cursor=getContentResolver().query(uri,null,null,null,null);
                    cursor.moveToFirst();
                    int index=cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    instant_wish ob=new instant_wish(getApplicationContext());
                  textView2.setText(cursor.getString(index));

            }
        }
    }
 public void reciever(){
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()){
                    case Activity.RESULT_OK:Toast.makeText(getBaseContext(),"Message sent..",Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
              }
        },new IntentFilter("SMS_SENT"));
     registerReceiver(new BroadcastReceiver(){
         @Override
         public void onReceive(Context context, Intent intent) {
             Toast.makeText(getBaseContext(), "Inside delivery reciever..", Toast.LENGTH_SHORT).show();
             Log.d("DELIVERY","INSIDE DELIVERY RECIEVER>>>>");
             switch (getResultCode())
             {
                 case Activity.RESULT_OK:
                     Toast.makeText(getBaseContext(), "SMS delivered",
                             Toast.LENGTH_LONG).show();
                     break;
                 case Activity.RESULT_CANCELED:
                     Toast.makeText(getBaseContext(), "SMS not delivered",
                             Toast.LENGTH_LONG).show();
                     break;
             }
         }
     }, new IntentFilter("DELIVERED"));

 }

}




