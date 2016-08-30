package com.sonda.emsysmobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.network.AppRequestQueue;
import com.sonda.emsysmobile.network.RequestFactory;
import com.sonda.emsysmobile.utils.UIUtils;

import org.json.JSONArray;

public class ConsumeWSActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mURLEditText;
    private Button mSendRequestButton;
    private TextView mResponseTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume_ws);

        mURLEditText = (EditText) findViewById(R.id.input_url);
        mResponseTextView = (TextView) findViewById(R.id.text_response);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mSendRequestButton = (Button) findViewById(R.id.button_send_request);
        mSendRequestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_send_request) {
            UIUtils.hideSoftKeyboard(this);
            consumeWS();
        }
    }

    void consumeWS() {
        mResponseTextView.setText("");
        String url = mURLEditText.getText().toString();
        mProgressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = RequestFactory.genericGETRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mProgressBar.setVisibility(View.GONE);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(response);
                mResponseTextView.setText(json);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                mResponseTextView.setText(R.string.error_generic);
            }
        });
        AppRequestQueue.getInstance(this).addToRequestQueue(request);
    }
}
