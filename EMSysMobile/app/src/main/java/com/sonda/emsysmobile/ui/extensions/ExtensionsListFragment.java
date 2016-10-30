package com.sonda.emsysmobile.ui.extensions;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.adapters.ExtensionRecyclerViewAdapter;

import java.util.List;

/**
 * Created by ssainz on 10/29/16.
 */
public class ExtensionsListFragment
        extends MvpLceViewStateFragment<SwipeRefreshLayout, List<ExtensionDto>, ExtensionsView, ExtensionsPresenter>
        implements ExtensionsView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ExtensionRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extensions, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_detail_list_extensions);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup contentView == SwipeRefreshView
        contentView.setOnRefreshListener(this);

        // Setup recycler view
        mAdapter = new ExtensionRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public @NonNull LceViewState<List<ExtensionDto>, ExtensionsView> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public List<ExtensionDto> getData() {
        return mAdapter == null ? null : mAdapter.getExtensions();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "";
    }

    @Override
    public @NonNull ExtensionsPresenter createPresenter() {
        EventManager eventManager =
                EventManager.getInstance(this.getActivity().getApplicationContext());
        return new ExtensionsListPresenter(eventManager);
    }

    @Override
    public void setData(List<ExtensionDto> data) {
        mAdapter.setExtensions(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadExtensions(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showError(String errorMessage, boolean pullToRefresh) {

    }
}
