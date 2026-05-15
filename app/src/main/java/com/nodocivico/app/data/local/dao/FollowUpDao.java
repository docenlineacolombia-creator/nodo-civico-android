package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.FollowUp;
import java.util.List;

@Dao
public interface FollowUpDao {

    @Query("SELECT * FROM followups WHERE reportId = :reportId ORDER BY createdAt ASC")
    LiveData<List<FollowUp>> getByReport(long reportId);

    @Insert
    long insert(FollowUp followUp);

    @Delete
    void delete(FollowUp followUp);
}
