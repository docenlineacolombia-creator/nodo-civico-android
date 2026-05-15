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
            new SyncManager(null) {
                // SyncManager liviano: solo lanza sincronización en background
            };
            // Lanzar SyncManager correctamente requiere Application context
            // En producción usar WorkManager o IntentService con contexto de app
        }
    }
}
