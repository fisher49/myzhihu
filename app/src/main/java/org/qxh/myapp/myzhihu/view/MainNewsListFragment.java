package org.qxh.myapp.myzhihu.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseFragment;
import org.qxh.myapp.myzhihu.app.CommonAdapter;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.app.NewsTip;
import org.qxh.myapp.myzhihu.app.ViewHolder;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.BeforeNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.LatestNewsEntity;
import org.qxh.myapp.myzhihu.model.entities.StoriesEntity;
import org.qxh.myapp.myzhihu.model.entities.TopStoriesEntity;
import org.qxh.myapp.myzhihu.presenter.MainNewsListPresenter;
import org.qxh.myapp.myzhihu.utils.Utility;

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
    private ViewPageLayout viewPage;
//    String latestNewsEntity;
    private boolean isLoading = false;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news_list, null);
        lv_news = (ListView)view.findViewById(R.id.lv_news_list);

        View listHeader = inflater.inflate(R.layout.view_page, null);
        viewPage = (ViewPageLayout) listHeader.findViewById(R.id.vpl_sliding);

        presenter = new MainNewsListPresenter(mActivity, getTag());

        storiesList = new ArrayList<StoriesEntity>();
        topStoriesList = new ArrayList<TopStoriesEntity>();


        viewPage.setTopEntities(topStoriesList);
        viewPage.setItemClickListener(new ViewPageLayout.OnItemClickListener(){

            @Override
            public void click(View v, TopStoriesEntity entity) {
                // TODO:转入文章浏览界面
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("newstip", new NewsTip(entity.getId(), entity.getTitle()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lv_news.addHeaderView(listHeader);

        adapter = new NewsListAdapter(mActivity, storiesList, R.layout.main_news_list_item, null);
        lv_news.setAdapter(adapter);
        lv_news.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lv_news != null && lv_news.getChildCount() > 0) {
                    boolean enable = (firstVisibleItem == 0) && (view.getChildAt(firstVisibleItem).getTop() == 0);
                    presenter.enableSwipeRefresh(enable);
                }

                // 在首页新闻状态下可下拉加载过往新闻
                if(MainNewsListFragment.this.getTag().equals(Constant.TAG_MAIN_LIST_FRAGMENT_LATEST)) {
                    String earliestDate = MainNewsListFragment.this.adapter.getEarliestDate();
                    if ((firstVisibleItem + visibleItemCount == totalItemCount) && (earliestDate != null)
                            && (totalItemCount != 0) && !isLoading) {
                        isLoading = true;
                        presenter.downloadBeforeNewsRemote(earliestDate);
                    }
                }
            }
        });

        presenter.initListContext();
        return view;
    }

    public class NewsListAdapter extends CommonAdapter<StoriesEntity>{
        // 包含所有列表的日期信息
        private List<String> dates;
        // 包含所有当日首条记录在列表中的索引信息
        private List<Integer> firstItemIndexOfDateList;

        public NewsListAdapter(Context context, List<StoriesEntity> datas, int layoutId, String date) {
            super(context, datas, layoutId);

            dates = new ArrayList<>();
            firstItemIndexOfDateList = new ArrayList<>();
            if(date != null) {
                dates.add(date);
                firstItemIndexOfDateList.add(0);
            }
        }

        @Override
        public void convert(ViewHolder holder, StoriesEntity storiesEntity) {
            holder.setText(R.id.tv_news_summary, storiesEntity.getTitle());
            if(storiesEntity.getImages() != null) {
                holder.setDraweeImageURL(R.id.sdv_thumbnail, storiesEntity.getImages().get(0));
//                holder.setViewVisible(R.id.sdv_thumbnail, View.VISIBLE);
            }else {
                holder.setViewVisible(R.id.sdv_thumbnail, View.GONE);
            }
            if(storiesEntity.getType() == Constant.TYPE_DATE_NEWS_FIRST){
                int pos = mDatas.indexOf(storiesEntity);
                if(firstItemIndexOfDateList.contains(pos)) {
                    holder.setHeadlineTextExtra(R.id.tv_news_date,
                            (0 == mDatas.indexOf(storiesEntity)) ? getResources().getString(R.string.tv_today_news)
                                    : Utility.formatYYYYMMDDDate(dates.get(firstItemIndexOfDateList.indexOf(pos))),
                            false);
                }else {
                    holder.setHeadlineTextExtra(R.id.tv_news_date, null, true);
                }
            }else {
                holder.setHeadlineTextExtra(R.id.tv_news_date, null, true);
            }
        }

        public void addList(List<StoriesEntity> list, String date){
            super.addList(list);
            dates.add(date);
            firstItemIndexOfDateList.add(mDatas.indexOf(list.get(0)));
        }

        public void addList(int location, List<StoriesEntity> list, String date){
            super.addList(location, list);
            dates.add(location, date);
            firstItemIndexOfDateList.add(mDatas.indexOf(list.get(0)));
        }

        public String getEarliestDate(){
            if((dates != null) && (dates.size() > 0)){
                return dates.get(dates.size()-1);
            }else {
                return null;
            }
        }

    }

    private void updateNewListView(LatestNewsEntity latestNewsEntity) {
        topStoriesList.clear();
        topStoriesList.addAll(latestNewsEntity.getTop_stories());
        viewPage.setTopEntities(topStoriesList);
        viewPage.invalidate();

        adapter.addList(0, latestNewsEntity.getStories(), latestNewsEntity.getDate());
    }

    private void updateBeforeNewListView(BeforeNewsEntity beforeNewsEntity) {
        adapter.addList(beforeNewsEntity.getStories(), beforeNewsEntity.getDate());
        isLoading = false;
    }

    private void onDownloadBeforeNewsFailed(){
        BeforeNewsEntity beforeNewsEntity = presenter.getLocalBeforeNews(adapter.getEarliestDate());
        if (beforeNewsEntity != null) {
            adapter.addList(beforeNewsEntity.getStories(), beforeNewsEntity.getDate());
        } else {
            Toast.makeText(mActivity, R.string.err_load_before, Toast.LENGTH_SHORT).show();
        }

        isLoading = false;
    }

//    private void updateThemeContent(int id) {
//        presenter.getThemeContent(id);
//    }

    public void onEventMainThread(EventBody event){
        switch (event.getEventName()){
            // 加载今日新闻结果
            case Constant.EVENT_NEWS_LOARD_SUCCESS:
                updateNewListView((LatestNewsEntity)event.getParameter());
                break;
            case Constant.EVENT_NEWS_LOARD_FAIL:
                Toast.makeText(mActivity, R.string.err_connect, Toast.LENGTH_SHORT).show();
                break;

            // 加载过往新闻结果
            case Constant.EVENT_NEWS_BEFORE_LOARD_FAIL:
                onDownloadBeforeNewsFailed();
                break;
            case Constant.EVENT_NEWS_BEFORE_LOARD_SUCCESS:
                updateBeforeNewListView((BeforeNewsEntity)event.getParameter());
                break;

            // 加载主题内容结果
            case Constant.EVENT_MAIN_NEWS_UPDATE_THEME_SUCCESS:
                updateNewListView((LatestNewsEntity)event.getParameter());
                break;
            case Constant.EVENT_MAIN_NEWS_UPDATE_THEME_FAIL:
                Toast.makeText(mActivity, getResources().getString(R.string.err_load_theme_content), Toast.LENGTH_SHORT).show();
                break;
            default:break;
        }
    }



}
