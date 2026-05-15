package com.nodocivico.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.nodocivico.app.data.sync.SyncManager;

/**
 * Detecta recuperación de conexión y dispara sincronización automática.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return;

        NetworkInfo info = cm.getActiveNetworkInfo();
        boolean connected = info != null && info.isConnected();

        if (connected) {
            // Usar applicationContext para no filtrar el BroadcastReceiver context
            SyncManager manager = new SyncManager(context.getApplicationContext().equals(context)
                    ? (android.app.Application) context
                    : (android.app.Application) context.getApplicationContext());
            manager.syncPending((success, failed) -> {
                android.util.Log.d("ConnectivityReceiver",
                        "Auto-sync: " + success + " ok, " + failed + " failed");
            });
        }
    }
}
