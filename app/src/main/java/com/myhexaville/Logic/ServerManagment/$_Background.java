package com.myhexaville.Logic.ServerManagment;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 
 */
public abstract class $_Background implements Runnable {

    /**
     * Default constructor
     */
    public $_Background() {
    }

    /**
     * 
     */
    public ExecutorService executorService;
    public Future<?> future;

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * @return
     */
    public void excute() {
        // TODO implement here
    }

}