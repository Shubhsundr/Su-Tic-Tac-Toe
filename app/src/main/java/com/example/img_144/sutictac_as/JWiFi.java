package com.example.img_144.sutictac_as;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JWiFi extends AppCompatActivity {
    public TextView mTextView;
    public ListView mListView;
    private Button searchBtn, homeBtn;
    private IntentFilter mIntentFilter;
    private ArrayAdapter<String> wifiP2pArrayAdapter;

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jwi_fi);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mTextView = (TextView) findViewById(R.id.textView2);
        mListView = (ListView) findViewById(R.id.list);

        wifiP2pArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        mListView.setAdapter(wifiP2pArrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("new", "Items " + wifiP2pArrayAdapter.getItem(position));

                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = wifiP2pArrayAdapter.getItem(position);
                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                        Log.d("new","device has been connected");
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(JWiFi.this, "Connect failed. Retry.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        searchBtn = (Button) findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        homeBtn = (Button) findViewById(R.id.home_button);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(JWiFi.this, MainActivity.class);
                startActivity(main);
                return;
            }
        });

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);
    }

    public void search(View view) {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                mTextView.setText("Wifi-Direct Searching...");
            }

            @Override
            public void onFailure(int reason) {
                mTextView.setText("Error: Code " + reason);
            }
        });
    }

    public void displayPeers(WifiP2pDeviceList peerlist) {
        wifiP2pArrayAdapter.clear();
        for(WifiP2pDevice peer : peerlist.getDeviceList()) {
            wifiP2pArrayAdapter.add(peer.deviceAddress);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
