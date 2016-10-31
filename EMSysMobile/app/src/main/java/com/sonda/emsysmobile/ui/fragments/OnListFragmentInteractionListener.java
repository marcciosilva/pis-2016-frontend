package com.sonda.emsysmobile.ui.fragments;

/**
 * Created by marccio on 10/20/16.
 */

import com.sonda.emsysmobile.logic.model.core.ExtensionDto;

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
    void onListFragmentInteraction(ExtensionDto event);
}