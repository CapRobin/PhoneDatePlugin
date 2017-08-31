package com.vcredit.android.vcreditlib.enums;

/**
 * whether or not to receive send ,
 * The type of message being sent
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午3:21
 */
public enum MessageType {

    RECEIVE(1,"1"),//receive sms
    SEND(2,"2"); //send sms
    int id;
    String type;

    MessageType(int id,String type) {
        this.id=id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
