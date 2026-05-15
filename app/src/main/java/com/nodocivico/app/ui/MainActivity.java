package com.nodocivico.app.ui;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.nodocivico.app.databinding.ActivityMainBinding;
import com.nodocivico.app.util.NetworkMonitor;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private NetworkMonitor networkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // NavController
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(com.nodocivico.app.R.id.nav_host_fragment);
        if (navHost != null) {
            navController = navHost.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNav, navController);
        }

        // Banner de conectividad
        networkMonitor = new NetworkMonitor(this);
        networkMonitor.observe(this, connected -> {
            binding.connectivityBanner.setVisibility(
                    connected ? View.GONE : View.VISIBLE);
        });
    }
}
