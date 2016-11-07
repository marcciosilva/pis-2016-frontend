package com.sonda.emsysmobile.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ExternalServiceResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.ExternalServiceRequest;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;
import com.sonda.emsysmobile.ui.views.adapters.ExternalServiceRecyclerViewAdapter;
import com.sonda.emsysmobile.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jmsmuy on 17/10/16.
 */

public class ExternalServiceResultActivity extends AppCompatActivity {

    private static final String TAG = ExternalServiceResultActivity.class.getName();

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Context context;
    private ExternalServiceQueryDto query;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_service);
        this.context = this;

        mRecyclerView = (RecyclerView) findViewById(R.id.list_external_service_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Bundle intentExtras = getIntent().getExtras();

        if (!intentExtras.isEmpty()) {

            String param1 = intentExtras.getString(Constants.ExternalService.QUERY_KEY_PARAM_1, "");
            String param2 = intentExtras.getString(Constants.ExternalService.QUERY_KEY_PARAM_2, "");
            String param3 = intentExtras.getString(Constants.ExternalService.QUERY_KEY_PARAM_3, "");

            query = new ExternalServiceQueryDto(param1, param2, param3);

            executeExternalServiceQuery(query);
        }

        List<ExternalServiceItemDto> mExternalServiceItems = new ArrayList<>();
    }

    private void executeExternalServiceQuery(ExternalServiceQueryDto query) {
        // TODO
        // Esto debería ir a un presenter
        ExternalServiceRequest<ExternalServiceResponse> request =
                new ExternalServiceRequest<>(context, ExternalServiceResponse.class, query);
        request.setListener(new Response.Listener<ExternalServiceResponse>() {
            @Override
            public void onResponse(ExternalServiceResponse response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final int i = 8;
                switch (response.getCode()) {
                    case 0:
                        mRecyclerView
                                .setAdapter(new ExternalServiceRecyclerViewAdapter(context, response
                                        .getItems()));
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mProgressBar.setVisibility(View.GONE);
                        Log.d(TAG, response.toString());
                        break;
                    case i:
                        // Error WS Unavailable
                        builder.setCancelable(true);
                        builder.setNegativeButton(getString(R.string.ok_button), new
                                DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setTitle(getString(R.string.error_title));
                        builder.setMessage(getString(R.string.error_ws_unavailable));
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        });
                        builder.create().show();
                        Log.d(TAG, getString(R.string.error_ws_unavailable));
                        break;
                    default:
                        // Error Unknown
                        builder.setCancelable(true);
                        builder.setNegativeButton(getString(R.string.ok_button), new
                                DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        builder.setTitle(getString(R.string.error_title));
                        builder.setMessage(getString(R.string.error_generic));
                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                finish();
                            }
                        });
                        builder.create().show();

                        Log.e(TAG, response.toString());
                        break;
                }

            }
        });
        request.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                // Error Unknown
                builder.setCancelable(true);
                builder.setNegativeButton(getString(R.string.ok_button), new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setTitle(getString(R.string.error_title));
                builder.setMessage(getString(R.string.error_generic));
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
                builder.create().show();
                Log.e(TAG, error.toString());
            }
        });
        request.execute();
    }

}
