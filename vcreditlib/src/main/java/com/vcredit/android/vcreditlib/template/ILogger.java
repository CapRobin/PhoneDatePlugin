package com.vcredit.android.vcreditlib.template;
import com.vcredit.android.vcreditlib.utils.Consts;

/**
 * logger工具
 * @author: zew$<a href="mailto:zhangerwei01@vcredit.com">Contact me.</a>
 * @since 17/8/24 上午9:40
 */
public interface ILogger {
    boolean isShowLog = false;
    boolean isShowStackTrace = false;
    String defaultTag = Consts.TAG;

    void showLog(boolean isShowLog);

    void showStackTrace(boolean isShowStackTrace);

    void debug(String tag, String message);

    void info(String tag, String message);

    void warning(String tag, String message);

    void error(String tag, String message);

    void monitor(String message);

    boolean isMonitorMode();

    String getDefaultTag();
}
