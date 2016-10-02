package com.sonda.emsysmobile.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.fragments.ExtensionsFragment;
import com.sonda.emsysmobile.model.core.ExtensionDto;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

public class HomeActivity extends AppCompatActivity implements ExtensionsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ExtensionDto item) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_event_button:
                System.out.println("Tocaste " + getString(R.string.menu_create_event_string));
                Fragment fragment = new TestFragment();
                Bundle args = new Bundle();
                args.putString("text", getString(R.string.menu_create_event_string));
                fragment.setArguments(args);
                replaceFragment(fragment);
                return true;
            case R.id.menu_list_events_button:
                System.out.println("Tocaste " + getString(R.string.menu_list_events_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_list_events_string));
                fragment.setArguments(args);
                replaceFragment(fragment);
                return true;
            case R.id.menu_external_service_button:
                System.out.println("Tocaste " + getString(R.string.menu_external_service_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_external_service_string));
                fragment.setArguments(args);
                replaceFragment(fragment);
                return true;
            case R.id.menu_view_map_button:
                System.out.println("Tocaste " + getString(R.string.menu_view_map_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_view_map_string));
                fragment.setArguments(args);
                replaceFragment(fragment);
                return true;
            case R.id.menu_logout_button:
                System.out.println("Tocaste " + getString(R.string.menu_logout_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_logout_string));
                fragment.setArguments(args);
                replaceFragment(fragment);
                logout();
            default:
                // Accion no reconocida, se lo delega a la superclase.
                return super.onOptionsItemSelected(item);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void logout() {
        GsonPostRequest<LoginLogoutResponse> request = RequestFactory.logoutRequest(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    goToMain();
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
                                    goToMain();
                                default:
                                    break;
                            }
                        }
                    });
                    builder.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }, getApplicationContext());
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
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
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.test_fragment_layout, container, false);
            String text = getArguments().getString("text");
            TextView textView = (TextView) rootView.findViewById(R.id.fragment_textview);
            textView.setText(text);
            return rootView;
        }
    }

}