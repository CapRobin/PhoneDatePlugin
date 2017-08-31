package com.vcredit.android.vcreditlib.launch;

import android.app.Application;

import com.vcredit.android.vcreditlib.enums.CallTimeType;
import com.vcredit.android.vcreditlib.enums.ITimeType;
import com.vcredit.android.vcreditlib.model.CallLogInfo;
import com.vcredit.android.vcreditlib.model.ContactInfo;
import com.vcredit.android.vcreditlib.model.MessageInfo;
import com.vcredit.android.vcreditlib.model.NativeAppInfo;
import com.vcredit.android.vcreditlib.template.ILogger;
import com.vcredit.android.vcreditlib.utils.Consts;

import java.util.List;

/**
 * VCreditManger
 *
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午5:42
 */
public class VCreditManger {

    private volatile static VCreditManger instance = null;
    private volatile static boolean hasInit = false;
    public static ILogger logger;

    /**
     * Get instance of VCreditManger. A
     * @return this is a instance
     */
    public static VCreditManger getInstance() {
        if (instance == null) {
            synchronized (VCreditManger.class) {
                if (instance == null) {
                    instance = new VCreditManger();
                }
            }
        }
        return instance;
    }

    /**
     * Init, it must be call before used VCredit.
     * @param application application
     */
    public static void init(Application application) {
        if (!hasInit) {
            logger = SupportVCreditManger.logger;
            SupportVCreditManger.logger.info(Consts.TAG, "VCerdit init start.");
            hasInit = SupportVCreditManger.init(application);
            SupportVCreditManger.logger.info(Consts.TAG, "VCerdit init over.");
        }
    }

    public static synchronized void openDebug() {
        SupportVCreditManger.openDebug();
    }

    public static boolean debuggable() {
        return SupportVCreditManger.debuggable();
    }

    public static synchronized void openLog() {
        SupportVCreditManger.openLog();
    }

    public static synchronized void printStackTrace() {
        SupportVCreditManger.printStackTrace();
    }

    /**
     *
     * @return get imei
     */
    public  synchronized String getIMEI() {
        return SupportVCreditManger.getInstance().getIMEI();
    }

    /**
     *
     * @return get imsi
     */
    public  String getIMSI(){
        return  SupportVCreditManger.getInstance().getIMSI() ;
    }

    /**
     *
     * @return Get the phone message
     */
    public List<MessageInfo> getMessage(){
        return  SupportVCreditManger.getInstance().getMessage();
    }

    /**
     *
     * @param iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.MessageType}
     * @return Get the phone message
     */
    public List<MessageInfo> getMessage(ITimeType iTimeType){
        return  SupportVCreditManger.getInstance().getMessage(iTimeType);
    }

    /**
     *
     * @return call records
     */
    public List<CallLogInfo> getCallRecords(){
        return SupportVCreditManger.getInstance().getCallRecords(CallTimeType.ALL);
    }


    /**
     *
     * @param iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.CallTimeType}
     * @return call records
     */
    public List<CallLogInfo> getCallRecords(ITimeType iTimeType){
        return SupportVCreditManger.getInstance().getCallRecords(iTimeType);
    }


    /**
     *
     * @return Call contact information
     */
    public List<ContactInfo> getContactInfo(){
        return SupportVCreditManger.getInstance().getContactInfo();
    }

    /**
     *
     * @return Call all application information
     */
    public List<NativeAppInfo> getAllAppsInfo(){
        return SupportVCreditManger.getInstance().getAllAppsInfo();
    }

    /**
     *
     * @param iTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.AppTimeType}
     * @return Call all application information
     */
    public List<NativeAppInfo> getAllAppsInfo(ITimeType iTimeType){
        return SupportVCreditManger.getInstance().getAllAppsInfo(iTimeType);
    }
}
