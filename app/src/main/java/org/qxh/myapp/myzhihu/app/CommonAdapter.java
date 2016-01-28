package org.qxh.myapp.myzhihu.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by QXH on 2016/1/23.
 */
public abstract class CommonAdapter<T> extends BaseAdapter{
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater inflater;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId){
        this.mContext =context;
        this.mDatas =datas;
        this.layoutId =layoutId;
        inflater =LayoutInflater.from(context);

    }

    public void addList(List<T> list){
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(int location, List<T> list){
        mDatas.addAll(location, list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size()>0?mDatas.size():0;
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);

        convert(holder, getItem(position));

        return holder.getmConvertView();
    };

    public abstract void convert(ViewHolder holder,T t);
}
