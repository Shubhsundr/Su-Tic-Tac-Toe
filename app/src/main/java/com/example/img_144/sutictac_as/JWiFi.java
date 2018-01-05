package com.example.img_144.sutictac_as;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import static android.R.id.list;

public class JWiFi extends AppCompatActivity {
    WifiManager mainWifiObj;
    //WifiScanReceiver wifiReciever;
    ListView list;
    String wifis[];

    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jwi_fi);
/*
        TextView config=(TextView) findViewById(R.id.text);

        WifiManager onwifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        onwifi.setWifiEnabled(true);
        wificonfig(config,onwifi);
    }
    protected void wificonfig(TextView config,WifiManager onwifi) {
        int totalTime = 50000; // in nanoseconds
        long startTime = System.nanoTime();
        boolean toFinish = false;

        while (!toFinish && !onwifi.isWifiEnabled())
        {
            toFinish = (System.nanoTime() - startTime >= totalTime);
        }
        onwifi.startScan();
*/
        ListView list = (ListView) findViewById(R.id.list);
        mainWifiObj = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mainWifiObj.setWifiEnabled(true);
        int tTime = 5000000; // in nanoseconds
        long sTime = System.nanoTime();
        boolean toFin = false;

        while (!toFin && !mainWifiObj.isWifiEnabled()) {
            toFin = (System.nanoTime() - sTime >= tTime);
        }
        int totalTime = 2000000000; // in nanoseconds
        long startTime = System.nanoTime();
        boolean toFinish = false;

        while (!toFinish) {
            toFinish = (System.nanoTime() - startTime >= totalTime);
            //wifiReciever = new WifiScanReceiver();
            mainWifiObj.startScan();
            // listening to single list item on click
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // selected item
                    String ssid = ((TextView) view).getText().toString();
                    connectToWifi(ssid);
                    Toast.makeText(JWiFi.this, "Wifi SSID : " + ssid, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

/*
    protected void onPause() {
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
*/
    class WifiScanReceiver extends BroadcastReceiver {
        @SuppressLint("UseValueOf")
        public void onReceive(Context c, Intent intent) {
            List<ScanResult> wifiScanList = mainWifiObj.getScanResults();
            wifis = new String[wifiScanList.size()];
            for(int i = 0; i < wifiScanList.size(); i++){
                wifis[i] = ((wifiScanList.get(i)).toString());
            }
            String filtered[] = new String[wifiScanList.size()];
            int counter = 0;
            for (String eachWifi : wifis) {
                String[] temp = eachWifi.split(",");

                filtered[counter] = temp[0].substring(5).trim();//+"\n" + temp[2].substring(12).trim()+"\n" +temp[3].substring(6).trim();//0->SSID, 2->Key Management 3-> Strength

                counter++;

            }
            list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.lwi_fi,R.id.label, filtered));

        }
    }

    private void finallyConnect(String networkPass, String networkSSID) {
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", networkSSID);
        wifiConfig.preSharedKey = String.format("\"%s\"", networkPass);

        // remember id
        int netId = mainWifiObj.addNetwork(wifiConfig);
        mainWifiObj.disconnect();
        mainWifiObj.enableNetwork(netId, true);
        mainWifiObj.reconnect();

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"\"" + networkSSID + "\"\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        mainWifiObj.addNetwork(conf);
    }

    private void connectToWifi(final String wifiSSID) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.connect);
        dialog.setTitle("Connect to Network");
        TextView textSSID = (TextView) dialog.findViewById(R.id.textSSID1);

        Button dialogButton = (Button) dialog.findViewById(R.id.okButton);
        pass = (EditText) dialog.findViewById(R.id.textPassword);
        textSSID.setText(wifiSSID);

        // if button is clicked, connect to the network;
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkPassword = pass.getText().toString();
                finallyConnect(checkPassword, wifiSSID);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
