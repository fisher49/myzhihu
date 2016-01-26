package org.qxh.myapp.myzhihu.model.usecase;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.db.CacheDbHelper;
import org.qxh.myapp.myzhihu.model.db.CacheDbOperator;
import org.qxh.myapp.myzhihu.model.entities.LatestNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.StoriesEntity;
import org.qxh.myapp.myzhihu.model.entities.TopStoriesEntity;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/26.
 */
public class LatestNewsUsecase {
    private Context context;
    private CacheDbHelper helper;
    private CacheDbOperator dbOperator;

    public LatestNewsUsecase(Context context) {
        this.context = context;
        helper = new CacheDbHelper(context);
        dbOperator = new CacheDbOperator(helper.getWritableDatabase());
    }

    public boolean parseLatestNewsJson(String json, LatestNewsEntity latestNewsEntity){
        try {
            if(json.length() > 0) {
                JSONObject jsonObject = new JSONObject(json);
                String date = jsonObject.getString(Constant.JSON_TAG_DATE);

                JSONArray jsonArray = jsonObject.getJSONArray(Constant.JSON_TAG_STORIES);
                parseStories(jsonArray, latestNewsEntity.getStories());

                JSONArray jsonArrayTop = jsonObject.getJSONArray(Constant.JSON_TAG_TOP_STORIES);
                parseTopStories(jsonArrayTop, latestNewsEntity.getTop_stories());

                return true;
            }else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void downloadLatestNewsSync(okhttp3.Callback callback){
        try {
            HttpUtils httpUtils = HttpUtils.getInstance();

            httpUtils.getDefaultUrlAsyn(Constant.LATEST_NEWS_URL, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LatestNewsEntity getLocalLatestNews(String date){
        String json = dbOperator.findNewsCache(date);
        LatestNewsEntity news = new LatestNewsEntity();
        if(parseLatestNewsJson(json, news)){
            return news;
        }else {
            return null;
        }
    }

    public List<String> readLatestNewsDates(){
        return dbOperator.readAllDateRecordNewsCache();
    }

    public void saveLatestNewsLocal(String date, String json){
        dbOperator.insertNewsCache(date, json);
    }

    private void parseStories(JSONArray jsonArray, List<StoriesEntity> stories){
        try {
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonStory = jsonArray.getJSONObject(i);

                JSONArray jsonArrayImages = jsonStory.getJSONArray(Constant.JSON_TAG_IMAGES);
                List<String> listImages = new ArrayList<String>();
                for (int j=0; j<jsonArrayImages.length(); j++){
                    listImages.add(jsonArrayImages.getString(i));
                }

                stories.add(new StoriesEntity(jsonStory.getInt(Constant.JSON_TAG_TYPE),
                        jsonStory.getInt(Constant.JSON_TAG_ID),
                        jsonStory.getString(Constant.JSON_TAG_GA_PREFIX),
                        jsonStory.getString(Constant.JSON_TAG_TITLE), listImages));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseTopStories(JSONArray jsonArray, List<TopStoriesEntity> stories){
        try {
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonStory = jsonArray.getJSONObject(i);

                stories.add(new TopStoriesEntity(jsonStory.getString(Constant.JSON_TAG_IMAGE),
                        jsonStory.getInt(Constant.JSON_TAG_TYPE),
                        jsonStory.getInt(Constant.JSON_TAG_ID),
                        jsonStory.getString(Constant.JSON_TAG_GA_PREFIX),
                        jsonStory.getString(Constant.JSON_TAG_TITLE)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
