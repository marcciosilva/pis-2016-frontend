package pis16.memsysandroid;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import pis16.memsysandroid.dao.DaoConfig;

/**
 * @author Andrés Canavesi
 */
public class MemSysApplication extends Application {

    private static MemSysApplication instance;
    private Context context;

    public static MemSysApplication getInstance() {
        if (instance == null) {
            //Should't but...
            throw new IllegalStateException("MemSysApplication instance still was not created");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        try {
            Log.i(DaoConfig.APP_TAG, "**************** Initializing Memsys ***************");
            context = getApplicationContext();
        } catch (Exception e) {
            throw new IllegalStateException("Error intitializing the application", e);
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(DaoConfig.APP_TAG, "**************** App terminated ****************");
    }

}
