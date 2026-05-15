package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Registro de cada intento de sincronización con la API.
 */
@Entity(tableName = "sync_events")
public class SyncEvent {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long reportId;
    public String action;      // CREATE, UPDATE, DELETE
    public String result;      // PENDING, SUCCESS, FAILED
    public String errorMessage;
    public long timestamp;

    public SyncEvent() {}

    public SyncEvent(long reportId, String action) {
        this.reportId = reportId;
        this.action = action;
        this.result = "PENDING";
        this.timestamp = System.currentTimeMillis();
    }
}
