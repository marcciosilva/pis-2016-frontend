package com.sonda.emsysmobile.ui.views.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sonda.emsysmobile.R;
import com.sonda.emsysmobile.ui.interfaces.OnListFragmentInteractionListener;

/**
 * Created by sasainz on 11/2/16.
 */

public class AttachImageDialogFragment extends DialogFragment {


    private OnAttachImageDialogListener mListener;
    private Button mOpenGalleryBtn;
    private Button mOpenCameraBtn;
    private Button mCancelBtn;

    public interface OnAttachImageDialogListener {
        void onOpenGalleryButtonSelected();
        void onOpenCameraButtonSelected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnAttachImageDialogListener) context;
        } else {
            throw new UnsupportedOperationException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public static AttachImageDialogFragment newInstance() {
        return new AttachImageDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attach_image_dialog, container, false);
        getDialog().setTitle(R.string.attach_image_menu_title);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mOpenGalleryBtn = (Button) view.findViewById(R.id.button_open_gallery);
        mOpenGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOpenGalleryButtonSelected();
                dismissDialog();
            }
        });

        mOpenCameraBtn = (Button) view.findViewById(R.id.button_open_camera);
        mOpenCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOpenCameraButtonSelected();
                dismissDialog();
            }
        });

        mCancelBtn = (Button) view.findViewById(R.id.button_cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
            }
        });
    }

    private void dismissDialog() {
        dismiss();
    }
}
