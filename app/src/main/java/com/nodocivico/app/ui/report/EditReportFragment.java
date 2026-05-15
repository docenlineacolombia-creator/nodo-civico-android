package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.nodocivico.app.databinding.FragmentEditReportBinding;

public class EditReportFragment extends Fragment {

    private FragmentEditReportBinding binding;
    private ReportViewModel viewModel;

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
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
