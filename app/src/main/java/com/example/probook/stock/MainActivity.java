package com.example.probook.stock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    NotificationManager myNotificationManager;
    private  int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button myGen = (Button) findViewById(R.id.button_cn);
        myGen.setOnClickListener(myGenOnClickListener);

        Button myClear = (Button) findViewById(R.id.button_dn);
        myClear.setOnClickListener(myClearOnClickListener);
    }

    private void GenerateNotification(){
        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder  builder = new Notification.Builder(this);
        builder.setContentTitle("Attention Please!");
        builder.setTicker("***Notification***");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("-Message for the user-");

        Intent notificationIntent = new Intent(this,MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        NOTIFICATION_ID++;
        myNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    Button.OnClickListener myGenOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            GenerateNotification();
        }
    };

    Button.OnClickListener myClearOnClickListener = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
           myNotificationManager.cancel(NOTIFICATION_ID);
           NOTIFICATION_ID--;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
