package com.sonda.emsysmobile.utils;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;

/**
 * Created by maserralta on 25.10.16.
 */
public class CrossfadeWrapper implements ICrossfader {
    private Crossfader mCrossfader;

    public CrossfadeWrapper(Crossfader crossfader) {
        this.mCrossfader = crossfader;
    }

    @Override
    public final void crossfade() {
        mCrossfader.crossFade();
    }

    @Override
    public final boolean isCrossfaded() {
        return mCrossfader.isCrossFaded();
    }
}
