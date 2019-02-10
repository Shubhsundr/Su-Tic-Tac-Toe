package in.channeli.img.sutictactoe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.world.img144.sutictacas.R;

public class middle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.middle);
    }

    public void host(View view) {
        final Context context = getApplicationContext();
        WifiApControl ap = new WifiApControl(context);
        if (ap.isApOn()) {
            Intent l = new Intent(this, LAN.class);
            l.putExtra("isClient", false);
            startActivity(l);
        } else {
            Toast.makeText(middle.this, "Create hotspot", Toast.LENGTH_LONG).show();
            final Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.TetherSettings");
            intent.setComponent(cn);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity( intent);
        }
    }

    public void join(View view) {
        if (checkWifiOnAndConnected()) {
            Intent l = new Intent(this, LAN.class);
            l.putExtra("isClient", true);
            startActivity(l);
        } else {
            final Context context = getApplicationContext();
            WifiApControl ap = new WifiApControl(context);
            if (ap.isApOn())
                ap.turnWifiApOff();
            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            Toast.makeText(middle.this, "Connect to host network", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
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