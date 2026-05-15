package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.Report;
import java.util.List;

@Dao
public interface ReportDao {

    @Query("SELECT * FROM reports ORDER BY createdAt DESC")
    LiveData<List<Report>> getAll();

    @Query("SELECT * FROM reports WHERE id = :id LIMIT 1")
    LiveData<Report> getById(long id);

    @Query("SELECT * FROM reports WHERE pendingSync = 1")
    List<Report> getPending();

    @Query("SELECT COUNT(*) FROM reports WHERE pendingSync = 1")
    LiveData<Integer> countPending();

    @Query("SELECT COUNT(*) FROM reports")
    LiveData<Integer> countAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Report report);

    @Update
    void update(Report report);

    @Delete
    void delete(Report report);

    @Query("UPDATE reports SET status = :status, pendingSync = 1, updatedAt = :now WHERE id = :id")
    void updateStatus(long id, String status, long now);

    @Query("UPDATE reports SET remoteId = :remoteId, pendingSync = 0 WHERE id = :id")
    void markSynced(long id, String remoteId);
}
