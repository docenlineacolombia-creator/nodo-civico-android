package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.ReportDao;
import com.nodocivico.app.data.model.Report;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportRepository {

    private final ReportDao reportDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ReportRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        reportDao = db.reportDao();
    }

    public LiveData<List<Report>> getAll() {
        return reportDao.getAll();
    }

    public LiveData<Report> getById(long id) {
        return reportDao.getById(id);
    }

    public LiveData<Integer> countPending() {
        return reportDao.countPending();
    }

    public LiveData<Integer> countAll() {
        return reportDao.countAll();
    }

    public void insert(Report report, Runnable onDone) {
        executor.execute(() -> {
            reportDao.insert(report);
            if (onDone != null) onDone.run();
        });
    }

    public void update(Report report) {
        executor.execute(() -> reportDao.update(report));
    }

    public void delete(Report report) {
        executor.execute(() -> reportDao.delete(report));
    }

    public void updateStatus(long id, String status) {
        executor.execute(() -> reportDao.updateStatus(id, status, System.currentTimeMillis()));
    }

    public void getPending(PendingCallback callback) {
        executor.execute(() -> {
            List<Report> pending = reportDao.getPending();
            callback.onResult(pending);
        });
    }

    public void markSynced(long id, String remoteId) {
        executor.execute(() -> reportDao.markSynced(id, remoteId));
    }

    public interface PendingCallback {
        void onResult(List<Report> reports);
    }
}
