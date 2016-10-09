package com.sonda.emsysmobile.ui.activities.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ResponseCodeCategory;
import com.sonda.emsysmobile.ui.activities.HomeActivity;
import com.sonda.emsysmobile.ui.activities.login.RoleChooserActivity.EleccionRol;
import com.sonda.emsysmobile.backendcommunication.model.responses.LoginLogoutResponse;
import com.sonda.emsysmobile.logic.model.core.ResourceDto;
import com.sonda.emsysmobile.logic.model.core.RoleDto;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;
import com.sonda.emsysmobile.backendcommunication.services.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sonda.emsysmobile.utils.UIUtils.handleErrorMessage;
import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by marccio on 9/28/16.
 */

public class ZonasRecursosChooserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mDispatcherButton;
    private Button mResourceButton;
    private Button mContinueButton;
    private static final String TAG = ZonasRecursosChooserActivity.class.getName();
    private List<String> mSelectedItems = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_recursos_chooser);

        ListView mRolesListView = (ListView) findViewById(R.id.listview_roles);
        mRolesListView.setItemsCanFocus(false);
        mRolesListView.setOnItemClickListener(this);
        // Los botones no deben ser clickeables segun el mockup.
        mDispatcherButton = (Button) findViewById(R.id.button_despachador);
        mDispatcherButton.setClickable(false);
        mDispatcherButton.setOnClickListener(this);
        mResourceButton = (Button) findViewById(R.id.button_recurso);
        mResourceButton.setClickable(false);
        mResourceButton.setOnClickListener(this);
        mContinueButton = (Button) findViewById(R.id.button_continuar);
        mContinueButton.setEnabled(false);
        mContinueButton.setOnClickListener(this);

        Bundle mExtras = getIntent().getExtras();
        EleccionRol eleccionRol =
                (EleccionRol) mExtras.getSerializable("eleccionRol");
        final ArrayList<String> list = new ArrayList<>();
        // Deshabilito el boton que corresponda en base a la eleccion previa del usuario.
        if (eleccionRol == EleccionRol.Despachador) {
            mResourceButton.setEnabled(false);
            mRolesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayList<ZoneDto> zonas = (ArrayList<ZoneDto>) mExtras.getSerializable("zonas");
            for (ZoneDto zona : zonas) {
                list.add(zona.getIdentifier() + " - " + zona.getName() + " - " + zona.getExecUnitName());
            }
        } else {
            mDispatcherButton.setEnabled(false);
            mRolesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayList<ResourceDto> recursos = (ArrayList<ResourceDto>) mExtras.getSerializable("recursos");
            for (ResourceDto recurso : recursos) {
                list.add(recurso.getId() + " - " + recurso.getCode());
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice,
                list);
        mRolesListView.setAdapter(adapter);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public final void onClick(View view) {
        if ((view.getId() == R.id.button_continuar) && (mContinueButton.isEnabled())) {
            // Construyo listas que van a servir para construir
            // el RoleDto para la request.
            ArrayList<ZoneDto> zonas = new ArrayList<>();
            ArrayList<ResourceDto> recursos = new ArrayList<>();
            String emptyString = "";
            if (mDispatcherButton.isEnabled()) {
                // Se parsea cada item seleccionado para construir
                // un RoleDto para cada uno.
                String regex = "^(.*)\\s-\\s(.*)\\s-\\s(.*)$";
                Pattern pattern = Pattern.compile(regex);
                for (String item : mSelectedItems) {
                    Matcher matcher = pattern.matcher(item);
                    String id = emptyString;
                    String nombre = emptyString;
                    String nombreUE = emptyString;
                    if (matcher.find()) {
                        final int[] indexes = {1, 2, 3};
                        id = matcher.group(indexes[0]);
                        nombre = matcher.group(indexes[1]);
                        nombreUE = matcher.group(indexes[2]);
                    }
                    zonas.add(new ZoneDto(nombre, Integer.parseInt(id), nombreUE));
                }
            } else if (mResourceButton.isEnabled()) {
                // Debe haber un solo recurso en los items.
                // Se parsea cada item seleccionado para construir
                // un RoleDto para cada uno.
                String regex = "^(.*)\\s-\\s(.*)$";
                Pattern pattern = Pattern.compile(regex);
                for (String item : mSelectedItems) {
                    Matcher matcher = pattern.matcher(item);
                    String idString = emptyString;
                    String code = emptyString;
                    if (matcher.find()) {
                        idString = matcher.group(1);
                        code = matcher.group(2);
                    }
                    recursos.add(new ResourceDto(code, Integer.parseInt(idString)));
                }
            }
            loginUser(new RoleDto(zonas, recursos), new VolleyCallbackLoginUser() {
                @Override
                public void onSuccess() {
                    // TODO agregar logica posterior al inicio de sesion.
                    goToHome();
                }
            });
        }
    }

    private void loginUser(RoleDto roles, final VolleyCallbackLoginUser callback) {
        LoginRequest<LoginLogoutResponse> request = new LoginRequest<>(getApplicationContext(),
                LoginLogoutResponse.class, roles);
        request.setListener(new Response.Listener<LoginLogoutResponse>() {
            @Override
            public void onResponse(LoginLogoutResponse response) {
                // Parseo el codigo de respuesta y determino el exito de la operacion.
                final int responseCode = response.getCode();
                if (responseCode == ResponseCodeCategory.SUCCESS.getNumVal()) {
                    callback.onSuccess();
                } else {
                    String errorMsg = response.getInnerResponse().getMsg();
                    handleErrorMessage(ZonasRecursosChooserActivity.this, responseCode, errorMsg);
                }
            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, getString(R.string.error_http));
                handleVolleyErrorResponse(ZonasRecursosChooserActivity.this, error);
            }
        });
        request.execute();
    }

    public final void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public final Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ZonasRecursosChooser Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public final void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public final void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public final void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView instanceof ListView) {
            ListView lv = (ListView) adapterView;
            SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
            // Como solo se puede seleccionar un recurso, al seleccionar otro se vacia
            // la lista por completo.
            if (mResourceButton.isEnabled()) {
                mSelectedItems.clear();
            }
            if (checkedItems != null) {
                int itemsSeleccionados = 0;
                for (int i = 0; i < checkedItems.size(); i++) {
                    String item = lv.getAdapter().getItem(
                            checkedItems.keyAt(i)).toString();
                    if (checkedItems.valueAt(i)) {
                        itemsSeleccionados++;
                        Log.i(TAG, item + " was selected");
                        // Solo se agrega un item en caso de ya no estar en la lista.
                        if (!mSelectedItems.contains(item)) {
                            mSelectedItems.add(item);
                        }
                    } else {
                        // Remuevo todas las ocurrencias del item en mi lista
                        // de items seleccionados.
                        boolean allItemsRemoved = false;
                        while (!allItemsRemoved) {
                            allItemsRemoved = !mSelectedItems.remove(item);
                        }
                    }
                }
                // Imprimo items seleccionados hasta el momento.
                Log.d(TAG, "Items seleccionados = " + Integer.toString(itemsSeleccionados));
                for (String item : mSelectedItems) {
                    Log.d(TAG, item);
                }
                // Habilito o deshabilito el boton de continuar en base a la cantidad
                // de elementos seleccionados.
                if (itemsSeleccionados > 0) {
                    mContinueButton.setEnabled(true);
                } else {
                    mContinueButton.setEnabled(false);
                }
            }
        }
    }

    /**
     * Interfaz implementada para recibir el resultado de la request
     * de Volley una vez que finalice.
     */
    public interface VolleyCallbackLoginUser {
        void onSuccess();
    }
}
