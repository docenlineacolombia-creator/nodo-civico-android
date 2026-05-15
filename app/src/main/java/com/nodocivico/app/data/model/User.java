package com.nodocivico.app.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String email;
    public String sector;
    public String avatarInitials;
    public long createdAt;

    public User() {}

    public User(String name, String email, String sector) {
        this.name = name;
        this.email = email;
        this.sector = sector;
        this.avatarInitials = name != null && name.length() >= 2
                ? name.substring(0, 2).toUpperCase() : "NC";
        this.createdAt = System.currentTimeMillis();
    }
}
