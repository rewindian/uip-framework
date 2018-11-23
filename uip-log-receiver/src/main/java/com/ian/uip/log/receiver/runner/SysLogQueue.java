package com.ian.uip.log.receiver.runner;

import com.ian.uip.core.model.SysLog;

import java.util.concurrent.ArrayBlockingQueue;

public final class SysLogQueue {

    private static final class SingletonHolder {
        private static final ArrayBlockingQueue<SysLog> INSTANCE = new ArrayBlockingQueue<>(1000, false);
    }


    private SysLogQueue() {
    }

    public static ArrayBlockingQueue<SysLog> getMyQueue() {
        return SingletonHolder.INSTANCE;
    }
}
