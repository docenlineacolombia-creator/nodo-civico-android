package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.nodocivico.app.R;
import com.nodocivico.app.data.model.Report;
import com.nodocivico.app.databinding.FragmentCreateReportBinding;

public class CreateReportFragment extends Fragment {

    private FragmentCreateReportBinding binding;
    private ReportViewModel viewModel;

    private static final String[] CATEGORIES = {"Alumbrado","Aseo","Seguridad","Servicios públicos"};
    private static final String[] PRIORITIES = {"BAJA","MEDIA","ALTA"};

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCreateReportBinding.inflate(inflater, container, false);
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

        binding.btnSave.setOnClickListener(v -> saveReport(false));
        binding.btnSaveOffline.setOnClickListener(v -> saveReport(true));
    }

    private void saveReport(boolean offline) {
        String desc = binding.etDescription.getText().toString().trim();
        String loc  = binding.etLocation.getText().toString().trim();
        if (desc.isEmpty()) {
            binding.tvError.setVisibility(View.VISIBLE);
            binding.tvError.setText("La descripción es obligatoria");
            return;
        }
        binding.tvError.setVisibility(View.GONE);
        String cat  = binding.spinnerCategory.getSelectedItem().toString();
        String prio = binding.spinnerPriority.getSelectedItem().toString();
        Report r = new Report(1L, desc.substring(0, Math.min(40, desc.length())), desc, cat, prio, loc);
        r.pendingSync = true;
        viewModel.insert(r);
        Toast.makeText(requireContext(),
                offline ? "Guardado offline" : "Reporte creado", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(requireView()).navigate(R.id.reportListFragment);
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
