package pis16.memsysandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.locks.Lock;

import pis16.memsysandroid.R;
import pis16.memsysandroid.notifications.factories.FactoryNotifications;
import pis16.memsysandroid.notifications.interfaces.INotifications;


public class MainActivity extends AppCompatActivity {

    public  static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    ArrayList<String> list;
    ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INotifications notificationSystem;
        // Se pasa la MainActivity por parámetro para poder enviar de vuelta
        // los mensajes recibidos a esta actividad.
        //TODO encontrar una manera mejor.
        notificationSystem = FactoryNotifications.GetInstance(this);
        // Hay que esperar que se conecte al servidor antes de hacer
        // la suscripción.
        while (!notificationSystem.isConnected());
        String ChannelName="myChannel";
        notificationSystem.SubscribeChannel(ChannelName);
        // Inicio de manejo de lista.
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        listView = (ListView) findViewById((R.id.list));
        listView.setAdapter(adapter);
        // Fin de manejo de lista.
    }

    // Llamado cuando el cliente envía un mensaje con el botón de enviar.
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void onMessageReceived(String msg) {
        list.add(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Actualización de elementos de UI.
                // No se puede modificar ninguna view desde otro thread
                // que no sea este.
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount() - 1);
            }
        });
    }


}
