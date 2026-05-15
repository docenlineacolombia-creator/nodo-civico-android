package com.nodocivico.app.data.sync;

import android.app.Application;
import android.util.Log;
import com.nodocivico.app.data.model.Report;
import com.nodocivico.app.data.model.SyncEvent;
import com.nodocivico.app.data.remote.ApiClient;
import com.nodocivico.app.data.remote.ApiService;
import com.nodocivico.app.data.repository.ReportRepository;
import com.nodocivico.app.data.repository.SyncEventRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Response;

/**
 * Encargado de sincronizar reportes pendientes con la API REST.
 * Se llama desde ConnectivityReceiver o manualmente desde SyncStatusFragment.
 */
public class SyncManager {

    private static final String TAG = "SyncManager";
    private final ReportRepository reportRepo;
    private final SyncEventRepository syncRepo;
    private final ApiService api;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SyncManager(Application app) {
        reportRepo = new ReportRepository(app);
        syncRepo   = new SyncEventRepository(app);
        api        = ApiClient.getInstance();
    }

    /** Sincroniza todos los reportes con pendingSync=true. */
    public void syncPending(SyncCallback callback) {
        executor.execute(() -> {
            reportRepo.getPending(pending -> {
                int success = 0, failed = 0;
                for (Report report : pending) {
                    SyncEvent event = new SyncEvent(report.id,
                            report.remoteId == null ? "CREATE" : "UPDATE");
                    syncRepo.insert(event);
                    try {
                        Response<Report> res;
                        if (report.remoteId == null) {
                            res = api.createReport(report).execute();
                        } else {
                            res = api.updateReport(report.remoteId, report).execute();
                        }
                        if (res.isSuccessful() && res.body() != null) {
                            String remoteId = res.body().remoteId != null
                                    ? res.body().remoteId : String.valueOf(res.body().id);
                            reportRepo.markSynced(report.id, remoteId);
                            syncRepo.markSuccess(event.id);
                            success++;
                        } else {
                            syncRepo.markFailed(event.id, "HTTP " + res.code());
                            failed++;
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error syncing report " + report.id, e);
                        syncRepo.markFailed(event.id, e.getMessage());
                        failed++;
                    }
                }
                if (callback != null) callback.onFinished(success, failed);
            });
        });
    }

    public interface SyncCallback {
        void onFinished(int success, int failed);
    }
}
