package com.example.img144.sutictacas;

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
import java.util.Set;

/**
 * Created by kanish on 3/4/18.
 */

public class LAN extends AppCompatActivity {
    Button b[] = new Button[9];
    Boolean turn=true;
    int j;
    Integer[][] my = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    List<Set<Integer>> win = new ArrayList<>();
    List<Integer> myuser = new ArrayList<>();
    List<Integer> opponent = new ArrayList<>();

    public final int port_number = 8080;
    public ServerSocket serverSocket;
    public boolean isClient = false;
    public String connectedIP="";
    public String serverName = "";
    public String clientName = "";

    Button client_join,server_create;
    RelativeLayout join_layout, create_server_layout, game_layout;
    ClientThread clientThread = null;
    List<ChatClient> userList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        for (j = 0; j < 8; j++) {
            Set<Integer> abs = new HashSet<>();
            abs.addAll(Arrays.asList(my[j]));
            win.add(abs);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lan);
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

        for (j = 0; j < 9; j++) {
            setOnClick(b[j],j);
        }

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
        Toast.makeText(LAN.this, "Connect server at: "+connectedIP ,Toast.LENGTH_LONG).show();
        client_join = (Button)findViewById(R.id.join_button);
        client_join.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        String client_name = "zero";
                        if (client_name.equals("")) {
                            Toast.makeText(LAN.this, "Enter User Name",Toast.LENGTH_LONG).show();
                            return;
                        }
                        clientName = client_name;
                        join_layout.setVisibility(View.GONE);
                        game_layout.setVisibility(View.VISIBLE);

                        clientThread = new ClientThread(client_name, connectedIP, port_number);
                        clientThread.start();
                    }
                }
        );

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
                        String server_name = "Cross";
                        if(server_name.equals("")){
                            Toast.makeText(LAN.this, "Enter User Name",Toast.LENGTH_LONG).show();
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
    }

    public void setOnClick(final Button btn, final Integer x) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button sb = (Button) v;
                if (sb.getText().toString().equals("")) {
                    if (turn) {
                        myuser.add(x);
                        turn = false;
                        sb.setText("X");
                        ServerSenderThread serverSenderThread = new ServerSenderThread(Integer.toString(x));
                        Toast.makeText(LAN.this, "You clicked on " + x + " ",Toast.LENGTH_LONG).show();
                        serverSenderThread.start();
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
                        LAN.this.runOnUiThread(new Runnable(){
                            public void run(){
                                    try {
                                        Integer msg = Integer.parseInt(msgLog);
                                        opponent.add(msg);
                                        turn = true;
                                        b[msg].setText("O");
                                        Toast.makeText(LAN.this, "1st received message " + msg + " ",Toast.LENGTH_LONG).show();
                                        } catch (NumberFormatException ex) {
                                        Toast.makeText(LAN.this, "1st msgLog is " + msgLog + " ",Toast.LENGTH_LONG).show();}
                            }
                        });
                    }

                    if(!message.equals("")){
                        dataOutputStream.writeUTF(String.valueOf(message));
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
                        Toast.makeText(LAN.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                e.printStackTrace();
                final String eString = e.toString();
                LAN.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(LAN.this, eString, Toast.LENGTH_LONG).show();
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
                    }
                });

                dataOutputStream.writeUTF("Welcome " + n + "\n");
                dataOutputStream.flush();
                broadcastMsg(n + " join our chat.\n");

                while (true) {
                    if (dataInputStream.available() > 0) {
                        final String newMsg = dataInputStream.readUTF();

                        LAN.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                    try {
                                        Integer msg = Integer.parseInt(newMsg);
                                        opponent.add(msg);
                                        turn = true;
                                        b[msg].setText("O");
                                        Toast.makeText(LAN.this, "2nd Received Message " + msg, Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                        Toast.makeText(LAN.this, "2nd Message Log " + newMsg, Toast.LENGTH_LONG).show();
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

    private class ServerSenderThread extends Thread{
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
                            try {
                                int x = Integer.parseInt(msgToSend);
                                b[x].setText("#");
                            }
                            catch (Exception e) {
                                Toast.makeText(LAN.this , " " + e, Toast.LENGTH_LONG).show();
                            }
                           Toast.makeText(LAN.this, "Both Received Comeback Message " + msgToSend, Toast.LENGTH_LONG).show();
                        }
                    });
                    broadcastMsg(msgToSend);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void broadcastMsg(String msg){
            for(int i=0; i<userList.size(); i++){
                userList.get(i).chatThread.sendMsg(msg);
                //msgLog += "- send to " + userList.get(i).name + "\n";
            }
        }
    }
}
