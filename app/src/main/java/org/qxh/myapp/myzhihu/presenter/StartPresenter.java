package org.qxh.myapp.myzhihu.presenter;


import android.content.Context;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.net.HttpUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by QXH on 2016/1/21.
 */
public class StartPresenter {

    private HttpUtils httpUtils;
    private File startImageFile;
    private Context activityContext;

    public StartPresenter(Context context) {
        httpUtils = HttpUtils.getInstance();
        activityContext = context;
        startImageFile = new File(activityContext.getFilesDir(), Constant.DEFAULT_START_IMAGE);
    }

    public void getLocalImage(){
        if(startImageFile.exists()){
            EventBus.getDefault().post(new EventBody(Constant.EVENT_START_SHOW_IMAGE_BITMAP, BitmapFactory.decodeFile(startImageFile.getAbsolutePath())));
        }else{
            EventBus.getDefault().post(new EventBody(Constant.EVENT_START_SHOW_IMAGE_RESOURCE, R.mipmap.start));
        }
    }

    public void downloadRemoteImage(){
        try {
            httpUtils.getDefaultUrlAsyn(Constant.START_IMAGE_URL, new okhttp3.Callback(){

                @Override
                public void onFailure(Call call, IOException e) {
                    EventBus.getDefault().post(new EventBody());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if(response.isSuccessful()) {
                            JSONObject jsonObject = new JSONObject(response.body().string());

                            String imageUrl = jsonObject.getString(Constant.JSON_TYPE_START_IMAGE);
                            try {
                                httpUtils.getAsyn(imageUrl, new okhttp3.Callback(){
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        if(response.isSuccessful()) {
                                            saveImageFile(response.body().bytes());
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    EventBus.getDefault().post(new EventBody());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void saveImageFile(byte[] bytes){
        if(startImageFile.exists()){
            startImageFile.delete();
        }
        try {
            FileOutputStream stream = new FileOutputStream(startImageFile);
            stream.write(bytes);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}