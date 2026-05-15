package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.nodocivico.app.R;
import com.nodocivico.app.databinding.FragmentEditReportBinding;

public class EditReportFragment extends Fragment {

    private FragmentEditReportBinding binding;
    private ReportViewModel viewModel;
    private long reportId = -1;

    private static final String[] CATEGORIES = {"Alumbrado","Aseo","Seguridad","Servicios públicos"};
    private static final String[] PRIORITIES  = {"BAJA","MEDIA","ALTA"};
    private static final String[] STATUSES    = {"OPEN","IN_PROGRESS","CLOSED"};

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEditReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

        binding.spinnerCategory.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, CATEGORIES));
        binding.spinnerPriority.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, PRIORITIES));
        binding.spinnerStatus.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, STATUSES));

        if (getArguments() != null) reportId = getArguments().getLong("reportId", -1);

        if (reportId != -1) {
            viewModel.getById(reportId).observe(getViewLifecycleOwner(), report -> {
                if (report == null) return;
                binding.etDescription.setText(report.description);
                int catIdx = indexOf(CATEGORIES, report.category);
                int prioIdx = indexOf(PRIORITIES, report.priority);
                int statIdx = indexOf(STATUSES, report.status);
                if (catIdx  >= 0) binding.spinnerCategory.setSelection(catIdx);
                if (prioIdx >= 0) binding.spinnerPriority.setSelection(prioIdx);
                if (statIdx >= 0) binding.spinnerStatus.setSelection(statIdx);
            });
        }

        binding.btnUpdate.setOnClickListener(v -> {
            if (reportId == -1) return;
            viewModel.getById(reportId).observe(getViewLifecycleOwner(), report -> {
                if (report == null) return;
                report.description = binding.etDescription.getText().toString().trim();
                report.category    = binding.spinnerCategory.getSelectedItem().toString();
                report.priority    = binding.spinnerPriority.getSelectedItem().toString();
                report.status      = binding.spinnerStatus.getSelectedItem().toString();
                report.pendingSync = true;
                report.updatedAt   = System.currentTimeMillis();
                viewModel.update(report);
                Toast.makeText(requireContext(), "Reporte actualizado", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).popBackStack();
            });
        });

        binding.btnDelete.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Eliminar reporte")
                    .setMessage("¿Estás seguro? Esta acción no se puede deshacer.")
                    .setPositiveButton("Eliminar", (d, w) -> {
                        viewModel.getById(reportId).observe(getViewLifecycleOwner(), report -> {
                            if (report != null) viewModel.delete(report);
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.reportListFragment);
                        });
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private int indexOf(String[] arr, String val) {
        if (val == null) return -1;
        for (int i = 0; i < arr.length; i++) if (arr[i].equals(val)) return i;
        return -1;
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
