package com.sonda.emsysmobile.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.activities.ExternalServiceResultActivity;
import com.sonda.emsysmobile.utils.constants.ExternalService;

/**
 * Created by jmsmuy on 17/10/16.
 */

public class ExternalServiceQueryFragment extends Fragment {

    private static final String TAG = ExternalServiceQueryFragment.class.getName();
    private EditText mFirstParamET;
    private EditText mSecondParamET;
    private EditText mThirdParamET;

    public static ExternalServiceQueryFragment newInstance() {
        return new ExternalServiceQueryFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_external_service, container, false);

        // Obtengo los controles necesarios

        mFirstParamET = (EditText) view.findViewById(R.id.first_param_et);
        mSecondParamET = (EditText) view.findViewById(R.id.second_param_et);
        mThirdParamET = (EditText) view.findViewById(R.id.third_param_et);

        // Obtengo el botón y le incluyo el action del mismo

        Button mQueryBtn = (Button) view.findViewById(R.id.query_btn);
        mQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String param1 = mFirstParamET.getText().toString();
                String param2 = mFirstParamET.getText().toString();
                String param3 = mFirstParamET.getText().toString();

                performExternalServiceQuery(param1, param2, param3);
            }
        });

        return view;
    }

    private void performExternalServiceQuery(String param1, String param2, String param3) {
        // Se llama a la activity correspondiente con los datos necesarios.

        Intent intent = new Intent(getActivity(), ExternalServiceResultActivity.class);
        intent.putExtra(ExternalService.QUERY_KEY_PARAM_1, param1);
        intent.putExtra(ExternalService.QUERY_KEY_PARAM_2, param2);
        intent.putExtra(ExternalService.QUERY_KEY_PARAM_3, param3);
        startActivity(intent);
    }

}
