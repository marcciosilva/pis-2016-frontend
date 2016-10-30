package com.sonda.emsysmobile.ui.extensions;

import android.content.DialogInterface;
import android.util.Log;

import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.ui.views.adapters.ExtensionRecyclerViewAdapter;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by ssainz on 10/29/16.
 */
public class ExtensionsListPresenter extends MvpBasePresenter<ExtensionsView>
        implements ExtensionsPresenter {

    private static final String TAG = "ExtensionsListPresenter";

    private EventManager mEventManager;

    ExtensionsListPresenter(EventManager eventManager){
        mEventManager = eventManager;
    }

    @Override
    public void loadExtensions(final boolean pullToRefresh) {

        if (getView() != null) {
            getView().showLoading(pullToRefresh);
        }

        mEventManager.fetchExtensions(new ApiCallback<List<ExtensionDto>>() {
            @Override
            public void onSuccess(List<ExtensionDto> extensions) {
                if (isViewAttached()) {
                    getView().setData(extensions);
                    getView().showContent();
                }
            }

            @Override
            public void onLogicError(String errorMessage, int errorCode) {
                if (isViewAttached()) {
                    getView().showError("", pullToRefresh);
                }
            }

            @Override
            public void onNetworkError(VolleyError error) {
                if (isViewAttached()) {
                    getView().showError("", pullToRefresh);
                }
            }
        });
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        Log.d(TAG, "---- detachView(" + retainInstance + ") ----");
    }

    @Override public void attachView(ExtensionsView view) {
        super.attachView(view);
        Log.d(TAG, "attach view " + view.toString());
    }
}
