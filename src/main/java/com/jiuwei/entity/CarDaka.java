package com.jiuwei.entity;

import java.util.Date;

public class CarDaka {
    private String dakaId;
    private String userId;
    private String carId;
    private String borrowTime;
    private String borrowPic;
    private String returnTime;
    private String returnPic;
    private String leaveWord;
    private String useState;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDakaId() {
        return dakaId;
    }

    public void setDakaId(String dakaId) {
        this.dakaId = dakaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getBorrowPic() {
        return borrowPic;
    }

    public void setBorrowPic(String borrowPic) {
        this.borrowPic = borrowPic;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnPic() {
        return returnPic;
    }

    public void setReturnPic(String returnPic) {
        this.returnPic = returnPic;
    }

    public String getLeaveWord() {
        return leaveWord;
    }

    public void setLeaveWord(String leaveWord) {
        this.leaveWord = leaveWord;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }
}
