package com.nodocivico.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.ReminderDao;
import com.nodocivico.app.util.AlarmScheduler;
import java.util.concurrent.Executors;

/**
 * Reprograma todos los recordatorios activos tras reinicio del dispositivo.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
        Executors.newSingleThreadExecutor().execute(() -> {
            ReminderDao dao = AppDatabase.getInstance(context).reminderDao();
            AlarmScheduler.rescheduleAll(context, dao.getPendingFire());
        });
    }
}
