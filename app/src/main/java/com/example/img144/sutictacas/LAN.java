package com.example.img144.sutictacas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by kanish on 3/4/18.
 */

public class LAN extends AppCompatActivity {
    Button b[][] = new Button[10][9];
    RelativeLayout s[][] = new RelativeLayout[10][8];
    LinearLayout L[] = new LinearLayout[9];
    ImageView p[] = new ImageView[2];
    TextView t[] = new TextView[2];
    Boolean turn = true;
    Boolean ingame = false;
    Boolean mode = false;
    Boolean myturn = true;
    Boolean ifServer = false;
    int i,j;
    int last=9;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<List<Integer>> myuser = new ArrayList<>(10);
    List<List<Integer>> opponent = new ArrayList<>(10);
    List<Integer> neutral = new ArrayList<>();
    List<Integer> proceeding = new ArrayList<>();

    public final int port_number = 8080;
    public ServerSocket serverSocket;
    public boolean isClient = false;
    public String connectedIP="";
    public String serverName = "Server";
    public String clientName = "Client";

    Button startgame, but3, but9;
    LinearLayout create_server_layout, game_layout, menu_layout, mode_layout;
    TextView wait_layout;
    ClientThread clientThread = null;
    List<ChatClient> userList;

    public void onCreate(Bundle savedInstanceState) {

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
        setContentView(R.layout.lan);

        wait_layout = (TextView)findViewById(R.id.wait_layout);
        mode_layout = (LinearLayout)findViewById(R.id.mode_layout);
        menu_layout = (LinearLayout)findViewById(R.id.menu_layout);
        game_layout = (LinearLayout)findViewById(R.id.game_layout);
        create_server_layout = (LinearLayout) findViewById(R.id.create_server_layout);

        turn = true;

        but3 = (Button) findViewById(R.id.B3X3);
        but9 = (Button) findViewById(R.id.B9X9);

        startgame = (Button) findViewById(R.id.start_game);

        L[0] = (LinearLayout) findViewById(R.id.a0);
        L[1] = (LinearLayout) findViewById(R.id.a1);
        L[2] = (LinearLayout) findViewById(R.id.a2);
        L[3] = (LinearLayout) findViewById(R.id.a3);
        L[4] = (LinearLayout) findViewById(R.id.a4);
        L[5] = (LinearLayout) findViewById(R.id.a5);
        L[6] = (LinearLayout) findViewById(R.id.a6);
        L[7] = (LinearLayout) findViewById(R.id.a7);
        L[8] = (LinearLayout) findViewById(R.id.a8);

        b[0][0] = (Button) findViewById(R.id.b00);
        b[0][1] = (Button) findViewById(R.id.b01);
        b[0][2] = (Button) findViewById(R.id.b02);
        b[0][3] = (Button) findViewById(R.id.b03);
        b[0][4] = (Button) findViewById(R.id.b04);
        b[0][5] = (Button) findViewById(R.id.b05);
        b[0][6] = (Button) findViewById(R.id.b06);
        b[0][7] = (Button) findViewById(R.id.b07);
        b[0][8] = (Button) findViewById(R.id.b08);

        b[1][0] = (Button) findViewById(R.id.b10);
        b[1][1] = (Button) findViewById(R.id.b11);
        b[1][2] = (Button) findViewById(R.id.b12);
        b[1][3] = (Button) findViewById(R.id.b13);
        b[1][4] = (Button) findViewById(R.id.b14);
        b[1][5] = (Button) findViewById(R.id.b15);
        b[1][6] = (Button) findViewById(R.id.b16);
        b[1][7] = (Button) findViewById(R.id.b17);
        b[1][8] = (Button) findViewById(R.id.b18);

        b[2][0] = (Button) findViewById(R.id.b20);
        b[2][1] = (Button) findViewById(R.id.b21);
        b[2][2] = (Button) findViewById(R.id.b22);
        b[2][3] = (Button) findViewById(R.id.b23);
        b[2][4] = (Button) findViewById(R.id.b24);
        b[2][5] = (Button) findViewById(R.id.b25);
        b[2][6] = (Button) findViewById(R.id.b26);
        b[2][7] = (Button) findViewById(R.id.b27);
        b[2][8] = (Button) findViewById(R.id.b28);

        b[3][0] = (Button) findViewById(R.id.b30);
        b[3][1] = (Button) findViewById(R.id.b31);
        b[3][2] = (Button) findViewById(R.id.b32);
        b[3][3] = (Button) findViewById(R.id.b33);
        b[3][4] = (Button) findViewById(R.id.b34);
        b[3][5] = (Button) findViewById(R.id.b35);
        b[3][6] = (Button) findViewById(R.id.b36);
        b[3][7] = (Button) findViewById(R.id.b37);
        b[3][8] = (Button) findViewById(R.id.b38);

        b[4][0] = (Button) findViewById(R.id.b40);
        b[4][1] = (Button) findViewById(R.id.b41);
        b[4][2] = (Button) findViewById(R.id.b42);
        b[4][3] = (Button) findViewById(R.id.b43);
        b[4][4] = (Button) findViewById(R.id.b44);
        b[4][5] = (Button) findViewById(R.id.b45);
        b[4][6] = (Button) findViewById(R.id.b46);
        b[4][7] = (Button) findViewById(R.id.b47);
        b[4][8] = (Button) findViewById(R.id.b48);

        b[5][0] = (Button) findViewById(R.id.b50);
        b[5][1] = (Button) findViewById(R.id.b51);
        b[5][2] = (Button) findViewById(R.id.b52);
        b[5][3] = (Button) findViewById(R.id.b53);
        b[5][4] = (Button) findViewById(R.id.b54);
        b[5][5] = (Button) findViewById(R.id.b55);
        b[5][6] = (Button) findViewById(R.id.b56);
        b[5][7] = (Button) findViewById(R.id.b57);
        b[5][8] = (Button) findViewById(R.id.b58);

        b[6][0] = (Button) findViewById(R.id.b60);
        b[6][1] = (Button) findViewById(R.id.b61);
        b[6][2] = (Button) findViewById(R.id.b62);
        b[6][3] = (Button) findViewById(R.id.b63);
        b[6][4] = (Button) findViewById(R.id.b64);
        b[6][5] = (Button) findViewById(R.id.b65);
        b[6][6] = (Button) findViewById(R.id.b66);
        b[6][7] = (Button) findViewById(R.id.b67);
        b[6][8] = (Button) findViewById(R.id.b68);

        b[7][0] = (Button) findViewById(R.id.b70);
        b[7][1] = (Button) findViewById(R.id.b71);
        b[7][2] = (Button) findViewById(R.id.b72);
        b[7][3] = (Button) findViewById(R.id.b73);
        b[7][4] = (Button) findViewById(R.id.b74);
        b[7][5] = (Button) findViewById(R.id.b75);
        b[7][6] = (Button) findViewById(R.id.b76);
        b[7][7] = (Button) findViewById(R.id.b77);
        b[7][8] = (Button) findViewById(R.id.b78);

        b[8][0] = (Button) findViewById(R.id.b80);
        b[8][1] = (Button) findViewById(R.id.b81);
        b[8][2] = (Button) findViewById(R.id.b82);
        b[8][3] = (Button) findViewById(R.id.b83);
        b[8][4] = (Button) findViewById(R.id.b84);
        b[8][5] = (Button) findViewById(R.id.b85);
        b[8][6] = (Button) findViewById(R.id.b86);
        b[8][7] = (Button) findViewById(R.id.b87);
        b[8][8] = (Button) findViewById(R.id.b88);

        b[9][0] = (Button) findViewById(R.id.b0);
        b[9][1] = (Button) findViewById(R.id.b1);
        b[9][2] = (Button) findViewById(R.id.b2);
        b[9][3] = (Button) findViewById(R.id.b3);
        b[9][4] = (Button) findViewById(R.id.b4);
        b[9][5] = (Button) findViewById(R.id.b5);
        b[9][6] = (Button) findViewById(R.id.b6);
        b[9][7] = (Button) findViewById(R.id.b7);
        b[9][8] = (Button) findViewById(R.id.b8);


        s[0][0] = (RelativeLayout) findViewById(R.id.s00);
        s[0][1] = (RelativeLayout) findViewById(R.id.s01);
        s[0][2] = (RelativeLayout) findViewById(R.id.s02);
        s[0][3] = (RelativeLayout) findViewById(R.id.s03);
        s[0][4] = (RelativeLayout) findViewById(R.id.s04);
        s[0][5] = (RelativeLayout) findViewById(R.id.s05);
        s[0][6] = (RelativeLayout) findViewById(R.id.s06);
        s[0][7] = (RelativeLayout) findViewById(R.id.s07);

        s[1][0] = (RelativeLayout) findViewById(R.id.s10);
        s[1][1] = (RelativeLayout) findViewById(R.id.s11);
        s[1][2] = (RelativeLayout) findViewById(R.id.s12);
        s[1][3] = (RelativeLayout) findViewById(R.id.s13);
        s[1][4] = (RelativeLayout) findViewById(R.id.s14);
        s[1][5] = (RelativeLayout) findViewById(R.id.s15);
        s[1][6] = (RelativeLayout) findViewById(R.id.s16);
        s[1][7] = (RelativeLayout) findViewById(R.id.s17);

        s[2][0] = (RelativeLayout) findViewById(R.id.s20);
        s[2][1] = (RelativeLayout) findViewById(R.id.s21);
        s[2][2] = (RelativeLayout) findViewById(R.id.s22);
        s[2][3] = (RelativeLayout) findViewById(R.id.s23);
        s[2][4] = (RelativeLayout) findViewById(R.id.s24);
        s[2][5] = (RelativeLayout) findViewById(R.id.s25);
        s[2][6] = (RelativeLayout) findViewById(R.id.s26);
        s[2][7] = (RelativeLayout) findViewById(R.id.s27);

        s[3][0] = (RelativeLayout) findViewById(R.id.s30);
        s[3][1] = (RelativeLayout) findViewById(R.id.s31);
        s[3][2] = (RelativeLayout) findViewById(R.id.s32);
        s[3][3] = (RelativeLayout) findViewById(R.id.s33);
        s[3][4] = (RelativeLayout) findViewById(R.id.s34);
        s[3][5] = (RelativeLayout) findViewById(R.id.s35);
        s[3][6] = (RelativeLayout) findViewById(R.id.s36);
        s[3][7] = (RelativeLayout) findViewById(R.id.s37);

        s[4][0] = (RelativeLayout) findViewById(R.id.s40);
        s[4][1] = (RelativeLayout) findViewById(R.id.s41);
        s[4][2] = (RelativeLayout) findViewById(R.id.s42);
        s[4][3] = (RelativeLayout) findViewById(R.id.s43);
        s[4][4] = (RelativeLayout) findViewById(R.id.s44);
        s[4][5] = (RelativeLayout) findViewById(R.id.s45);
        s[4][6] = (RelativeLayout) findViewById(R.id.s46);
        s[4][7] = (RelativeLayout) findViewById(R.id.s47);

        s[5][0] = (RelativeLayout) findViewById(R.id.s50);
        s[5][1] = (RelativeLayout) findViewById(R.id.s51);
        s[5][2] = (RelativeLayout) findViewById(R.id.s52);
        s[5][3] = (RelativeLayout) findViewById(R.id.s53);
        s[5][4] = (RelativeLayout) findViewById(R.id.s54);
        s[5][5] = (RelativeLayout) findViewById(R.id.s55);
        s[5][6] = (RelativeLayout) findViewById(R.id.s56);
        s[5][7] = (RelativeLayout) findViewById(R.id.s57);

        s[6][0] = (RelativeLayout) findViewById(R.id.s60);
        s[6][1] = (RelativeLayout) findViewById(R.id.s61);
        s[6][2] = (RelativeLayout) findViewById(R.id.s62);
        s[6][3] = (RelativeLayout) findViewById(R.id.s63);
        s[6][4] = (RelativeLayout) findViewById(R.id.s64);
        s[6][5] = (RelativeLayout) findViewById(R.id.s65);
        s[6][6] = (RelativeLayout) findViewById(R.id.s66);
        s[6][7] = (RelativeLayout) findViewById(R.id.s67);

        s[7][0] = (RelativeLayout) findViewById(R.id.s70);
        s[7][1] = (RelativeLayout) findViewById(R.id.s71);
        s[7][2] = (RelativeLayout) findViewById(R.id.s72);
        s[7][3] = (RelativeLayout) findViewById(R.id.s73);
        s[7][4] = (RelativeLayout) findViewById(R.id.s74);
        s[7][5] = (RelativeLayout) findViewById(R.id.s75);
        s[7][6] = (RelativeLayout) findViewById(R.id.s76);
        s[7][7] = (RelativeLayout) findViewById(R.id.s77);

        s[8][0] = (RelativeLayout) findViewById(R.id.s80);
        s[8][1] = (RelativeLayout) findViewById(R.id.s81);
        s[8][2] = (RelativeLayout) findViewById(R.id.s82);
        s[8][3] = (RelativeLayout) findViewById(R.id.s83);
        s[8][4] = (RelativeLayout) findViewById(R.id.s84);
        s[8][5] = (RelativeLayout) findViewById(R.id.s85);
        s[8][6] = (RelativeLayout) findViewById(R.id.s86);
        s[8][7] = (RelativeLayout) findViewById(R.id.s87);

        s[9][0] = (RelativeLayout) findViewById(R.id.s0);
        s[9][1] = (RelativeLayout) findViewById(R.id.s1);
        s[9][2] = (RelativeLayout) findViewById(R.id.s2);
        s[9][3] = (RelativeLayout) findViewById(R.id.s3);
        s[9][4] = (RelativeLayout) findViewById(R.id.s4);
        s[9][5] = (RelativeLayout) findViewById(R.id.s5);
        s[9][6] = (RelativeLayout) findViewById(R.id.s6);
        s[9][7] = (RelativeLayout) findViewById(R.id.s7);

        p[0] = (ImageView) findViewById(R.id.p1);
        p[1] = (ImageView) findViewById(R.id.p2);

        t[0] = (TextView) findViewById(R.id.t1);
        t[1] = (TextView) findViewById(R.id.t2);

        t[0].setText("Me");
        t[1].setText("LAN");

        ArrayList<String> devices = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("/proc/net/arp"));
            String a;
            while ((a = br.readLine()) != null) {
                String[] all = a.split(" ");
                devices.add(all[0]);
            }
            connectedIP = devices.get(1);
            String[] temp = connectedIP.split("\\.");
            if(temp[3].equals("1")) {
                isClient = true;
            }
        } catch(Exception e){
            Log.d("Error in reading arp", e.toString());
        }

        //set the layout Client/Server
        if(isClient){
            wait_layout.setVisibility(View.VISIBLE);
            startClientSequence();
        } else{
            wait_layout.setVisibility(View.VISIBLE);
            startServerSequence();
        }
    }

    public void startClientSequence(){
        //Join layout stuff
        Toast.makeText(LAN.this, "Connect server at: "+connectedIP ,Toast.LENGTH_LONG).show();
        String client_name = clientName;
        if (client_name.equals("")) {
            Toast.makeText(LAN.this, "Enter User Name",Toast.LENGTH_LONG).show();
            return;
        }

        clientThread = new ClientThread(client_name, connectedIP, port_number);
        clientThread.start();

        ifServer=false;

/*        client_disconnect_button = (Button)findViewById(R.id.disconnect_client);
        client_disconnect_button.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(clientThread==null){
                            return;
                        }
                        clientThread.disconn();
                    }
                }
        );
*/
    }

    public void startServerSequence(){

        String server_name = "Server";
        if(server_name.equals("")){
            Toast.makeText(LAN.this, "Enter User Name",Toast.LENGTH_LONG).show();
            return;
        }

        serverName = server_name;
        userList = new ArrayList<>();

        ServerThread chatServerThread = new ServerThread();
        chatServerThread.start();

        ifServer=true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //outer classes
    class ChatClient {
        String name;
        Socket socket;
        ConnectThread chatThread;
    }

    private class ClientThread extends Thread{
        String name, destip;
        int port;
        public String message = "";
        boolean disconnect;

        public ClientThread(String n, String ip, int p) {
            name = n;
            destip = ip;
            port = p;
            disconnect = false;
        }

        public void run(){
            Socket sock = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                sock = new Socket(destip, port);
                dataOutputStream = new DataOutputStream(sock.getOutputStream());
                dataInputStream = new DataInputStream(sock.getInputStream());
                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();

                while(!disconnect){
                    if(dataInputStream.available()>0){
                        final String msgLog = dataInputStream.readUTF();
                        LAN.this.runOnUiThread(new Runnable(){
                            public void run(){
                                try {
                                    if (ingame) {
                                        Integer msg = Integer.parseInt(msgLog);
                                        if(msg.equals(100)) {
                                            undo_a();
                                        } else if(msg.equals(101)) {
                                            Toast.makeText(LAN.this, "your friend leave the game", Toast.LENGTH_LONG).show();
                                            home_a();
                                        } else if(msg.equals(102)) {
                                            restart_a();
                                        } else if (mode) {
                                            if (!myuser.get(9).contains(msg) && !opponent.get(9).contains(msg)) {
                                                opponent.get(9).add(msg);
                                                turn = true;
                                                if (myturn) {
                                                    b[9][msg].setText("O");
                                                    b[9][msg].setBackgroundColor(getResources().getColor(R.color.quick_play));
                                                } else {
                                                    b[9][msg].setText("X");
                                                    b[9][msg].setBackgroundColor(getResources().getColor(R.color.play_online));
                                                }
                                                endGame(opponent.get(9));
//                                        Toast.makeText(LAN3X3.this, "client send message " + msg, Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            Integer x = msg / 10;
                                            Integer y = msg % 10;
                                            if (valid(x)) {
                                                last = y;
                                                opponent.get(x).add(y);
                                                proceeding.add(10 * x + y);
                                                turn = true;
                                                if (myturn) {
                                                    b[x][y].setText("O");
                                                    b[x][y].setTextColor(getResources().getColor(R.color.quick_play));
                                                } else {
                                                    b[x][y].setText("X");
                                                    b[x][y].setTextColor(getResources().getColor(R.color.play_online));
                                                }
                                                int be = bendGame(x);
                                                if (be == 1) {
                                                    L[x].setVisibility(View.GONE);
                                                    b[9][x].setVisibility(View.VISIBLE);
                                                    if (myturn) {
                                                        b[9][x].setText("O");
                                                        b[9][x].setBackgroundColor(getResources().getColor(R.color.quick_play));
                                                    } else {
                                                        b[9][x].setText("X");
                                                        b[9][x].setBackgroundColor(getResources().getColor(R.color.play_online));
                                                    }
                                                    opponent.get(9).add(x);
                                                    endGame(opponent.get(9));
                                                } else if (be == 2) {
                                                    neutral.add(x);
                                                    endGame(opponent.get(9));
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        if (msgLog.equals("Welcome")) {
                                            wait_layout.setVisibility(View.GONE);
                                            create_server_layout.setVisibility(View.VISIBLE);
                                        } else if (msgLog.equals("true")) {
                                            but9.getBackground().setAlpha(127);
                                            but3.getBackground().setAlpha(255);
                                        } else if (msgLog.equals("false")) {
                                            but3.getBackground().setAlpha(127);
                                            but9.getBackground().setAlpha(255);
                                        } else if (msgLog.equals("falsetrue")) {
                                            mode = false;
                                            ingame = true;
                                            for (i = 0; i < 9; i++) {
                                                for (j = 0 ; j<9; j++)
                                                {
                                                    setOnClick(b[i][j], b[9][i], i, j, ifServer);
                                                    b[i][j].setText("");
                                                }
                                                b[9][i].setText("");
                                                b[9][i].setBackgroundColor(getResources().getColor(R.color.trans));
                                            }
                                            create_server_layout.setVisibility(View.GONE);
                                            game_layout.setVisibility(View.VISIBLE);
                                            for (i = 0; i<9; i++) {
                                                L[i].setVisibility(View.VISIBLE);
                                                b[9][i].setVisibility(View.GONE);
                                            }
                                        } else if (msgLog.equals("truetrue")) {
                                            mode = true;
                                            ingame = true;
                                            for (j = 0; j < 9; j++) {
                                                b[9][j].setText("");
                                                b[9][j].setBackgroundColor(getResources().getColor(R.color.trans));
                                                setOnClick3(b[9][j],j,ifServer);
                                            }
                                            create_server_layout.setVisibility(View.GONE);
                                            game_layout.setVisibility(View.VISIBLE);
                                            for (i = 0; i<9; i++) {
                                                L[i].setVisibility(View.GONE);
                                                b[9][i].setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(LAN.this, msgLog, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        });
                    }

                    if(!message.equals("")){
                        dataOutputStream.writeUTF(message);
                        dataOutputStream.flush();
                        message="";
                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
                final String eString = e.toString();
                LAN.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                LAN.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                    }

                });
            } finally {
                if (sock != null) {
                    try {
                        sock.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            LAN.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast.makeText(LAN.this, "no game found!!!", Toast.LENGTH_LONG).show();
                    home(findViewById(R.id.back));
                    disconn();
                }

            });
        }

        public void sendMsg(String msg){
            message = msg;
        }

        public void disconn(){
            disconnect = true;
        }
    }

    private class ServerThread extends Thread{
        @Override
        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(port_number);
                LAN.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LAN.this, "say client to connect" + serverSocket.getLocalPort(), Toast.LENGTH_LONG).show();
                    }
                });

                while (true) {
                    socket = serverSocket.accept();
                    ChatClient client = new ChatClient();
                    userList.add(client);
                    ConnectThread connectThread = new ConnectThread(client, socket);
                    connectThread.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class ConnectThread extends Thread{
        Socket socket;
        ChatClient connectClient;
        String msgToSend = "";
        String msgLog = "";

        ConnectThread(ChatClient client, Socket socket){
            connectClient = client;
            this.socket= socket;
            client.socket = socket;
            client.chatThread = this;
        }

        @Override
        public void run() {
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String n = dataInputStream.readUTF();

                connectClient.name = n;
                msgLog = connectClient.name + " connected@" +
                        connectClient.socket.getInetAddress() +
                        ":" + connectClient.socket.getPort() + "\n";

                LAN.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN.this, msgLog, Toast.LENGTH_LONG).show();
                        wait_layout.setVisibility(View.GONE);
                        create_server_layout.setVisibility(View.VISIBLE);
                    }
                });

                dataOutputStream.writeUTF("Welcome");
                dataOutputStream.flush();


                while (true) {
                    if (dataInputStream.available() > 0) {
                        final String newMsg = dataInputStream.readUTF();

                        LAN.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    if(ingame) {
                                        Integer msg = Integer.parseInt(newMsg);
                                        if(msg.equals(100)) {
                                            undo_a();
                                        } else if(msg.equals(101)) {
                                            Toast.makeText(LAN.this, "your friend leave the game", Toast.LENGTH_LONG).show();
                                            home_a();
                                        } else if(msg.equals(102)) {
                                            restart_a();
                                        } else if (mode) {
                                            if(!myuser.get(9).contains(msg) && !opponent.get(9).contains(msg)) {
                                                opponent.get(9).add(msg);
                                                turn = true;
                                                if(myturn) {
                                                    b[9][msg].setText("O");
                                                    b[9][msg].setBackgroundColor(getResources().getColor(R.color.quick_play));
                                                } else {
                                                    b[9][msg].setText("X");
                                                    b[9][msg].setBackgroundColor(getResources().getColor(R.color.play_online));
                                                }
                                                endGame(opponent.get(9));
//                                            Toast.makeText(LAN3X3.this, "Server Received Message " + msg, Toast.LENGTH_LONG).show();
                                                broadcastMsg(newMsg);
                                            }
                                        } else {
                                            Integer x = msg / 10;
                                            Integer y = msg % 10;
                                            if (valid(x)) {
                                                last = y;
                                                opponent.get(x).add(y);
                                                proceeding.add(10 * x + y);
                                                turn = true;
                                                if (myturn) {
                                                    b[x][y].setText("O");
                                                    b[x][y].setTextColor(getResources().getColor(R.color.quick_play));
                                                } else {
                                                    b[x][y].setText("X");
                                                    b[x][y].setTextColor(getResources().getColor(R.color.play_online));
                                                }
                                                int be = bendGame(x);
                                                if (be == 1) {
                                                    L[x].setVisibility(View.GONE);
                                                    b[9][x].setVisibility(View.VISIBLE);
                                                    if (myturn) {
                                                        b[9][x].setText("O");
                                                        b[9][x].setBackgroundColor(getResources().getColor(R.color.quick_play));
                                                    } else {
                                                        b[9][x].setText("X");
                                                        b[9][x].setBackgroundColor(getResources().getColor(R.color.play_online));
                                                    }
                                                    opponent.get(9).add(x);
                                                    endGame(opponent.get(9));
                                                } else if (be == 2) {
                                                    neutral.add(x);
                                                    endGame(opponent.get(9));
                                                }
                                            }
                                        }
                                    }
                                    else {
                                        if (newMsg.equals("true")) {
                                            but9.getBackground().setAlpha(127);
                                            but3.getBackground().setAlpha(255);
                                        } else if (newMsg.equals("false")) {
                                            but3.getBackground().setAlpha(127);
                                            but9.getBackground().setAlpha(255);
                                        } else if (newMsg.equals("falsetrue")) {
                                            mode = false;
                                            ingame = true;
                                            for (i = 0; i < 9; i++) {
                                                for (j = 0 ; j<9; j++)
                                                {
                                                    setOnClick(b[i][j], b[9][i], i, j, ifServer);
                                                    b[i][j].setText("");
                                                }
                                                b[9][i].setText("");
                                                b[9][i].setBackgroundColor(getResources().getColor(R.color.trans));
                                            }
                                            create_server_layout.setVisibility(View.GONE);
                                            game_layout.setVisibility(View.VISIBLE);
                                            for (i = 0; i<9; i++) {
                                                L[i].setVisibility(View.VISIBLE);
                                                b[9][i].setVisibility(View.GONE);
                                            }
                                        } else if (newMsg.equals("truetrue")) {
                                            mode = true;
                                            ingame = true;
                                            for (j = 0; j < 9; j++) {
                                                b[9][j].setText("");
                                                b[9][j].setBackgroundColor(getResources().getColor(R.color.trans));
                                                setOnClick3(b[9][j],j,ifServer);
                                            }
                                            create_server_layout.setVisibility(View.GONE);
                                            game_layout.setVisibility(View.VISIBLE);
                                            for (i = 0; i<9; i++) {
                                                L[i].setVisibility(View.GONE);
                                                b[9][i].setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(LAN.this, newMsg, Toast.LENGTH_LONG).show();
                                        }
                                    }
//                                            Toast.makeText(LAN.this, "Server Received Message " + msg, Toast.LENGTH_LONG).show();
                                    broadcastMsg(newMsg);
                                } catch (Exception e) {
                                    Toast.makeText(LAN.this, newMsg, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        broadcastMsg(newMsg);
                    }

                    if (!msgToSend.equals("")) {
                        dataOutputStream.writeUTF(String.valueOf(msgToSend));
                        dataOutputStream.flush();
                        msgToSend = "";
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                userList.remove(connectClient);
                LAN.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN.this,
                                connectClient.name + " removed.", Toast.LENGTH_LONG).show();
                        msgLog = "-- " + connectClient.name + " left\n";
                        LAN.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LAN.this, "Client's Last Message " + msgLog, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        }

        public void sendMsg(String msg){
            msgToSend = msg;
        }

        public void broadcastMsg(String msg){
            for(int i=0; i<userList.size(); i++){
                userList.get(i).chatThread.sendMsg(msg);
                //msgLog += "- send to " + userList.get(i).name + "\n";
            }
        }
    }

    public void setOnClick(final Button btn, final Button bt, final Integer x, final Integer y, final Boolean ifServer) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button sb = (Button) v;
                if (sb.getText().toString().equals("")) {
                    if (turn && ingame) {
                        if(ifServer) {
                            if(valid(x)) {
                                last = y;
                                opponent.get(x).add(y);
                                proceeding.add(10*x+y);
                                turn = false;
                                if(myturn) {
                                    btn.setText("X");
                                    btn.setTextColor(getResources().getColor(R.color.play_online));
                                } else {
                                    btn.setText("O");
                                    btn.setTextColor(getResources().getColor(R.color.quick_play));
                                }
                                int be=bendGame(x);
                                if(be==1) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    if(myturn) {
                                        bt.setText("X");
                                        bt.setBackgroundColor(getResources().getColor(R.color.play_online));
                                    } else {
                                        bt.setText("O");
                                        bt.setBackgroundColor(getResources().getColor(R.color.quick_play));
                                    }
                                    opponent.get(9).add(x);
                                    endGame(opponent.get(9));
                                } else if(be==2) {
                                    neutral.add(x);
                                    endGame(opponent.get(9));
                                }
                            }
                            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(10*x+y));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
                            serverSenderThread.start();
                        }
                        else if (ingame) {
                            if(valid(x)) {
                                last = y;
                                opponent.get(x).add(y);
                                proceeding.add(10*x+y);
                                turn = false;
                                if(myturn) {
                                    btn.setText("X");
                                    btn.setTextColor(getResources().getColor(R.color.play_online));
                                } else {
                                    btn.setText("O");
                                    btn.setTextColor(getResources().getColor(R.color.quick_play));
                                }
                                btn.setTextColor(getResources().getColor(R.color.quick_play));
                                int be=bendGame(x);
                                if(be==1) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    if(myturn) {
                                        bt.setText("X");
                                        bt.setBackgroundColor(getResources().getColor(R.color.play_online));
                                    } else {
                                        bt.setText("O");
                                        bt.setBackgroundColor(getResources().getColor(R.color.quick_play));
                                    }
                                    opponent.get(9).add(x);
                                    endGame(opponent.get(9));
                                } else if(be==2) {
                                    neutral.add(x);
                                    endGame(opponent.get(9));
                                }
                            }
                            clientThread.sendMsg(Integer.toString(10*x+y));
//                            Toast.makeText(LAN.this, x + " send to server", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    public void setOnClick3(final Button btn, final Integer x, final Boolean ifServer) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button sb = (Button) v;
                if (sb.getText().toString().equals("")) {
                    if (turn) {
                        if(ifServer) {
                            myuser.get(9).add(x);
                            turn = false;
                            if(myturn) {
                                sb.setText("X");
                                sb.setBackgroundColor(getResources().getColor(R.color.play_online));
                            } else {
                                sb.setText("O");
                                sb.setBackgroundColor(getResources().getColor(R.color.quick_play));
                            }
                            LAN.ServerSenderThread serverSenderThread = new LAN.ServerSenderThread(Integer.toString(x));
//                            Toast.makeText(LAN3X3.this, x + " send to client", Toast.LENGTH_LONG).show();
                            serverSenderThread.start();
                            endGame(myuser.get(9));
                        }
                        else {
                            if(myturn) {
                                sb.setText("X");
                                sb.setBackgroundColor(getResources().getColor(R.color.play_online));
                            } else {
                                sb.setText("O");
                                sb.setBackgroundColor(getResources().getColor(R.color.quick_play));
                            }
                            turn=false;
                            myuser.get(9).add(x);
                            clientThread.sendMsg(Integer.toString(x));
                            endGame(myuser.get(9));
//                            Toast.makeText(LAN.this, x + " send to server", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    public class ServerSenderThread extends Thread{
        String msgToSend = "";
        public ServerSenderThread(String msg){
            msgToSend = msg;
        }

        @Override
        public void run() {
            try {
                if (!msgToSend.equals("")) {
                    LAN.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(!msgToSend.equals("")) {
//                                Toast.makeText(LAN.this, "The Comeback Message " + msgToSend, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    broadcastMsg(msgToSend);
                    msgToSend="";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void broadcastMsg(String msg){
            for(int i=0; i<userList.size(); i++){
                userList.get(i).chatThread.sendMsg(msg);
                //msgLog += "- send to " + userList.get(i).name + "\n";
            }
        }
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
        if (mode) {
            for (int i = 0; i < 8; i++ ) {
                if (turn!=myturn)
                    s[9][i].setBackgroundColor(getResources().getColor(R.color.quick_play));
                else
                    s[9][i].setBackgroundColor(getResources().getColor(R.color.play_online));
            }
            if (turn!=myturn)
                t[0].setTextColor(getResources().getColor(R.color.quick_play));
            else
                t[0].setTextColor(getResources().getColor(R.color.play_online));
        }
        else {
            for (int i = 0; i < 8; i++) {
                if (turn)
                    s[9][i].setBackgroundColor(getResources().getColor(R.color.quick_play));
                else
                    s[9][i].setBackgroundColor(getResources().getColor(R.color.play_online));
            }
            if (turn) {
                t[0].setTextColor(getResources().getColor(R.color.quick_play));
                t[1].setTextColor(getResources().getColor(R.color.black));
            } else {
                t[0].setTextColor(getResources().getColor(R.color.black));
                t[1].setTextColor(getResources().getColor(R.color.play_online));
            }
            for (int i = 0; i < 9; i++) {
                if (valid(i)) {
                    if (turn)
                        L[i].setBackgroundColor(getResources().getColor(R.color.quick_play));
                    else
                        L[i].setBackgroundColor(getResources().getColor(R.color.play_online));
                    L[i].getBackground().setAlpha(20);
                } else {
                    L[i].setBackgroundColor(getResources().getColor(R.color.trans));
                }
            }
        }
    }

    private int bendGame(int a1) {
        List<Set<Integer>> x;
        onturn();
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
        if (game_layout.getVisibility() != View.VISIBLE) {
            game_layout.setVisibility(View.VISIBLE);
            menu_layout.setVisibility(View.GONE);
        }
        else {
            menu_layout.setVisibility(View.VISIBLE);
            game_layout.setVisibility(View.GONE);
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
                            Toast.makeText(LAN.this, "You Loss", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LAN.this, "You Won",Toast.LENGTH_LONG).show();
                        }
                        restart_a();
                        return;
                    }
                }
            }
        }
        if ((myuser.get(9)).size()+(opponent.get(9)).size()+neutral.size()==9) {
            Toast.makeText(LAN.this, "Match Tie!!", Toast.LENGTH_LONG).show();
            turn=!turn;
            restart_a();
            return;
        }
        onturn();
    }

    public void home(View view) {
        ingame = false;
        if (ifServer) {
            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(101));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
            serverSenderThread.start();
        } else {
            clientThread.sendMsg(Integer.toString(101));
        }
        home_a();
    }
    private void home_a() {
        restart_a();
        Intent main = new Intent(this, MainActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        return;
    }

    public void restart(View view) {
        if (ifServer) {
            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(102));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
            serverSenderThread.start();
        } else {
            clientThread.sendMsg(Integer.toString(102));
        }
    }

    private void restart_a() {
        win.clear();
        myuser.clear();
        opponent.clear();
        myuser = new ArrayList<>(10);
        opponent = new ArrayList<>(10);
        neutral.clear();
        proceeding.clear();
        last = 9;
        wait_layout.setVisibility(View.GONE);
        menu_layout.setVisibility(View.GONE);
        game_layout.setVisibility(View.GONE);
        create_server_layout.setVisibility(View.VISIBLE);
        ingame=false;
        turn=!turn;
        for (j = 0; j < 8; j++) {
            Set<Integer> abs = new HashSet<>();
            abs.addAll(Arrays.asList(my[j]));
            win.add(abs);
        }
        for (i=0; i<10; i++) {
            myuser.add(new ArrayList<Integer>());
            opponent.add(new ArrayList<Integer>());
        }
        return;
    }

    public void undo(View view) {
        if (ifServer) {
            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(100));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
            serverSenderThread.start();
        } else {
            clientThread.sendMsg(Integer.toString(100));
        }
        undo_a();
    }

    private void undo_a() {
        try {
            if (mode) {
                if (turn) {
                    int x = opponent.get(9).get(opponent.get(9).size() - 1);
                    opponent.get(9).remove(opponent.get(9).size() - 1);
                    b[9][x].setText("");
                    b[9][x].setBackgroundColor(getResources().getColor(R.color.trans));
                    turn = false;
                } else {
                    int x = myuser.get(9).get(myuser.get(9).size() - 1);
                    myuser.get(9).remove(myuser.get(9).size() - 1);
                    b[9][x].setText("");
                    b[9][x].setBackgroundColor(getResources().getColor(R.color.trans));
                    turn = true;
                }
            } else {
                int prev = proceeding.get(proceeding.size() - 1);
                proceeding.remove(proceeding.size() - 1);
                try {
                    opponent.get(prev / 10).remove(opponent.get(prev / 10).size() - 1);
                    b[prev / 10][prev % 10].setText("");
                    turn = false;
                    try {
                        if (opponent.get(9).get(opponent.get(9).size() - 1) == prev / 10) {
                            opponent.get(9).remove(opponent.get(9).size() - 1);
                            b[9][prev / 10].setVisibility(View.GONE);
                            L[prev / 10].setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {}
                    try {
                        if (neutral.get(neutral.size() - 1) == prev / 10) {
                            neutral.remove(neutral.size() - 1);
                            b[9][prev / 10].setVisibility(View.GONE);
                            L[prev / 10].setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {}
                } catch (Exception e1) {
                    myuser.get(prev / 10).remove(myuser.get(prev / 10).size() - 1);
                    b[prev / 10][prev % 10].setText("");
                    turn = true;
                    try {
                        if (myuser.get(9).get(myuser.get(9).size() - 1) == prev / 10) {
                            myuser.get(9).remove(myuser.get(9).size() - 1);
                            b[9][prev / 10].setVisibility(View.GONE);
                            L[prev / 10].setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {}
                    try {
                        if (neutral.get(neutral.size() - 1) == prev / 10) {
                            neutral.remove(neutral.size() - 1);
                            b[9][prev / 10].setVisibility(View.GONE);
                            L[prev / 10].setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {}
                }
                if (proceeding.size() == 0)
                    last = 9;
                else
                    last = proceeding.get(proceeding.size() - 1) % 10;
                onturn();
            }
        } catch (Exception e) {
            if (proceeding.size()==0)
                last=9;
            Toast.makeText(LAN.this, "can't undo", Toast.LENGTH_LONG).show();
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

    public void changemode(View view) {
        mode = !mode;
        if (!mode) {
            but3.getBackground().setAlpha(127);
            but9.getBackground().setAlpha(255);
        } else {
            but9.getBackground().setAlpha(127);
            but3.getBackground().setAlpha(255);
        }
        if(ifServer) {
            ServerSenderThread serverSenderThread = new ServerSenderThread(Boolean.toString(mode));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
            serverSenderThread.start();
        } else {
            clientThread.sendMsg(Boolean.toString(mode));
        }
    }
    public void firstspone(View view) {
        myturn = !myturn;
        Button b = (Button) view;
        if (myturn) {
            b.setText("X");
        } else {
            b.setText("O");
        }
    }
    public void start(View view) {
        if (mode) {
            for (j = 0; j < 9; j++) {
                b[9][j].setText("");
                b[9][j].setBackgroundColor(getResources().getColor(R.color.trans));
                setOnClick3(b[9][j],j,ifServer);
            }
            create_server_layout.setVisibility(View.GONE);
            game_layout.setVisibility(View.VISIBLE);
            for (i = 0; i<9; i++) {
                L[i].setVisibility(View.GONE);
                b[9][i].setVisibility(View.VISIBLE);
            }
        } else {
            for (i = 0; i < 9; i++) {
                for (j = 0 ; j<9; j++)
                {
                    setOnClick(b[i][j], b[9][i], i, j, ifServer);
                    b[i][j].setText("");
                }
                b[9][i].setText("");
                b[9][i].setBackgroundColor(getResources().getColor(R.color.trans));
            }
            create_server_layout.setVisibility(View.GONE);
            game_layout.setVisibility(View.VISIBLE);
            for (i = 0; i<9; i++) {
                L[i].setVisibility(View.VISIBLE);
                b[9][i].setVisibility(View.GONE);
            }
        }
        ingame = true;
        if(ifServer) {
            ServerSenderThread serverSenderThread = new ServerSenderThread(Boolean.toString(mode) + Boolean.toString(true));
//                            Toast.makeText(LAN.this, x + " send to client", Toast.LENGTH_LONG).show();
            serverSenderThread.start();
        } else {
            clientThread.sendMsg(Boolean.toString(mode) + Boolean.toString(true));
        }
    }
    @Override
    public void onBackPressed() {
        if (game_layout.getVisibility()==View.VISIBLE)
            Switch(findViewById(R.id.back));
        else
            home(findViewById(R.id.back));
    }
}
