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
import com.sonda.emsysmobile.ui.fragments.OnListFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttachDescriptionDialogFragment extends DialogFragment {

    private OnAttachDescriptionDialogListener mListener;
    private EditText mDescriptionEditText;
    private Button mCancelBtn;
    private Button mUpdateBtn;

    // Defines the listener interface
    public interface OnAttachDescriptionDialogListener {
        void onAttachDescription(String inputText);
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

    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        mListener.onAttachDescription(mDescriptionEditText.getText().toString());
        dismissDialog();
    }

    public AttachDescriptionDialogFragment() {
        // Required empty public constructor
    }

    public static AttachDescriptionDialogFragment newInstance() {
        return new AttachDescriptionDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView =
                inflater.inflate(R.layout.fragment_attach_description_dialog, container, false);
        getDialog().setTitle(R.string.action_update_desc);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDescriptionEditText = (EditText) view.findViewById(R.id.input_description);
        mDescriptionEditText.requestFocus();

        mCancelBtn = (Button) view.findViewById(R.id.button_cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });

        mUpdateBtn = (Button) view.findViewById(R.id.button_update);
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
}
