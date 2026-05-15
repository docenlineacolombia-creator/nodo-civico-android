package com.nodocivico.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Detecta cambios de conectividad y dispara sincronización pendiente.
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
            // TODO: lanzar SyncService o WorkManager para enviar reportes pendientes
        }
    }
}
