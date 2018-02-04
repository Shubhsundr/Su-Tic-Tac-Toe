package com.example.img_144.sutictac_as;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by IMG-144 on 2/5/2018.
 */

public class Client extends Thread {
    InetAddress address;

    public Client(InetAddress address){
        this.address = address;
    }
    @Override
    public void run() {
        communication();
    }

    private void communication(){
        Socket socket = null;
        try {
            //Create a stream socket and connect it to the specified port number on the specified host
            socket = new Socket(address, Server.PORT);

            //Read server-side data
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //Send data to the server
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Log.d("new","please enter: \t");
//            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
//            out.writeUTF(str);
            out.writeUTF("test");

            String ret = input.readUTF();
            Log.d("new","Back to the server is: " + ret);
            // If you receive "OK" then disconnect
            if ("OK".equals(ret)) {
                Log.d("new","Clients will close the connection");
                Thread.sleep(500);
            }

            out.close();
            input.close();
        } catch (Exception e) {
            Log.d("new","The client is abnormal:" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    socket = null;
                    Log.d("new","Client finally abnormal:" + e.getMessage());
                }
            }
        }
    }
}
