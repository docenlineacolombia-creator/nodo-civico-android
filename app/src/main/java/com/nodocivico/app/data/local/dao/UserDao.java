package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.User;
import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY id ASC")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(User user);

    @Update
    void update(User user);

    @Query("SELECT COUNT(*) FROM users")
    int count();
}
