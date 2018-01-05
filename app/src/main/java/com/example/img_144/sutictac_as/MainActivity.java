package com.example.img_144.sutictac_as;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void SMDP3X3( View v) {
        Intent i=new Intent(this,SMDP3X3.class);
        startActivity(i);
    }
    public void SMSP3X3( View v) {
        Intent i=new Intent(this,SMSP3X3.class);
        startActivity(i);
    }
    public void DMDP3X3( View v) {
        Intent i=new Intent(this,DMDP3X3.class);
        startActivity(i);
    }
    public void SMDP9X9( View v) {
        Intent i=new Intent(this,SMDP9X9.class);
        startActivity(i);
    }
    public void JWiFi( View v) {
        Intent i=new Intent(this,JWiFi.class);
        startActivity(i);
    }
    public void HWiFi( View v) {
        Intent i=new Intent(this,HWiFi.class);
        startActivity(i);
    }
}
