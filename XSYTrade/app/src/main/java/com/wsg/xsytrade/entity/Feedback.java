package com.wsg.xsytrade.entity;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.entity
 * 文件名：Feedback
 * 创建者：wsg
 * 创建时间：2017/9/18  17:47
 * 描述：用户反馈的实体
 */

public class Feedback extends BmobObject {
    //反馈内容
    private String Content;
    //联系方式
    private String Contacts;

    private String deviceType;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getContacts() {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
