package org.qxh.myapp.myzhihu.model.usecase;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.db.PreferenceHelper;
import org.qxh.myapp.myzhihu.model.entities.ThemeContentEntity;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/29.
 */
public class ThemeContentUsecase {
    Context context;
    String themeUrl = Constant.BASE_URL+Constant.THEME_CONTENT_URL;

    public ThemeContentUsecase(Context context) {
        this.context = context;
    }

    public ThemeContentEntity parseThemeContentJson(String json){
        try {
            if(json.length() > 0) {
                ThemeContentEntity content = new ThemeContentEntity();
                JSONObject jsonObject = new JSONObject(json);

                content.setBackground(jsonObject.getString(Constant.JSON_TAG_BACKGROUND));
                content.setColor(jsonObject.getInt(Constant.JSON_TAG_COLOR));
                content.setDescription(jsonObject.getString(Constant.JSON_TAG_DESCRIPTION));
                content.setImage(jsonObject.getString(Constant.JSON_TAG_IMAGE));
                content.setImage_source(jsonObject.getString(Constant.JSON_TAG_IMAGE_SOURCE));
                content.setName(jsonObject.getString(Constant.JSON_TAG_NAME));

                JSONArray jsonArray = jsonObject.getJSONArray(Constant.JSON_TAG_STORIES);
                List<ThemeContentEntity.StoriesEntity> stories = new ArrayList<>();
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonStories = jsonArray.getJSONObject(i);

                    List<String> images = null;
                    if(jsonStories.has(Constant.JSON_TAG_IMAGES)) {
                        JSONArray jsonArrayImages = jsonStories.getJSONArray(Constant.JSON_TAG_IMAGES);
                        images = new ArrayList<>();
                        for (int j = 0; j < jsonArrayImages.length(); j++) {
                            images.add(jsonArrayImages.getString(j));
                        }
                    }
                    stories.add(new ThemeContentEntity.StoriesEntity(jsonStories.getInt(Constant.JSON_TAG_TYPE),
                            jsonStories.getInt(Constant.JSON_TAG_ID),
                            jsonStories.getString(Constant.JSON_TAG_TITLE),
                            images));
                }

                JSONArray jsonArray2 = jsonObject.getJSONArray(Constant.JSON_TAG_EDITORS);
                List<ThemeContentEntity.EditorsEntity> editors = new ArrayList<>();
                for(int i=0; i<jsonArray2.length(); i++){
                    JSONObject jsonStories = jsonArray2.getJSONObject(i);
                    // TODO:这里json不一定含有以下tag的所有内容需要容错处理
//                    editors.add(new ThemeContentEntity.EditorsEntity(jsonStories.getString(Constant.JSON_TAG_URL),
//                            jsonStories.getString(Constant.JSON_TAG_BIO),
//                            jsonStories.getInt(Constant.JSON_TAG_ID),
//                            jsonStories.getString(Constant.JSON_TAG_AVATAR),
//                            jsonStories.getString(Constant.JSON_TAG_NAME)));

                    // 暂不使用该部分内容
                    editors.add(new ThemeContentEntity.EditorsEntity());
                }

                content.setStories(stories);
                content.setEditors(editors);
                return content;
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ThemeContentEntity  getLocalThemeContent(int id){
        try {
            String json = PreferenceHelper.readStringPreference(context, themeUrl+id, "");
            return parseThemeContentJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void saveThemesContentLocal(int id, String json){
        PreferenceHelper.saveStringPreference(context, themeUrl+id, json);
    }

    public void requestRemoteThemesContentSync(int id, okhttp3.Callback callback){
        try {
            HttpUtils httpUtils = HttpUtils.getInstance();

            httpUtils.getDefaultUrlAsyn(Constant.THEME_CONTENT_URL+id, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
