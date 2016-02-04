package org.qxh.myapp.myzhihu.model.usecase;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.db.CacheOperator;
import org.qxh.myapp.myzhihu.model.entities.NewsContentEntity;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;

/**
 * Created by QXH on 2016/2/2.
 */
public class NewsContentUsecase {
    Context context;
    private CacheOperator dbOperator;
//    String contentUrl = Constant.BASE_URL+Constant.NEWS_CONTENT_URL;

    public NewsContentUsecase(Context context) {
        this.context = context;
        dbOperator = new CacheOperator();
    }

    public NewsContentEntity parseNewsContentJson(String json){
        try {
            if(json.length() > 0) {
                JSONObject jsonObject = new JSONObject(json);

                NewsContentEntity content = new NewsContentEntity(jsonObject.getString(Constant.JSON_TAG_BODY),
                        (jsonObject.has(Constant.JSON_TAG_IMAGE_SOURCE) ? jsonObject.getString(Constant.JSON_TAG_IMAGE_SOURCE) : null),
                        jsonObject.getString(Constant.JSON_TAG_TITLE),
                        (jsonObject.has(Constant.JSON_TAG_IMAGE) ? jsonObject.getString(Constant.JSON_TAG_IMAGE) : null),
                        (jsonObject.has(Constant.JSON_TAG_SHARE_URL) ? jsonObject.getString(Constant.JSON_TAG_SHARE_URL) : null),
                        jsonObject.getString(Constant.JSON_TAG_GA_PREFIX),
                        jsonObject.getInt(Constant.JSON_TAG_TYPE),
                        jsonObject.getInt(Constant.JSON_TAG_ID),
                        null
                );

                return content;
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NewsContentEntity  getLocalNewsContent(int id){
        String json = dbOperator.findWebCache(String.valueOf(id));
        if(json != null) {
            return parseNewsContentJson(json);
        }else {
            return null;
        }
    }

    public void saveNewsContentLocal(int id, String json){
        dbOperator.insertWebCache(String.valueOf(id), json);
    }

    public void requestRemoteNewsContentSync(int id, okhttp3.Callback callback){
        try {
            HttpUtils httpUtils = HttpUtils.getInstance();

            httpUtils.getDefaultUrlAsyn(Constant.NEWS_CONTENT_URL+id, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
