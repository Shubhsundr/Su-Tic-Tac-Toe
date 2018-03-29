package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SMSP3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn, my_turn, first_turn;
    int i;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<Integer> myuser = new ArrayList<>();
    List<Integer> opponent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        my_turn = bundle.getBoolean("my_turn");
        first_turn = bundle.getBoolean("first_turn");

        for (int j = 0; j < 8; j++) {
            Set<Integer> abs = new HashSet<>();
            abs.addAll(Arrays.asList(my[j]));
            win.add(abs);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smdp3_x3);

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

        turn = !first_turn;
        bot();
    }

    private void setOnClick(final Button btn, final Integer x) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("")) {
                    myuser.add(x);
                    turn = true;
                    if(my_turn==true) {
                        btn.setText("X");
                    } else {
                        btn.setText("O");
                    }
                    endGame(myuser);
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
                        if (turn) {
                            Toast.makeText(SMSP3X3.this, "You Won!!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SMSP3X3.this, "You Lose!!", Toast.LENGTH_LONG).show();
                        }
                        restart(findViewById(R.id.reset));
                        return;
                    }
                }
            }
        }
        if (myuser.size()+opponent.size()==9) {
            Toast.makeText(SMSP3X3.this, "Match Tie!!", Toast.LENGTH_LONG).show();
            restart(findViewById(R.id.reset));
            return;
        }
        bot();
    }

    public void bot() {
        while(turn) {
            Random rand = new Random();
            int n = rand.nextInt(9);
            if (b[n].getText().toString().equals("")) {
                opponent.add(n);
                if(my_turn==true) {
                    b[n].setText("O");
                } else {
                    b[n].setText("X");
                }
                turn = false;
                endGame(opponent);
            }
        }
    }

    public void home(View view) {
        Intent main = new Intent(this, MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void restart(View view) {
        Intent main = new Intent(this, SMSP3X3.class);
        main.putExtra("my_turn", my_turn);
        main.putExtra("first_turn", turn);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void undo(View view) {
        try {
            int x = myuser.get(myuser.size() - 1);
            myuser.remove(myuser.size() - 1);
            b[x].setText("");
            int y = opponent.get(opponent.size() - 1);
            opponent.remove(opponent.size() - 1);
            b[y].setText("");
        } catch (Exception e) {
            Toast.makeText(SMSP3X3.this, "can't undo", Toast.LENGTH_LONG).show();
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
