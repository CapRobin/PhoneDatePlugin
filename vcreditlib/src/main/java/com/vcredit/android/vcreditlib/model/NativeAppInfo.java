package com.vcredit.android.vcreditlib.model;

import java.io.Serializable;

/**
 * Native installed App entity
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午4:56
 */
public class NativeAppInfo implements Serializable{

    private String appName; //application name

    private String packageName; //application package name

    private boolean isSystemApp; //if sysytem app one if not zero

    private String firstInstallTime; //initial installation time

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isSystemApp() {
        return isSystemApp;
    }

    public void setSystemApp(boolean systemApp) {
        isSystemApp = systemApp;
    }

    public String getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(String firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    @Override
    public String toString() {
        return "NativeAppInfo{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", isSystemApp=" + isSystemApp +
                ", firstInstallTime='" + firstInstallTime + '\'' +
                '}';
    }
}
