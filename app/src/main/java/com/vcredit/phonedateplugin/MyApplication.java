package com.vcredit.phonedateplugin;

import android.app.Application;

import com.vcredit.android.vcreditlib.launch.VCreditManger;

/**
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/24 下午1:38
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VCreditManger.getInstance().init(this);
    }
}
