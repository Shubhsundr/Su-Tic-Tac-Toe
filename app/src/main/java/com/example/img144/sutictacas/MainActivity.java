package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void SMSP3X3(View view) {
        startActivity(new Intent(this, SMSP3X3.class));
    }
    public void SMDP3X3(View view) {
        startActivity(new Intent(this, SMDP3X3.class));
    }
    public void LAN(View view) { startActivity(new Intent(this, LAN.class)); }
    public void SMSP9X9(View view) {
        startActivity(new Intent(this, SMSP9X9.class));
    }
    public void SMDP9X9(View view) {
        startActivity(new Intent(this, SMDP9X9.class));
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
