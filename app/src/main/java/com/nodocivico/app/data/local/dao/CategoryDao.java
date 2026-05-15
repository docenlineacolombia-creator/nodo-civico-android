package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.Category;
import java.util.List;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    LiveData<List<Category>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Category> categories);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Category category);

    @Query("DELETE FROM categories")
    void deleteAll();
}
