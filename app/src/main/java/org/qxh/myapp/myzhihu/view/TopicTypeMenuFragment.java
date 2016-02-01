package org.qxh.myapp.myzhihu.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.qxh.myapp.myzhihu.R;
import org.qxh.myapp.myzhihu.app.BaseFragment;
import org.qxh.myapp.myzhihu.app.CommonAdapter;
import org.qxh.myapp.myzhihu.app.EventBody;
import org.qxh.myapp.myzhihu.app.ViewHolder;
import org.qxh.myapp.myzhihu.config.Constant;
import org.qxh.myapp.myzhihu.model.entities.ThemeEntity;
import org.qxh.myapp.myzhihu.presenter.TopicTypeMenuPresenter;

import java.util.ArrayList;

/**
 * Created by QXH on 2016/1/23.
 */
public class TopicTypeMenuFragment extends BaseFragment{

    ListView lv_topic_list;
    TopicTypeMenuPresenter presenter;
    ArrayList<ThemeEntity> themes;
    TextView tv_home_page;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_type_menu, container, false);

        lv_topic_list = (ListView)view.findViewById(R.id.lv_topic_list);
        tv_home_page = (TextView)view.findViewById(R.id.tv_home_page);

        presenter = new TopicTypeMenuPresenter(getActivity());
        presenter.getTopicInformation();

        tv_home_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateMainList(Constant.TAG_MAIN_LIST_FRAGMENT_LATEST, getResources().getString(R.string.app_name));
                presenter.closeMenu();
            }
        });

        return view;
    }

    private void initMenuItems(ArrayList<ThemeEntity> themes){

        if(themes != null) {
            this.themes = themes;
            lv_topic_list.setAdapter(new CommonAdapter<ThemeEntity>(getActivity(), themes, R.layout.item_topic_list) {

                @Override
                public void convert(ViewHolder holder, ThemeEntity topicItem) {
                    holder.setText(R.id.tv_topic_list_item, topicItem.getName())
                            .setText(R.id.tv_topic_list_subitem, topicItem.getDescription());
//                        .setImageResource(R.id.iv_topic_list_item, R.drawable.ic_star_white_24dp);
                }
            });

            lv_topic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ThemeEntity entity = TopicTypeMenuFragment.this.themes.get(position);
                    presenter.updateMainList(String.valueOf(entity.getId()), entity.getName());
                    presenter.closeMenu();
                }
            });
        }else {
            Toast.makeText(getActivity(), R.string.err_load_themes, Toast.LENGTH_LONG).show();
        }
    }

    public void onEventMainThread(EventBody event){
        switch (event.getEventName()){
            // 加载菜单theme信息
            case Constant.EVENT_THEMES_LOARD:
                initMenuItems((ArrayList<ThemeEntity>)event.getParameter());
                break;
            default:break;
        }
    }

}
