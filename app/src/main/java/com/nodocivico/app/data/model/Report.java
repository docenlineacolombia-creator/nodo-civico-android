package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reports")
public class Report {

    @PrimaryKey(autoGenerate = true)
    public long id;

    /** ID del servidor tras sincronización. Null si aún no fue enviado. */
    public String remoteId;

    public long userId;
    public String title;
    public String description;
    public String category;    // Alumbrado, Aseo, Seguridad, Servicios
    public String priority;    // BAJA, MEDIA, ALTA
    public String status;      // OPEN, IN_PROGRESS, CLOSED
    public String location;    // Ubicación referencial
    public long createdAt;
    public long updatedAt;

    /** true = hay cambios locales pendientes de sincronizar */
    public boolean pendingSync;

    /** Ruta local de imagen adjunta (opcional) */
    public String evidencePath;

    public Report() {}

    public Report(long userId, String title, String description,
                  String category, String priority, String location) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.location = location;
        this.status = "OPEN";
        this.pendingSync = true;
        long now = System.currentTimeMillis();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public String getIconLetter() {
        return (title != null && !title.isEmpty())
                ? String.valueOf(title.charAt(0)).toUpperCase() : "R";
    }
}
