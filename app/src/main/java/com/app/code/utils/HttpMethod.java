package com.app.code.utils;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class HttpMethod {

    public Request doGet(String url){
        return new Request.Builder().url(url).build();
    }

    public Request doGet(String url, Map<String, String> param){
        //HttpUrl.canonicalize(value, FORM_ENCODE_SET, true, false, true, true);
        String theUrl = N.getAddParamToUrl(url, param);
        return doGet(theUrl);
    }

    public Request doPost(String url, Map<String, String> param){
        FormBody.Builder builder = new FormBody.Builder();
        if (param != null && param.size() > 0) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                //builder.add(entry.getKey(), entry.getValue());//or
                builder.addEncoded(entry.getKey(), entry.getValue());
            }
        }
        RequestBody formBody = builder.build();
        return new Request.Builder().url(url).post(formBody).build();
    }

    public Request doPost(String url, Map<String, String> param, Map<String, File> files){
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (param != null && param.size() > 0) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        if (files != null && files.size() > 0) {
            for (Map.Entry<String, File> entry : files.entrySet()) {
                builder.addFormDataPart(entry.getKey(),
                        entry.getValue().getName(),
                        RequestBody.create(MediaType.parse("image/png"), entry.getValue()));
            }
        }
        MultipartBody multipartBody = builder.build();
        return new Request.Builder().url(url).post(multipartBody).build();
    }

}
