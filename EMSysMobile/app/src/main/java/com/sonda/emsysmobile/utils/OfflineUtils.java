package com.sonda.emsysmobile.utils;

import com.sonda.emsysmobile.logic.model.core.offline.OfflineAttachDescriptionDto;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jmsmuy on 04/11/16.
 */

public class OfflineUtils {

    private static BlockingQueue<OfflineAttachDescriptionDto> queue = new LinkedBlockingQueue<OfflineAttachDescriptionDto>();

    public static BlockingQueue<OfflineAttachDescriptionDto> getQueue() {
        return queue;
    }

}
