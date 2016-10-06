package com.sonda.emsysmobile.backendcommunication;

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
    void onSuccess(T object);

    /**
     * This method must be implemented inline in any Activity, Fragment or etc to handle
     * networking error callback.
     */
    @SuppressWarnings("unused")
    void onError(String errorMessage);
}
