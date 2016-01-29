package org.qxh.myapp.myzhihu.model.net;

import org.qxh.myapp.myzhihu.config.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by QXH on 2016/1/21.
 */
public class HttpUtils {
    private OkHttpClient okHttpClient;
    private static HttpUtils httpUtils;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private HttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
//        okHttpClient = new OkHttpClient();
    }

    public static HttpUtils getInstance(){
        httpUtils = new HttpUtils();
        return httpUtils;
    }

    public String get(String url) throws Exception{
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+url)
                .build();
        Response response = okHttpClient.newCall(request).execute();

        return response.body().toString();
    }

    public String post(String url, String json) throws Exception{
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+url)
                .post(requestBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.toString();
    }

    public void getDefaultUrlAsyn(String url, okhttp3.Callback callback) throws Exception{
        getAsyn(Constant.BASE_URL+url, callback);
    }

    public void getAsyn(String url, okhttp3.Callback callback) throws Exception{
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void postAsyn(String url, String json, okhttp3.Callback callback) throws Exception{
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(Constant.BASE_URL+url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }
}
