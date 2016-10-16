package com.sonda.emsysmobile.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.backendcommunication.model.responses.ExternalServiceResponse;
import com.sonda.emsysmobile.backendcommunication.services.request.ExternalServiceRequest;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceItemDto;
import com.sonda.emsysmobile.logic.model.core.ExternalServiceQueryDto;
import com.sonda.emsysmobile.ui.views.adapters.ExternalServiceRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ExternalServiceFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private List<ExternalServiceItemDto> mExternalServiceItems;
    private ProgressBar mProgressBar;
    private static final String TAG = ExternalServiceFragment.class.getName();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ExternalServiceFragment() {
    }

    public static ExternalServiceFragment newInstance() {
        return new ExternalServiceFragment();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExternalServiceItems = new ArrayList<>();
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_external_service_result, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_external_service_items);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        ExternalServiceQueryDto query = new ExternalServiceQueryDto();
        query.setParameter1("hola");
        query.setParameter2("jua");
        query.setParameter3("pufffff");

        executeExternalServiceQuery(query);

        return view;
    }

    private void executeExternalServiceQuery(ExternalServiceQueryDto query) {
        // TODO
        // Esto debería ir a un presenter
        ExternalServiceRequest<ExternalServiceResponse> request = new ExternalServiceRequest<>(getActivity(), ExternalServiceResponse.class, query);
        request.setListener(new Response.Listener<ExternalServiceResponse>() {
            @Override
            public void onResponse(ExternalServiceResponse response) {
                mRecyclerView.setAdapter(new ExternalServiceRecyclerViewAdapter(getActivity(), response.getItems(), mListener));
                Log.d("tag", response.toString());
            }
        });
        request.execute();
    }

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(ExternalServiceItemDto item);
    }

}
