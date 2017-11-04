package com.example.img_144.sutictac_as;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SMDP3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn;
    int i;
    int win[][]={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    List<Integer> myuser = new ArrayList<Integer>();
    List<Integer> opponent = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smdp3_x3);

        turn = true;

        b[0] = (Button) findViewById(R.id.b0);
        b[1] = (Button) findViewById(R.id.b1);
        b[2] = (Button) findViewById(R.id.b2);
        b[3] = (Button) findViewById(R.id.b3);
        b[4] = (Button) findViewById(R.id.b4);
        b[5] = (Button) findViewById(R.id.b5);
        b[6] = (Button) findViewById(R.id.b6);
        b[7] = (Button) findViewById(R.id.b7);
        b[8] = (Button) findViewById(R.id.b8);

        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().toString().equals("")) {
                    if (turn) {
                        myuser.add(b.getId());
                        Log.d("me","P1"+myuser);
                        turn = false;
                        b.setText("X");
                    } else {
                        opponent.add(b.getId());
                        Log.d("me","P2"+opponent);
                        turn = true;
                        b.setText("O");
                    }
                }
            }
        };

        for(i=0;i<9;i++) {
            b[i].setOnClickListener(buttonListener);
        }
    }
}
