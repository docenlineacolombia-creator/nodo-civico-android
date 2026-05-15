package com.nodocivico.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.nodocivico.app.data.model.Reminder;
import com.nodocivico.app.receiver.ReminderReceiver;

/**
 * Programa y cancela alarmas exactas para los recordatorios de reportes.
 */
public class AlarmScheduler {

    private AlarmScheduler() {}

    public static void schedule(Context context, Reminder reminder) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(ReminderReceiver.EXTRA_MESSAGE, reminder.message);
        intent.putExtra(ReminderReceiver.EXTRA_REMINDER_ID, reminder.id);

        PendingIntent pi = PendingIntent.getBroadcast(
                context,
                (int) reminder.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, reminder.triggerAtMillis, pi);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, reminder.triggerAtMillis, pi);
        }
    }

    public static void cancel(Context context, long reminderId) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        Intent intent = new Intent(context, ReminderReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(
                context,
                (int) reminderId,
                intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE
        );
        if (pi != null) {
            am.cancel(pi);
            pi.cancel();
        }
    }

    /** Reprograma todos los recordatorios activos tras reinicio. */
    public static void rescheduleAll(Context context,
                                     java.util.List<com.nodocivico.app.data.model.Reminder> reminders) {
        for (Reminder r : reminders) {
            if (r.active && !r.fired) schedule(context, r);
        }
    }
}
