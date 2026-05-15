package com.nodocivico.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * LiveData que emite true cuando hay conexión activa, false cuando no.
 * Se registra/desregistra automáticamente con el ciclo de vida del observer.
 */
public class NetworkMonitor extends LiveData<Boolean> {

    private final ConnectivityManager cm;

    private final ConnectivityManager.NetworkCallback callback =
            new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(@NonNull Network network) {
                    postValue(true);
                }
                @Override
                public void onLost(@NonNull Network network) {
                    postValue(false);
                }
            };

    public NetworkMonitor(Context context) {
        cm = (ConnectivityManager) context.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onActive() {
        // Emitir estado actual al suscribirse
        postValue(isConnectedNow());
        NetworkRequest req = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        cm.registerNetworkCallback(req, callback);
    }

    @Override
    protected void onInactive() {
        try { cm.unregisterNetworkCallback(callback); } catch (Exception ignored) {}
    }

    private boolean isConnectedNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network net = cm.getActiveNetwork();
            if (net == null) return false;
            NetworkCapabilities caps = cm.getNetworkCapabilities(net);
            return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else {
            android.net.NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
    }
}
