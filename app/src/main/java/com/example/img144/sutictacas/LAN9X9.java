package com.example.img144.sutictacas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class LAN9X9 extends AppCompatActivity {
    Button b[][] = new Button[10][9];
    LinearLayout L[] = new LinearLayout[9];
    Boolean turn = true;
    Boolean ifServer = false;
    int i,j;
    int last=9;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<List<Integer>> myuser = new ArrayList<>(10);
    List<List<Integer>> opponent = new ArrayList<>(10);
    List<Integer> neutral = new ArrayList<>();

    public final int port_number = 8080;
    public ServerSocket serverSocket;
    public boolean isClient = false;
    public String connectedIP="";
    public String serverName = "Server";
    public String clientName = "Client";

    Button client_join,server_create;
    RelativeLayout join_layout, create_server_layout, game_layout;
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
        setContentView(R.layout.lan9_x9);

        join_layout = (RelativeLayout)findViewById(R.id.join_layout);
        game_layout = (RelativeLayout)findViewById(R.id.game_layout);
        create_server_layout = (RelativeLayout)findViewById(R.id.create_server_layout);

        turn = true;

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
            join_layout.setVisibility(View.VISIBLE);
            startClientSequence();
        } else{
            create_server_layout.setVisibility(View.VISIBLE);
            startServerSequence();
        }
    }

    public void startClientSequence(){
        //Join layout stuff
        Toast.makeText(LAN9X9.this, "Connect server at: "+connectedIP ,Toast.LENGTH_LONG).show();
        client_join = (Button)findViewById(R.id.join_button);
        client_join.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String client_name = clientName;
                        if (client_name.equals("")) {
                            Toast.makeText(LAN9X9.this, "Enter User Name",Toast.LENGTH_LONG).show();
                            return;
                        }

                        join_layout.setVisibility(View.GONE);
                        game_layout.setVisibility(View.VISIBLE);

                        clientThread = new ClientThread(client_name, connectedIP, port_number);
                        clientThread.start();
                    }
                }
        );
        ifServer=false;

        for (i = 0; i < 9; i++) {
            for (j = 0 ; j<9; j++)
                setOnClick(b[i][j], b[9][i], i, j, ifServer);
        }
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
        //create Server stuff
        server_create = (Button)findViewById(R.id.create_server_button);
        server_create.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){

                        String server_name = "Server";
                        if(server_name.equals("")){
                            Toast.makeText(LAN9X9.this, "Enter User Name",Toast.LENGTH_LONG).show();
                            return;
                        }

                        serverName = server_name;
                        userList = new ArrayList<>();

                        ServerThread chatServerThread = new ServerThread();
                        chatServerThread.start();

                        game_layout.setVisibility(View.VISIBLE);
                        create_server_layout.setVisibility(View.GONE);

                    }
                }
        );

        ifServer=true;
        for (i = 0; i < 9; i++) {
            for (j = 0 ; j<9; j++)
                setOnClick(b[i][j], b[9][i], i, j, ifServer);
        }
    }

    public void setOnClick(final Button btn, final Button bt, final Integer x, final Integer y, final Boolean ifServer) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button sb = (Button) v;
                if (sb.getText().toString().equals("")) {
                    if (turn) {
                        if(ifServer) {
                            if(valid(x, y)) {
                                myuser.get(x).add(y);
                                turn = false;
                                btn.setText("X");
                                int be = bendGame(myuser.get(x));
                                if(be==1) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    bt.setText("X");
                                    myuser.get(9).add(x);
                                    endGame(myuser.get(9));
                                } else if(be==2) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    bt.setText("#");
                                    neutral.add(x);
                                }
                            }
                            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(10*x+y));
//                            Toast.makeText(LAN9X9.this, x + " send to client", Toast.LENGTH_LONG).show();
                            serverSenderThread.start();
                        }
                        else {
                            if(valid(x, y)) {
                                myuser.get(x).add(y);
                                turn = false;
                                btn.setText("X");
                                int be=bendGame(myuser.get(x));
                                if(be==1) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    bt.setText("X");
                                    myuser.get(9).add(x);
                                    endGame(myuser.get(9));
                                } else if(be==2) {
                                    L[x].setVisibility(View.GONE);
                                    b[9][x].setVisibility(View.VISIBLE);
                                    bt.setText("#");
                                    neutral.add(x);
                                }
                            }
                            clientThread.sendMsg(Integer.toString(10*x+y));
//                            Toast.makeText(LAN9X9.this, x + " send to server", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
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
                        LAN9X9.this.runOnUiThread(new Runnable(){
                            public void run(){
                                try {
                                    Integer msg = Integer.parseInt(msgLog);
                                    Integer x = msg/10;
                                    Integer y = msg%10;
                                    if(valid(x, y)) {
                                        opponent.get(x).add(y);
                                        turn = true;
                                        b[x][y].setText("O");
                                        int be=bendGame(opponent.get(x));
                                        if(be==1) {
                                            L[x].setVisibility(View.GONE);
                                            b[9][x].setVisibility(View.VISIBLE);
                                            b[9][x].setText("O");
                                            opponent.get(9).add(x);
                                            endGame(opponent.get(9));
                                        } else if(be==2) {
                                            L[x].setVisibility(View.GONE);
                                            b[9][x].setVisibility(View.VISIBLE);
                                            b[9][x].setText("#");
                                            neutral.add(x);
                                        }
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LAN9X9.this, msgLog, Toast.LENGTH_LONG).show();
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
                LAN9X9.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN9X9.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                LAN9X9.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN9X9.this, eString, Toast.LENGTH_LONG).show();
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

            LAN9X9.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    join_layout.setVisibility(View.VISIBLE);
                    game_layout.setVisibility(View.GONE);
                }

            });
        }

        public void sendMsg(String msg){
            message = msg;
        }
/*
        public void disconn(){
            disconnect = true;
            Log.d("gg","disconnected at client side");
        }
*/
    }

    private class ServerThread extends Thread{
        @Override
        public void run() {
            Socket socket = null;
            try {
                serverSocket = new ServerSocket(port_number);
                LAN9X9.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LAN9X9.this, "say client to connect" + serverSocket.getLocalPort(), Toast.LENGTH_LONG).show();
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

                LAN9X9.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN9X9.this, msgLog, Toast.LENGTH_LONG).show();
                    }
                });

                dataOutputStream.writeUTF("Welcome " + n + "\n");
                dataOutputStream.flush();
                broadcastMsg(n + " join our chat.\n");

                while (true) {
                    if (dataInputStream.available() > 0) {
                        final String newMsg = dataInputStream.readUTF();

                        LAN9X9.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    Integer msg = Integer.parseInt(newMsg);
                                    Integer x = msg/10;
                                    Integer y = msg%10;
                                    if(valid(x, y)) {
                                        opponent.get(x).add(y);
                                        turn = true;
                                        b[x][y].setText("O");
                                        int be=bendGame(opponent.get(x));
                                        if(be==1) {
                                            L[x].setVisibility(View.GONE);
                                            b[9][x].setVisibility(View.VISIBLE);
                                            b[9][x].setText("O");
                                            opponent.get(9).add(x);
                                            endGame(opponent.get(9));
                                        } else if(be==2) {
                                            L[x].setVisibility(View.GONE);
                                            b[9][x].setVisibility(View.VISIBLE);
                                            b[9][x].setText("#");
                                            neutral.add(x);
                                        }
                                    }
//                                            Toast.makeText(LAN9X9.this, "Server Received Message " + msg, Toast.LENGTH_LONG).show();
                                    broadcastMsg(newMsg);
                                } catch (Exception e) {
                                    Toast.makeText(LAN9X9.this, newMsg, Toast.LENGTH_LONG).show();
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
                LAN9X9.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN9X9.this,
                                connectClient.name + " removed.", Toast.LENGTH_LONG).show();
                        msgLog = "-- " + connectClient.name + " left\n";
                        LAN9X9.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LAN9X9.this, "Client's Last Message " + msgLog, Toast.LENGTH_LONG).show();
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

    public class ServerSenderThread extends Thread{
        String msgToSend = "";
        public ServerSenderThread(String msg){
            msgToSend = msg;
        }

        @Override
        public void run() {
            try {
                if (!msgToSend.equals("")) {
                    LAN9X9.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(msgToSend!="") {
//                                Toast.makeText(LAN9X9.this, "The Comeback Message " + msgToSend, Toast.LENGTH_LONG).show();
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

    public boolean valid(Integer x, Integer y) {
        if (myuser.get(x).contains(y) || opponent.get(x).contains(y)) {
            return false;
        } else if (myuser.get(9).contains(x) || opponent.get(9).contains(x) || neutral.contains(x)) {
            return false;
        } else if(last==x || last==9) {
            last=y;
            return true;
        } else if (myuser.get(9).contains(last) || opponent.get(9).contains(last) || neutral.contains(last)) {
            last=y;
            return true;
        } else {
            return false;
        }
    }

    private int bendGame(List a1) {
        List<Set<Integer>> x = getSubsets(a1, 3);
        if (x.size() > 0) {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    if ((win.get(j)).equals(x.get(i))) {
                        return 1;
                    }
                }
            }
        }
        if (myuser.size()+opponent.size()==9) {
            return 1;
        } else {
            return 0;
        }
    }

    private void endGame(List a1) {
        List<Set<Integer>> x = getSubsets(a1, 3);
        if (x.size() > 0) {
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < 8; j++) {
                    if ((win.get(j)).equals(x.get(i))) {
                        Intent main = new Intent(this, MainActivity.class);
                        main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                        return;
                    }
                }
            }
        }
        if (myuser.size()+opponent.size()==9) {
            Intent main = new Intent(this, MainActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
