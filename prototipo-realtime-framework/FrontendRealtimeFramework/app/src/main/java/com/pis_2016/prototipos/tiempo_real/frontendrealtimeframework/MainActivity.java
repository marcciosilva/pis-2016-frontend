package com.pis_2016.prototipos.tiempo_real.frontendrealtimeframework;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ibt.ortc.api.Ortc;
import ibt.ortc.extensibility.OnConnected;
import ibt.ortc.extensibility.OnMessage;
import ibt.ortc.extensibility.OrtcClient;
import ibt.ortc.extensibility.OrtcFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //begin list handling
        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        String[] values = new String[] {};

        for (int i = 0; i < values.length; i++) {
            list.add(values[i]);
        }
        ListView listView = (ListView) findViewById((R.id.list));
        listView.setAdapter(adapter);
        //end list handling

//        Button button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.add("Random element");
//                adapter.notifyDataSetChanged();
//            }
//        });


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(view, "Abrir http://console.realtime.co/, usar token \"testToken\", app key \"HDzwHh\", channel \"myChannel\", subscribirse al canal y enviar mensajes", Snackbar.LENGTH_LONG)
                        .setAction("Action", null);
                TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                textView.setMaxLines(5); //mostrar multiples lineas
                snackbar.show();
            }
        });

        setUpConnectionRealtime();

    }

    private void setUpConnectionRealtime() {
        OrtcFactory factory;
        final OrtcClient client;
        Ortc ortc = new Ortc();
        try {
            factory = ortc.loadOrtcFactory("IbtRealtimeSJ");
            client = factory.createClient();
            client.setClusterUrl("http://ortc-developers.realtime.co/server/2.1");
            client.connect("HDzwHh", "testToken");

            client.onConnected = new OnConnected() {
                @Override
                public void run(final OrtcClient sender) {
                    // Messaging client connected

                    // Now subscribe the channel
                    client.subscribe("myChannel", true,
                            new OnMessage() {
                                // This function is the message handler
                                // It will be invoked for each message received in myChannel

                                public void run(OrtcClient sender, String channel, String message) {
                                    // Received a message
                                    System.out.println(message);
                                    list.add(message);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //stuff that updates ui
                                            //no se puede modificar ninguna view desde otro thread
                                            //que no sea este
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                    //client.send("myChannel", "Acknowledgement");
                                }
                            });
                }
            };

        } catch (Exception e) {
            System.out.println(String.format("Realtime Error: %s", e.toString()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
