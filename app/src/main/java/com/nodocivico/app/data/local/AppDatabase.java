package com.nodocivico.app.data.local;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.nodocivico.app.data.local.dao.*;
import com.nodocivico.app.data.model.*;
import java.util.Arrays;
import java.util.List;

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

    public abstract UserDao userDao();
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
                    )
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Seed de datos iniciales en background
                            java.util.concurrent.Executors.newSingleThreadExecutor().execute(() -> {
                                AppDatabase database = INSTANCE;
                                if (database == null) return;
                                seedCategories(database);
                                seedDefaultUser(database);
                            });
                        }
                    })
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static void seedCategories(AppDatabase db) {
        List<Category> defaults = Arrays.asList(
                new Category("Alumbrado",          "💡"),
                new Category("Aseo",               "🗑️"),
                new Category("Seguridad",          "🔒"),
                new Category("Servicios públicos", "💧")
        );
        db.categoryDao().insertAll(defaults);
    }

    private static void seedDefaultUser(AppDatabase db) {
        if (db.userDao().count() == 0) {
            db.userDao().insert(new User("Usuario Nodo", "usuario@nodocivico.co", "Barrio Centro"));
        }
    }
}
