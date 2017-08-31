package com.vcredit.android.vcreditlib.utils.phonedate;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import com.vcredit.android.vcreditlib.enums.ITimeType;
import com.vcredit.android.vcreditlib.model.CallLogInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Get call history
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/23 下午4:35
 */
public class CallLogUtils {
    //    private static final Uri uri = CallLog.Calls.CONTENT_URI;
    private static final Uri uri =  Uri.parse("content://call_log/calls");
    private static final String[] projection = {
            CallLog.Calls.DATE, // 日期
            CallLog.Calls.NUMBER, // 号码
            CallLog.Calls.TYPE, // 类型
            CallLog.Calls.CACHED_NAME, // 名字
            CallLog.Calls._ID, // id
            CallLog.Calls.DURATION//通话时长
    };

    /**
     * 获取本地通话记录信息
     * @param context context
     * @param callTimeType this is time type{@link com.vcredit.android.vcreditlib.enums.CallTimeType}
     * @return a calllog list
     */
    public static List<CallLogInfo> getCallLogInfos(Context context, ITimeType callTimeType) {
        List<CallLogInfo> callLogInfos = new ArrayList<>();
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取ContentResolver
        ContentResolver contentResolver = context.getContentResolver();
        long lastPostTime=callTimeType.getData();
        Cursor cursor = contentResolver.query(uri, projection, null, null,
                CallLog.Calls.DEFAULT_SORT_ORDER);
        if (cursor != null && cursor.getCount() > 0) {
            Date date;
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                if (lastPostTime > cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE))) {
                    continue;
                }
                date = new Date(cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DATE)));
                String number = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER));
                int type = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE));
                String cachedName = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));
                int id = cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls._ID));
                long duration = cursor.getLong(cursor
                        .getColumnIndex(CallLog.Calls.DURATION));
                CallLogInfo callLog = new CallLogInfo();
                callLog.setId(id);
                callLog.setNumber(number);
                callLog.setName(cachedName);
                callLog.setDuration(duration);
                if (null == cachedName || "".equals(cachedName)) {
                    callLog.setName(number);
                }
                callLog.setType(type);
                callLog.setDate(sfd.format(date));
                callLogInfos.add(callLog);
            }
        }

        if (null != cursor && !cursor.isClosed()) {
            cursor.close();
        }
        return callLogInfos;
    }
}
