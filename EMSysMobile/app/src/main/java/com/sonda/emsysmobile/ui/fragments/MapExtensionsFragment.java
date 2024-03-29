package com.sonda.emsysmobile.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.adapters.ExtensionRecyclerViewAdapter;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MapExtensionsFragment extends Fragment {

    private static final String EVENTS_UPDATED = "events_updated";
    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<ExtensionDto> mExtensions;
    private ProgressBar mProgressBar;
    private String mFilter = "Prioridad";
    /**
     * Receives a notification when event list is updated
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getMapEvents();
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MapExtensionsFragment() {
    }

    public static MapExtensionsFragment newInstance() {
        return new MapExtensionsFragment();
    }

    public final void setFilter(String selectedFilter) {
        mFilter = selectedFilter;
    }

    public final void getMapEvents() {
        EventManager eventManager = EventManager.getInstance(getActivity().getApplicationContext());
        eventManager.fetchExtensions(false, mFilter, true, new ApiCallback<List<ExtensionDto>>() {
            @Override
            public void onSuccess(List<ExtensionDto> extensions) {
                showSpinner(false);
                mExtensions = extensions;
                ExtensionRecyclerViewAdapter adapter =
                        (ExtensionRecyclerViewAdapter) mRecyclerView.getAdapter();
                if (adapter == null) {
                    mRecyclerView.setAdapter(new ExtensionRecyclerViewAdapter(
                            MapExtensionsFragment.this.getActivity(), mExtensions, mListener));
                } else {
                    adapter.setExtensions(mExtensions);
                }
            }

            @Override
            public void onLogicError(String errorMessage, int errorCode) {
                showSpinner(false);
                if (!getActivity().isFinishing()) {
                    UIUtils.handleErrorMessage(getContext(), errorCode, errorMessage);
                }
            }

            @Override
            public void onNetworkError(VolleyError error) {
                showSpinner(false);
                handleVolleyErrorResponse(getContext(), error,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showSpinner(true);
                                getMapEvents();
                            }
                        });
            }
        });
    }

    private void showSpinner(boolean visible) {
        if (visible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new UnsupportedOperationException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExtensions = new ArrayList<>();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extensions, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_detail_list_extensions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mProgressBar = (ProgressBar) view.findViewById(R.id.loadingView);

        showSpinner(true);
        getMapEvents();

        return view;
    }

    @Override
    public final void onResume() {
        super.onResume();

        //We wants than Broadcast Receiver be registered when the fragment is active
        LocalBroadcastManager.getInstance(this.getActivity())
                .registerReceiver(broadcastReceiverEvents, new IntentFilter(EVENTS_UPDATED));
    }

    @Override
    public final void onPause() {
        super.onPause();

        //We should unregister Broadcast Reciever when te fragment is paused
        LocalBroadcastManager.getInstance(this.getActivity())
                .unregisterReceiver(broadcastReceiverEvents);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
