package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SMDP3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn, my_turn;
    int i;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<Integer> myuser = new ArrayList<>();
    List<Integer> opponent = new ArrayList<>();

    @Override   
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        my_turn = bundle.getBoolean("my_turn");
        turn = my_turn;

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
    }

    private void setOnClick(final Button btn, final Integer x) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("")) {
                    if (turn) {
                        myuser.add(x);
                        turn = false;
                        btn.setText("X");
                        endGame(myuser);
                    } else {
                        opponent.add(x);
                        turn = true;
                        btn.setText("O");
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
                        if (turn) {
                            Toast.makeText(SMDP3X3.this, "Circle wins", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SMDP3X3.this, "Cross wins",Toast.LENGTH_LONG).show();
                        }
                        restart(findViewById(R.id.reset));
                    }
                }
            }
        }
        if (myuser.size()+opponent.size()==9) {
            Toast.makeText(SMDP3X3.this, "Match Tie!!", Toast.LENGTH_LONG).show();
            restart(findViewById(R.id.reset));
        }
    }

    public void home(View view) {
        Intent main = new Intent(this, MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void restart(View view) {
        Intent main = new Intent(this, SMDP3X3.class);
        main.putExtra("my_turn", turn);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void undo(View view) {
        try {
            if (turn) {
                int x = opponent.get(opponent.size() - 1);
                opponent.remove(opponent.size() - 1);
                b[x].setText("");
                turn = false;
            } else {
                int x = myuser.get(myuser.size() - 1);
                myuser.remove(myuser.size() - 1);
                b[x].setText("");
                turn = true;
            }
        } catch (Exception e) {
            Toast.makeText(SMDP3X3.this, "can't undo", Toast.LENGTH_LONG).show();
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
