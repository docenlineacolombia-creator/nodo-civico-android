package com.nodocivico.app.ui.settings;

import android.os.Bundle;
import android.view.*;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import com.nodocivico.app.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.spinnerTheme.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{"Sistema","Claro","Oscuro"}));
        binding.spinnerNotifications.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{"Activas","Silenciosas","Desactivadas"}));
        binding.spinnerStatusFilter.setAdapter(new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, new String[]{"Todos","Abiertos","En proceso","Cerrados"}));

        binding.btnSaveSettings.setOnClickListener(v ->
                Toast.makeText(requireContext(), "Preferencias guardadas", Toast.LENGTH_SHORT).show());
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
