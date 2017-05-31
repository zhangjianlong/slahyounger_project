package com.slash.youth.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhouyifeng on 2016/11/22.
 */
public class ChatCmdWebChangeContactBean {


    /**
     * uid : 10003
     * name : XXXXXX
     * relationTitle : 相关项目名字
     */

    private String uid;
    private String name;
    private String relationTitle;
    private String phone;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationTitle() {
        return relationTitle;
    }

    public void setRelationTitle(String relationTitle) {
        this.relationTitle = relationTitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
