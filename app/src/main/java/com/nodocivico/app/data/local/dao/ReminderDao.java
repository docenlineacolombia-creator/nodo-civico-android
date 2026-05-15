package com.nodocivico.app.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.nodocivico.app.data.model.Reminder;
import java.util.List;

@Dao
public interface ReminderDao {

    @Query("SELECT * FROM reminders WHERE active = 1 ORDER BY triggerAtMillis ASC")
    LiveData<List<Reminder>> getActive();

    @Query("SELECT * FROM reminders WHERE reportId = :reportId")
    LiveData<List<Reminder>> getByReport(long reportId);

    @Query("SELECT * FROM reminders WHERE fired = 0 AND active = 1")
    List<Reminder> getPendingFire();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Query("UPDATE reminders SET fired = 1 WHERE id = :id")
    void markFired(long id);
}
