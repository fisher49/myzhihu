package org.qxh.myapp.myzhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseFragment;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;

/**
 * Created by QXH on 2016/1/26.
 */
public class MainNewsListFragment extends BaseFragment{

    ListView lv_news;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news_list, null);
        lv_news = (ListView)view.findViewById(R.id.lv_news_list);

        return view;
    }

    public void onEventMainThread(EventBody event){
        switch (event.getEventName()){
            case Constant.EVENT_NEWS_LOARD_SUCCESS:
                break;
            case Constant.EVENT_NEWS_LOARD_FAIL:
                break;
            default:break;
        }
    }
}
