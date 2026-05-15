package com.nodocivico.app.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.nodocivico.app.R;
import com.nodocivico.app.databinding.FragmentHomeBinding;
import com.nodocivico.app.ui.report.ReportViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ReportViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReportViewModel.class);

        binding.btnCreate.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.createReportFragment));

        binding.btnList.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.reportListFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
