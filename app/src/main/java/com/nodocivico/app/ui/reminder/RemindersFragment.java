package com.nodocivico.app.ui.reminder;

import android.os.Bundle;
import android.view.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.nodocivico.app.databinding.FragmentRemindersBinding;

public class RemindersFragment extends Fragment {

    private FragmentRemindersBinding binding;
    private ReminderViewModel viewModel;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRemindersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ReminderViewModel.class);

        binding.recyclerReminders.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getActive().observe(getViewLifecycleOwner(), reminders -> {
            if (reminders == null || reminders.isEmpty()) {
                binding.emptyReminders.setVisibility(View.VISIBLE);
                binding.recyclerReminders.setVisibility(View.GONE);
            } else {
                binding.emptyReminders.setVisibility(View.GONE);
                binding.recyclerReminders.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
