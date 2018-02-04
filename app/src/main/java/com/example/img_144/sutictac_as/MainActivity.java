package com.example.img_144.sutictac_as;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import java.util.jar.Attributes;


public class MainActivity extends AppCompatActivity implements ViewStub.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.SMDP3X3:
                startActivity(new Intent(this,SMDP3X3.class));
                break;
            case R.id.SMSP3X3:
                startActivity(new Intent(this,SMSP3X3.class));
                break;
            /**case R.id.JWIFI:
                startActivity(new Intent(this,JWiFi.class));
                break;**/
            case R.id.HWIFI:
                startActivity(new Intent(this,HWiFi.class));
                break;
        }
    }
}
