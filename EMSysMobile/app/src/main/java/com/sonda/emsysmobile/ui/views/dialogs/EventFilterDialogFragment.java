package com.sonda.emsysmobile.ui.views.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFilterDialogFragment extends DialogFragment {

    private OnEventFilterDialogListener mListener;

    private String[] mValues = {"Prioridad", "Fecha", "Zona"};
    private String mSelectedFilter;

    public EventFilterDialogFragment() {
        // Required empty public constructor
    }

    public static EventFilterDialogFragment newInstance() {
        return new EventFilterDialogFragment();
    }

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnEventFilterDialogListener) context;
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
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_event_filter_dialog, container, false);
        getDialog().setTitle("Ordenar Eventos");
        return rootView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Spinner mList = (Spinner) view.findViewById(R.id.filter_list);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, mValues);
        mList.setAdapter(adapter);
        mList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSelectedFilter = mValues[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button mCancelBtn = (Button) view.findViewById(R.id.button_cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

        Button mFilterBtn = (Button) view.findViewById(R.id.button_filter);
        mFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackResult();
            }
        });
    }

    private void dismissDialog() {
        dismiss();
    }

    // Call this method to send the data back to the parent fragment
    public final void sendBackResult() {
        mListener.onEventFilter(mSelectedFilter);
        dismissDialog();
    }

    // Defines the listener interface
    public interface OnEventFilterDialogListener {
        void onEventFilter(String inputText);
    }

}
