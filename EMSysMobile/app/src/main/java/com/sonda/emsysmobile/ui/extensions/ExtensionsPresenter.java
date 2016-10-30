package com.sonda.emsysmobile.ui.extensions;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by ssainz on 10/29/16.
 */
public interface ExtensionsPresenter extends MvpPresenter<ExtensionsView> {

    public void loadExtensions(final boolean pullToRefresh);
}
