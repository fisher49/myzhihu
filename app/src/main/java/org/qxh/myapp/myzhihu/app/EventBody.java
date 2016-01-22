package org.qxh.myapp.myzhihu.app;

import org.qxh.myapp.myzhihu.config.Constant;

/**
 * Created by QXH on 2016/1/22.
 */
public class EventBody {
    private String eventName;
    Object parameter;

    public EventBody() {
        eventName = new String(Constant.EVENT_DEFAULT);
    }

    public EventBody(String messageName, Object parameter) {
        this.eventName = messageName;
        this.parameter = parameter;
    }

    public String getEventName() {
        return eventName;
    }

    public Object getParameter() {
        return parameter;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }
}
