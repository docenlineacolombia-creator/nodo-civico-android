package com.nodocivico.app.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

/**
 * Muestra notificación local cuando un recordatorio de reporte se activa.
 */
public class ReminderReceiver extends BroadcastReceiver {

    public static final String EXTRA_MESSAGE  = "reminder_message";
    public static final String EXTRA_REMINDER_ID = "reminder_id";
    private static final String CHANNEL_ID    = "nodo_reminders";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        if (message == null) message = "Tienes un seguimiento pendiente";

        createChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Nodo Cívico")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) {
            int notifId = (int) intent.getLongExtra(EXTRA_REMINDER_ID, 1);
            nm.notify(notifId, builder.build());
        }
    }

    private void createChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID, "Recordatorios Nodo Cívico",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager nm = context.getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(channel);
        }
    }
}
