package com.world.img144.sutictacas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class middle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle);
        final Context context = getApplicationContext();
        WifiApControl ap = new WifiApControl(context);
        //if (ap.isApOn()) {
        //    ap.turnWifiApOff();
        //}

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    public void host(View view) {
        final Context context = getApplicationContext();
        WifiApControl ap = new WifiApControl(context);
        if (ap.isApOn()) {
            Intent l = new Intent(getApplicationContext(), LAN.class);
            startActivity(l);
        } else {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.userprompt, null);
            final SharedPreferences p = getSharedPreferences("data", MODE_PRIVATE);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = promptsView
                    .findViewById(R.id.user);

            final EditText userPass = promptsView
                    .findViewById(R.id.pass);
            String value = p.getString("user", null);
            userInput.setText(value);
            value = p.getString("pass", null);
            userPass.setText(value);
            // set dialog message
            alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result
                            // edit text
                            SharedPreferences.Editor edit = p.edit();
                            edit.putString("user", userInput.getText().toString());
                            edit.putString("pass", userPass.getText().toString());
                            edit.apply();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (Settings.System.canWrite(context)) {

                                } else {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }
                            WifiApControl ap = new WifiApControl(context);
                            ap.createNewNetwork(userInput.getText().toString(), userPass.getText().toString());
                            if (ap.isApOn()) {
                                Intent l = new Intent(getApplicationContext(), LAN.class);
                                startActivity(l);
                            }
                        }
                    })
                .setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    public void join(View view) {
        if (checkWifiOnAndConnected()) {
            Intent l = new Intent(getApplicationContext(), LAN.class);
            startActivity(l);
        } else {
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.userprompt, null);
            final SharedPreferences p = getSharedPreferences("data", MODE_PRIVATE);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = promptsView
                    .findViewById(R.id.user);

            final EditText userPass = promptsView
                    .findViewById(R.id.pass);
            String value = p.getString("wuser", null);
            userInput.setText(value);
            value = p.getString("wpass", null);
            userPass.setText(value);
            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    SharedPreferences.Editor edit = p.edit();
                                    edit.putString("wuser", userInput.getText().toString());
                                    edit.putString("wpass", userPass.getText().toString());
                                    edit.apply();
                                    try {
                                        WifiApControl ap = new WifiApControl(getApplicationContext());
                                        if (ap.isApOn()) {
                                            ap.turnWifiApOff();
                                        }
                                        WifiConfiguration wifiConfig = new WifiConfiguration();
                                        wifiConfig.SSID = String.format("\"%s\"", userInput.getText());
                                        wifiConfig.preSharedKey = String.format("\"%s\"", userPass.getText());
                                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                                        wifiManager.addNetwork(wifiConfig);
                                        wifiManager.setWifiEnabled(true);
                                        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                                        for (WifiConfiguration i : list) {
                                            if (i.SSID != null && i.SSID.equals("\"" + userInput.getText().toString() + "\"")) {
                                                wifiManager.disconnect();
                                                wifiManager.enableNetwork(i.networkId, true);
                                                wifiManager.reconnect();

                                                if(checkWifiOnAndConnected()) {
                                                    Intent l = new Intent(getApplicationContext(), LAN.class);
                                                    startActivity(l);
                                                    return;
                                                }
                                            }
                                        }
                                        Toast.makeText(middle.this, "Check Username or Password!", Toast.LENGTH_LONG).show();
                                    } catch (NullPointerException e) {
                                        Toast.makeText(middle.this, "No Connection Found!", Toast.LENGTH_LONG).show();
                                    } catch (Exception e) {
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }

    }
    private boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }
}