package com.wsg.xsytrade.entity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.entity
 * 文件名：Sell
 * 创建者：wsg
 * 创建时间：2017/9/21  19:37
 * 描述：求购实体
 */

public class Sell extends BmobObject {
    private String name;
    private String title;
    private String content;
    private String image;

    public List<String> getPhoto() {
        return photo;
    }

    public void setPhoto(List<String> photo) {
        this.photo = photo;
    }

    private List<String> photo=new ArrayList<>();



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

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
