package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nodocivico.app.R;
import com.nodocivico.app.data.model.Reminder;
import com.nodocivico.app.databinding.FragmentReportDetailBinding;
import com.nodocivico.app.ui.reminder.ReminderViewModel;

public class ReportDetailFragment extends Fragment {

    private FragmentReportDetailBinding binding;
    private ReportViewModel reportViewModel;
    private ReminderViewModel reminderViewModel;
    private long reportId = -1;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReportDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reportViewModel  = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
        reminderViewModel = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        // Obtener reportId desde args (se pasará en Entregable 3 con SafeArgs)
        if (getArguments() != null) reportId = getArguments().getLong("reportId", -1);

        if (reportId != -1) {
            reportViewModel.getById(reportId).observe(getViewLifecycleOwner(), report -> {
                if (report == null) return;
                binding.tvDetailTitle.setText(report.title);
                binding.tvDetailMeta.setText(
                        report.location + " · " + report.category + " · " + report.priority);
                binding.tvDetailStatus.setText(report.status);
                binding.tvDetailDescription.setText(report.description);
            });
        }

        binding.btnUpdateStatus.setOnClickListener(v -> showStatusDialog());

        binding.btnEdit.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.editReportFragment));

        binding.btnReminder.setOnClickListener(v -> scheduleQuickReminder());
    }

    private void showStatusDialog() {
        String[] options = {"OPEN", "IN_PROGRESS", "CLOSED"};
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Actualizar estado")
                .setItems(options, (dialog, which) -> {
                    if (reportId != -1)
                        reportViewModel.updateStatus(reportId, options[which]);
                })
                .show();
    }

    private void scheduleQuickReminder() {
        if (reportId == -1) return;
        long inOneHour = System.currentTimeMillis() + 3_600_000L;
        Reminder r = new Reminder(reportId, "Revisa el estado de tu reporte", inOneHour);
        reminderViewModel.scheduleReminder(r);
        android.widget.Toast.makeText(requireContext(),
                "Recordatorio programado en 1 hora", android.widget.Toast.LENGTH_SHORT).show();
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
