package com.nodocivico.app.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.local.AppDatabase;
import com.nodocivico.app.data.local.dao.UserDao;
import com.nodocivico.app.data.model.User;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {

    private final UserDao dao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public UserRepository(Application app) {
        dao = AppDatabase.getInstance(app).userDao();
    }

    public LiveData<List<User>> getAll() { return dao.getAll(); }

    public void insert(User user) {
        executor.execute(() -> dao.insert(user));
    }

    public void update(User user) {
        executor.execute(() -> dao.update(user));
    }

    public void getById(long id, UserCallback cb) {
        executor.execute(() -> cb.onResult(dao.getById(id)));
    }

    public interface UserCallback { void onResult(User user); }
}
