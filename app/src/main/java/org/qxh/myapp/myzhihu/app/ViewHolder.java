package org.qxh.myapp.myzhihu.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by QXH on 2016/1/23.
 */
public class ViewHolder {
    private SparseArray<View> mViews;
    private int mPositon;
    private View mConvertView;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        this.mPositon = position;
        this.mViews = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);

        mConvertView.setTag(this);

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
}