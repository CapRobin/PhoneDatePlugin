package com.vcredit.phonedateplugin;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vcredit.android.vcreditlib.enums.AppTimeType;
import com.vcredit.android.vcreditlib.launch.VCreditManger;
import com.vcredit.android.vcreditlib.model.CallLogInfo;
import com.vcredit.android.vcreditlib.model.ContactInfo;
import com.vcredit.android.vcreditlib.model.MessageInfo;
import com.vcredit.android.vcreditlib.model.NativeAppInfo;

import java.util.List;

import static com.vcredit.phonedateplugin.PermissionManager.PERMISSION_REQUEST_FIVE;
import static com.vcredit.phonedateplugin.PermissionManager.PERMISSION_REQUEST_FOUR;
import static com.vcredit.phonedateplugin.PermissionManager.PERMISSION_REQUEST_ONE;
import static com.vcredit.phonedateplugin.PermissionManager.PERMISSION_REQUEST_THREE;
import static com.vcredit.phonedateplugin.PermissionManager.PERMISSION_REQUEST_TWO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btnMessage;
    private Button btnContact;
    private Button callLog;
    private Button btnCalllog;
    private Button btnApplist;
    private Button btnImei;
    private Button btnImsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMessage = (Button) findViewById(R.id.btn_message);
        btnContact = (Button) findViewById(R.id.btn_contact);
        btnCalllog = (Button) findViewById(R.id.btn_calllog);
        callLog = (Button) findViewById(R.id.btn_calllog);
        btnApplist = (Button) findViewById(R.id.btn_applist);
        btnImei = (Button) findViewById(R.id.btn_imei);
        btnImsi = (Button) findViewById(R.id.btn_imsi);
        btnMessage.setOnClickListener(this);
        btnContact.setOnClickListener(this);
        btnCalllog.setOnClickListener(this);
        btnApplist.setOnClickListener(this);
        btnImei.setOnClickListener(this);
        btnImsi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_message) {
            if (PermissionManager.lacksPermissions(this, Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS)) {
                PermissionManager.requestPermissions(this, PERMISSION_REQUEST_ONE, PermissionManager.getPermission()); // 请求权限
            } else {
                readMessage();
            }
        } else if (v.getId() == R.id.btn_contact) {
            if (PermissionManager.lacksPermissions(this, Manifest.permission.READ_CONTACTS)) {
                PermissionManager.requestPermissions(this, PERMISSION_REQUEST_FIVE, PermissionManager.getPermission()); // 请求权限
            }else {
                readContact();
            }
        } else if (v.getId() == R.id.btn_calllog) {
            if (PermissionManager.lacksPermissions(this, Manifest.permission.READ_CALL_LOG)) {
                PermissionManager.requestPermissions(this, PERMISSION_REQUEST_TWO, PermissionManager.getPermission()); // 请求权限
            } else {
                readCalllog();
            }
        } else if (v.getId() == R.id.btn_applist) {
            List<NativeAppInfo> allApps = VCreditManger.getInstance().getAllAppsInfo(AppTimeType.MONTH);
            Toast.makeText(this, "" + allApps.toString(), Toast.LENGTH_SHORT).show();
            Log.e(TAG, allApps.toString());
        } else if (v.getId() == R.id.btn_imei) {
            if (PermissionManager.lacksPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
                PermissionManager.requestPermissions(this, PERMISSION_REQUEST_THREE, PermissionManager.getPermission()); // 请求权限
            } else {
                readImei();
            }
        } else if (v.getId() == R.id.btn_imsi) {
            if (PermissionManager.lacksPermissions(this, Manifest.permission.READ_PHONE_STATE)) {
                PermissionManager.requestPermissions(this, PERMISSION_REQUEST_FOUR, PermissionManager.getPermission()); // 请求权限
            } else {
                readImsi();
            }
        }
    }

    /**
     * 读取联系人
     */
    private void readContact() {
        List<ContactInfo> contactInfoList = VCreditManger.getInstance().getContactInfo();
        Log.e(TAG, contactInfoList.toString());
        Toast.makeText(this, "" + contactInfoList.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取imei
     */
    private void readImei() {
        String imei = VCreditManger.getInstance().getIMEI();
        Toast.makeText(this, "" + imei, Toast.LENGTH_SHORT).show();
        Log.e(TAG, imei);
    }

    /**
     * 获取imsi
     */
    private void readImsi() {
        String imsi = VCreditManger.getInstance().getIMSI();
        Toast.makeText(this, "" + imsi, Toast.LENGTH_SHORT).show();
        Log.e(TAG, imsi);
    }

    /**
     * 获取通话记录的信息
     */
    private void readCalllog() {
        List<CallLogInfo> callLogInfoList = VCreditManger.getInstance().getCallRecords();
        Log.e(TAG, callLogInfoList.toString());
        Toast.makeText(this, "" + callLogInfoList.toString(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取手机的讯息
     */
    private void readMessage() {
        List<MessageInfo> message = VCreditManger.getInstance().getMessage();
        Log.e(TAG, message.toString());
        Toast.makeText(this, "" + message.toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ONE && PermissionManager.hasAllPermissionsGranted(grantResults)) {
            readMessage();
        } else {
            Toast.makeText(this, "请开启读取短信权限，否则不能选择联系人！", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PERMISSION_REQUEST_TWO && PermissionManager.hasAllPermissionsGranted(grantResults)) {
            readMessage();
        } else {
            Toast.makeText(this, "请开启读取通话记录权限，否则不能选择联系人！", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PERMISSION_REQUEST_THREE && PermissionManager.hasAllPermissionsGranted(grantResults)) {
            readImei();
        } else {
            Toast.makeText(this, "请开启读取电话权限，否则不能选择联系人！", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PERMISSION_REQUEST_FOUR && PermissionManager.hasAllPermissionsGranted(grantResults)) {
            readImsi();
        } else {
            Toast.makeText(this, "请开启读取电话权限，否则不能选择联系人！", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == PERMISSION_REQUEST_FIVE && PermissionManager.hasAllPermissionsGranted(grantResults)) {
            readContact();
        } else {
            Toast.makeText(this, "请开启读取联系人权限，否则不能选择联系人！", Toast.LENGTH_SHORT).show();
        }
    }
}
