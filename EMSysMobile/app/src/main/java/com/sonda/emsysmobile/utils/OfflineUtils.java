package com.sonda.emsysmobile.utils;

import com.sonda.emsysmobile.logic.model.core.OfflineRequestDto;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by jmsmuy on 04/11/16.
 */

public class OfflineUtils {

    private static BlockingQueue<OfflineRequestDto> queue = new LinkedBlockingQueue<OfflineRequestDto>();

    public static BlockingQueue<OfflineRequestDto> getQueue() {
        return queue;
    }

}
