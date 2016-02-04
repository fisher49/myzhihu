package org.qxh.myapp.myzhihu.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.qxh.myapp.myzhihu.R;

/**
 * Created by QXH on 2016/1/23.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPositon;
    private View mConvertView;
    private Context context;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        this.mPositon = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);

        mConvertView.setTag(this);
        this.context = context;
    }

    public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutid, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutid, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPositon =position;
            return holder;
        }
    }



    public int getPositon() {
        return mPositon;
    }

    /**
     * 通过iewId获取控件
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view =mViews.get(viewId);
        if (view ==null) {
            view =mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    /**
     * 如果Adapter是TextView ，设置TextView的值
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text){
        TextView tv =getView(viewId);
        tv.setText(text);
        return this;

    }

    /**
     * 如果Adapter是TextView ，设置TextView的值
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId,String text, int colorResourceID){
        TextView tv =getView(viewId);
        tv.setText(text);
        tv.setTextColor(context.getResources().getColor(colorResourceID));
        return this;
    }

    /**
     *
     * @param viewId 资源ID
     * @param text 内容
     * @param hide 是否隐藏
     * @return
     */
    public ViewHolder setHeadlineTextExtra(int viewId, String text, boolean hide){
        TextView tv =getView(viewId);
        if(hide){
//            ((LinearLayout)tv.getParent()).setBackgroundColor(context.getResources().getColor(R.color.main_list_background, null));
            tv.setVisibility(View.GONE);
        }else {
//            ((LinearLayout)tv.getParent()).setBackgroundColor(Color.GRAY);
            tv.setVisibility(View.VISIBLE);
//            tv.setTextColor(context.getResources().getColor(R.color.main_list_date));
            tv.setText(text);
        }
        return this;

    }
    /**
     * 如果Adapter是ImageView ，设置图片1111111
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setImageResource(int viewId,int resId){
        ImageView view =getView(viewId);
        view.setImageResource(resId);
        return this;

    }
    /**
     * 如果Adapter是ImageView ，设置图片22222
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setImageBitmap(int viewId,Bitmap bitmap){
        ImageView view =getView(viewId);
        view.setImageBitmap(bitmap);
        return this;

    }
    /**
     * 如果Adapter是SimpleDraweeView ，设置图片33333
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setDraweeImageURL(int viewId,String url){
        SimpleDraweeView view = getView(viewId);
        view.setImageURI(Uri.parse(url));
        view.setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 如果Adapter是SimpleDraweeView ，设置图片33333
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setDraweeImageResource(int viewId,int resId){
        SimpleDraweeView view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * 这是控件是否可见
     * @param viewId 控件资源ID
     * @param visible 可见参数
     * @return
     */
    public ViewHolder setViewVisible(int viewId, int visible){
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }
}
