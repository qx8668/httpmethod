package com.app.code.utils;

import okhttp3.OkHttpClient;

/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class OkHttpManager {

    private static OkHttpManager instance = null;

    private OkHttpClient client = null;

    private OkHttpManager() {
        client = new OkHttpClient();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    private static synchronized void init() {
        if (instance == null) {
            instance = new OkHttpManager();
        }
    }

    public OkHttpClient getDefaultClient() {
        return client;
    }

    public OkHttpClient getNewClient(){
        return new OkHttpClient.Builder().build();
    }
}
