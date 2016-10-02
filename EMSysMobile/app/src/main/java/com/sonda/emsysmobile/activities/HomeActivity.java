package com.sonda.emsysmobile.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.sonda.emsysmobile.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

import static com.sonda.emsysmobile.utils.JsonUtils.getErrorMessage;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
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
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                return true;
            case R.id.menu_list_events_button:
                System.out.println("Tocaste " + getString(R.string.menu_list_events_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_list_events_string));
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                return true;
            case R.id.menu_external_service_button:
                System.out.println("Tocaste " + getString(R.string.menu_external_service_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_external_service_string));
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                return true;
            case R.id.menu_view_map_button:
                System.out.println("Tocaste " + getString(R.string.menu_view_map_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_view_map_string));
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                return true;
            case R.id.menu_logout_button:
                System.out.println("Tocaste " + getString(R.string.menu_logout_string));
                fragment = new TestFragment();
                args = new Bundle();
                args.putString("text", getString(R.string.menu_logout_string));
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                logout();
            default:
                // Accion no reconocida, se lo delega a la superclase.
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        GsonPostRequest<LoginLogoutResponse> request = RequestFactory.logoutRequest(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                final int responseCode = response.getCode();
                if (responseCode == 0) {
                    goToMain();
                } else {
                    String errorMsg = getErrorMessage(responseCode);
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


//package com.sonda.emsysmobile.activities;
//
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.sonda.emsysmobile.R;
//import com.sonda.emsysmobile.model.responses.LoginLogoutResponse;
//import com.sonda.emsysmobile.network.AppRequestQueue;
//import com.sonda.emsysmobile.network.GsonPostRequest;
//import com.sonda.emsysmobile.network.RequestFactory;
//
//import static com.sonda.emsysmobile.utils.JsonUtils.getErrorMessage;
//
///**
// * Created by marccio on 10/1/16.
// */
//
//public class HomeActivity extends AppCompatActivity {
//    private DrawerLayout mDrawerLayout;
//    private ListView mDrawerList;
//    private ActionBarDrawerToggle mDrawerToggle;
//
//    private CharSequence mDrawerTitle;
//    private CharSequence mTitle;
//    private String[] mMenuItems;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//
//        mTitle = mDrawerTitle = getTitle();
//        mMenuItems = getResources().getStringArray(R.array.navdrawer_array);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
//
//        // set a custom shadow that overlays the main content when the drawer opens
//        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//        // set up the drawer's list view with items and click listener
//        mDrawerList.setAdapter(new ArrayAdapter<>(this,
//                R.layout.drawer_list_item, mMenuItems));
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//
//        // enable ActionBar app icon to behave as action to toggle nav drawer
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
//
//        // ActionBarDrawerToggle ties together the the proper interactions
//        // between the sliding drawer and the action bar app icon
//        mDrawerToggle = new ActionBarDrawerToggle(
//                this,                  /* host Activity */
//                mDrawerLayout,         /* DrawerLayout object */
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close  /* "close drawer" description for accessibility */
//        ) {
//            public void onDrawerClosed(View view) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            public void onDrawerOpened(View drawerView) {
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//        mDrawerLayout.addDrawerListener(mDrawerToggle);
//
//        if (savedInstanceState == null) {
//            selectItem(0);
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    /* Called whenever we call invalidateOptionsMenu() */
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        return super.onPrepareOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // The action bar home/up action should open or close the drawer.
//        // ActionBarDrawerToggle will take care of this.
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        // Handle action buttons
//        switch (item.getItemId()) {
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
//
//    /* The click listener for ListView in the navigation drawer */
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    private void selectItem(int position) {
//        // update the main content by replacing fragments
//        Fragment fragment = new TestFragment();
//        Bundle args = new Bundle();
//        args.putString("text", mMenuItems[position]);
//        fragment.setArguments(args);
//
//        FragmentManager fragmentManager = getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//
//        // update selected item and title, then close the drawer
//        mDrawerList.setItemChecked(position, true);
//        if (mMenuItems[position].equals(getString(R.string.menu_logout))) {
//            logout();
//        }
//        mDrawerLayout.closeDrawer(mDrawerList);
//    }
//
//    private void logout() {
//        GsonPostRequest<LoginLogoutResponse> request = RequestFactory.logoutRequest(new Response.Listener<LoginLogoutResponse>() {
//            @Override
//            public void onResponse(LoginLogoutResponse response) {
//                final int responseCode = response.getCode();
//                if (responseCode == 0) {
//                    goToMain();
//                } else {
//                    String errorMsg = getErrorMessage(responseCode);
//                    //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
//                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, android.R.style.Theme_Material_Light_Dialog_MinWidth);
//                    builder.setTitle("Error");
//                    builder.setMessage(errorMsg);
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            switch (responseCode) {
//                                case 2:
//                                    goToMain();
//                                default:
//                                    break;
//                            }
//                        }
//                    });
//                    builder.show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        }, getApplicationContext());
//        AppRequestQueue.getInstance(this).addToRequestQueue(request);
//    }
//
//    private void goToMain() {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//
//    @Override
//    public void setTitle(CharSequence title) {
//        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
//    }
//
//    /**
//     * When using the ActionBarDrawerToggle, you must call it during
//     * onPostCreate() and onConfigurationChanged()...
//     */
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    /**
//     * Fragment that appears in the "content_frame", shows a planet
//     */
//    public static class TestFragment extends Fragment {
//
//        public TestFragment() {
//            // Empty constructor required for fragment subclasses
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.test_fragment_layout, container, false);
//            String text = getArguments().getString("text");
////            getActivity().setTitle(text);
//            return rootView;
//        }
//    }
//}