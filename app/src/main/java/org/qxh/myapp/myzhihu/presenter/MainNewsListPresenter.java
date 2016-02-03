package org.qxh.myapp.myzhihu.presenter;

import android.content.Context;

import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.BeforeNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.LatestNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.StoriesEntity;
import org.qxh.myapp.myzhihu.model.entities.ThemeContentEntity;
import org.qxh.myapp.myzhihu.model.entities.TopStoriesEntity;
import org.qxh.myapp.myzhihu.model.usecase.LatestNewsUsecase;
import org.qxh.myapp.myzhihu.model.usecase.ThemeContentUsecase;
import org.qxh.myapp.myzhihu.utils.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by QXH on 2016/1/26.
 */
public class MainNewsListPresenter {
//    private HttpUtils httpUtils;
    private Context context;
    private LatestNewsUsecase usecase;
    private ThemeContentUsecase themeContentUsecase;
    private int currentId = -1;
    private String tag = Constant.TAG_MAIN_LIST_FRAGMENT_LATEST;

    public MainNewsListPresenter(Context context, String tag) {
        this.context = context;
        this.tag = tag;
//        httpUtils = HttpUtils.getInstance();
        usecase = new LatestNewsUsecase(context);
        themeContentUsecase = new ThemeContentUsecase(context);
    }

    public void downloadNewsRemote(){
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
    }

    public void downloadBeforeNewsRemote(String date){
        if(Utility.isNetworkConnected(context)) {
            usecase.downloadBeforeNewsSync(date, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    onDownloadBeforeFailed();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onDownloadBeforeSuccess(response.body().string());
                    } else {
                        onDownloadBeforeFailed();
                    }
                }
            });
        }else {
            onDownloadBeforeFailed();
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

    /**
     * 获取特定日期前一日的消息纪录
     * @param date 指定日期，查询的是该日期前一日的记录
     * @return 往日记录
     */
    public BeforeNewsEntity getLocalBeforeNews(String date){
        List<String> dates = getLoacalNewsDate();
        if(dates.size() > 0) {
            int pos = dates.indexOf(date);
            if ((pos >= 0) && (dates.size() > pos+1)) {
                return usecase.getLocalBeforeNews(dates.get(pos+1));
            }
        }

        return null;
    }

    public LatestNewsEntity getLocalNews(String date){
//        LatestNewsEntity latestNewsEntity;
        return usecase.getLocalLatestNews(date);
    }

    private List<String> getLoacalNewsDate(){
        return usecase.readLatestNewsDates();
    }

    private void onDownloadBeforeFailed(){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_BEFORE_LOARD_FAIL, null));
    }

    private void onDownloadBeforeSuccess(String json){
        BeforeNewsEntity beforeNewsEntity = new BeforeNewsEntity();
        usecase.parseBeforeNewsJson(json, beforeNewsEntity);
        usecase.saveLatestNewsLocal(beforeNewsEntity.getDate(), json);

        EventBus.getDefault().post(new EventBody(Constant.EVENT_NEWS_BEFORE_LOARD_SUCCESS, beforeNewsEntity));
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

    public void enableSwipeRefresh(boolean enable) {
        EventBus.getDefault().post(new EventBody(Constant.EVENT_MAIN_NEWS_SWITCH_SWIPEREFRESH, enable));
    }

    /**
     * 获取指定主题下的内容
     * @param id 主题id
     * @return
     */
    public void getThemeContent(int id) {
        currentId = id;
        if(Utility.isNetworkConnected(context)){
            themeContentUsecase.requestRemoteThemesContentSync(id, new okhttp3.Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    EventBus.getDefault().post(new EventBody(Constant.EVENT_MAIN_NEWS_UPDATE_THEME_FAIL, null));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        onDownloadThemeSuccess(currentId, response.body().string());
                    } else {
                        EventBus.getDefault().post(new EventBody(Constant.EVENT_MAIN_NEWS_UPDATE_THEME_FAIL, null));
                    }
                }
            });

        }else {
            ThemeContentEntity entity = themeContentUsecase.getLocalThemeContent(id);
            postUpdateThemeEvent(entity);
        }
    }

    private void postUpdateThemeEvent(ThemeContentEntity entity){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_MAIN_NEWS_UPDATE_THEME_SUCCESS, formatThemeContentToNews(entity)));
    }

    private void onDownloadThemeSuccess(int id, String json){
        ThemeContentEntity entity = themeContentUsecase.parseThemeContentJson(json);
        themeContentUsecase.saveThemesContentLocal(id, json);

        postUpdateThemeEvent(entity);
    }

    public void initListContext() {
        if(tag.equals(Constant.TAG_MAIN_LIST_FRAGMENT_LATEST)){
            if(Utility.isNetworkConnected(context)) {
                downloadNewsRemote();
            }else {
                LatestNewsEntity entity = getLocalNews(0);
                if(entity != null) {
                    EventBus.getDefault().post(new EventBody(Constant.EVENT_MAIN_NEWS_UPDATE_THEME_SUCCESS, entity));
                }
            }
        }else{
            getThemeContent(Integer.valueOf(tag));
        }
    }

    private LatestNewsEntity formatThemeContentToNews(ThemeContentEntity entity){
        LatestNewsEntity news = new LatestNewsEntity();
        List<StoriesEntity> stories = formatThemesStoriesToNews(entity.getStories());

        List<TopStoriesEntity> topStories = new ArrayList<>();
        TopStoriesEntity topStory = new TopStoriesEntity(entity.getBackground(), 0, 0, "", entity.getDescription());
        topStories.add(topStory);

        news.setDate("");
        news.setStories(stories);
        news.setTop_stories(topStories);
        return news;
    }

    private List<StoriesEntity> formatThemesStoriesToNews(List<ThemeContentEntity.StoriesEntity> themesStories){
        List<StoriesEntity> stories = new ArrayList<>();
        for(ThemeContentEntity.StoriesEntity entity : themesStories){
            StoriesEntity story = new StoriesEntity(entity.getType(), entity.getId(), "", entity.getTitle(), entity.getImages());
            stories.add(story);
        }
        return stories;
    }

    /**
     * 更新主页图片轮播控件
     * @param topStoriesList 头条新闻信息
     */
//    public void updateSlidingPage(List<TopStoriesEntity> topStoriesList) {
//        EventBus.getDefault().post(new EventBody(Constant.EVENT_SLIDING_PAGE_UPDATE, topStoriesList));
//    }
}
