package com.sonda.emsysmobile;

import android.app.Application;

/**
 * Created by marccio on 04-Nov-16.
 */

public class EmsysMobile extends Application {

    // Variables globales.
    private boolean offlineMode = true;

    public boolean isOfflineMode() {
        return offlineMode;
    }

    public void setOfflineMode(boolean offlineMode) {
        this.offlineMode = offlineMode;
    }
}
