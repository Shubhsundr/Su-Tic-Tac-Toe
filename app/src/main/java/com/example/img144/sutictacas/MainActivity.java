package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    Boolean qplay = true;
    Boolean player = true;
    Boolean mode = true;
    Boolean me = true;

    Button but3, but9;
    LinearLayout screen0, screen1, screen2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but3 = (Button) findViewById(R.id.B3X3);
        but9 = (Button) findViewById(R.id.B9X9);
        changemode(but3);

        screen0 = (LinearLayout) findViewById(R.id.screen0);
        screen1 = (LinearLayout) findViewById(R.id.screen1);
        screen2 = (LinearLayout) findViewById(R.id.screen2);
    }

    public void quick(View view) {
        qplay = true;
        screen0.setVisibility(View.GONE);
        screen1.setVisibility(View.VISIBLE);
        screen2.setVisibility(View.GONE);
    }
    public void lan(View view) {
        Intent l = new Intent(this, LAN.class);
        startActivity(l);
    }
    public void single(View view) {
        player = true;
        screen0.setVisibility(View.GONE);
        screen1.setVisibility(View.GONE);
        screen2.setVisibility(View.VISIBLE);
        findViewById(R.id.taketurn).setVisibility(View.VISIBLE);
    }
    public void dual(View view) {
        player = false;
        screen0.setVisibility(View.GONE);
        screen1.setVisibility(View.GONE);
        screen2.setVisibility(View.VISIBLE);
    }

    public void quit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
    public void info(View view) {

    }

    public void start(View view) {
        if (qplay) {
            if (player) {
                if (mode) {
                    Intent l = new Intent(this, SMSP3X3.class);
                    l.putExtra("my_turn", me);
                    l.putExtra("first_turn", ((CheckBox) findViewById(R.id.taketurn)).isChecked());
                    startActivity(l);
                }
                else {
                    Intent l = new Intent(this, SMSP9X9.class);
                    l.putExtra("my_turn", me);
                    l.putExtra("first_turn", ((CheckBox) findViewById(R.id.taketurn)).isChecked());
                    startActivity(l);
                }
            }
            else {
                if (mode) {
                    Intent l = new Intent(this, SMDP3X3.class);
                    l.putExtra("my_turn", me);
                    startActivity(l);
                }
                else {
                    Intent l = new Intent(this, SMDP9X9.class);
                    l.putExtra("my_turn", me);
                    startActivity(l);
                }
            }
        }
    }

    public void changemode(View view) {
        mode = !mode;
        if (!mode) {
            but3.getBackground().setAlpha(127);
            but9.getBackground().setAlpha(255);
        } else {
            but9.getBackground().setAlpha(127);
            but3.getBackground().setAlpha(255);
        }
    }
    public void firstspone(View view) {
        me = !me;
        Button b = (Button) view;
        if (me) {
            b.setText("X");
        } else {
            b.setText("O");
        }
    }

    @Override
    public void onBackPressed() {
        if (screen0.getVisibility() != View.VISIBLE) {
            Intent main = new Intent(this, MainActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
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
