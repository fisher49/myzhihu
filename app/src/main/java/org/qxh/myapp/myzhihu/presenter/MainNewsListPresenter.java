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
        usecase = new LatestNewsUsecase(context);
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

    public LatestNewsEntity getLocalNews(int index){
        List<String> dates = getLoacalNewsDate();
        if((dates != null) && (dates.size() > index)){
            return usecase.getLocalLatestNews(dates.get(index));
        }else {
            return null;
        }
    }

    public LatestNewsEntity getLocalNews(String date){
        LatestNewsEntity latestNewsEntity;
        return usecase.getLocalLatestNews(date);
    }

    private List<String> getLoacalNewsDate(){
        return usecase.readLatestNewsDates();
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

    /**
     * 更新主页图片轮播控件
     * @param topStoriesList 头条新闻信息
     */
//    public void updateSlidingPage(List<TopStoriesEntity> topStoriesList) {
//        EventBus.getDefault().post(new EventBody(Constant.EVENT_SLIDING_PAGE_UPDATE, topStoriesList));
//    }
}
