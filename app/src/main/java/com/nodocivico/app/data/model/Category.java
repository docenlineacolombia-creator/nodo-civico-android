package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;        // Alumbrado, Aseo, Seguridad...
    public String remoteId;
    public String iconEmoji;   // 💡 🗑️ 🔒 💧

    public Category() {}

    public Category(String name, String iconEmoji) {
        this.name = name;
        this.iconEmoji = iconEmoji;
    }
}
