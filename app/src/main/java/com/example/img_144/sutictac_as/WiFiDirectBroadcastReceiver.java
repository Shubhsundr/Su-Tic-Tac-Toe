package com.example.img_144.sutictac_as;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import java.util.ArrayList;
import java.util.List;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {


    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private JWiFi mJwiFi;
    private List<WifiP2pDevice> mPeers;
    private List<WifiP2pConfig> mConfigs;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, JWiFi jwifi) {
        this.mManager = manager;
        this.mChannel = channel;
        this.mJwiFi = jwifi;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                mJwiFi.mTextView.setText("Wifi-Detect: Enabled");
            } else {
                mJwiFi.mTextView.setText("Wifi-Detect: Disabled");
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            mPeers = new ArrayList<WifiP2pDevice>();
            mConfigs = new ArrayList<WifiP2pConfig>();

            if (mManager != null) {
                WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
                    @Override
                    public void onPeersAvailable(WifiP2pDeviceList peerList) {
                        mPeers.clear();
                        mPeers.addAll(peerList.getDeviceList());
                        mJwiFi.displayPeers(peerList);
                        mPeers.addAll(peerList.getDeviceList());
                        for(int i=0; i<peerList.getDeviceList().size(); i++) {
                            WifiP2pConfig config = new WifiP2pConfig();
                            config.deviceAddress = mPeers.get(i).deviceAddress;
                            mConfigs.add(config);
                        }
                    }
                };
                mManager.requestPeers(mChannel, peerListListener);
            }
        }
    }
}
