package org.qxh.myapp.myzhihu.model.entities;

/**
 * Created by QXH on 2016/1/26.
 */
public class TopStoriesEntity {

    public TopStoriesEntity(String image, int type, int id, String ga_prefix, String title) {
        this.image = image;
        this.type = type;
        this.id = id;
        this.ga_prefix = ga_prefix;
        this.title = title;
    }

    /**
     * image : http://pic4.zhimg.com/647eed1d9ceacf94b7c9e6643a0bf1ff.jpg
     * type : 0
     * id : 7761997
     * ga_prefix : 012607
     * title : 导演卡梅隆的团队造出万米深潜器，怎么中国只能达到七千
     */

    private String image;
    private int type;
    private int id;
    private String ga_prefix;
    private String title;

    public void setImage(String image) {
        this.image = image;
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

    public String getImage() {
        return image;
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
}
