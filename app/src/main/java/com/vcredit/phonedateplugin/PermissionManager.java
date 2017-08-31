package com.vcredit.phonedateplugin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 权限管理类
 * @author: zew <a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @version:1.0
 * @since 17/8/24 下午3:12
 */
public class PermissionManager {


    private static ArrayList<String> mPermissionList;
    public static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
    public static final int PERMISSION_REQUEST_ONE = 1;
    public static final int PERMISSION_REQUEST_TWO = 2;
    public static final int PERMISSION_REQUEST_THREE = 3;
    public static final int PERMISSION_REQUEST_FOUR = 4;
    public static final int PERMISSION_REQUEST_FIVE = 5;
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案

    /**
     * 判定输入的敏感权限的权限是否有放开
     *
     * @param mContext    当前的上下文权限
     * @param permissions 权限列表
     * @return
     */
    public static boolean lacksPermissions(Context mContext, String... permissions) {
        boolean flag = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionList = new ArrayList<String>();
            for (String permission : permissions) {

                if (!TextUtils.isEmpty(permission) && lacksPermission(mContext, permission)) {
                    mPermissionList.add(permission);
                    flag = true;
                }
            }
        }
        return flag;

    }

    // 判断是否缺少权限
    private static boolean lacksPermission(Context mContext, String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

    /**
     * 返回当前所需要的所有的敏感权限
     *
     * @return
     */
    public static String[] getPermission() {
        String[] permissionArray = new String[mPermissionList.size()];

        mPermissionList.toArray(permissionArray);
        return permissionArray;
    }

    /**
     * 请求权限兼容低版本
     *
     * @param permissions
     */
    public static void requestPermissions(Activity activity, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSION_REQUEST_CODE);
    }

    /**
     * 请求权限兼容低版本
     *
     * @param permissions
     */
    public static void requestPermissions(Activity activity, int requestcode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestcode);
    }

    /**
     * 含有全部的权限
     *
     * @param grantResults
     * @return
     */
    public static boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    // 显示缺失权限提示
    public static void showMissingPermissionDialog(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                SupportAppManager.get().loginOutFinishActivity2();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings(mContext);
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private static void startAppSettings(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mContext.getPackageName()));
        mContext.startActivity(intent);
//        mContext.startActivity(new Intent(Settings.ACTION_SETTINGS));
    }


    // 显示缺失权限提示
    public static void showMissingPermissionExitDialog(final Context mContext) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击两次后退按钮，即可返回。");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                SupportAppManager.get().loginOutFinishActivity2();
            }
        });

//        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startAppSettings(mContext);
//            }
//        });

        builder.setCancelable(false);

        builder.show();
    }

}
