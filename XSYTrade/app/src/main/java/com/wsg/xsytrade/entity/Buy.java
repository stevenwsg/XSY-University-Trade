package com.wsg.xsytrade.entity;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.entity
 * 文件名：Buy
 * 创建者：wsg
 * 创建时间：2017/9/22  14:47
 * 描述：buy实体类
 */

public class Buy extends BmobObject {

    private String name;
    private String title;
    private String content;

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    private String messageid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
