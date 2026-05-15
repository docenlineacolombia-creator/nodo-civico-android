package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Comentario o nota de seguimiento sobre un reporte.
 */
@Entity(tableName = "followups")
public class FollowUp {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long reportId;
    public long userId;
    public String comment;
    public String newStatus;   // estado al que cambió con este seguimiento (puede ser null)
    public long createdAt;

    public FollowUp() {}

    public FollowUp(long reportId, long userId, String comment, String newStatus) {
        this.reportId = reportId;
        this.userId = userId;
        this.comment = comment;
        this.newStatus = newStatus;
        this.createdAt = System.currentTimeMillis();
    }
}
