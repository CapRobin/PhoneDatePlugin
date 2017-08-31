package com.vcredit.android.vcreditlib.launch;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.vcredit.android.vcreditlib.enums.AppTimeType;
import com.vcredit.android.vcreditlib.enums.CallTimeType;
import com.vcredit.android.vcreditlib.enums.ITimeType;
import com.vcredit.android.vcreditlib.enums.SmsTimeType;
import com.vcredit.android.vcreditlib.exception.InitException;
import com.vcredit.android.vcreditlib.model.CallLogInfo;
import com.vcredit.android.vcreditlib.model.ContactInfo;
import com.vcredit.android.vcreditlib.model.MessageInfo;
import com.vcredit.android.vcreditlib.model.NativeAppInfo;
import com.vcredit.android.vcreditlib.template.ILogger;
import com.vcredit.android.vcreditlib.utils.phonedate.CallLogUtils;
import com.vcredit.android.vcreditlib.utils.Consts;
import com.vcredit.android.vcreditlib.utils.phonedate.ContactUtils;
import com.vcredit.android.vcreditlib.utils.DefaultLogger;
import com.vcredit.android.vcreditlib.utils.phonedate.MessageUtils;
import com.vcredit.android.vcreditlib.utils.phonedate.NativeAppUtils;

import java.util.List;

/**
 * VCreditManger core
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/24 上午11:25
 */
public class SupportVCreditManger {
    static ILogger logger = new DefaultLogger(Consts.TAG); // 日志工具
    private volatile static boolean monitorMode = false;
    private volatile static boolean debuggable = false;
    private volatile static SupportVCreditManger instance = null;
    private volatile static boolean hasInit = false;
    private static Context mContext;


    private SupportVCreditManger() {

    }

    protected static synchronized boolean init(Application application) {
        mContext = application;
        logger.info(Consts.TAG, "VCredit init success!");
        hasInit = true;
        return true;
    }
    protected static SupportVCreditManger getInstance() {
        if (!hasInit) {
            throw new InitException("VCredit::Init::Invoke init(context) first!");
        } else {
            if (instance == null) {
                synchronized (SupportVCreditManger.class) {
                    if (instance == null) {
                        instance = new SupportVCreditManger();
                    }
                }
            }
            return instance;
        }
    }

    static boolean debuggable() {
        return debuggable;
    }
    static synchronized void openDebug() {
        debuggable = true;
        logger.info(Consts.TAG, "VCredit openDebug");
    }

    static synchronized void openLog() {
        logger.showLog(true);
        logger.info(Consts.TAG, "VCredit openLog");
    }

    static synchronized void printStackTrace() {
        logger.showStackTrace(true);
        logger.info(Consts.TAG, "VCredit printStackTrace");
    }


    /**************************业务方法**************************************/
    /**
     *
     * @return get imei
     */
    public  synchronized String getIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        logger.info(Consts.TAG,imei);
        return imei;
    }

    /**
     * @return get imsi
     */
    public  String getIMSI(){
        TelephonyManager mTelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        logger.info(Consts.TAG,imsi);
        return imsi ;
    }

    /**
     * @return Get the phone message
     */
    public List<MessageInfo> getMessage(){
        List<MessageInfo> smsInPhone = MessageUtils.getSmsInPhone(mContext, SmsTimeType.ALL);
        logger.info(Consts.TAG,smsInPhone.toString());
        return smsInPhone;
    }

    /**
     *
     * @param  iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.MessageType}
     * @return Get the phone message
     */
    public List<MessageInfo> getMessage(ITimeType iTimeType){
        List<MessageInfo> smsInPhone = MessageUtils.getSmsInPhone(mContext, iTimeType);
        logger.info(Consts.TAG,smsInPhone.toString());
        return smsInPhone;
    }

    /**
     * @return call records
     */
    public List<CallLogInfo> getCallRecords(){
        return getCallRecords(CallTimeType.ALL);
    }


    /**
     *
     * @param iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.CallTimeType}
     * @return call records
     */
    public List<CallLogInfo> getCallRecords(ITimeType iTimeType){
        CallLogUtils callLogService = new CallLogUtils();
        List<CallLogInfo> callLogInfos = callLogService.getCallLogInfos(mContext, iTimeType);
        logger.info(Consts.TAG,callLogInfos.toString());
        return callLogInfos;
    }


    /**
     * @return Call contact information
     */
    public List<ContactInfo> getContactInfo(){
        List<ContactInfo> allContact = ContactUtils.getAllContact(mContext);
        logger.info(Consts.TAG,allContact.toString());
        return allContact;
    }

    /**
     *
     * @return Call all application information
     */
    public List<NativeAppInfo> getAllAppsInfo(){
        List<NativeAppInfo> allApps = NativeAppUtils.getAllApps(mContext, AppTimeType.MONTH);
        logger.info(Consts.TAG,allApps.toString());
        return allApps;
    }

    /**
     *
     * @param iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.AppTimeType}
     * @return Call all application information
     */
    public List<NativeAppInfo> getAllAppsInfo(ITimeType iTimeType){
        List<NativeAppInfo> allApps = NativeAppUtils.getAllApps(mContext, iTimeType);
        logger.info(Consts.TAG,allApps.toString());
        return allApps;
    }
}
