package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.adapters.EventDetailExtensionRecyclerViewAdapter;
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
public class EventDetailExtensionsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<ExtensionDto> mExtensions;
    private ProgressBar mProgressBar;
    private static final String TAG = EventDetailExtensionsFragment.class.getName();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventDetailExtensionsFragment() {
    }

    public static EventDetailExtensionsFragment newInstance() {
        return new EventDetailExtensionsFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExtensions = new ArrayList<>();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_detail_fragment_extensions, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_detail_list_extensions);
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
                mRecyclerView.setAdapter(new EventDetailExtensionRecyclerViewAdapter(
                        EventDetailExtensionsFragment.this.getActivity(), mExtensions, mListener));
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

}
