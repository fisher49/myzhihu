package org.qxh.myapp.myzhihu.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.facebook.drawee.view.SimpleDraweeView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.config.Constant;

import java.io.File;

public class StartActivity extends Activity {

    private SimpleDraweeView fs_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);

        initView();
    }

    private void initView(){
        fs_start = (SimpleDraweeView)this.findViewById(R.id.fs_start);

        File dir = getFilesDir();
        final File imageFile = new File(dir, Constant.DEFAULT_START_IMAGE);
        if(imageFile.exists()){

        }else{
//            fs_start.setImageResource(R.mipmap.start);
            fs_start.setBackgroundResource(R.mipmap.start);
        }

        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.start_activity_animation);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fs_start.startAnimation(scaleAnimation);
    }
}
