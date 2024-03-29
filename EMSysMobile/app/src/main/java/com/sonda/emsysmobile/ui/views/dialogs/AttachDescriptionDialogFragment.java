package com.sonda.emsysmobile.ui.views.dialogs;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachDescriptionDialogFragment extends DialogFragment {

    private OnAttachDescriptionDialogListener mListener;
    private EditText mDescriptionEditText;

    public AttachDescriptionDialogFragment() {
        // Required empty public constructor
    }

    public static AttachDescriptionDialogFragment newInstance() {
        return new AttachDescriptionDialogFragment();
    }

    @Override
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnAttachDescriptionDialogListener) context;
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
                inflater.inflate(R.layout.fragment_attach_description_dialog, container, false);
        getDialog().setTitle(R.string.action_update_desc);
        return rootView;
    }

    @Override
    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDescriptionEditText = (EditText) view.findViewById(R.id.input_description);
        mDescriptionEditText.requestFocus();

        Button mCancelBtn = (Button) view.findViewById(R.id.button_cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

        Button mUpdateBtn = (Button) view.findViewById(R.id.button_update);
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackResult();
            }
        });

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void dismissDialog() {
        dismiss();
    }

    // Call this method to send the data back to the parent fragment
    public final void sendBackResult() {
        mListener.onAttachDescription(mDescriptionEditText.getText().toString());
        dismissDialog();
    }

    // Defines the listener interface
    public interface OnAttachDescriptionDialogListener {
        void onAttachDescription(String inputText);
    }

}
