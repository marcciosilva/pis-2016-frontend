package com.sonda.emsysmobile.backendcommunication.model.responses;

import android.content.Context;

import com.sonda.emsysmobile.R;

/**
 * Created by marccio on 06-Oct-16.
 */

public enum ErrorCodeCategory {
    NETWORK_ERROR(-3, R.string.error_http),
    NO_AVAILABLE_MULTIMEDIA(-2, R.string.error_no_available_multimedia),
    LOGIC_ERROR(-1, R.string.error_internal), SUCCESS(0, R.string.success_msg),
    INVALID_CREDENTIALS(1, R.string.error_invalid_credentials), NO_AUTH(2, R.string.error_no_auth),
    RESOURCE_NOT_AVAILABLE(4, R.string.error_resource_unavailable),
    NOT_ALLOWED(15, R.string.error_no_permission),
    TIME_ALREADY_REPORTED(20, R.string.error_already_reported_hour);

    private int numVal;
    private int stringResourceId;

    ErrorCodeCategory(int numVal, int stringResourceId) {
        this.numVal = numVal;
        this.stringResourceId = stringResourceId;
    }

    public int getNumVal() {
        return numVal;
    }

    public String getMsg(Context context) {
        return context.getString(stringResourceId);
    }


}
