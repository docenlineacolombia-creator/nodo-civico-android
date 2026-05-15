package com.nodocivico.app.data.remote;

import com.nodocivico.app.data.model.Category;
import com.nodocivico.app.data.model.Report;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @GET("reports")
    Call<List<Report>> getReports();

    @GET("reports/{id}")
    Call<Report> getReport(@Path("id") String id);

    @POST("reports")
    Call<Report> createReport(@Body Report report);

    @PUT("reports/{id}")
    Call<Report> updateReport(@Path("id") String id, @Body Report report);

    @GET("categories")
    Call<List<Category>> getCategories();
}
