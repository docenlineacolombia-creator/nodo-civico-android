package com.nodocivico.app.ui.sync;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.nodocivico.app.data.model.SyncEvent;
import com.nodocivico.app.data.repository.SyncEventRepository;
import com.nodocivico.app.data.sync.SyncManager;
import java.util.List;

public class SyncViewModel extends AndroidViewModel {

    private final SyncEventRepository syncRepo;
    private final SyncManager syncManager;
    private final LiveData<List<SyncEvent>> recent;
    private final MutableLiveData<String> syncResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>(false);

    public SyncViewModel(@NonNull Application app) {
        super(app);
        syncRepo    = new SyncEventRepository(app);
        syncManager = new SyncManager(app);
        recent      = syncRepo.getRecent();
    }

    public LiveData<List<SyncEvent>> getRecent()   { return recent; }
    public LiveData<String>          getSyncResult(){ return syncResult; }
    public LiveData<Boolean>         isLoading()    { return loading; }

    public void syncNow() {
        loading.setValue(true);
        syncManager.syncPending((success, failed) -> {
            loading.postValue(false);
            syncResult.postValue("Enviados: " + success + " — Fallidos: " + failed);
        });
    }
}
