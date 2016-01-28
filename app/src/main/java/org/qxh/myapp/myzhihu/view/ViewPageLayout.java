package org.qxh.myapp.myzhihu.view;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.model.entities.TopStoriesEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/25.
 */
public class ViewPageLayout extends FrameLayout implements View.OnClickListener {
    private final int AUTO_PLAY_MILLIS = 5000;
    private final int START_PLAY_MILLIS = 3000;
    private List<TopStoriesEntity> informations;
    private List<View> views;
    private List<SimpleDraweeView> ls_dot;
    private Context context;
    private int currentItem = 0;
    private Handler handler = new Handler();
    private boolean isAutoPlay = false;
    private ViewPager vp_sliding;
    private OnItemClickListener itemClickListener = null;

    public ViewPageLayout(Context context) {
        this(context, null);
    }

    public ViewPageLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        informations = new ArrayList<TopStoriesEntity>();
        this.context = context;
        initView();
    }

    public void setItemClickListener(OnItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener != null){
            itemClickListener.click(v, informations.get(correctPosion(currentItem)));
        }
    }

    private void initView() {
//        informations = new ArrayList<StoriesEntity>();
        views = new ArrayList<View>();
    }

    public void setTopEntities(List<TopStoriesEntity> storiesEntities){
        informations = storiesEntities;
        views.clear();
        initUI();
    }

    private void initUI(){
        int len = informations.size();
        View view = LayoutInflater.from(context).inflate(R.layout.view_pages_layout, this, true);
        LinearLayout ll_dot = (LinearLayout) view.findViewById(R.id.ll_dot);
        vp_sliding = (ViewPager)view.findViewById(R.id.vp_sliding);
        ls_dot = new ArrayList<>();

        ll_dot.removeAllViews();
        for(int i=0; i<len; i++){
            SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            simpleDraweeView.setBackgroundResource((0==i) ? R.drawable.dot_focus : R.drawable.dot_blur);
            ll_dot.addView(simpleDraweeView, params);
            ls_dot.add(simpleDraweeView);
        }

        for(int i=0; i<len; i++){
            View fml = LayoutInflater.from(context).inflate(R.layout.view_page_item, null);
            TopStoriesEntity entity = informations.get(i);

            SimpleDraweeView simpleDraweeView = (SimpleDraweeView)fml.findViewById(R.id.sdv_image);
            simpleDraweeView.setImageURI(Uri.parse(entity.getImage()));

            TextView textView = (TextView)fml.findViewById(R.id.tv_title);
            textView.setText(entity.getTitle());

            fml.setOnClickListener(this);
            views.add(fml);
        }

        vp_sliding.setAdapter(new SlindingAdapter());
        vp_sliding.setFocusable(true);
        vp_sliding.setCurrentItem(0);
        currentItem = 0;
        vp_sliding.addOnPageChangeListener(new OnSlidingChangeListener());

        startAutoPlay();
    }

    private void startAutoPlay() {
        isAutoPlay = true;
        handler.postDelayed(task, START_PLAY_MILLIS);
    }

    private final Runnable task = new Runnable() {

        @Override
        public void run() {
            if(isAutoPlay){
                if(views.size() > 0) {
                    vp_sliding.setCurrentItem(currentItem+1);
//                    currentItem = (currentItem + 1) % views.size();
                }
                handler.postDelayed(task, AUTO_PLAY_MILLIS);
            }else {
                handler.postDelayed(task, AUTO_PLAY_MILLIS);
            }
        }
    };


    private class SlindingAdapter extends PagerAdapter {

        @Override
        public int getCount() {
//            return views.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (View)object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            View view = views.get(correctPosion(position));
            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
            ViewParent parent = view.getParent();
            if(parent != null){
                ((ViewGroup)parent).removeView(view);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //在instantiateItem()方法中已经处理了remove的逻辑，因此这里并不需要处理
        }
    }

    private int correctPosion(int position){
        if(views.size() > 0) {
            if (position < 0) {
                position = 0 - position;
            }
            return position % views.size();
        }else {
            return 0;
        }
    }

    private class OnSlidingChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            isAutoPlay = false;
            int realPosition = correctPosion(position);
            currentItem = position;

            for(int i=0; i<views.size(); i++){
                if(i == realPosition){
                    ls_dot.get(i).setBackgroundResource(R.drawable.dot_focus);
                }else {
                    ls_dot.get(i).setBackgroundResource(R.drawable.dot_blur);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state){
                case ViewPager.SCROLL_STATE_IDLE:
                    isAutoPlay = true;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    break;
                default:break;
            }
        }
    }

    public interface OnItemClickListener {
        public void click(View v, TopStoriesEntity entity);
    }
}
