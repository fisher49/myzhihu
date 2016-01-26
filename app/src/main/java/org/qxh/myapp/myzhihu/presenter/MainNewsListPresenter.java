package org.qxh.myapp.myzhihu.presenter;

import android.content.Context;

import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.LatestNewsEntity;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;
import org.qxh.myapp.myzhihu.model.usecase.LatestNewsUsecase;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by QXH on 2016/1/26.
 */
public class MainNewsListPresenter {
    private HttpUtils httpUtils;
    private Context context;
    private LatestNewsUsecase usecase;

    public MainNewsListPresenter(Context context) {
        this.context = context;
        httpUtils = HttpUtils.getInstance();
    }

    public void downloadNewsRemote(){
        if(HttpUtils.isNetworkConnected(context)) {
            usecase.downloadLatestNewsSync(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onDownloadFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onDownloadSuccess(response.body().string());
                    } else {
                        onDownloadFailed();
                    }
                }
            });
        }else {
            onDownloadFailed();
        }
    }

    public List<String> getLoacalNewsDate(){
        return usecase.readLatestNewsDates();
    }

    public void getLocalNews(String date){
        LatestNewsEntity latestNewsEntity;
        usecase.getLocalLatestNews(date);
    }

    private void onDownloadFailed(){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_LOARD_FAIL, null));
    }

    private void onDownloadSuccess(String json){
        LatestNewsEntity latestNewsEntity = new LatestNewsEntity();
        usecase.parseLatestNewsJson(json, latestNewsEntity);
        usecase.saveLatestNewsLocal(latestNewsEntity.getDate(), json);

        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_LOARD_SUCCESS, latestNewsEntity));
    }
}
