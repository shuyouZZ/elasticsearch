package com.learn.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.util.DateUtil;

import java.io.Serializable;
import java.text.ParseException;

/**
 * @author dshuyou
 * @date 2019/11/29 12:50
 */
public class IndexSource implements Serializable {
    @JSONField(name = "ID")
    private String id;
    @JSONField(name = "TIME")
    private String time;
    @JSONField(name = "TYPE")
    private String type;
    @JSONField(name = "STYPE")
    private String stype;
    @JSONField(name = "TITLE")
    private String title;
    @JSONField(name = "CONTENT")
    private String content;
    @JSONField(name = "QY")
    private String qy;
    @JSONField(name = "TVIEW")
    private String tview;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        try {
            this.time = DateUtil.convert(time);
        } catch (ParseException e) {
            this.time = null;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getTview() {
        return tview;
    }

    public void setTview(String tview) {
        this.tview = tview;
    }
}
