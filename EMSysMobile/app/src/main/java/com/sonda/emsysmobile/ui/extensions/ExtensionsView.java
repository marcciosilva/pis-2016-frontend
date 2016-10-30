package com.sonda.emsysmobile.ui.extensions;

import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;

import java.util.List;

/**
 * Created by ssainz on 10/29/16.
 */
public interface ExtensionsView extends MvpLceView<List<ExtensionDto>> {

    /**
     * ExtensionsView show error view with @param errorMessage text
     * @param errorMessage Message to be shown in errorView
     * @param pullToRefresh Boolean indicating if error was caused during pullToRefresh
     */
    public void showError(String errorMessage, int errorCode, boolean pullToRefresh);

    public void showError(VolleyError error, boolean pullToRefresh);
}
