package org.qxh.myapp.myzhihu.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import de.greenrobot.event.EventBus;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if(EventBus.getDefault().isRegistered(this)) {
//            Log.i("onCreate-------------->", "already register");
//        }else{
//            Log.i("onCreate-------------->", "not register");
//        }

        EventBus.getDefault().register(this);
    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        EventBus.getDefault().unregister(this);
//    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("Activity", "on start");
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Activity", "on stop");
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventBody event){

    }

    public void onSubFragmentEvent(EventBody event, int... args){
        BaseFragment fragment;
        for(int i=0; i<args.length; i++){
            fragment = (BaseFragment)getFragmentManager().findFragmentById(args[i]);
            fragment.onEventMainThread(event);
        }
    }
}
