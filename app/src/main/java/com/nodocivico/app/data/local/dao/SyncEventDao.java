package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.SyncEvent;
import java.util.List;

@Dao
public interface SyncEventDao {

    @Query("SELECT * FROM sync_events ORDER BY timestamp DESC LIMIT 50")
    LiveData<List<SyncEvent>> getRecent();

    @Query("SELECT * FROM sync_events WHERE result = 'PENDING'")
    List<SyncEvent> getPending();

    @Insert
    long insert(SyncEvent event);

    @Query("UPDATE sync_events SET result = :result, errorMessage = :error WHERE id = :id")
    void updateResult(long id, String result, String error);
}
