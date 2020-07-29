package com.jiuwei.entity;

import java.util.List;

public class Accident {
    private int acId;
    private String acTime;
    private String userId;
    private String acPics;
    private String acWord;
    private String carId;
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAcWord() {
        return acWord;
    }

    public void setAcWord(String acWord) {
        this.acWord = acWord;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public int getAcId() {
        return acId;
    }

    public void setAcId(int acId) {
        this.acId = acId;
    }

    public String getAcTime() {
        return acTime;
    }

    public void setAcTime(String acTime) {
        this.acTime = acTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcPics() {
        return acPics;
    }

    public void setAcPics(String acPics) {
        this.acPics = acPics;
    }
}
