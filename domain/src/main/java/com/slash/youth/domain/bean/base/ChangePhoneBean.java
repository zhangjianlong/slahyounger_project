package com.slash.youth.domain.bean.base;

/**
 * Created by acer on 2017/3/31.
 */

public class ChangePhoneBean {

    private int status;
    private long uid1;
    private String uid1phone;
    private long uid2;
    private String uid2phone;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getUid1() {
        return uid1;
    }

    public void setUid1(long uid1) {
        this.uid1 = uid1;
    }

    public String getUid1phone() {
        return uid1phone;
    }

    public void setUid1phone(String uid1phone) {
        this.uid1phone = uid1phone;
    }

    public long getUid2() {
        return uid2;
    }

    public void setUid2(long uid2) {
        this.uid2 = uid2;
    }

    public String getUid2phone() {
        return uid2phone;
    }

    public void setUid2phone(String uid2phone) {
        this.uid2phone = uid2phone;
    }
}
