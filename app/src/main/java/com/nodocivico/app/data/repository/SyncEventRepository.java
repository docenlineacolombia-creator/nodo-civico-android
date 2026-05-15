package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.SyncEventDao;
import com.nodocivico.app.data.model.SyncEvent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncEventRepository {

    private final SyncEventDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SyncEventRepository(Application app) {
        dao = AppDatabase.getInstance(app).syncEventDao();
    }

    public LiveData<List<SyncEvent>> getRecent() { return dao.getRecent(); }

    public void insert(SyncEvent e) {
        executor.execute(() -> dao.insert(e));
    }

    public void markSuccess(long id) {
        executor.execute(() -> dao.updateResult(id, "SUCCESS", null));
    }

    public void markFailed(long id, String error) {
        executor.execute(() -> dao.updateResult(id, "FAILED", error));
    }

    public void getPending(PendingCallback cb) {
        executor.execute(() -> cb.onResult(dao.getPending()));
    }

    public interface PendingCallback { void onResult(List<SyncEvent> list); }
}
