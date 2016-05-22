package com.app.code.utils;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class HttpAction extends HttpMethod implements Executor {

    private static HttpAction instance;
    private Handler handler;

    private HttpAction() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static HttpAction getInstance() {
        if (instance == null) {
            init();
        }
        return instance;
    }

    private static synchronized void init() {
        if (instance == null) {
            instance = new HttpAction();
        }
    }

    private String runAction(Request request) throws Exception {
        Response response = OkHttpManager.getInstance()
                .getDefaultClient()
                .newCall(request)
                .execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }

    private void runActionHandler(Request request, final StringListener stringListener) {
        OkHttpManager.getInstance()
                .getDefaultClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        //responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                        execute(new Runnable() {
                            @Override
                            public void run() {
                                stringListener.hasException(e);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        final String data = response.body().string();

                        execute(new Runnable() {
                            @Override
                            public void run() {
                                stringListener.onResult(data);
                            }
                        });
                    }
                });
    }

    private <T> void runActionHandler(Request request, final Class<T> cls, final ClassListener classListener) {
        OkHttpManager.getInstance()
                .getDefaultClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        //responseCallback.onFailure(RealCall.this, new IOException("Canceled"));
                        execute(new Runnable() {
                            @Override
                            public void run() {
                                classListener.hasException(e);
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            throw new IOException("Unexpected code " + response);
                        }
                        String value = response.body().string();
                        try {
                            final T t = FastJsonTool.get(value, cls);
                            execute(new Runnable() {
                                @Override
                                public void run() {
                                    classListener.onResult(t);
                                }
                            });
                        }catch (Exception ex){
                            throw new IOException("json Exception " + response);
                        }
                    }
                });
    }

    public String doGetString(String url) throws Exception {
        return runAction(super.doGet(url));
    }

    public void doGetStringHandler(String url, StringListener stringListener) {
        runActionHandler(super.doGet(url), stringListener);
    }

    public <T> T doGetClass(String url, Class<T> cls) throws Exception {
        String value = doGetString(url);
        return FastJsonTool.get(value, cls);
    }

    public <T> void doGetClassHandler(String url, Class<T> cls, ClassListener classListener) {
        runActionHandler(super.doGet(url), cls, classListener);
    }

    public String doGetString(String url, Map<String, String> param) throws Exception {
        return runAction(super.doGet(url, param));
    }

    public void doGetStringHandler(String url, Map<String, String> param,
                                   StringListener stringListener){
        runActionHandler(super.doGet(url, param), stringListener);
    }

    public <T> T doGetClass(String url, Map<String, String> param, Class<T> cls) throws Exception {
        String value = runAction(super.doGet(url, param));
        return FastJsonTool.get(value, cls);
    }

    public <T> void doGetClassHandler(String url, Map<String, String> param,
                                      Class<T> cls, ClassListener classListener){
        runActionHandler(super.doGet(url, param), cls, classListener);
    }

    public String doPostString(String url, Map<String, String> param) throws Exception {
        return runAction(super.doPost(url, param));
    }

    public void doPostStringHandler(String url, Map<String, String> param,
                                    StringListener stringListener){
        runActionHandler(super.doPost(url, param), stringListener);
    }

    public <T> T doPostClass(String url, Map<String, String> param, Class<T> cls) throws Exception {
        String value = doPostString(url, param);
        return FastJsonTool.get(value, cls);
    }

    public <T> void doPostClassHandler(String url, Map<String, String> param, Class<T> cls,
                                       ClassListener classListener){
        runActionHandler(super.doPost(url, param), cls, classListener);
    }

    public String doPostString(String url, Map<String, String> param,
                               Map<String, File> files) throws Exception {
        return runAction(super.doPost(url, param, files));
    }

    public void doPostStringHandler(String url, Map<String, String> param,
                                    Map<String, File> files, StringListener stringListener){
        runActionHandler(super.doPost(url, param, files), stringListener);
    }

    public <T> T doPostClass(String url, Map<String, String> param,
                             Map<String, File> files, Class<T> cls) throws Exception {
        String value = doPostString(url, param, files);
        return FastJsonTool.get(value, cls);
    }

    public <T> void doPostClassHandler(String url, Map<String, String> param,
                                       Map<String, File> files, Class<T> cls, ClassListener classListener){
        runActionHandler(super.doPost(url, param, files), cls, classListener);
    }

    @Override
    public void execute(Runnable command) {
        handler.post(command);
    }
}
