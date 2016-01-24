package org.qxh.myapp.myzhihu.model.entities;

/**
 * Created by QXH on 2016/1/24.
 */
public class ThemeEntity {

    /**
     * color : 15007
     * thumbnail : http://pic3.zhimg.com/0e71e90fd6be47630399d63c58beebfc.jpg
     * description : 了解自己和别人，了解彼此的欲望和局限。
     * id : 13
     * name : 日常心理学
     */

    private int color;
    private String thumbnail;
    private String description;
    private int id;
    private String name;

    public ThemeEntity(int color, String thumbnail, String description, int id, String name) {
        this.color = color;
        this.thumbnail = thumbnail;
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
