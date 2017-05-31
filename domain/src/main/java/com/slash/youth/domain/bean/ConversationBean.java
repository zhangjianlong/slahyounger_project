package com.slash.youth.domain.bean;

import java.io.Serializable;

public class ConversationBean implements Serializable {
    private String avatar;
    private String company;
    private int isAuth;
    private String name;
    private String position;
    private long uid;
    private long uts;

    public ConversationBean() {
    }

    public ConversationBean(long uid, long uts) {
        this.uid = uid;
        this.uts = uts;
    }

    public ConversationBean(long uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(int isAuth) {
        this.isAuth = isAuth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUts() {
        return uts;
    }

    public void setUts(long uts) {
        this.uts = uts;
    }
}