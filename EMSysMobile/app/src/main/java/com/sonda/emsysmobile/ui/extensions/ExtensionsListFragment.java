package com.sonda.emsysmobile.ui.extensions;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;
import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.managers.EventManager;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.views.adapters.ExtensionRecyclerViewAdapter;
import com.sonda.emsysmobile.utils.UIUtils;

import java.util.List;

import static com.sonda.emsysmobile.utils.UIUtils.handleVolleyErrorResponse;

/**
 * Created by ssainz on 10/29/16.
 */
public class ExtensionsListFragment
        extends
        MvpLceViewStateFragment<SwipeRefreshLayout, List<ExtensionDto>, ExtensionsView, ExtensionsPresenter>
        implements ExtensionsView, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ExtensionRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup contentView == SwipeRefreshView
        contentView.setOnRefreshListener(this);

        // Setup recycler view
        mAdapter = new ExtensionRecyclerViewAdapter(getActivity(), mListener);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected final String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "";
    }

    @Override
    public final void onDestroyView() {
        super.onDestroyView();
        mAdapter = null;
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

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                   @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_extensions, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_detail_list_extensions);
        return view;
    }

    @Override
    @NonNull
    public final LceViewState<List<ExtensionDto>, ExtensionsView> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public final void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public final void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        if (pullToRefresh && !contentView.isRefreshing()) {
            contentView.post(new Runnable() {
                @Override
                public void run() {
                    contentView.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public final List<ExtensionDto> getData() {
        return mAdapter == null ? null : mAdapter.getExtensions();
    }

    @Override
    public final void setData(List<ExtensionDto> data) {
        mAdapter.setExtensions(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public final void loadData(boolean pullToRefresh) {
        presenter.loadExtensions(pullToRefresh);
    }

    @Override
    @NonNull
    public final ExtensionsPresenter createPresenter() {
        EventManager eventManager =
                EventManager.getInstance(this.getActivity().getApplicationContext());
        LocalBroadcastManager localBroadcastManager =
                LocalBroadcastManager.getInstance(getActivity());
        return new ExtensionsListPresenter(eventManager, localBroadcastManager);
    }

    @Override
    public final void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public final void onRefresh() {
        loadData(true);
    }

    @Override
    public final void showError(String errorMessage, int errorCode, boolean pullToRefresh) {
        contentView.setRefreshing(false);
        if (!getActivity().isFinishing()) {
            UIUtils.handleErrorMessage(getActivity(), errorCode, errorMessage);
        }
    }

    @Override
    public final void showError(VolleyError error, final boolean pullToRefresh) {
        contentView.setRefreshing(false);
        if (!getActivity().isFinishing()) {
            handleVolleyErrorResponse(getContext(), error, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    presenter.loadExtensions(pullToRefresh);
                }
            });
        }
    }

    public final void setFilter(String filter) {
        presenter.setSelectedFilter(filter);
    }
}
