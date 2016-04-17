package com.peyto.aiengine.core;

public class AIEngine {

    private static volatile AIEngine instance;

    private GlobalStrategy globalStrategy;

    private AIEngine() {
        globalStrategy = new GlobalStrategy();
    }

    public static AIEngine getInstance() {
        if (instance!=null) return instance;
        else {
            synchronized (AIEngine.class) {
                if (instance==null) {
                    instance = new AIEngine();
                }
                return instance;
            }
        }
    }

    public void run() {

    }
}
