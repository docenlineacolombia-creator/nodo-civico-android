package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.nodocivico.app.R;
import com.nodocivico.app.databinding.FragmentReportListBinding;

public class ReportListFragment extends Fragment {

    private FragmentReportListBinding binding;
    private ReportViewModel viewModel;
    private ReportAdapter adapter;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentReportListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

        adapter = new ReportAdapter();
        binding.recyclerReports.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerReports.setAdapter(adapter);

        viewModel.getAllReports().observe(getViewLifecycleOwner(), reports -> {
            boolean empty = reports == null || reports.isEmpty();
            binding.emptyState.setVisibility(empty ? View.VISIBLE : View.GONE);
            binding.recyclerReports.setVisibility(empty ? View.GONE : View.VISIBLE);
            if (!empty) adapter.submitList(reports);
        });

        binding.fabCreate.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_list_to_create));

        binding.swipeRefresh.setOnRefreshListener(() ->
                binding.swipeRefresh.setRefreshing(false));
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
