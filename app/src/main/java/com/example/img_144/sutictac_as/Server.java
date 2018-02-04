package com.example.img_144.sutictac_as;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by IMG-144 on 2/5/2018.
 */

public class Server extends Thread {
    InetAddress address;
    public static final int PORT = 1234;
    public Server(InetAddress groupOwnerAddress){
        address = groupOwnerAddress;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT,5,address);
            Socket socket = null;
            while (true){
                socket = serverSocket.accept();
                Log.d("new","Add connection："+socket.getInetAddress()+":"+socket.getPort());
                new HandlerThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // Read client data
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String clientInputStr = input.readUTF();//Here and pay attention to the client output stream write method corresponding, otherwise it will throw EOFException
                // Handle client data
                Log.d("new","Client sent over the content:" + clientInputStr);

                // Reply to the client information
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Log.d("new","please enter:\t");
//                // Send the keyboard input line
//                String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
//                out.writeUTF(s);

                out.writeUTF("back");

                out.close();
                input.close();
            } catch (Exception e) {
                Log.d("new","Server run exception: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        Log.d("new","The server finally abnormal:" + e.getMessage());
                    }
                }
            }
        }
    }
}

