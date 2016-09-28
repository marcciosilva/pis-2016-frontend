package com.sonda.emsysmobile.activities.iniciar_sesion;

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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.activities.HomeActivity;
import com.sonda.emsysmobile.activities.iniciar_sesion.RoleChooserActivity.EleccionRol;
import com.sonda.emsysmobile.model.core.DtoRecurso;
import com.sonda.emsysmobile.model.core.DtoRol;
import com.sonda.emsysmobile.model.core.DtoZona;

import java.util.ArrayList;

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
        mRolesListView .setItemsCanFocus(false);
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
        // Deshabilito el boton que corresponda en base a la eleccion previa del usuario.
        if (eleccionRol == EleccionRol.Despachador) {
            mRecursoButton.setEnabled(false);
            mRolesListView .setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        } else {
            mDespachadorButton.setEnabled(false);
            mRolesListView .setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }

        // Configuracion de ListView.
        ArrayList<DtoRol> modelIntentRoles = (ArrayList<DtoRol>) mExtras.getSerializable("modelIntentRoles");
        final ArrayList<String> list = new ArrayList<>();
        for (DtoRol rol : modelIntentRoles) {
            if (rol instanceof DtoRecurso && mRecursoButton.isEnabled()) {
                list.add(((DtoRecurso) rol).getCodigo());
            } else if (rol instanceof DtoZona && mDespachadorButton.isEnabled()) {
                list.add(((DtoZona) rol).getNombre());
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
               goToHome();
           }
        }
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
            if (checkedItems != null) {
                int itemsSeleccionados = 0;
                for (int i=0 ; i < checkedItems.size() ; i++) {
                    if (checkedItems.valueAt(i)) {
                        itemsSeleccionados++;
                        String item = lv.getAdapter().getItem(
                                checkedItems.keyAt(i)).toString();
                        Log.i(TAG,item + " was selected");
                    }
                }
                Log.d(TAG, "Items seleccionados = " + Integer.toString(itemsSeleccionados));
                if (itemsSeleccionados > 0) {
                    mContinuarButton.setEnabled(true);
                } else {
                    mContinuarButton.setEnabled(false);
                }
            }



//            int size = checked.size();
//            Log.d(TAG, "Items seleccionados = " + Integer.toString(size));
//            for (int i = 0 ; i < size ; i++) {
//                int key = checked.keyAt(i);
//                boolean value = checked.get(key);
//                if (value)
////                    doSomethingWithSelectedIndex(key);
//            }
        }
    }
}
