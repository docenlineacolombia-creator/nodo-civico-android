package com.nodocivico.app.ui.profile;

import android.os.Bundle;
import android.view.*;
import android.widget.Toast;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.nodocivico.app.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        // Cargar datos del usuario 1
        viewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                com.nodocivico.app.data.model.User u = users.get(0);
                binding.tvProfileName.setText(u.name);
                binding.tvProfileEmail.setText(u.email);
                binding.tvAvatarInitials.setText(u.avatarInitials);
                binding.etName.setText(u.name);
                binding.etSector.setText(u.sector);
            }
        });

        viewModel.isSaved().observe(getViewLifecycleOwner(), saved -> {
            if (Boolean.TRUE.equals(saved))
                Toast.makeText(requireContext(), "Perfil guardado", Toast.LENGTH_SHORT).show();
        });

        binding.btnSaveProfile.setOnClickListener(v -> {
            String name   = binding.etName.getText().toString().trim();
            String sector = binding.etSector.getText().toString().trim();
            if (name.isEmpty()) {
                binding.etName.setError("Nombre obligatorio");
                return;
            }
            viewModel.saveProfile(name, sector);
        });
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
