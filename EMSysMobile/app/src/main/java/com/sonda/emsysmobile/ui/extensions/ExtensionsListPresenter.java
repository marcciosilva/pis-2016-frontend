package com.sonda.emsysmobile.ui.extensions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
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
    private static final String EVENTS_UPDATED = "events_updated";

    private EventManager mEventManager;
    private LocalBroadcastManager mLocalBroadcastManager;
    private String mFilter = "Prioridad";

    ExtensionsListPresenter(EventManager eventManager, LocalBroadcastManager localBroadcastManager){
        mEventManager = eventManager;
        mLocalBroadcastManager = localBroadcastManager;
    }

    @Override
    public void loadExtensions(final boolean pullToRefresh) {

        if (getView() != null) {
            getView().showLoading(pullToRefresh);
        }

        updateExtensions(pullToRefresh);
    }

    @Override
    public void setSelectedFilter(String filter) {
        mFilter = filter;
    }

    private void updateExtensions(final boolean fromServer) {

        if (getView() == null) {
            return;
        }

        mEventManager.fetchExtensions(fromServer, mFilter, false, new ApiCallback<List<ExtensionDto>>() {
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
                    getView().showError(errorMessage, errorCode, fromServer);
                }
            }

            @Override
            public void onNetworkError(VolleyError error) {
                if (isViewAttached()) {
                    getView().showError(error, fromServer);
                }
            }
        });
    }

    public void setFilter(String selectedFilter){
        mFilter = selectedFilter;
    }

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        //We should unregister Broadcast Reciever when te fragment is paused
        mLocalBroadcastManager.unregisterReceiver(broadcastReceiverEvents);
        Log.d(TAG, "---- detachView(" + retainInstance + ") ----");
    }

    @Override public void attachView(ExtensionsView view) {
        super.attachView(view);
        //We wants than Broadcast Receiver be registered when the view is attached
        mLocalBroadcastManager
                .registerReceiver(broadcastReceiverEvents, new IntentFilter(EVENTS_UPDATED));
        Log.d(TAG, "attach view " + view.toString());
    }

    /**
     * Receives a notification when event list is updated
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateExtensions(false);
        }
    };
}