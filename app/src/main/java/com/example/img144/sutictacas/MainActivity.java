package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.img_144.sutictac_as.R;


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

    public void JOIN(View view) {
        //String u_name = user_name.getText().toString();
        Intent i = new Intent(this, FindServers.class);
        i.putExtra("Name", "");
        startActivity(i);
    }
    public void HOST(View view) {
        Intent i = new Intent(this, Server.class);
        //String u_name = user_name.getText().toString();
        //String server = server_name.getText().toString();
        i.putExtra("Name", "");
        i.putExtra("ServerName", "my_server");
        startActivity(i);
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
