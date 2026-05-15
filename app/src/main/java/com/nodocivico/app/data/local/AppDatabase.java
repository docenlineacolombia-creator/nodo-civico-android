package com.nodocivico.app.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.nodocivico.app.data.local.dao.*;
import com.nodocivico.app.data.model.*;

@Database(
    entities = {
        User.class,
        Report.class,
        Category.class,
        Reminder.class,
        SyncEvent.class,
        FollowUp.class
    },
    version = 1,
    exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract ReportDao reportDao();
    public abstract CategoryDao categoryDao();
    public abstract ReminderDao reminderDao();
    public abstract FollowUpDao followUpDao();
    public abstract SyncEventDao syncEventDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "nodo_civico.db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
