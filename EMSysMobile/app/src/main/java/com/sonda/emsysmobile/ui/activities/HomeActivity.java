package com.sonda.emsysmobile.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.fragments.ExtensionsFragment;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.LogoutRequest;

public class HomeActivity extends AppCompatActivity implements ExtensionsFragment.OnListFragmentInteractionListener {

    private static final String TAG = HomeActivity.class.getName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ExtensionsFragment extensionsFragment = new ExtensionsFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, extensionsFragment).commit();
        }
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto item) {
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        String textString = "text";
        String touchedString = "Tocaste ";
        switch (item.getItemId()) {
            case R.id.menu_create_event_button:
                Log.d(TAG, touchedString + getString(R.string.menu_create_event_string));
                Fragment fragment = new TestFragment();
                Bundle args = new Bundle();
                args.putString(textString, getString(R.string.menu_create_event_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment1");
                return true;
            case R.id.menu_list_events_button:
                Log.d(TAG, touchedString + getString(R.string.menu_list_events_string));
                ExtensionsFragment extensionsFragment = (ExtensionsFragment) getSupportFragmentManager().findFragmentByTag(ExtensionsFragment.class.getSimpleName());
                if (extensionsFragment == null) {
                    extensionsFragment = new ExtensionsFragment();
                    replaceFragment(extensionsFragment, ExtensionsFragment.class.getSimpleName());
                }
                return true;
            case R.id.menu_external_service_button:
                Log.d(TAG, touchedString + getString(R.string.menu_external_service_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString(textString, getString(R.string.menu_external_service_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment2");
                return true;
            case R.id.menu_view_map_button:
                Log.d(TAG, touchedString + getString(R.string.menu_view_map_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString(textString, getString(R.string.menu_view_map_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment3");
                return true;
            case R.id.menu_logout_button:
                Log.d(TAG, touchedString + getString(R.string.menu_logout_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString(textString, getString(R.string.menu_logout_string));
                fragment.setArguments(args);
                replaceFragment(fragment, "fragment4");
                logout();
            default:
                // Accion no reconocida, se lo delega a la superclase.
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment, String fragmentTAG) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, fragmentTAG).commit();
    }

    private void logout() {
        LogoutRequest<LoginLogoutResponse> request = new LogoutRequest<>(getApplicationContext(), LoginLogoutResponse.class);
        request.setListener(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    // Se reinicia el token de autenticacion.
                    PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit().putString("access_token", "").commit();
                    goToSplash();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
                    builder.setTitle("Error");
                    builder.setMessage(errorMsg);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            switch (responseCode) {
                                case 2:
                                    goToSplash();
                                default:
                                    break;
                            }
                        }
                    });
                    builder.show();
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error en la comunicación con el servidor.");
            }
        });
        request.execute();
    }

    private void goToSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class TestFragment extends Fragment {

        public TestFragment() {
            // Constructor vacio requerido por todas las subclases de Fragment.
        }

        @Override
        public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.test_fragment_layout, container, false);
            String text = getArguments().getString("text");
            TextView textView = (TextView) rootView.findViewById(R.id.fragment_textview);
            textView.setText(text);
            return rootView;
        }
    }

}