package pis16.memsysandroid.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pis16.memsysandroid.R;
import pis16.memsysandroid.wservices.RetrofitManager;

public class DisplayMessageActivity extends AppCompatActivity {

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desplay_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);

        this.context = this;

        new AsyncTaskPrototipo().execute();

        textView.setText(message);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }

    private class AsyncTaskPrototipo extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... args) {

            String username = "Andres";

            RetrofitManager.getInstance().getUser(username);

            return null;
        }

        protected void onPostExecute(Void empty) {
            Toast.makeText(context, "Fin de lista alcanzado", Toast.LENGTH_LONG).show();
        }

    }
}
