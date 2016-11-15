[1mdiff --git a/EMSysMobile/app/build.gradle b/EMSysMobile/app/build.gradle[m
[1mindex 72a818a..b8493bf 100644[m
[1m--- a/EMSysMobile/app/build.gradle[m
[1m+++ b/EMSysMobile/app/build.gradle[m
[36m@@ -66,6 +66,7 @@[m [mdependencies {[m
     compile 'com.squareup.okhttp3:okhttp:3.4.1'[m
     compile 'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0'[m
     compile 'com.github.chrisbanes.photoview:library:1.2.3'[m
[32m+[m[32m    compile group: 'org.apache.commons', name: 'commons-io', version: '1.3.2'[m
 }[m
 [m
 repositories {[m
[1mdiff --git a/EMSysMobile/app/src/main/AndroidManifest.xml b/EMSysMobile/app/src/main/AndroidManifest.xml[m
[1mindex 7de8beb..62bf419 100644[m
[1m--- a/EMSysMobile/app/src/main/AndroidManifest.xml[m
[1m+++ b/EMSysMobile/app/src/main/AndroidManifest.xml[m
[36m@@ -6,11 +6,14 @@[m
     <uses-permission android:name="android.permission.INTERNET"/>[m
     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>[m
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>[m
[31m-    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"[m
[31m-        android:maxSdkVersion="18" />[m
[32m+[m[32m    <uses-permission[m
[32m+[m[32m        android:name="android.permission.WRITE_EXTERNAL_STORAGE"[m
[32m+[m[32m        android:maxSdkVersion="18"/>[m
 [m
     <!-- Android app features -->[m
[31m-    <uses-feature android:name="android.hardware.camera" android:required="false" />[m
[32m+[m[32m    <uses-feature[m
[32m+[m[32m        android:name="android.hardware.camera"[m
[32m+[m[32m        android:required="false"/>[m
 [m
     <application[m
         android:name="com.activeandroid.app.Application"[m
[36m@@ -47,18 +50,20 @@[m
             android:theme="@style/NoActionBar"[m
             android:windowSoftInputMode="adjustResize|stateHidden"/>[m
         <activity[m
[31m-            android:name=".ui.activities.SettingsActivity"[m
[31m-            android:label="Preferences"[m
[31m-            android:theme="@style/NoActionBar"[m
[31m-            android:windowSoftInputMode="adjustPan|stateHidden"/>[m
[31m-        <activity[m
             android:name=".ui.activities.login.RoleChooserActivity"[m
             android:configChanges="orientation|screenSize"[m
[32m+[m[32m            android:noHistory="true"[m
             android:theme="@style/NoActionBar"/>[m
         <activity[m
             android:name=".ui.activities.login.ZonasRecursosChooserActivity"[m
             android:configChanges="orientation|screenSize"[m
[32m+[m[32m            android:noHistory="true"[m
             android:theme="@style/NoActionBar"/>[m
[32m+[m[32m        <activity[m
[32m+[m[32m            android:name=".ui.activities.SettingsActivity"[m
[32m+[m[32m            android:label="Preferences"[m
[32m+[m[32m            android:theme="@style/NoActionBar"[m
[32m+[m[32m            android:windowSoftInputMode="adjustPan|stateHidden"/>[m
         <activity android:name=".ui.activities.ConsumeWSActivity"/>[m
         <activity[m
             android:name=".ui.activities.HomeActivity"[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/GlobalVariables.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/GlobalVariables.java[m
[1mindex 8dabb53..be3cfa7 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/GlobalVariables.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/GlobalVariables.java[m
[36m@@ -12,10 +12,15 @@[m [mimport java.util.concurrent.LinkedBlockingQueue;[m
 [m
 public class GlobalVariables {[m
 [m
[31m-    private static UserDto userData;[m
[32m+[m[32m    private static UserDto userData = null;[m
 [m
     private static BlockingQueue<OfflineDto> queue = new LinkedBlockingQueue<>();[m
 [m
[32m+[m[32m    public static void setQueue([m
[32m+[m[32m            BlockingQueue<OfflineDto> queue) {[m
[32m+[m[32m        GlobalVariables.queue = queue;[m
[32m+[m[32m    }[m
[32m+[m
     public static UserDto getUserData() {[m
         return userData;[m
     }[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/notifications/MyFirebaseMessagingService.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/notifications/MyFirebaseMessagingService.java[m
[1mindex 5d4162f..a44b72b 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/notifications/MyFirebaseMessagingService.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/notifications/MyFirebaseMessagingService.java[m
[36m@@ -12,7 +12,9 @@[m [mimport android.util.Log;[m
 [m
 import com.google.firebase.messaging.FirebaseMessagingService;[m
 import com.google.firebase.messaging.RemoteMessage;[m
[32m+[m[32mimport com.sonda.emsysmobile.GlobalVariables;[m
 import com.sonda.emsysmobile.R;[m
[32m+[m[32mimport com.sonda.emsysmobile.ui.activities.HomeActivity;[m
 import com.sonda.emsysmobile.ui.activities.SplashActivity;[m
 [m
 /**[m
[36m@@ -78,7 +80,12 @@[m [mpublic class MyFirebaseMessagingService extends FirebaseMessagingService {[m
      * @param messageBody FCM message body received.[m
      */[m
     private void showNotificationOnStatusBar(String messageTitle, String messageBody) {[m
[31m-        Intent intent = new Intent(this, SplashActivity.class);[m
[32m+[m[32m        Intent intent;[m
[32m+[m[32m        if (GlobalVariables.getUserData() != null) {[m
[32m+[m[32m            intent = new Intent(this, HomeActivity.class);[m
[32m+[m[32m        } else {[m
[32m+[m[32m            intent = new Intent(this, SplashActivity.class);[m
[32m+[m[32m        }[m
         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);[m
         PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,[m
                 PendingIntent.FLAG_ONE_SHOT);[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/HomeActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/HomeActivity.java[m
[1mindex 02d74ad..efe5155 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/HomeActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/HomeActivity.java[m
[36m@@ -209,10 +209,6 @@[m [mpublic class HomeActivity extends RootActivity[m
         //TODO: show badge in notifications icon[m
     }[m
 [m
[31m-    public final void onBackPressed() {[m
[31m-        super.onBackPressed();[m
[31m-    }[m
[31m-[m
     @Override[m
     protected final void goToEventCreateView() {[m
         getSupportActionBar().setTitle("Creación de evento");[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/RootActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/RootActivity.java[m
[1mindex 0b3d2b0..4504b36 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/RootActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/RootActivity.java[m
[36m@@ -4,6 +4,7 @@[m [mimport android.content.DialogInterface;[m
 import android.content.Intent;[m
 import android.os.Bundle;[m
 import android.preference.PreferenceManager;[m
[32m+[m[32mimport android.provider.Settings;[m
 import android.support.v4.app.DialogFragment;[m
 import android.support.v7.app.AppCompatActivity;[m
 import android.support.v7.widget.Toolbar;[m
[36m@@ -213,10 +214,6 @@[m [mpublic abstract class RootActivity extends AppCompatActivity {[m
     public void onBackPressed() {[m
         if (crossFader != null && crossFader.isCrossFaded()) {[m
             crossFader.crossFade();[m
[31m-        } else {[m
[31m-            DialogFragment dialog =[m
[31m-                    UIUtils.getSimpleDialog("Debe cerrar sesión para modificar su rol.");[m
[31m-            dialog.show(getSupportFragmentManager(), TAG);[m
         }[m
     }[m
 [m
[36m@@ -229,7 +226,6 @@[m [mpublic abstract class RootActivity extends AppCompatActivity {[m
     protected abstract void goToExternalServiceView();[m
 [m
     private void logout() {[m
[31m-        //TODO Move this logic to other service[m
         LogoutRequest<LoginLogoutResponse> request =[m
                 new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);[m
         request.setListener(new Response.Listener<LoginLogoutResponse>() {[m
[36m@@ -272,8 +268,9 @@[m [mpublic abstract class RootActivity extends AppCompatActivity {[m
     }[m
 [m
     private void goToSplash() {[m
[32m+[m[32m        GlobalVariables.setUserData(null);[m
         Intent intent = new Intent(this, SplashActivity.class);[m
[31m-        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);[m
[32m+[m[32m        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);[m
         startActivity(intent);[m
         finish();[m
     }[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SettingsActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SettingsActivity.java[m
[1mindex ccae12f..c94ef8a 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SettingsActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SettingsActivity.java[m
[36m@@ -1,12 +1,15 @@[m
 package com.sonda.emsysmobile.ui.activities;[m
 [m
[32m+[m[32mimport android.content.Intent;[m
 import android.content.SharedPreferences;[m
 import android.os.Bundle;[m
 import android.preference.PreferenceActivity;[m
 import android.preference.PreferenceManager;[m
[32m+[m[32mimport android.support.v4.app.FragmentManager;[m
 import android.util.Log;[m
 [m
 import com.sonda.emsysmobile.R;[m
[32m+[m[32mimport com.sonda.emsysmobile.ui.activities.login.AuthActivity;[m
 import com.sonda.emsysmobile.ui.fragments.SettingsFragment;[m
 [m
 import java.util.List;[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SplashActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SplashActivity.java[m
[1mindex 0c46322..f5dbfde 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SplashActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/SplashActivity.java[m
[36m@@ -13,6 +13,10 @@[m [mimport com.sonda.emsysmobile.R;[m
 import com.sonda.emsysmobile.backendcommunication.offline.OfflineService;[m
 import com.sonda.emsysmobile.ui.activities.login.AuthActivity;[m
 [m
[32m+[m[32mimport org.apache.commons.io.FileUtils;[m
[32m+[m
[32m+[m[32mimport java.io.IOException;[m
[32m+[m
 public class SplashActivity extends AppCompatActivity {[m
 [m
     private static final String TAG = SplashActivity.class.getName();[m
[36m@@ -33,8 +37,12 @@[m [mpublic class SplashActivity extends AppCompatActivity {[m
                 goToAuthActivity();[m
             }[m
         }, delayMillis);[m
[31m-[m
[31m-[m
[32m+[m[32m        // Se borra el cache.[m
[32m+[m[32m        try {[m
[32m+[m[32m            FileUtils.deleteDirectory(SplashActivity.this.getCacheDir());[m
[32m+[m[32m        } catch (IOException e) {[m
[32m+[m[32m            Log.e(TAG, "Error deleting cache dir", e);[m
[32m+[m[32m        }[m
         // Start Offline service.[m
         Intent intent = new Intent(SplashActivity.this, OfflineService.class);[m
         startService(intent);[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/AuthActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/AuthActivity.java[m
[1mindex 508e179..89b4cb8 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/AuthActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/AuthActivity.java[m
[36m@@ -194,7 +194,9 @@[m [mpublic class AuthActivity extends FragmentActivity implements View.OnClickListen[m
      */[m
     private void goToRoleChooser() {[m
         Intent intent = new Intent(this, RoleChooserActivity.class);[m
[32m+[m[32m        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);[m
         startActivity(intent);[m
[32m+[m[32m        finish();[m
     }[m
 [m
     private void sendRegistrationToServer() {[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/RoleChooserActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/RoleChooserActivity.java[m
[1mindex 014ac4b..2f4ab25 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/RoleChooserActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/RoleChooserActivity.java[m
[36m@@ -4,6 +4,7 @@[m [mimport android.content.DialogInterface;[m
 import android.content.Intent;[m
 import android.net.Uri;[m
 import android.os.Bundle;[m
[32m+[m[32mimport android.preference.PreferenceManager;[m
 import android.support.v7.app.AppCompatActivity;[m
 import android.util.Log;[m
 import android.view.View;[m
[36m@@ -15,14 +16,20 @@[m [mimport com.google.android.gms.appindexing.Action;[m
 import com.google.android.gms.appindexing.AppIndex;[m
 import com.google.android.gms.appindexing.Thing;[m
 import com.google.android.gms.common.api.GoogleApiClient;[m
[32m+[m[32mimport com.sonda.emsysmobile.GlobalVariables;[m
 import com.sonda.emsysmobile.R;[m
 import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorCodeCategory;[m
 import com.sonda.emsysmobile.backendcommunication.model.responses.GetRolesResponse;[m
[32m+[m[32mimport com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;[m
[32m+[m[32mimport com.sonda.emsysmobile.backendcommunication.services.KeepAliveService;[m
 import com.sonda.emsysmobile.backendcommunication.services.request.GetRolesRequest;[m
[32m+[m[32mimport com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;[m
 import com.sonda.emsysmobile.logic.model.core.ResourceDto;[m
 import com.sonda.emsysmobile.logic.model.core.RoleDto;[m
 import com.sonda.emsysmobile.logic.model.core.ZoneDto;[m
[32m+[m[32mimport com.sonda.emsysmobile.managers.EventManager;[m
 import com.sonda.emsysmobile.ui.activities.HomeActivity;[m
[32m+[m[32mimport com.sonda.emsysmobile.ui.activities.RootActivity;[m
 [m
 import java.io.Serializable;[m
 import java.util.List;[m
[36m@@ -81,6 +88,59 @@[m [mpublic class RoleChooserActivity extends AppCompatActivity implements View.OnCli[m
     }[m
 [m
     @Override[m
[32m+[m[32m    public final void onBackPressed() {[m
[32m+[m[32m        logout();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void logout() {[m
[32m+[m[32m        LogoutRequest<LoginLogoutResponse> request =[m
[32m+[m[32m                new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);[m
[32m+[m[32m        request.setListener(new Response.Listener<LoginLogoutResponse>() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onResponse(LoginLogoutResponse response) {[m
[32m+[m[32m                final int responseCode = response.getCode();[m
[32m+[m[32m                if (responseCode == 0) {[m
[32m+[m[32m                    // Stop KeepAlive service.[m
[32m+[m[32m                    Intent intent = new Intent(RoleChooserActivity.this, KeepAliveService.class);[m
[32m+[m[32m                    stopService(intent);[m
[32m+[m[32m                    // Se reinicia el token de autenticacion.[m
[32m+[m[32m                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit()[m
[32m+[m[32m                            .putString("access_token", "").commit();[m
[32m+[m[32m                    EventManager.getInstance(RoleChooserActivity.this).onLogout();[m
[32m+[m[32m                    goToAuth();[m
[32m+[m[32m                } else {[m
[32m+[m[32m                    String errorMsg = response.getInnerResponse().getMsg();[m
[32m+[m[32m                    if (!isFinishing()) {[m
[32m+[m[32m                        handleErrorMessage(RoleChooserActivity.this, responseCode, errorMsg);[m
[32m+[m[32m                    }[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m        request.setErrorListener(new Response.ErrorListener() {[m
[32m+[m[32m            @Override[m
[32m+[m[32m            public void onErrorResponse(VolleyError error) {[m
[32m+[m[32m                Log.d(TAG, getString(R.string.error_http));[m
[32m+[m[32m                if (!isFinishing()) {[m
[32m+[m[32m                    handleVolleyErrorResponse(RoleChooserActivity.this, error, new DialogInterface[m
[32m+[m[32m                            .OnClickListener() {[m
[32m+[m[32m                        @Override[m
[32m+[m[32m                        public void onClick(DialogInterface dialog, int which) {[m
[32m+[m[32m                            logout();[m
[32m+[m[32m                        }[m
[32m+[m[32m                    });[m
[32m+[m[32m                }[m
[32m+[m[32m            }[m
[32m+[m[32m        });[m
[32m+[m[32m        request.execute();[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    private void goToAuth() {[m
[32m+[m[32m        GlobalVariables.setUserData(null);[m
[32m+[m[32m        Intent intent = new Intent(this, AuthActivity.class);[m
[32m+[m[32m        startActivity(intent);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
     public final void onStart() {[m
         super.onStart();[m
         // ATTENTION: This was auto-generated to implement the App Indexing API.[m
[1mdiff --git a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/ZonasRecursosChooserActivity.java b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/ZonasRecursosChooserActivity.java[m
[1mindex 68c4b0f..89a5b13 100644[m
[1m--- a/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/ZonasRecursosChooserActivity.java[m
[1m+++ b/EMSysMobile/app/src/main/java/com/sonda/emsysmobile/ui/activities/login/ZonasRecursosChooserActivity.java[m
[36m@@ -141,6 +141,12 @@[m [mpublic class ZonasRecursosChooserActivity extends AppCompatActivity implements V[m
     }[m
 [m
     @Override[m
[32m+[m[32m    public final void onBackPressed() {[m
[32m+[m[32m        Intent intent = new Intent(this, RoleChooserActivity.class);[m
[32m+[m[32m        startActivity(intent);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m    @Override[m
     public final void onStop() {[m
         super.onStop();[m
 [m
[36m@@ -246,7 +252,6 @@[m [mpublic class ZonasRecursosChooserActivity extends AppCompatActivity implements V[m
 [m
     public final void goToHome() {[m
         Intent intent = new Intent(this, HomeActivity.class);[m
[31m-        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);[m
         startActivity(intent);[m
     }[m
 [m
