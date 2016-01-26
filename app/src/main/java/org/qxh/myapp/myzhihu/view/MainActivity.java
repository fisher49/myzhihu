package org.qxh.myapp.myzhihu.view;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseActivity;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.config.Constant;

public class MainActivity extends BaseActivity {

    Toolbar toolbar;
    DrawerLayout drawerlayout;
    FrameLayout fl_topic_list;
    SwipeRefreshLayout srl_topic_list;
//    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                SimpleDraweeView view = (SimpleDraweeView)findViewById(R.id.sdv_view);
//                Uri uri = Uri.parse("http://k.sinaimg.cn/n/sports/transform/20160123/Xzo1-fxnuvxc1695509.jpg/w5702cd.jpg");
//                view.setImageURI(uri);

//                view.setImageURI(ImageRequest.fromUri("http://p1.gexing.com/G1/M00/45/DB/rBABFFGu9h3ChMDmAAA0vyy1_UA611.jpg"));
//            }
//        });
        initView();
    }

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
    }

    private void refreshFragment() {
//        fl_topic_list.repl
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

    public void onEventMainThread(EventBody event){
            switch (event.getEventName()) {
                // 显示图片
                case Constant.EVENT_START_SHOW_IMAGE_BITMAP:
                    break;
                default:
                    onSubFragmentEvent(event, R.id.fm_topic_menu, R.id.fl_topic_list);
                    break;
            }
    }
}
