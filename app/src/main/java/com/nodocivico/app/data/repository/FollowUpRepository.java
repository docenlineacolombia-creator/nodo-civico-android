package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.FollowUpDao;
import com.nodocivico.app.data.model.FollowUp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FollowUpRepository {

    private final FollowUpDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FollowUpRepository(Application app) {
        dao = AppDatabase.getInstance(app).followUpDao();
    }

    public LiveData<List<FollowUp>> getByReport(long reportId) {
        return dao.getByReport(reportId);
    }

    public void insert(FollowUp f) {
        executor.execute(() -> dao.insert(f));
    }

    public void delete(FollowUp f) {
        executor.execute(() -> dao.delete(f));
    }
}
