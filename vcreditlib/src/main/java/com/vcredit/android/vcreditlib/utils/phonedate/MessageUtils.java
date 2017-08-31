package com.vcredit.android.vcreditlib.utils.phonedate;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;

import com.vcredit.android.vcreditlib.enums.ITimeType;
import com.vcredit.android.vcreditlib.enums.MessageType;
import com.vcredit.android.vcreditlib.model.MessageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Get a list of SMS messages
 *
 * @author: zew <a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @version:1.0
 * @since 17/8/23 下午4:19
 */
public class MessageUtils {
    private static final String SMS_URI_ALL = "content://sms/";
    private static final String SMS_URI_INBOX = "content://sms/inbox";
    private static final String SMS_URI_SEND = "content://sms/sent";
    private static final String SMS_URI_DRAFT = "content://sms/draft";

    /**
     * @param context this is context
     * @param smsTimeType The last time to submit to the database
     * @return a list if message
     */
    public static List<MessageInfo> getSmsInPhone(Context context, ITimeType smsTimeType) {
        //TODO:是否加权限判断
        List<MessageInfo> messageList = new ArrayList<>();
        StringBuilder smsBuilder = new StringBuilder();
        Cursor cursor = null;
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type"};
            long lastPostTime = smsTimeType.getData();
            String where = " date >  "
                    + lastPostTime;
            Uri uri = Uri.parse(SMS_URI_ALL);
            cursor = cr.query(uri, projection, where, null, "date desc");

            if (cursor != null && cursor.moveToFirst()) {
                String name;
                String phoneNumber;
                String smsbody;
                String date;
                String type;

                int phoneNumberColumn = cursor.getColumnIndex("address");
                int smsbodyColumn = cursor.getColumnIndex("body");
                int dateColumn = cursor.getColumnIndex("date");
                int typeColumn = cursor.getColumnIndex("type");

                do {
                    phoneNumber = cursor.getString(phoneNumberColumn);
                    smsbody = cursor.getString(smsbodyColumn);

                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
                    date = dateFormat.format(d);

                    int typeId = cursor.getInt(typeColumn);
                    if (typeId == MessageType.RECEIVE.getId()) {
                        type = MessageType.RECEIVE.getType();
                    } else {
                        type = MessageType.SEND.getType();
                    }

                    name = getPeopleNameFromPerson(context, phoneNumber);

                    MessageInfo message = new MessageInfo();
                    message.setName(name);
                    message.setType(type);
                    message.setPhoneNumber(phoneNumber);
                    message.setSmsBody(smsbody);
                    message.setDate(date);
                    messageList.add(message);
                    if (smsbody == null) smsbody = "";
                } while (cursor.moveToNext());
            } else {

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return messageList;
    }


    // 通过address手机号关联Contacts联系人的显示名字
    private static String getPeopleNameFromPerson(Context context, String address) {
        if (address == null || address == "") {
            return "( no address )\n";
        }

        String strPerson = "null";
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};

        Uri uri_Person = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, address);  // address 手机号过滤
        Cursor cursor = context.getContentResolver().query(uri_Person, projection, null, null, null);

        if (cursor.moveToFirst()) {
            int index_PeopleName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String strPeopleName = cursor.getString(index_PeopleName);
            strPerson = strPeopleName;
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return strPerson;
    }
}
