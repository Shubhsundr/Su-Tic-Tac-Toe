package com.example.img144.sutictacas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LAN3X3 extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn = true;
    Boolean myturn;
    Boolean ifServer = false;
    int j;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<Integer> myuser = new ArrayList<>();
    List<Integer> opponent = new ArrayList<>();

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lan3_x3);

        Bundle bundle = this.getIntent().getExtras();
        myturn = bundle.getBoolean("my_turn");

        join_layout = (RelativeLayout)findViewById(R.id.join_layout);
        game_layout = (RelativeLayout)findViewById(R.id.game_layout);
        create_server_layout = (RelativeLayout)findViewById(R.id.create_server_layout);

        //server layout stuff
        b[0] = (Button) findViewById(R.id.b0);
        b[1] = (Button) findViewById(R.id.b1);
        b[2] = (Button) findViewById(R.id.b2);
        b[3] = (Button) findViewById(R.id.b3);
        b[4] = (Button) findViewById(R.id.b4);
        b[5] = (Button) findViewById(R.id.b5);
        b[6] = (Button) findViewById(R.id.b6);
        b[7] = (Button) findViewById(R.id.b7);
        b[8] = (Button) findViewById(R.id.b8);


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
        Toast.makeText(LAN3X3.this, "Connect server at: "+connectedIP ,Toast.LENGTH_LONG).show();
        client_join = (Button)findViewById(R.id.join_button);
        client_join.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String client_name = clientName;
                        if (client_name.equals("")) {
                            Toast.makeText(LAN3X3.this, "Enter User Name",Toast.LENGTH_LONG).show();
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

        for (j = 0; j < 9; j++) {
            setOnClick(b[j],j,ifServer);
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
                            Toast.makeText(LAN3X3.this, "Enter User Name",Toast.LENGTH_LONG).show();
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
        for (j = 0; j < 9; j++) {
            setOnClick(b[j],j,ifServer);
        }
    }

    public void setOnClick(final Button btn, final Integer x, final Boolean ifServer) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button sb = (Button) v;
                if (sb.getText().toString().equals("")) {
                    if (turn) {
                        if(ifServer) {
                            myuser.add(x);
                            turn = false;
                            if(myturn==true) {
                                sb.setText("X");
                            } else {
                                sb.setText("O");
                            }
                            ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(x));
//                            Toast.makeText(LAN3X3.this, x + " send to client", Toast.LENGTH_LONG).show();
                            serverSenderThread.start();
                            endGame(myuser);
                        }
                        else {
                            if(myturn==true) {
                                sb.setText("X");
                            } else {
                                sb.setText("O");
                            }
                            turn=false;
                            myuser.add(x);
                            clientThread.sendMsg(Integer.toString(x));
                            endGame(myuser);
//                            Toast.makeText(LAN3X3.this, x + " send to server", Toast.LENGTH_LONG).show();
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
                        LAN3X3.this.runOnUiThread(new Runnable(){
                            public void run(){
                                try {
                                    Integer msg = Integer.parseInt(msgLog);
                                    if(!myuser.contains(msg) && !opponent.contains(msg)) {
                                        opponent.add(msg);
                                        turn = true;
                                        if(myturn==true) {
                                            b[msg].setText("O");
                                        } else {
                                            b[msg].setText("X");
                                        }
                                        endGame(opponent);
//                                        Toast.makeText(LAN3X3.this, "client send message " + msg, Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LAN3X3.this, msgLog, Toast.LENGTH_LONG).show();
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
                LAN3X3.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN3X3.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                LAN3X3.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN3X3.this, eString, Toast.LENGTH_LONG).show();
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

            LAN3X3.this.runOnUiThread(new Runnable() {

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
                LAN3X3.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LAN3X3.this, "say client to connect" + serverSocket.getLocalPort(), Toast.LENGTH_LONG).show();
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

                LAN3X3.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN3X3.this, msgLog, Toast.LENGTH_LONG).show();
                    }
                });

                dataOutputStream.writeUTF("Welcome " + n + "\n");
                dataOutputStream.flush();
                broadcastMsg(n + " join our chat.\n");

                while (true) {
                    if (dataInputStream.available() > 0) {
                        final String newMsg = dataInputStream.readUTF();

                        LAN3X3.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    Integer msg = Integer.parseInt(newMsg);
                                    if(!myuser.contains(msg) && !opponent.contains(msg)) {
                                        opponent.add(msg);
                                        turn = true;
                                        if(myturn==true) {
                                            b[msg].setText("O");
                                        } else {
                                            b[msg].setText("X");
                                        }
                                        endGame(opponent);
//                                            Toast.makeText(LAN3X3.this, "Server Received Message " + msg, Toast.LENGTH_LONG).show();
                                        broadcastMsg(newMsg);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(LAN3X3.this, newMsg, Toast.LENGTH_LONG).show();
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
                LAN3X3.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN3X3.this,
                                connectClient.name + " removed.", Toast.LENGTH_LONG).show();
                        msgLog = "-- " + connectClient.name + " left\n";
                        LAN3X3.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LAN3X3.this, "Client's Last Message " + msgLog, Toast.LENGTH_LONG).show();
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
                    LAN3X3.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(msgToSend!="") {
//                                Toast.makeText(LAN3X3.this, "The Comeback Message " + msgToSend, Toast.LENGTH_LONG).show();
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
            Toast.makeText(LAN3X3.this, "can't undo", Toast.LENGTH_LONG).show();
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
