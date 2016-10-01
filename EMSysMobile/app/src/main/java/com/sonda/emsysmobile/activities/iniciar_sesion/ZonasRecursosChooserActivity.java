package com.sonda.emsysmobile.activities.iniciar_sesion;

import android.app.AlertDialog;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.HomeActivity;
import com.sonda.emsysmobile.activities.iniciar_sesion.RoleChooserActivity.EleccionRol;
import com.sonda.emsysmobile.model.responses.LoginResponse;
import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.GsonPostRequest;
import com.sonda.emsysmobile.network.RequestFactory;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sonda.emsysmobile.utils.JsonUtils.getErrorMessage;
import static com.sonda.emsysmobile.utils.JsonUtils.isSuccessfulResponse;

/**
 * Created by marccio on 9/28/16.
 */

public class ZonasRecursosChooserActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button mDespachadorButton;
    private Button mRecursoButton;
    private Button mContinuarButton;
    private Bundle mExtras;
    private ListView mRolesListView;
    private static final String TAG = ZonasRecursosChooserActivity.class.getName();
    private ArrayList<String> mItemsSeleccionados = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_recursos_chooser);

        mRolesListView = (ListView) findViewById(R.id.listview_roles);
        mRolesListView.setItemsCanFocus(false);
        mRolesListView.setOnItemClickListener(this);
        // Los botones no deben ser clickeables segun el mockup.
        mDespachadorButton = (Button) findViewById(R.id.button_despachador);
        mDespachadorButton.setClickable(false);
        mDespachadorButton.setOnClickListener(this);
        mRecursoButton = (Button) findViewById(R.id.button_recurso);
        mRecursoButton.setClickable(false);
        mRecursoButton.setOnClickListener(this);
        mContinuarButton = (Button) findViewById(R.id.button_continuar);
        mContinuarButton.setEnabled(false);
        mContinuarButton.setOnClickListener(this);

        mExtras = getIntent().getExtras();
        EleccionRol eleccionRol =
                (EleccionRol) mExtras.getSerializable("eleccionRol");
        final ArrayList<String> list = new ArrayList<>();
        // Deshabilito el boton que corresponda en base a la eleccion previa del usuario.
        if (eleccionRol == EleccionRol.Despachador) {
            mRecursoButton.setEnabled(false);
            mRolesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            ArrayList<DtoZona> zonas = (ArrayList<DtoZona>) mExtras.getSerializable("zonas");
            for (DtoZona zona : zonas) {
                list.add(zona.getId() + " - " + zona.getNombre() + " - " + zona.getNombreUnidadEjecutora());
            }
        } else {
            mDespachadorButton.setEnabled(false);
            mRolesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            ArrayList<DtoRecurso> recursos = (ArrayList<DtoRecurso>) mExtras.getSerializable("recursos");
            for (DtoRecurso recurso : recursos) {
                list.add(recurso.getCodigo());
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
    public void onClick(View view) {
        if (view.getId() == R.id.button_continuar) {
            if (mContinuarButton.isEnabled()) {
                // Construyo listas que van a servir para construir
                // el DtoRol para la request.
                ArrayList<DtoZona> zonas = new ArrayList<>();
                ArrayList<DtoRecurso> recursos = new ArrayList<>();
                if (mDespachadorButton.isEnabled()) {
                    // Se parsea cada item seleccionado para construir
                    // un DtoRol para cada uno.
                    String regex = "^(.*)\\s-\\s(.*)\\s-\\s(.*)$";
                    Pattern pattern = Pattern.compile(regex);
                    for (String item : mItemsSeleccionados) {
                        Matcher matcher = pattern.matcher(item);
                        String id = "";
                        String nombre = "";
                        String nombreUE = "";
                        if (matcher.find()) {
                            id = matcher.group(1);
                            nombre = matcher.group(2);
                            nombreUE = matcher.group(3);
                        }
                        zonas.add(new DtoZona(nombre, id, nombreUE));
                    }
                } else if (mRecursoButton.isEnabled()){
                    // Debe haber un solo recurso en los items.
                    for (String item : mItemsSeleccionados) {
                        recursos.add(new DtoRecurso(item));
                    }
                }
                loginUser(new DtoRol(zonas, recursos), new VolleyCallbackLoginUser() {
                    @Override
                    public void onSuccess() {
                        // TODO agregar logica posterior al inicio de sesion.
                        goToHome();
                    }
                });
            }
        }
    }

    private void loginUser(DtoRol roles, final VolleyCallbackLoginUser callback) {
        GsonPostRequest<LoginResponse> request = RequestFactory.loguearUsuarioRequest(roles, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                // Parseo el codigo de respuesta y determino el exito de la operacion.
                String codigoRespuestaString = response.getCodigoRespuesta();
                if (codigoRespuestaString != null) {
                    int codigoRespuesta = Integer.parseInt(codigoRespuestaString);
                    boolean loginExitoso = isSuccessfulResponse(codigoRespuesta);
                    if (loginExitoso) {
                        callback.onSuccess();
                    } else {
                        // Obtengo mensaje de error correspondiente al codigo.
                        String errorMsg = getErrorMessage(codigoRespuesta);
                        Log.d(TAG, "errorMsg : " + errorMsg);
                        //Genero un AlertDialog para informarle al usuario cual fue el error ocurrido.
                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                ZonasRecursosChooserActivity.this,
                                android.R.style.Theme_Material_Light_Dialog_MinWidth);
                        builder.setTitle("Error");
                        builder.setMessage(errorMsg);
                        builder.setPositiveButton("OK", null);
                        builder.show();
                    }
                } else {
                    Log.d(TAG, "Error en el formato del mensaje recibido.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error en la comunicación con el servidor.");
            }
        }, getApplicationContext());
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    /**
     * Interfaz implementada para recibir el resultado de la request
     * de Volley una vez que finalice.
     */
    public interface VolleyCallbackLoginUser {
        void onSuccess();
    }

    public void goToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
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
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView instanceof ListView) {
            ListView lv = (ListView) adapterView;
            SparseBooleanArray checkedItems = lv.getCheckedItemPositions();
            // Como solo se puede seleccionar un recurso, al seleccionar otro se vacia
            // la lista por completo.
            if (mRecursoButton.isEnabled()) {
                mItemsSeleccionados.clear();
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
                        if (!mItemsSeleccionados.contains(item)) {
                            mItemsSeleccionados.add(item);
                        }
                    } else {
                        // Remuevo todas las ocurrencias del item en mi lista
                        // de items seleccionados.
                        while (mItemsSeleccionados.remove(item)) {
                        }
                    }
                }
                // Imprimo items seleccionados hasta el momento.
                Log.d(TAG, "Items seleccionados = " + Integer.toString(itemsSeleccionados));
                for (String item : mItemsSeleccionados) {
                    Log.d(TAG, item);
                }
                // Habilito o deshabilito el boton de continuar en base a la cantidad
                // de elementos seleccionados.
                if (itemsSeleccionados > 0) {
                    mContinuarButton.setEnabled(true);
                } else {
                    mContinuarButton.setEnabled(false);
                }
            }
        }
    }
}
