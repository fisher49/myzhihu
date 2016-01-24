package org.qxh.myapp.myzhihu.model.usecase;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.ThemeEntity;
import org.qxh.myapp.myzhihu.model.db.PreferenceHelper;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/24.
 */
public class ThemesUsecase {
    Context context;
    String themeUrl = Constant.BASE_URL+Constant.THEME_URL;

    public ThemesUsecase(Context context) {
        this.context = context;
    }

    public List<ThemeEntity> parseThemesJson(String json){
        try {
            if(json.length() > 0) {
                List<ThemeEntity> themes = new ArrayList<ThemeEntity>();
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray(Constant.JSON_TAG_OTHERS);

                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonTheme = jsonArray.getJSONObject(i);
                    themes.add(new ThemeEntity(jsonTheme.getInt(Constant.JSON_TAG_COLOR),
                            jsonTheme.getString(Constant.JSON_TAG_THUMBNAIL),
                            jsonTheme.getString(Constant.JSON_TAG_DESCRIPTION),
                            jsonTheme.getInt(Constant.JSON_TAG_ID),
                            jsonTheme.getString(Constant.JSON_TAG_NAME)));
                }
                return themes;
            }else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ThemeEntity>  getLocalThemes(){
        try {
            String json = PreferenceHelper.readStringPreference(context, themeUrl, "");
            return parseThemesJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void saveThemesLocal(String json){
        PreferenceHelper.saveStringPreference(context, themeUrl, json);
    }

    public void requestRemoteThemesSync(okhttp3.Callback callback){
        try {
            HttpUtils httpUtils = HttpUtils.getInstance();

            httpUtils.getDefaultUrlAsyn(Constant.THEME_URL, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
