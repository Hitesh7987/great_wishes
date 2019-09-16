package com.example.honey.myapplication;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class member extends AppCompatActivity {
    SensorManager sensormanager;
    List sensorlist;
    long lastupdate;
    ListView blist;
    String[] bname={"CSE ","ECE ","IT ","MECHANICAL ","CIVIL "};
    int[] bimages={R.drawable.cse,R.drawable.ece,R.drawable.it,R.drawable.mech,R.drawable.civil};
    TextView limit;
    EditText name, date, mobile, email;
    Button reset, addmember;
     long id;
    String sn,sm,sdel,se,sd,sb="string";
    SensorEventListener sel=new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            float accelerate = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long actualtime = event.timestamp;
            if (accelerate >= 9) {
                if (actualtime - lastupdate > 6000) {
                    limit.setText(String.valueOf(accelerate));
                    lastupdate = actualtime;
                    Toast.makeText(getApplicationContext(), "Device Shaked", Toast.LENGTH_SHORT).show();

                    clear();
                }
            }
        }
         };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        name = (EditText) findViewById(R.id.name);
        date = (EditText) findViewById(R.id.date);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        blist=(ListView)findViewById(R.id.branch);
        addinglist();
    blist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            sb=bname[i] ;
            Toast.makeText(getApplicationContext(),sb,Toast.LENGTH_SHORT).show();
        }
    });
        lastupdate = System.currentTimeMillis();
        limit=(TextView)findViewById(R.id.accelerate);
        sensormanager=(SensorManager)getSystemService(SENSOR_SERVICE);
        sensorlist=sensormanager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensorlist.size()>0){
            sensormanager.registerListener(sel,(Sensor)sensorlist.get(0),SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(getApplicationContext(),"No Sensor to shake it", Toast.LENGTH_SHORT).show();
        }
    }

    public void reset(View view) {
        name.setText("");
        date.setText("");
        mobile.setText("");
        email.setText("");
    }
    public void addmember(View view){
    data dataob=new data(view.getContext());
     sn=name.getText().toString();
     sm=mobile.getText().toString();
     se=email.getText().toString();
     sd=date.getText().toString();

     if(sn.isEmpty() || sm.isEmpty() || se.isEmpty() || sd.isEmpty()){
         Toast.makeText(view.getContext(),"All Fiels are manadatory.",Toast.LENGTH_SHORT).show();
     }
    else{
       id=dataob.insert(sn,sm,se,sd,sb);
       if(id>0){
           Toast.makeText(view.getContext(),"Adding data Successful.",Toast.LENGTH_SHORT).show();
           reset(view);
       }
           else {
           Toast.makeText(view.getContext(), "Adding data Unsuccessful.", Toast.LENGTH_SHORT).show();
       }
     }
    }
public void clear(){

            data ob=new data(getApplicationContext());
        ob.deltable();
}
    public void delete_member(View v){
  final data dataob=new data(v.getContext());
        AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
    builder.setTitle("What's that name ?");
    builder.setCancelable(true);
    final EditText delname=new EditText(this);
    delname.setInputType(InputType.TYPE_CLASS_TEXT);
    builder.setView(delname);
         builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            sdel=delname.getText().toString();
        int count=dataob.delete(sdel);
        if (count<=0){Toast.makeText(getApplicationContext(),"Unsuccessful",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getApplicationContext(), "Deleted Succssfully", Toast.LENGTH_SHORT).show();
    }});
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    });
    builder.show();
}
    public void viewmember(View view){
        data ob=new data(view.getContext());
        String str=ob.viewdata();
        Toast.makeText(view.getContext(),str,Toast.LENGTH_LONG).show();
}
public void addinglist(){
    ArrayList<HashMap<String,String>> arraylist=new ArrayList<>();
    for(int i=0;i<bname.length;i++){
    HashMap<String,String> hshmap=new HashMap<>();
    hshmap.put("name",bname[i]);
    hshmap.put("image",bimages[i]+"");
    arraylist.add(hshmap);
    }
String[] from={"name","image"};

    int[] to={R.id.list_text,R.id.list_image};
    SimpleAdapter simpleAdapter=new SimpleAdapter(this,arraylist,R.layout.branch,from,to);
    blist.setAdapter(simpleAdapter);
    }
@Override
    protected void onStop(){
    if(sensorlist.size()>0)
        sensormanager.unregisterListener(sel);
super.onStop();
}
}