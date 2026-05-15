package com.nodocivico.app.ui.category;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.model.Category;
import com.nodocivico.app.data.remote.ApiClient;
import com.nodocivico.app.data.repository.CategoryRepository;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Response;

public class CategoryViewModel extends AndroidViewModel {

    private final CategoryRepository repo;
    private final LiveData<List<Category>> allCategories;

    public CategoryViewModel(@NonNull Application app) {
        super(app);
        repo          = new CategoryRepository(app);
        allCategories = repo.getAll();
    }

    public LiveData<List<Category>> getAll() { return allCategories; }

    /** Descarga categorías del servidor y reemplaza las locales. */
    public void fetchFromApi() {
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Response<List<Category>> res = ApiClient.getInstance().getCategories().execute();
                if (res.isSuccessful() && res.body() != null) {
                    repo.replaceAll(res.body());
                }
            } catch (Exception ignored) {}
        });
    }
}
