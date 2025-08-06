package demo.ads;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

public class NetworkManager {
    private static Activity activity;
    private static OnMonitorListener listener;
    private static final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        /* class demo.ads.NetworkManager.AnonymousClass1 */

        public void onAvailable(Network network) {
            super.onAvailable(network);
            if (NetworkManager.listener != null) {
                NetworkManager.listener.onConnectivityChanged(true);
            }
        }

        public void onLost(Network network) {
            super.onLost(network);
            if (NetworkManager.listener != null) {
                NetworkManager.listener.onConnectivityChanged(false);
            }
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            networkCapabilities.hasCapability(11);
        }
    };
    private static final NetworkRequest networkRequest = new NetworkRequest.Builder().addCapability(12).addTransportType(1).addTransportType(0).build();

    public interface OnMonitorListener {
        void onConnectivityChanged(boolean z);
    }

    public static void Monitoring(Activity activity2, OnMonitorListener onMonitorListener) {
        activity = activity2;
        listener = onMonitorListener;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity2.getSystemService(ConnectivityManager.class);
        if (connectivityManager != null) {
            connectivityManager.requestNetwork(networkRequest, networkCallback);
        }
    }
}
