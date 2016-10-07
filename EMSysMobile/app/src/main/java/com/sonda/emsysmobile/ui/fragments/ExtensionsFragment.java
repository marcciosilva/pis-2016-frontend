package com.sonda.emsysmobile.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sonda.emsysmobile.events.managers.EventManager;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.backendcommunication.ApiCallback;
import com.sonda.emsysmobile.ui.views.adapters.ExtensionRecyclerViewAdapter;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

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
        eventManager.fetchEvents(new ApiCallback<List<ExtensionDto>>() {
            @Override
            public void onSuccess(List<ExtensionDto> extensions) {
                mProgressBar.setVisibility(View.GONE);
                mExtensions = extensions;
                mRecyclerView.setAdapter(new ExtensionRecyclerViewAdapter(ExtensionsFragment.this.getActivity(), mExtensions, mListener));
            }

            @Override
            public void onError(String errorMessage, int errorCode) {
                mProgressBar.setVisibility(View.GONE);
                UIUtils.handleErrorMessage(getContext(), errorCode, errorMessage);
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
}
