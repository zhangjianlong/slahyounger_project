package com.slash.youth.domain.bean;

/**
 * Created by acer on 2017/3/31.
 */

public class UserEvaluateBean {

    /**
     * avatar : group1/M00/00/04/eBtor1i4zRSAY8A6ABTML2QOXFc.0a9ae1
     * cts : 1487588480709
     * name : 徐珉
     * remark :
     * score : 5
     * title : 针对公司情况 做一套合理的薪酬体系
     * uid : 10000
     */

    private String avatar;
    private long cts;
    private String name;
    private String remark;
    private int score;
    private String title;
    private long uid;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getCts() {
        return cts;
    }

    public void setCts(long cts) {
        this.cts = cts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
