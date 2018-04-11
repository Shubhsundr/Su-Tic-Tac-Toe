package com.example.img144.sutictacas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SMDP9X9 extends AppCompatActivity {
    Button b[][] = new Button[10][9];
    RelativeLayout s[][] = new RelativeLayout[10][8];
    LinearLayout L[] = new LinearLayout[9];
    ImageView p[] = new ImageView[2];
    TextView t[] = new TextView[2];
    Boolean turn, my_turn;
    int i,j;
    int last=9;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<List<Integer>> myuser = new ArrayList(10);
    List<List<Integer>> opponent = new ArrayList(10);
    List<Integer> neutral = new ArrayList<>();
    List<Integer> proceeding = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        my_turn = bundle.getBoolean("my_turn");
        turn = my_turn;

        for (j = 0; j < 8; j++) {
            Set<Integer> abs = new HashSet<>();
            abs.addAll(Arrays.asList(my[j]));
            win.add(abs);
        }
        for (i=0; i<10; i++) {
            myuser.add(new ArrayList<Integer>());
            opponent.add(new ArrayList<Integer>());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smdp9_x9);

        L[0] = findViewById(R.id.a0);
        L[1] = findViewById(R.id.a1);
        L[2] = findViewById(R.id.a2);
        L[3] = findViewById(R.id.a3);
        L[4] = findViewById(R.id.a4);
        L[5] = findViewById(R.id.a5);
        L[6] = findViewById(R.id.a6);
        L[7] = findViewById(R.id.a7);
        L[8] = findViewById(R.id.a8);

        b[0][0] = findViewById(R.id.b00);
        b[0][1] = findViewById(R.id.b01);
        b[0][2] = findViewById(R.id.b02);
        b[0][3] = findViewById(R.id.b03);
        b[0][4] = findViewById(R.id.b04);
        b[0][5] = findViewById(R.id.b05);
        b[0][6] = findViewById(R.id.b06);
        b[0][7] = findViewById(R.id.b07);
        b[0][8] = findViewById(R.id.b08);

        b[1][0] = findViewById(R.id.b10);
        b[1][1] = findViewById(R.id.b11);
        b[1][2] = findViewById(R.id.b12);
        b[1][3] = findViewById(R.id.b13);
        b[1][4] = findViewById(R.id.b14);
        b[1][5] = findViewById(R.id.b15);
        b[1][6] = findViewById(R.id.b16);
        b[1][7] = findViewById(R.id.b17);
        b[1][8] = findViewById(R.id.b18);

        b[2][0] = findViewById(R.id.b20);
        b[2][1] = findViewById(R.id.b21);
        b[2][2] = findViewById(R.id.b22);
        b[2][3] = findViewById(R.id.b23);
        b[2][4] = findViewById(R.id.b24);
        b[2][5] = findViewById(R.id.b25);
        b[2][6] = findViewById(R.id.b26);
        b[2][7] = findViewById(R.id.b27);
        b[2][8] = findViewById(R.id.b28);

        b[3][0] = findViewById(R.id.b30);
        b[3][1] = findViewById(R.id.b31);
        b[3][2] = findViewById(R.id.b32);
        b[3][3] = findViewById(R.id.b33);
        b[3][4] = findViewById(R.id.b34);
        b[3][5] = findViewById(R.id.b35);
        b[3][6] = findViewById(R.id.b36);
        b[3][7] = findViewById(R.id.b37);
        b[3][8] = findViewById(R.id.b38);

        b[4][0] = findViewById(R.id.b40);
        b[4][1] = findViewById(R.id.b41);
        b[4][2] = findViewById(R.id.b42);
        b[4][3] = findViewById(R.id.b43);
        b[4][4] = findViewById(R.id.b44);
        b[4][5] = findViewById(R.id.b45);
        b[4][6] = findViewById(R.id.b46);
        b[4][7] = findViewById(R.id.b47);
        b[4][8] = findViewById(R.id.b48);

        b[5][0] = findViewById(R.id.b50);
        b[5][1] = findViewById(R.id.b51);
        b[5][2] = findViewById(R.id.b52);
        b[5][3] = findViewById(R.id.b53);
        b[5][4] = findViewById(R.id.b54);
        b[5][5] = findViewById(R.id.b55);
        b[5][6] = findViewById(R.id.b56);
        b[5][7] = findViewById(R.id.b57);
        b[5][8] = findViewById(R.id.b58);

        b[6][0] = findViewById(R.id.b60);
        b[6][1] = findViewById(R.id.b61);
        b[6][2] = findViewById(R.id.b62);
        b[6][3] = findViewById(R.id.b63);
        b[6][4] = findViewById(R.id.b64);
        b[6][5] = findViewById(R.id.b65);
        b[6][6] = findViewById(R.id.b66);
        b[6][7] = findViewById(R.id.b67);
        b[6][8] = findViewById(R.id.b68);

        b[7][0] = findViewById(R.id.b70);
        b[7][1] = findViewById(R.id.b71);
        b[7][2] = findViewById(R.id.b72);
        b[7][3] = findViewById(R.id.b73);
        b[7][4] = findViewById(R.id.b74);
        b[7][5] = findViewById(R.id.b75);
        b[7][6] = findViewById(R.id.b76);
        b[7][7] = findViewById(R.id.b77);
        b[7][8] = findViewById(R.id.b78);

        b[8][0] = findViewById(R.id.b80);
        b[8][1] = findViewById(R.id.b81);
        b[8][2] = findViewById(R.id.b82);
        b[8][3] = findViewById(R.id.b83);
        b[8][4] = findViewById(R.id.b84);
        b[8][5] = findViewById(R.id.b85);
        b[8][6] = findViewById(R.id.b86);
        b[8][7] = findViewById(R.id.b87);
        b[8][8] = findViewById(R.id.b88);

        b[9][0] = findViewById(R.id.b0);
        b[9][1] = findViewById(R.id.b1);
        b[9][2] = findViewById(R.id.b2);
        b[9][3] = findViewById(R.id.b3);
        b[9][4] = findViewById(R.id.b4);
        b[9][5] = findViewById(R.id.b5);
        b[9][6] = findViewById(R.id.b6);
        b[9][7] = findViewById(R.id.b7);
        b[9][8] = findViewById(R.id.b8);


        s[0][0] = findViewById(R.id.s00);
        s[0][1] = findViewById(R.id.s01);
        s[0][2] = findViewById(R.id.s02);
        s[0][3] = findViewById(R.id.s03);
        s[0][4] = findViewById(R.id.s04);
        s[0][5] = findViewById(R.id.s05);
        s[0][6] = findViewById(R.id.s06);
        s[0][7] = findViewById(R.id.s07);

        s[1][0] = findViewById(R.id.s10);
        s[1][1] = findViewById(R.id.s11);
        s[1][2] = findViewById(R.id.s12);
        s[1][3] = findViewById(R.id.s13);
        s[1][4] = findViewById(R.id.s14);
        s[1][5] = findViewById(R.id.s15);
        s[1][6] = findViewById(R.id.s16);
        s[1][7] = findViewById(R.id.s17);

        s[2][0] = findViewById(R.id.s20);
        s[2][1] = findViewById(R.id.s21);
        s[2][2] = findViewById(R.id.s22);
        s[2][3] = findViewById(R.id.s23);
        s[2][4] = findViewById(R.id.s24);
        s[2][5] = findViewById(R.id.s25);
        s[2][6] = findViewById(R.id.s26);
        s[2][7] = findViewById(R.id.s27);

        s[3][0] = findViewById(R.id.s30);
        s[3][1] = findViewById(R.id.s31);
        s[3][2] = findViewById(R.id.s32);
        s[3][3] = findViewById(R.id.s33);
        s[3][4] = findViewById(R.id.s34);
        s[3][5] = findViewById(R.id.s35);
        s[3][6] = findViewById(R.id.s36);
        s[3][7] = findViewById(R.id.s37);

        s[4][0] = findViewById(R.id.s40);
        s[4][1] = findViewById(R.id.s41);
        s[4][2] = findViewById(R.id.s42);
        s[4][3] = findViewById(R.id.s43);
        s[4][4] = findViewById(R.id.s44);
        s[4][5] = findViewById(R.id.s45);
        s[4][6] = findViewById(R.id.s46);
        s[4][7] = findViewById(R.id.s47);

        s[5][0] = findViewById(R.id.s50);
        s[5][1] = findViewById(R.id.s51);
        s[5][2] = findViewById(R.id.s52);
        s[5][3] = findViewById(R.id.s53);
        s[5][4] = findViewById(R.id.s54);
        s[5][5] = findViewById(R.id.s55);
        s[5][6] = findViewById(R.id.s56);
        s[5][7] = findViewById(R.id.s57);

        s[6][0] = findViewById(R.id.s60);
        s[6][1] = findViewById(R.id.s61);
        s[6][2] = findViewById(R.id.s62);
        s[6][3] = findViewById(R.id.s63);
        s[6][4] = findViewById(R.id.s64);
        s[6][5] = findViewById(R.id.s65);
        s[6][6] = findViewById(R.id.s66);
        s[6][7] = findViewById(R.id.s67);

        s[7][0] = findViewById(R.id.s70);
        s[7][1] = findViewById(R.id.s71);
        s[7][2] = findViewById(R.id.s72);
        s[7][3] = findViewById(R.id.s73);
        s[7][4] = findViewById(R.id.s74);
        s[7][5] = findViewById(R.id.s75);
        s[7][6] = findViewById(R.id.s76);
        s[7][7] = findViewById(R.id.s77);

        s[8][0] = findViewById(R.id.s80);
        s[8][1] = findViewById(R.id.s81);
        s[8][2] = findViewById(R.id.s82);
        s[8][3] = findViewById(R.id.s83);
        s[8][4] = findViewById(R.id.s84);
        s[8][5] = findViewById(R.id.s85);
        s[8][6] = findViewById(R.id.s86);
        s[8][7] = findViewById(R.id.s87);

        s[9][0] = findViewById(R.id.s0);
        s[9][1] = findViewById(R.id.s1);
        s[9][2] = findViewById(R.id.s2);
        s[9][3] = findViewById(R.id.s3);
        s[9][4] = findViewById(R.id.s4);
        s[9][5] = findViewById(R.id.s5);
        s[9][6] = findViewById(R.id.s6);
        s[9][7] = findViewById(R.id.s7);

        p[0] = findViewById(R.id.p1);
        p[1] = findViewById(R.id.p2);

        t[0] = findViewById(R.id.t1);
        t[1] = findViewById(R.id.t2);

        for (i = 0; i < 9; i++) {
            for (j = 0 ; j<9; j++)
            setOnClick(b[i][j], b[9][i], i, j);
        }
        onturn();
    }

    private void setOnClick(final Button btn, final Button bt, final Integer x, final Integer y) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn.getText().toString().equals("")) {
                    if (turn) {
                        if(valid(x)) {
                            last = y;
                            myuser.get(x).add(y);
                            proceeding.add(10*x+y);
                            turn = false;
                            btn.setText("X");
                            btn.setTextColor(getResources().getColor(R.color.play_online));
                            int be = bendGame(x);
                            if(be==1) {
                                L[x].setVisibility(View.GONE);
                                b[9][x].setVisibility(View.VISIBLE);
                                bt.setText("X");
                                bt.setBackgroundColor(getResources().getColor(R.color.play_online));
                                myuser.get(9).add(x);
                                endGame(myuser.get(9));
                            } else if(be==2) {
                                neutral.add(x);
                                endGame(myuser.get(9));
                            }
                        }
                    } else {
                        if(valid(x)) {
                            last = y;
                            opponent.get(x).add(y);
                            proceeding.add(10*x+y);
                            turn = true;
                            btn.setText("O");
                            btn.setTextColor(getResources().getColor(R.color.quick_play));
                            int be=bendGame(x);
                            if(be==1) {
                                L[x].setVisibility(View.GONE);
                                b[9][x].setVisibility(View.VISIBLE);
                                bt.setText("O");
                                bt.setBackgroundColor(getResources().getColor(R.color.quick_play));
                                opponent.get(9).add(x);
                                endGame(opponent.get(9));
                            } else if(be==2) {
                                neutral.add(x);
                                endGame(opponent.get(9));
                            }
                        }
                    }
                    onturn();
                }
            }
        });
    }

    public boolean valid(Integer x) {
        if (myuser.get(9).contains(x) || opponent.get(9).contains(x) || neutral.contains(x)) {
            return false;
        } else if(last==x || last==9) {
            return true;
        } else if (myuser.get(9).contains(last) || opponent.get(9).contains(last) || neutral.contains(last)) {
            return true;
        } else {
            return false;
        }
    }

    private void onturn() {
        for (int i = 0; i < 8; i++ ) {
            if (!turn)
                s[9][i].setBackgroundColor(getResources().getColor(R.color.quick_play));
            else
                s[9][i].setBackgroundColor(getResources().getColor(R.color.play_online));
        }
        if (!turn) {
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
        for (int i = 0; i < 9; i++) {
            if (valid(i)) {
                if (!turn)
                    L[i].setBackgroundColor(getResources().getColor(R.color.quick_play));
                else
                    L[i].setBackgroundColor(getResources().getColor(R.color.play_online));
                L[i].getBackground().setAlpha(20);
            }
            else {
                L[i].setBackgroundColor(getResources().getColor(R.color.trans));
            }
        }
    }

    private int bendGame(int a1) {
        List<Set<Integer>> x;
        if (turn)
            x = getSubsets(opponent.get(a1), 3);
        else
            x = getSubsets(myuser.get(a1), 3);
        if (x.size() > 0) {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    if ((win.get(j)).equals(x.get(i))) {
                        return 1;
                    }
                }
            }
        }
        if ((myuser.get(a1)).size()+(opponent.get(a1)).size()==9) {
            return 2;
        } else {
            return 0;
        }
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

    private void endGame(List a1) {
        List<Set<Integer>> x = getSubsets(a1, 3);
        if (x.size() > 0) {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    if ((win.get(j)).equals(x.get(i))) {
                        if (turn) {
                            Toast.makeText(SMDP9X9.this, "Circle wins", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SMDP9X9.this, "Cross wins",Toast.LENGTH_LONG).show();
                        }
                        restart(findViewById(R.id.reset));
                        return;
                    }
                }
            }
        }
        if ((myuser.get(9)).size()+(opponent.get(9)).size()+neutral.size()==9) {
            Toast.makeText(SMDP9X9.this, "Match Tie!!", Toast.LENGTH_LONG).show();
            turn=!turn;
            restart(findViewById(R.id.reset));
            return;
        }
    }

    public void home(View view) {
        Intent main = new Intent(this, MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void restart(View view) {
        Intent main = new Intent(this, SMDP9X9.class);
        main.putExtra("my_turn", !turn);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void undo(View view) {
        try {
            int prev = proceeding.get(proceeding.size()-1);
            proceeding.remove(proceeding.size()-1);
            if (turn) {
                opponent.get(prev/10).remove(opponent.get(prev/10).size()-1);
                b[prev/10][prev%10].setText("");
                turn = false;
                try {
                    if (opponent.get(9).get(opponent.get(9).size() - 1) == prev / 10) {
                        opponent.get(9).remove(opponent.get(9).size() - 1);
                        b[9][prev / 10].setVisibility(View.GONE);
                        L[prev / 10].setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {}
                try {
                    if (neutral.get(neutral.size()-1) == prev/10) {
                        neutral.remove(neutral.size()-1);
                        b[9][prev/10].setVisibility(View.GONE);
                        L[prev/10].setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {}
            } else {
                myuser.get(prev/10).remove(myuser.get(prev/10).size()-1);
                b[prev/10][prev%10].setText("");
                turn = true;
                try {
                    if (myuser.get(9).get(myuser.get(9).size()-1) == prev/10) {
                        myuser.get(9).remove(myuser.get(9).size()-1);
                        b[9][prev/10].setVisibility(View.GONE);
                        L[prev/10].setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {}
                try {
                    if (neutral.get(neutral.size()-1) == prev/10) {
                        neutral.remove(neutral.size()-1);
                        b[9][prev/10].setVisibility(View.GONE);
                        L[prev/10].setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {}
            }
            if (proceeding.size()==0)
                last=9;
            else
                last = proceeding.get(proceeding.size()-1)%10;
            onturn();
        } catch (Exception e) {
            if (proceeding.size()==0)
                last=9;
            Toast.makeText(SMDP9X9.this, "can't undo", Toast.LENGTH_LONG).show();
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
