package com.example.probook.stock;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClicked(View view){
        String text = view.getId() == R.id.view ? "Background" : "Foreground";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
