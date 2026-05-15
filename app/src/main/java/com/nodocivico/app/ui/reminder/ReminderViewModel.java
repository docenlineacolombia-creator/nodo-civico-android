package com.nodocivico.app.ui.reminder;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.nodocivico.app.data.model.Reminder;
import com.nodocivico.app.data.repository.ReminderRepository;
import com.nodocivico.app.util.AlarmScheduler;
import java.util.List;

public class ReminderViewModel extends AndroidViewModel {

    private final ReminderRepository repo;
    private final LiveData<List<Reminder>> active;

    public ReminderViewModel(@NonNull Application app) {
        super(app);
        repo   = new ReminderRepository(app);
        active = repo.getActive();
    }

    public LiveData<List<Reminder>> getActive() { return active; }

    public LiveData<List<Reminder>> getByReport(long reportId) {
        return repo.getByReport(reportId);
    }

    /** Guarda el recordatorio en Room y programa la alarma. */
    public void scheduleReminder(Reminder reminder) {
        repo.insert(reminder, insertedId -> {
            reminder.id = insertedId;
            new Handler(Looper.getMainLooper()).post(() ->
                    AlarmScheduler.schedule(getApplication(), reminder));
        });
    }

    /** Cancela la alarma y marca como inactivo (no la borra de BD). */
    public void cancelReminder(Reminder reminder) {
        AlarmScheduler.cancel(getApplication(), reminder.id);
        reminder.active = false;
        repo.markFired(reminder.id);
    }
}
