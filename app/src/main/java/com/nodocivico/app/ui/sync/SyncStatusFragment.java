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
    private ReportViewModel reportViewModel;
    private SyncViewModel syncViewModel;

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
        reportViewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);
        syncViewModel   = new ViewModelProvider(this).get(SyncViewModel.class);

        // Contadores en vivo
        reportViewModel.getPendingCount().observe(getViewLifecycleOwner(), count ->
                binding.tvPendingCount.setText(count != null ? String.valueOf(count) : "0"));
        reportViewModel.getTotalCount().observe(getViewLifecycleOwner(), count ->
                binding.tvSentCount.setText(count != null ? String.valueOf(count) : "0"));

        // Resultado de sincronización
        syncViewModel.getSyncResult().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null)
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
        });

        // Estado de carga
        syncViewModel.isLoading().observe(getViewLifecycleOwner(), loading -> {
            binding.btnSyncNow.setEnabled(!loading);
            binding.btnSyncNow.setText(loading ? "Sincronizando..." : "Sincronizar ahora");
        });

        binding.btnSyncNow.setOnClickListener(v -> syncViewModel.syncNow());
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
