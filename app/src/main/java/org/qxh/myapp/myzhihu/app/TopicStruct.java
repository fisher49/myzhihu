package org.qxh.myapp.myzhihu.app;

/**
 * Created by QXH on 2016/2/1.
 */
public class TopicStruct {
    String tag;
    String name;

    public TopicStruct(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }
}
