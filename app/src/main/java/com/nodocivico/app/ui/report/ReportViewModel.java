package com.nodocivico.app.ui.report;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.model.Report;
import com.nodocivico.app.data.repository.ReportRepository;
import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private final ReportRepository repository;
    private final LiveData<List<Report>> allReports;
    private final LiveData<Integer> pendingCount;
    private final LiveData<Integer> totalCount;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        repository = new ReportRepository(application);
        allReports = repository.getAll();
        pendingCount = repository.countPending();
        totalCount = repository.countAll();
    }

    public LiveData<List<Report>> getAllReports() { return allReports; }
    public LiveData<Integer> getPendingCount() { return pendingCount; }
    public LiveData<Integer> getTotalCount() { return totalCount; }

    public void insert(Report report) {
        repository.insert(report, null);
    }

    public void update(Report report) {
        repository.update(report);
    }

    public void delete(Report report) {
        repository.delete(report);
    }

    public void updateStatus(long id, String status) {
        repository.updateStatus(id, status);
    }

    public LiveData<Report> getById(long id) {
        return repository.getById(id);
    }
}
