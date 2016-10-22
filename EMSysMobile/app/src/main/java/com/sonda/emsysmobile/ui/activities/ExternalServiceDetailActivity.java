package com.sonda.emsysmobile.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;
import com.sonda.emsysmobile.utils.Constants;

/**
 * Created by jmsmuy on 17/10/16.
 */

public class ExternalServiceDetailActivity extends AppCompatActivity {

    private static final String TAG = ExternalServiceDetailActivity.class.getName();

    private Context context;
    private EditText mFirstET;
    private EditText mSecondET;
    private EditText mThirdET;
    private EditText mFourthET;
    private EditText mFifthET;
    private EditText mSixthET;
    private EditText mSeventhET;
    private EditText mEighthET;
    private EditText mNinthET;
    private EditText mTenthET;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_service_detail);
        this.context = this;

        mFirstET = (EditText) findViewById(R.id.first_field_et);
        mSecondET = (EditText) findViewById(R.id.second_field_et);
        mThirdET = (EditText) findViewById(R.id.third_field_et);
        mFourthET = (EditText) findViewById(R.id.fourth_field_et);
        mFifthET = (EditText) findViewById(R.id.fifth_field_et);
        mSixthET = (EditText) findViewById(R.id.sixth_field_et);
        mSeventhET = (EditText) findViewById(R.id.seventh_field_et);
        mEighthET = (EditText) findViewById(R.id.eighth_field_et);
        mNinthET = (EditText) findViewById(R.id.ninth_field_et);
        mTenthET = (EditText) findViewById(R.id.tenth_field_et);

        ExternalServiceItemDto dto = (ExternalServiceItemDto) getIntent()
                .getSerializableExtra(Constants.ExternalService.DETAIL_KEY_PARAM);

        if (dto == null) {
            // si no llegó correctamente el item volvemos
            finish();
        }

        loadDetails(dto);

    }

    private void loadDetails(ExternalServiceItemDto dto) {
        mFirstET.setText(dto.getField1());
        mSecondET.setText(dto.getField2());
        mThirdET.setText(dto.getField3());
        mFourthET.setText(dto.getField4());
        mFifthET.setText(dto.getField5());
        mSixthET.setText(dto.getField6());
        mSeventhET.setText(dto.getField7());
        mEighthET.setText(dto.getField8());
        mNinthET.setText(dto.getField9());
        mTenthET.setText(dto.getField10());
    }

}
