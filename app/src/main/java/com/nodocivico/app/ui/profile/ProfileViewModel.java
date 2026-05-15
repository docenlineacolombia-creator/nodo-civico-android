package com.nodocivico.app.ui.profile;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.nodocivico.app.data.model.User;
import com.nodocivico.app.data.repository.UserRepository;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private final UserRepository repo;
    private final LiveData<List<User>> users;
    private final MutableLiveData<Boolean> saved = new MutableLiveData<>(false);

    public ProfileViewModel(@NonNull Application app) {
        super(app);
        repo  = new UserRepository(app);
        users = repo.getAll();
    }

    public LiveData<List<User>> getUsers() { return users; }
    public LiveData<Boolean>    isSaved()  { return saved; }

    public void saveProfile(String name, String sector) {
        repo.getById(1L, user -> {
            if (user != null) {
                user.name   = name;
                user.sector = sector;
                if (name.length() >= 2)
                    user.avatarInitials = name.substring(0, 2).toUpperCase();
                repo.update(user);
            } else {
                repo.insert(new User(name, "", sector));
            }
            new Handler(Looper.getMainLooper()).post(() -> saved.setValue(true));
        });
    }
}
