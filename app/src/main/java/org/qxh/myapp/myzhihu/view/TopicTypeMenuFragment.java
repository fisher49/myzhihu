package org.qxh.myapp.myzhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseFragment;
import org.qxh.myapp.myzhihu.app.CommonAdapter;
import org.qxh.myapp.myzhihu.app.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QXH on 2016/1/23.
 */
public class TopicTypeMenuFragment extends BaseFragment{

    ListView lv_topic_list;
    List<TopicItem> list_topic;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_type_menu, container, false);

        lv_topic_list = (ListView)view.findViewById(R.id.lv_topic_list);

        initListData();

        lv_topic_list.setAdapter(new CommonAdapter<TopicItem>(getActivity(), list_topic, R.layout.item_topic_list) {

            @Override
            public void convert(ViewHolder holder, TopicItem topicItem) {
                holder.setText(R.id.tv_topic_list_item, topicItem.getTopicName());
//                        .setImageResource(R.id.iv_topic_list_item, R.drawable.ic_star_white_24dp);
            }
        });
        return view;
    }

    private void initListData() {
        list_topic = new ArrayList<TopicItem>();

        for(int i=0; i<26; i++){
//            list_topic.add(new TopicItem(String.format("longlongitme-02d%", i)));
            String string = new String("你好abc");
            list_topic.add(new TopicItem(string));
        }
    }

    class TopicItem {
        String topicName;

        public TopicItem(String topicName) {
            this.topicName = topicName;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }
    }
}
