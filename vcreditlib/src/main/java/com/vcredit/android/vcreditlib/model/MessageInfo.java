package com.vcredit.android.vcreditlib.model;

import java.io.Serializable;

/**
 * @author: zew <a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @version:1.0
 * @since 17/8/23 下午3:06
 */
public class MessageInfo implements Serializable{
    private String name;
    private String phoneNumber;
    private String smsBody;
    private String date;
    private String type;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsBody() {
        return smsBody;
    }

    public void setSmsBody(String smsBody) {
        this.smsBody = smsBody;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsBody='" + smsBody + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

