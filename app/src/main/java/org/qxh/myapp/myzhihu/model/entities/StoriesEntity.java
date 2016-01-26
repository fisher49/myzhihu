package org.qxh.myapp.myzhihu.model.entities;

import java.util.List;

/**
 * Created by QXH on 2016/1/25.
 */
public class StoriesEntity {

    /**
     * images : ["http://pic2.zhimg.com/e9956bc7a6d123cbf3e5d6c9ac2df6fd.jpg"]
     * type : 0
     * id : 7778518
     * ga_prefix : 012516
     * title : 一小块地儿让我塞一千多个字还得好看，一定是在逗我
     */

    private int type;
    private int id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public StoriesEntity(int type, int id, String ga_prefix, String title, List<String> images) {
        this.type = type;
        this.id = id;
        this.ga_prefix = ga_prefix;
        this.title = title;
        this.images = images;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getImages() {
        return images;
    }









}
