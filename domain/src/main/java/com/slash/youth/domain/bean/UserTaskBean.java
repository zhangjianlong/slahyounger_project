package com.slash.youth.domain.bean;

/**
 * Created by acer on 2017/3/31.
 */

public class UserTaskBean {
    /**
     * anonymity : 1
     * avatar : group1/M00/00/09/eBtor1jXNGKAE0TYAAAngmQ0KUI.242bfe
     * endtime : 0
     * id : 63
     * instalment : 1
     * isauth : 1
     * lat : 0
     * lng : 0
     * name : 杨路
     * pattern : 0
     * place : null
     * quote : 1
     * quoteunit : 3
     * starttime : 0
     * timetype : 4
     * title : 承接安卓项目
     * type : 2
     * uid : 10289
     */

    private int anonymity;
    private String avatar;
    private long endtime;
    private long id;
    private int instalment;
    private int isauth;
    private double lat;
    private double lng;
    private String name;
    private String place;
    private int pattern;
    private int quote;
    private int quoteunit;
    private long starttime;
    private int timetype;
    private String title;
    private int type;
    private long uid;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(int anonymity) {
        this.anonymity = anonymity;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getInstalment() {
        return instalment;
    }

    public void setInstalment(int instalment) {
        this.instalment = instalment;
    }

    public int getIsauth() {
        return isauth;
    }

    public void setIsauth(int isauth) {
        this.isauth = isauth;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    public int getQuote() {
        return quote;
    }

    public void setQuote(int quote) {
        this.quote = quote;
    }

    public int getQuoteunit() {
        return quoteunit;
    }

    public void setQuoteunit(int quoteunit) {
        this.quoteunit = quoteunit;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public int getTimetype() {
        return timetype;
    }

    public void setTimetype(int timetype) {
        this.timetype = timetype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
