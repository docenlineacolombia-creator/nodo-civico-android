package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.CategoryDao;
import com.nodocivico.app.data.model.Category;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {

    private final CategoryDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CategoryRepository(Application app) {
        dao = AppDatabase.getInstance(app).categoryDao();
    }

    public LiveData<List<Category>> getAll() { return dao.getAll(); }

    public void insertAll(List<Category> categories) {
        executor.execute(() -> dao.insertAll(categories));
    }

    public void insert(Category c) {
        executor.execute(() -> dao.insert(c));
    }

    /** Reemplaza todas las categorías con las del servidor */
    public void replaceAll(List<Category> remote) {
        executor.execute(() -> {
            dao.deleteAll();
            dao.insertAll(remote);
        });
    }
}
