package org.qxh.myapp.myzhihu.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseActivity;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private FrameLayout fl_topic_list;
    private SwipeRefreshLayout srl_topic_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

//    @Override
//    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        SimpleDraweeView view = (SimpleDraweeView)findViewById(R.id.sdv_image123);
//        Uri uri = Uri.parse("http://k.sinaimg.cn/n/sports/transform/20160123/Xzo1-fxnuvxc1695509.jpg/w5702cd.jpg");
//        view.setImageURI(uri);
//    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);

        drawerlayout = (DrawerLayout)findViewById(R.id.dl_main);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.app_name, R.string.app_name);
        drawerToggle.syncState();

        fl_topic_list = (FrameLayout)findViewById(R.id.fl_topic_list);
        srl_topic_list = (SwipeRefreshLayout)findViewById(R.id.srl_topic_list);

        srl_topic_list.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFragment();
                srl_topic_list.setRefreshing(false);
            }
        });

        refreshFragment();
    }

    private void refreshFragment() {
        refreshFragment(Constant.TAG_MAIN_LIST_FRAGMENT_LATEST);
    }

    private void refreshFragment(String tag) {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left)
                .replace(R.id.fl_topic_list, new MainNewsListFragment(), tag).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void enableSwipeRefreshLayout(boolean enable){
        if(srl_topic_list != null) {
            srl_topic_list.setEnabled(enable);
        }
    }

    public void onEventMainThread(EventBody event){
            switch (event.getEventName()) {
                // 显示图片
                case Constant.EVENT_START_SHOW_IMAGE_BITMAP:
                    break;
                // 控制下拉刷新功能开关
                case Constant.EVENT_MAIN_NEWS_SWITCH_SWIPEREFRESH:
                    enableSwipeRefreshLayout((boolean)event.getParameter());
                    break;
                // 收起侧滑菜单
                case Constant.EVENT_THEME_CLOSE_MENU:
                    drawerlayout.closeDrawers();
                    break;
                // 加载主题内容结果
                case Constant.EVENT_MAIN_NEWS_UPDATE_THEME_SUCCESS:
                    refreshFragment(Constant.TAG_MAIN_LIST_FRAGMENT_THEME);
                    // TODO:传输ThemeContentEntity初始化main_list_fragment的内容。
                    break;
                case Constant.EVENT_MAIN_NEWS_UPDATE_THEME_FAIL:
                    Toast.makeText(this, getResources().getString(R.string.err_load_theme_content), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    onSubFragmentEvent(event, R.id.fm_topic_menu, R.id.fl_topic_list);
                    break;
            }
    }
}
