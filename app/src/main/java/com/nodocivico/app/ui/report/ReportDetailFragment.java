package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.nodocivico.app.R;
import com.nodocivico.app.databinding.FragmentReportDetailBinding;

public class ReportDetailFragment extends Fragment {

    private FragmentReportDetailBinding binding;

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
        binding.btnEdit.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.editReportFragment));
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
