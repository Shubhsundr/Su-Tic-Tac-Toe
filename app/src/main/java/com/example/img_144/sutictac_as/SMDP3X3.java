package com.example.img_144.sutictac_as;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SMDP3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn;
    int i;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<Set<Integer>>();
    List<Integer> myuser = new ArrayList<Integer>();
    List<Integer> opponent = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for (int j = 0; j < 8; j++) {
            Set<Integer> abs = new HashSet<Integer>();
            abs.addAll(Arrays.asList(my[j]));
            win.add(abs);
        }
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

        for (i = 0; i < 9; i++) {
            setOnClick(b[i], i);
        }
    }

    private void setOnClick(final Button btn, final Integer x) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().toString().equals("")) {
                    if (turn) {
                        myuser.add(x);
                        turn = false;
                        b.setText("X");
                        endGame(myuser);
                    } else {
                        opponent.add(x);
                        turn = true;
                        b.setText("O");
                        endGame(opponent);
                    }
                }
            }
        });
    }

    private void endGame(List a1) {
        List<Set<Integer>> x = getSubsets(a1, 3);
        if (x.size() > 0) {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    if ((win.get(j)).equals(x.get(i))) {
                        Intent main = new Intent(this, MainActivity.class);
                        startActivity(main);
                        return;
                    }
                }
            }
        }
        if (myuser.size()+opponent.size()==9) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
            return;
        }
    }

    private static void getSubsets(List<Integer> superSet, int k, int idx, Set<Integer> current, List<Set<Integer>> solution) {
        //successful stop clause
        if (current.size() == k) {
            solution.add(new HashSet<>(current));
            return;
        }
        //unseccessful stop clause
        if (idx == superSet.size()) return;
        Integer x = superSet.get(idx);
        current.add(x);
        //"guess" x is in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
        current.remove(x);
        //"guess" x is not in the subset
        getSubsets(superSet, k, idx + 1, current, solution);
    }

    public static List<Set<Integer>> getSubsets(List<Integer> superSet, int k) {
        List<Set<Integer>> res = new ArrayList<>();
        getSubsets(superSet, k, 0, new HashSet<Integer>(), res);
        return res;
    }

    public <T> List<T> twoDArrayToList(T[][] twoDArray) {
        List<T> list = new ArrayList<T>();
        for (T[] array : twoDArray) {
            list.addAll(Arrays.asList(array));
        }
        return list;
    }
}
