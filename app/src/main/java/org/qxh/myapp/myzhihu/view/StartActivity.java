package org.qxh.myapp.myzhihu.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.facebook.drawee.view.SimpleDraweeView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseActivity;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.presenter.StartPresenter;

public class StartActivity extends BaseActivity {

    private SimpleDraweeView fs_start;
    private StartPresenter startPresenter;
    private Boolean isInitView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        startPresenter = new StartPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!isInitView){
            isInitView = false;
            initView();
        }
    }

    private void initView(){
        fs_start = (SimpleDraweeView)this.findViewById(R.id.fs_start);
        startPresenter.getLocalImage();
    }

    private void startImageAnimation(){
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.start_activity_animation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startPresenter.downloadRemoteImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fs_start.startAnimation(scaleAnimation);
    }

    private void luanchMain(){
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void onEvent(EventBody event){
        switch (event.getEventName()){
            case Constant.EVENT_START_SHOW_IMAGE_BITMAP:
                fs_start.setImageBitmap((Bitmap) event.getParameter());
                startImageAnimation();
                break;
            case Constant.EVENT_START_SHOW_IMAGE_RESOURCE:
                fs_start.setImageResource((int) event.getParameter());
                startImageAnimation();
                break;
            case Constant.EVENT_DEFAULT:
                luanchMain();
                break;
            default:break;
        }
    }

}
