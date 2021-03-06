package org.qxh.myapp.myzhihu.presenter;

import android.content.Context;

import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.app.TopicStruct;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.ThemeEntity;
import org.qxh.myapp.myzhihu.model.usecase.ThemesUsecase;
import org.qxh.myapp.myzhihu.utils.Utility;

import java.io.IOException;
import java.util.List;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by QXH on 2016/1/24.
 */
public class TopicTypeMenuPresenter {
    ThemesUsecase themesUsecase;
    Context context;

    public TopicTypeMenuPresenter(Context context) {
        this.themesUsecase = new ThemesUsecase(context);
        this.context = context;
    }

    public void getTopicInformation(){

        if(Utility.isNetworkConnected(context)) {
            themesUsecase.requestRemoteThemesSync(new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    dealThemesInformation(themesUsecase.getLocalThemes());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String json = response.body().string();
                        themesUsecase.saveThemesLocal(json);
                        dealThemesInformation(themesUsecase.parseThemesJson(json));
                    } else {
                        dealThemesInformation(themesUsecase.getLocalThemes());
                    }
                }
            });
        }else {
            dealThemesInformation(themesUsecase.getLocalThemes());
        }
    }

    private void dealThemesInformation(List<ThemeEntity> themes){
        EventBus.getDefault().post(new EventBody(Constant.EVENT_THEMES_LOARD, themes));
    }

//    public void updateMainlist(int id) {
//        EventBus.getDefault().post(new EventBody(Constant.EVENT_THEME_SELECT, id));
//    }
    public void updateMainList(String tag, String name) {
        TopicStruct topic = new TopicStruct(tag, name);
        EventBus.getDefault().post(new EventBody(Constant.EVENT_THEME_SELECT, topic));
    }

    public void closeMenu() {
        EventBus.getDefault().post(new EventBody(Constant.EVENT_THEME_CLOSE_MENU, null));
    }
}
