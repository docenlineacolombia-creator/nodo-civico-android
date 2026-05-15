package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.ReminderDao;
import com.nodocivico.app.data.model.Reminder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderRepository {

    private final ReminderDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReminderRepository(Application app) {
        dao = AppDatabase.getInstance(app).reminderDao();
    }

    public LiveData<List<Reminder>> getActive() { return dao.getActive(); }

    public LiveData<List<Reminder>> getByReport(long reportId) {
        return dao.getByReport(reportId);
    }

    public void insert(Reminder r, InsertCallback cb) {
        executor.execute(() -> {
            long id = dao.insert(r);
            if (cb != null) cb.onInserted(id);
        });
    }

    public void markFired(long id) {
        executor.execute(() -> dao.markFired(id));
    }

    public void getPendingFire(PendingCallback cb) {
        executor.execute(() -> cb.onResult(dao.getPendingFire()));
    }

    public interface InsertCallback { void onInserted(long id); }
    public interface PendingCallback { void onResult(List<Reminder> list); }
}
