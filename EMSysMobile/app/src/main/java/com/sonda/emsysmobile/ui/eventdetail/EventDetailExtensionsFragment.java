package com.sonda.emsysmobile.ui.eventdetail;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;
import com.sonda.emsysmobile.ui.interfaces.ProgressBarListener;
import com.sonda.emsysmobile.ui.views.adapters.EventDetailExtensionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EventDetailExtensionsFragment extends Fragment {

    private OnListFragmentInteractionListener mListFragmentInteractionListener;
    private ProgressBarListener mProgressBarListener;
    private List<ExtensionDto> mExtensions;
    private ProgressBar mProgressBar;
    private EventDto mEventDto;
    private static final String TAG = EventDetailExtensionsFragment.class.getName();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventDetailExtensionsFragment() {
    }

    public static EventDetailExtensionsFragment newInstance(EventDto eventDto) {
        EventDetailExtensionsFragment f = new EventDetailExtensionsFragment();
        f.mEventDto = eventDto;
        return f;
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mEventDto != null) {
            mExtensions = mEventDto.getExtensions();
        } else {
            mExtensions = new ArrayList<>();
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_detail_fragment_extensions, container, false);
        Context context = view.getContext();
        RecyclerView mRecyclerView =
                (RecyclerView) view.findViewById(R.id.event_detail_list_extensions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(new EventDetailExtensionRecyclerViewAdapter(
                EventDetailExtensionsFragment.this
                        .getActivity(), mExtensions, mListFragmentInteractionListener));
        return view;
    }

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListFragmentInteractionListener = (OnListFragmentInteractionListener) context;
            mProgressBarListener = (ProgressBarListener) context;
        } else {
            throw new UnsupportedOperationException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public final void onDetach() {
        super.onDetach();
        mListFragmentInteractionListener = null;
    }

}
