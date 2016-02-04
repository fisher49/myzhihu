package org.qxh.myapp.myzhihu.presenter;

import android.content.Context;

import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.NewsContentEntity;
import org.qxh.myapp.myzhihu.model.usecase.NewsContentUsecase;
import org.qxh.myapp.myzhihu.utils.Utility;

import java.io.IOException;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by QXH on 2016/2/2.
 */
public class WebViewPresenter {

    private Context context;
    private NewsContentUsecase usecase;
    private int id;

    public WebViewPresenter(Context context, int id) {
        this.context = context;
        this.id = id;
        usecase = new NewsContentUsecase(context);
    }

    public void initNewsContext() {
        if(Utility.isNetworkConnected(context)) {
            downloadNewsRemote();
        }else {
            NewsContentEntity entity = usecase.getLocalNewsContent(id);
            if(entity != null) {
                postEventLoadContentSuccess(entity);
            }else {
                postEventLoadContentFail();
            }
        }
    }

    private void downloadNewsRemote() {
        usecase.requestRemoteNewsContentSync(id, new okhttp3.Callback() {
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
    }

    private void onDownloadSuccess(String json){
        json = json.replaceAll("'", "''");

        NewsContentEntity entity = usecase.parseNewsContentJson(json);
        if(entity != null) {
            usecase.saveNewsContentLocal(entity.getId(), json);
            postEventLoadContentSuccess(entity);
        }else {
            postEventLoadContentFail();
        }
    }

    private void onDownloadFailed(){
        postEventLoadContentFail();
    }

    private void postEventLoadContentFail(){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_CONTENT_LOAD_FAIL, null));
    }

    private void postEventLoadContentSuccess(NewsContentEntity entity){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_CONTENT_LOAD_SUCCESS, entity));
    }

//    public void postEventUpdateNewsList(){
//        EventBus.getDefault().post(new EventBody(Constant.EVENT_WEB_VIEW_UPDATE_READ_STATUS, null));
//    }
}
