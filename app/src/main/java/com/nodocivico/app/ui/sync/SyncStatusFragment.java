package com.nodocivico.app.ui.sync;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.nodocivico.app.databinding.FragmentSyncStatusBinding;
import com.nodocivico.app.ui.report.ReportViewModel;

public class SyncStatusFragment extends Fragment {

    private FragmentSyncStatusBinding binding;
    private ReportViewModel viewModel;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSyncStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

        viewModel.getPendingCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvPendingCount.setText(count != null ? String.valueOf(count) : "0");
        });

        viewModel.getTotalCount().observe(getViewLifecycleOwner(), count -> {
            binding.tvSentCount.setText(count != null ? String.valueOf(count) : "0");
        });

        binding.btnSyncNow.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Sincronizando...", Toast.LENGTH_SHORT).show());
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
