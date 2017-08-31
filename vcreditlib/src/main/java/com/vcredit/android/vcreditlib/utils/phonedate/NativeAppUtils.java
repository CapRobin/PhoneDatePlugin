package com.vcredit.android.vcreditlib.utils.phonedate;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.vcredit.android.vcreditlib.enums.AppTimeType;
import com.vcredit.android.vcreditlib.enums.ITimeType;
import com.vcredit.android.vcreditlib.model.NativeAppInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * apps installed by this machine
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午4:55
 */
public class NativeAppUtils {

    /**
     * Returns a list of all application information in the system
     * @param context this a context
     * @param appTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.AppTimeType}
     * @return a list if appinfo
     */
    public static List<NativeAppInfo> getAllApps(Context context, ITimeType appTimeType){
        PackageManager packageManager = context.getPackageManager();
        List<NativeAppInfo> list = new ArrayList<>();
        NativeAppInfo myAppInfo;
        long lastPostTime=appTimeType.getData();
        //Gets information about all installed applications,
        // including those that are uninstalled but do not have data removed
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo info : packageInfos) {
            myAppInfo = new NativeAppInfo();
            String packageName = info.packageName;
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Initial installation time
            myAppInfo.setFirstInstallTime(sfd.format(new Date(info.firstInstallTime)));
            ApplicationInfo appInfo = info.applicationInfo;
            //Program name
            String appName = appInfo.loadLabel(packageManager).toString();
            myAppInfo.setPackageName(packageName);
            myAppInfo.setAppName(appName);
            if (filterApp(appInfo)) {
                myAppInfo.setSystemApp(false);
            } else {
                myAppInfo.setSystemApp(true);
            }
            if (lastPostTime == AppTimeType.ALL.getData()) {
                list.add(myAppInfo);
            } else if (info.firstInstallTime > lastPostTime) {
                list.add(myAppInfo);
            }
        }
        return list;
    }

    /**
     * whether is  a system application
     * if yes return true otherwise false
     * @param info app info
     * @return whether is system application
     */
    public static boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
