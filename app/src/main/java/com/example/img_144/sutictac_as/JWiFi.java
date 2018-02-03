package com.example.img_144.sutictac_as;


import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class JWiFi extends AppCompatActivity {
    public TextView mTextView;
    public ListView mListView;
    private Button searchBtn, playBtn;
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

        searchBtn = (Button) findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(v);
            }
        });

        playBtn = (Button) findViewById(R.id.play_button);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(v);
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
    public void play(View view) {
        Toast.makeText(getApplicationContext(), "The game has been connected", Toast.LENGTH_LONG).show();
    }
    public void displayPeers(WifiP2pDeviceList peerlist) {
        wifiP2pArrayAdapter.clear();
        for(WifiP2pDevice peer : peerlist.getDeviceList()) {
            wifiP2pArrayAdapter.add(peer.deviceName + "\n" + peer.deviceAddress);
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
