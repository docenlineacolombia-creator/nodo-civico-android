package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long reportId;
    public String message;
    public long triggerAtMillis;
    public boolean fired;
    public boolean active;
    public long createdAt;

    public Reminder() {}

    public Reminder(long reportId, String message, long triggerAtMillis) {
        this.reportId = reportId;
        this.message = message;
        this.triggerAtMillis = triggerAtMillis;
        this.fired = false;
        this.active = true;
        this.createdAt = System.currentTimeMillis();
    }
}
