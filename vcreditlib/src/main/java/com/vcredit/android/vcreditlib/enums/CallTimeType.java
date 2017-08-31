package com.vcredit.android.vcreditlib.enums;

/**
 * Get the time type of the call
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午3:33
 */
public enum CallTimeType implements ITimeType{
    ALL(0),
    DAY(System.currentTimeMillis()-24 * 60 * 60 * 1000l),
    WEEK(System.currentTimeMillis()-7*24 * 60 * 60 * 1000l),
    MONTH(System.currentTimeMillis()-30 * 24 * 60 * 60 * 1000l),
    YEAR(System.currentTimeMillis()-12*30 * 24 * 60 * 60 * 1000l);
    long data;

    CallTimeType(long data) {
        this.data = data;
    }

    @Override
    public long getData() {
        return data;
    }

    public void setData(long data) {
        this.data = data;
    }
}
