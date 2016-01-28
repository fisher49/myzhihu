package org.qxh.myapp.myzhihu.config;

/**
 * Created by QXH on 2016/1/20.
 */
public class Constant {
    public static final String DEFAULT_START_IMAGE = "start.jpg";

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    public static final String START_IMAGE_URL = "start-image/1080*1776";
    public static final String THEME_URL = "themes";
    public static final String LATEST_NEWS_URL = "news/latest";

    public static final String JSON_TAG_START_IMAGE = "img";
    public static final String JSON_TAG_OTHERS = "others";
    public static final String JSON_TAG_COLOR = "color";
    public static final String JSON_TAG_THUMBNAIL = "thumbnail";
    public static final String JSON_TAG_DESCRIPTION = "description";
    public static final String JSON_TAG_ID = "id";
    public static final String JSON_TAG_NAME = "name";
    public static final String JSON_TAG_DATE = "date";
    public static final String JSON_TAG_STORIES = "stories";
    public static final String JSON_TAG_TOP_STORIES = "top_stories";
    public static final String JSON_TAG_IMAGES = "images";
    public static final String JSON_TAG_TYPE = "type";
    public static final String JSON_TAG_GA_PREFIX = "ga_prefix";
    public static final String JSON_TAG_TITLE = "title";
    public static final String JSON_TAG_IMAGE = "image";

    public static final String EVENT_DEFAULT = "EvtDefault";
    public static final String EVENT_START_SHOW_IMAGE_BITMAP = "EvtStart01";
    public static final String EVENT_START_SHOW_IMAGE_RESOURCE = "EvtStart02";
    public static final String EVENT_START_LAUNCH_MAIN = "EvtStart03";
    public static final String EVENT_THEMES_LOARD = "EvtTheme01";

    public static final String EVENT_NEWS_LOARD_FAIL = "EvtNews01";
    public static final String EVENT_NEWS_LOARD_SUCCESS = "EvtNews02";
    public static final String EVENT_NEWS_LOARD_NEWS = "EvtNews03";

    public static final String EVENT_SLIDING_PAGE_UPDATE = "EvtSP01";


    public static final int TYPE_DATE_NEWS_DEFAULT = 10001;
    public static final int TYPE_DATE_NEWS_FIRST = 10002;
}
