package com.sonda.emsysmobile.network;

/**
 * Created by ssainz on 10/1/16.
 */
public interface ApiCallback<T> {
    /**
     * This method must be implemented inline in any Activity, Fragment or etc to handle
     * networking success callback
     * @param object
     */
    @SuppressWarnings("unused")
    public void onSuccess(T object);

    /**
     * This method must be implemented inline in any Activity, Fragment or etc to handle
     * networking error callback.
     */
    @SuppressWarnings("unused")
    public void onError(String errorMessage);
}
