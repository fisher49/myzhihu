package org.qxh.myapp.myzhihu.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseActivity;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.app.NewsTip;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.NewsContentEntity;
import org.qxh.myapp.myzhihu.presenter.WebViewPresenter;

/**
 * Created by QXH on 2016/2/2.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private WebViewPresenter presenter;
//    private int id;
    private DraweeView draweeView;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsLayout;
    private NewsTip newsTip;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        newsTip = (NewsTip) getIntent().getSerializableExtra("newstip");

        presenter = new WebViewPresenter(this, newsTip.getId());

        initView();

        presenter.initNewsContext();
    }

    private void initView() {
        webView = (WebView)findViewById(R.id.wv_news_content);
        draweeView = (SimpleDraweeView)findViewById(R.id.drawee_view_content);
        toolbar = (Toolbar)findViewById(R.id.tb_content);
        collapsLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar_layout_content);
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar_layout_content);

        appBarLayout.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsLayout.setTitle(newsTip.getTitle());
//        collapsLayout.setContentScrimColor(getResources().getColor(R.color.light_toolbar));
//        collapsLayout.setStatusBarScrimColor(getResources().getColor(R.color.light_toolbar));

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 开启DOM storage API 功能
        webView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        webView.getSettings().setDatabaseEnabled(true);
        // 开启Application Cache功能
        webView.getSettings().setAppCacheEnabled(true);

//        setStatusBarColor(getResources().getColor(R.color.light_toolbar));
    }

    private void onLoadSuccess(NewsContentEntity entity){
        draweeView.setImageURI(Uri.parse(entity.getImage()));

        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
        String html = "<html><head>" + css + "</head><body>" + entity.getBody() + "</body></html>";
        html = html.replace("<div class=\"img-place-holder\">", "");
        webView.loadDataWithBaseURL("x-data://base", html, "text/html", "UTF-8", null);
    }

    public void onEventMainThread(EventBody event){
        switch (event.getEventName()) {
            case Constant.EVENT_NEWS_CONTENT_LOAD_FAIL:
                break;
            case Constant.EVENT_NEWS_CONTENT_LOAD_SUCCESS:
                onLoadSuccess((NewsContentEntity)event.getParameter());
                break;
            default:
//                onSubFragmentEvent(event, R.id.fm_topic_menu, R.id.fl_topic_list);
                break;
        }
    }
}
