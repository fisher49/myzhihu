package org.qxh.myapp.myzhihu.app;

import java.io.Serializable;

/**
 * Created by QXH on 2016/2/3.
 */
public class NewsTip implements Serializable {
    private int id;
    private String title;

    public NewsTip(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
