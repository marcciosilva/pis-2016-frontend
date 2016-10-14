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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.notifications.Notification;
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
public class ExtensionsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<ExtensionDto> mExtensions;
    private ProgressBar mProgressBar;
    private static final String TAG = ExtensionsFragment.class.getName();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExtensionsFragment() {
    }

    public static ExtensionsFragment newInstance() {
        return new ExtensionsFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExtensions = new ArrayList<>();

        LocalBroadcastManager.getInstance(this.getActivity())
                .registerReceiver(broadcastReceiverEvents, new IntentFilter("update-events"));
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extensions, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_extensions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        getEvents();

        return view;
    }

    private void getEvents() {
        mProgressBar.setVisibility(View.VISIBLE);
        EventManager eventManager = EventManager.getInstance(getActivity().getApplicationContext());
        eventManager.fetchExtensions(new ApiCallback<List<ExtensionDto>>() {
            @Override
            public void onSuccess(List<ExtensionDto> extensions) {
                mProgressBar.setVisibility(View.GONE);
                mExtensions = extensions;
                mRecyclerView.setAdapter(new ExtensionRecyclerViewAdapter(ExtensionsFragment.this.getActivity(), mExtensions, mListener));
            }

            @Override
            public void onLogicError(String errorMessage, int errorCode) {
                mProgressBar.setVisibility(View.GONE);
                UIUtils.handleErrorMessage(getContext(), errorCode, errorMessage);
            }

            @Override
            public void onNetworkError(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                handleVolleyErrorResponse(getContext(), error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getEvents();
                    }
                });
            }
        });
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
    public final void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ExtensionDto item);
    }

    /**
     * Receives notifications
     */
    private BroadcastReceiver broadcastReceiverEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Notification notification = (Notification) intent.getExtras().get("notification");
                if (notification != null) {
                    Log.i(TAG, "Recibiendo notificación con código: " + notification.getCode());
                    getEvents();
                }
            }
        }
    };
}
