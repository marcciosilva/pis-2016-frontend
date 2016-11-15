package com.sonda.emsysmobile;

import com.sonda.emsysmobile.logic.model.core.UserDto;
import com.sonda.emsysmobile.logic.model.core.offline.OfflineDto;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by marccio on 04-Nov-16.
 */

public class GlobalVariables {

    private static UserDto userData = null;

    private static BlockingQueue<OfflineDto> queue = new LinkedBlockingQueue<>();

    public static void setQueue(
            BlockingQueue<OfflineDto> queue) {
        GlobalVariables.queue = queue;
    }

    public static UserDto getUserData() {
        return userData;
    }

    public static void setUserData(UserDto userData) {
        GlobalVariables.userData = userData;
    }

    public static BlockingQueue<OfflineDto> getQueue() {
        return queue;
    }

    private GlobalVariables() {
        // Debe ser privado porque no debe ser utilizado.
    }

}
