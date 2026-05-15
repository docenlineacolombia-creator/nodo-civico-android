package com.nodocivico.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Reprograma alarmas y recordatorios tras reinicio del dispositivo.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // TODO: relanzar AlarmManager para cada Reminder activo de la BD
        }
    }
}
