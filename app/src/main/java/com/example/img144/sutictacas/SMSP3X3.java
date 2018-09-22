package com.example.img144.sutictacas;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SMSP3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    RelativeLayout s[] = new RelativeLayout[8];
    ImageView p[] = new ImageView[2];
    TextView t[] = new TextView[2];
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

        b[0] = findViewById(R.id.b0);
        b[1] = findViewById(R.id.b1);
        b[2] = findViewById(R.id.b2);
        b[3] = findViewById(R.id.b3);
        b[4] = findViewById(R.id.b4);
        b[5] = findViewById(R.id.b5);
        b[6] = findViewById(R.id.b6);
        b[7] = findViewById(R.id.b7);
        b[8] = findViewById(R.id.b8);

        s[0] = findViewById(R.id.s0);
        s[1] = findViewById(R.id.s1);
        s[2] = findViewById(R.id.s2);
        s[3] = findViewById(R.id.s3);
        s[4] = findViewById(R.id.s4);
        s[5] = findViewById(R.id.s5);
        s[6] = findViewById(R.id.s6);
        s[7] = findViewById(R.id.s7);

        p[0] = findViewById(R.id.p1);
        p[1] = findViewById(R.id.p2);

        t[0] = findViewById(R.id.t1);
        t[1] = findViewById(R.id.t2);

        if (my_turn) {
            t[0].setText("Bot");
            t[1].setText("Me");
        } else {
            t[0].setText("Me");
            t[1].setText("Bot");
        }

        for (i = 0; i < 9; i++) {
            setOnClick(b[i], i);
        }

        turn = !first_turn;
        bot();
        onturn();
    }

    private void setOnClick(final Button btn, final Integer x) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("") && !turn) {
                    myuser.add(x);
                    if(my_turn) {
                        btn.setBackgroundColor(getResources().getColor(R.color.play_online));
                        btn.setText("X");
                    } else {
                        btn.setBackgroundColor(getResources().getColor(R.color.quick_play));
                        btn.setText("O");
                    }
                    if (endgame(myuser)) {
                        turn = true;
                        onturn();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bot();
                            }
                        }, 700);
                        onturn();
                    }
                }
            }
        });
    }

    private void onturn() {
        for (int i = 0; i < 8; i++ ) {
            if (turn==my_turn)
                s[i].setBackgroundColor(getResources().getColor(R.color.quick_play));
            else
                s[i].setBackgroundColor(getResources().getColor(R.color.play_online));
        }
        if (turn==my_turn) {
            p[0].setImageResource(R.drawable.ic_profile_green);
            p[1].setImageResource(R.drawable.ic_profile_grey);
            t[0].setTextColor(getResources().getColor(R.color.quick_play));
            t[1].setTextColor(getResources().getColor(R.color.black));
        }
        else {
            p[0].setImageResource(R.drawable.ic_profile_grey);
            p[1].setImageResource(R.drawable.ic_profile_red);
            t[0].setTextColor(getResources().getColor(R.color.black));
            t[1].setTextColor(getResources().getColor(R.color.play_online));
        }
    }

    private boolean endgame(List a) {
        if (result(a)){
            if (!turn)
                Toast.makeText(SMSP3X3.this, "You Won!!", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(SMSP3X3.this, "You Lost!!", Toast.LENGTH_LONG).show();
            restart(findViewById(R.id.reset));
            return false;
        } else if (myuser.size()+opponent.size()==9) {
            Toast.makeText(SMSP3X3.this, "Match Tie!!", Toast.LENGTH_LONG).show();
            restart(findViewById(R.id.reset));
            return false;
        } else {
            return true;
        }
    }

    private boolean result(List a1) {
        List<Set<Integer>> x = getSubsets(a1, 3);
        if (x.size() > 0)
            for (int i = 0; i < x.size(); i++)
                for (int j = 0; j < 8; j++)
                    if ((win.get(j)).equals(x.get(i)))
                        return true;
        return false;
    }

    public void Switch(View view) {
        if (findViewById(R.id.layout1).getVisibility() != View.VISIBLE) {
            findViewById(R.id.layout1).setVisibility(View.VISIBLE);
            findViewById(R.id.layout2).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.layout2).setVisibility(View.VISIBLE);
            findViewById(R.id.layout1).setVisibility(View.GONE);
        }
    }

    public void sound(View view) {
        if (findViewById(R.id.soundon).getVisibility() != View.VISIBLE) {
            findViewById(R.id.soundon).setVisibility(View.VISIBLE);
            findViewById(R.id.soundoff).setVisibility(View.GONE);
        }
        else {
            findViewById(R.id.soundoff).setVisibility(View.VISIBLE);
            findViewById(R.id.soundon).setVisibility(View.GONE);
        }
    }

    public void bot() {
        if(turn) {
            Random rand = new Random();
            int xy = rand.nextInt(9);
            if(myuser.size()+opponent.size() > 0) {
                int xy_w = 100;
                for (int m = 0; m < 9; m++) {
                    if (b[m].getText().toString().equals("")) {
                        List a1 = new ArrayList(opponent);
                        List a2 = new ArrayList(myuser);
                        a1.add(m);
                        if (result(a1) || a1.size()+a2.size()==9) {
                            xy_w = -100;
                            xy = m;
                        } else if(xy_w!=-100) {
                            int weight = autohuman(a1, a2);
                            if (weight < xy_w || xy_w == 100) {
                                xy_w = weight;
                                xy = m;
                            }
                        }
                    }
                }
            }

            opponent.add(xy);

            if (my_turn) {
                b[xy].setBackgroundColor(getResources().getColor(R.color.quick_play));
                b[xy].setText("O");
            } else {
                b[xy].setBackgroundColor(getResources().getColor(R.color.play_online));
                b[xy].setText("X");
            }
            if (endgame(opponent)) {
                turn = false;
                onturn();
            }
        }
    }

    public int autobot(List a1, List a2) {
        int s1 = 2, s2;
        for (int n=0; n<9; n++) {
            List l1 = new ArrayList(a1);
            List l2 = new ArrayList(a2);
            if (!l1.contains(n) && !l2.contains(n)) {
                l1.add(n);
                if (result(l1)) {
                    return -2;
                } else if (l1.size()+l2.size()==9) {
                    return 0;
                } else {
                    s2 = autohuman(l1, l2);
                    if (s2 == -2) {
                        return -2;
                    } else if (s1 == 2) {
                        s1 = s2;
                    }
                }
            }
        }
        return s1;
    }

    public int autohuman(List a1, List a2) {
        int s1 = -3, s2;
        for (int n=0; n<9; n++) {
            List l1 = new ArrayList(a1);
            List l2 = new ArrayList(a2);
            if (!l1.contains(n) && !l2.contains(n)) {
                l2.add(n);
                if (result(l2)) {
                    return 2;
                } else if (l1.size()+l2.size()==9) {
                    return 0;
                } else {
                    s2 = autobot(l1, l2);
                    if (s2 == 2) {
                        return 2;
                    } else if (s1 == -3) {
                        s1 = s2;
                    } else if (s1 != 0 && s2 == 0) {
                        s1 = -1;
                    }
                }
            }
        }
        return s1;
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
        main.putExtra("first_turn", !turn);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void undo(View view) {
        boolean a = turn;
        turn = true;
        if (myuser.size()==0 && opponent.size()==0){
            Toast.makeText(SMSP3X3.this, "can't undo", Toast.LENGTH_LONG).show();
        } else {
            if (myuser.size()>0) {
                a = !a;
                int x = myuser.get(myuser.size() - 1);
                myuser.remove(myuser.size() - 1);
                b[x].setText("");
                b[x].setBackgroundColor(getResources().getColor(R.color.trans));
            }
            if (opponent.size()>0) {
                a = !a;
                int y = opponent.get(opponent.size() - 1);
                opponent.remove(opponent.size() - 1);
                b[y].setText("");
                b[y].setBackgroundColor(getResources().getColor(R.color.trans));
            }
        }
        turn = a;
        onturn();
        bot();
        onturn();
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
