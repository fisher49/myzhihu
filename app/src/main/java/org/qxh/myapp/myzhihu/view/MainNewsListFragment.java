package org.qxh.myapp.myzhihu.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseFragment;
import org.qxh.myapp.myzhihu.app.CommonAdapter;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.app.ViewHolder;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.LatestNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.StoriesEntity;
import org.qxh.myapp.myzhihu.model.entities.TopStoriesEntity;
import org.qxh.myapp.myzhihu.presenter.MainNewsListPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/26.
 */
public class MainNewsListFragment extends BaseFragment{

    private ListView lv_news;
    private MainNewsListPresenter presenter;
    private List<StoriesEntity> storiesList;
    private List<TopStoriesEntity> topStoriesList;
    private NewsListAdapter adapter;
    private int currentShowItems = 0;
    private ViewPageLayout viewPage;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news_list, null);
        lv_news = (ListView)view.findViewById(R.id.lv_news_list);

        presenter = new MainNewsListPresenter(mActivity);

        storiesList = new ArrayList<StoriesEntity>();
        topStoriesList = new ArrayList<TopStoriesEntity>();
        // 从本地缓存读取新闻
        currentShowItems = 0;
        LatestNewsEntity latestNewsEntity = presenter.getLocalNews(currentShowItems);
        if(latestNewsEntity != null){
            storiesList.addAll(latestNewsEntity.getStories());
            topStoriesList.addAll(latestNewsEntity.getTop_stories());
        }else {
            presenter.downloadNewsRemote();
        }

        View listHeader = inflater.inflate(R.layout.view_page, null);
        viewPage = (ViewPageLayout) listHeader.findViewById(R.id.vpl_sliding);
        viewPage.setTopEntities(topStoriesList);
        viewPage.setItemClickListener(new ViewPageLayout.OnItemClickListener(){

            @Override
            public void click(View v, TopStoriesEntity entity) {
                // TODO:转入文章浏览界面
            }
        });
        lv_news.addHeaderView(listHeader);

        adapter = new NewsListAdapter(mActivity, storiesList, R.layout.main_news_list_item);
        lv_news.setAdapter(adapter);
        return view;
    }

    public class NewsListAdapter extends CommonAdapter<StoriesEntity>{

        public NewsListAdapter(Context context, List<StoriesEntity> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(ViewHolder holder, StoriesEntity storiesEntity) {
            holder.setText(R.id.tv_news_summary, storiesEntity.getTitle())
                    .setDraweeImageURL(R.id.sdv_thumbnail, storiesEntity.getImages().get(0));
            if(storiesEntity.getType() == Constant.TYPE_DATE_NEWS_FIRST){
                holder.setHeadlineTextExtra(R.id.tv_news_date, getResources().getString(R.string.tv_today_news), false);
            }else {
                holder.setHeadlineTextExtra(R.id.tv_news_date, null, true);
            }
        }
    }

    private void updateNewListView(LatestNewsEntity latestNewsEntity) {
        topStoriesList.clear();
        topStoriesList.addAll(latestNewsEntity.getTop_stories());
//        presenter.updateSlidingPage(topStoriesList);
        viewPage.setTopEntities(topStoriesList);
        viewPage.invalidate();

        adapter.addList(0, latestNewsEntity.getStories());
    }

    public void onEventMainThread(EventBody event){
        switch (event.getEventName()){
            case Constant.EVENT_NEWS_LOARD_SUCCESS:
                updateNewListView((LatestNewsEntity)event.getParameter());
                break;
            case Constant.EVENT_NEWS_LOARD_FAIL:
//                updateNewListView((LatestNewsEntity)event.getParameter());
                Toast.makeText(mActivity, R.string.err_connect, Toast.LENGTH_SHORT).show();
                break;
            case Constant.EVENT_NEWS_LOARD_NEWS:
                presenter.downloadNewsRemote();
                break;
            default:break;
        }
    }


}
