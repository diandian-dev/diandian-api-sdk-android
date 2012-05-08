package com.diandian.api.sdk.android.util;

import android.util.Log;

import com.diandian.api.sdk.android.configuration.DDAPISettings;

/**
 * Log工具，配合DDAPISetting.DEBUG使用。
 * 
 * @author zhangdong zhangdong@diandian.com
 * 
 *         2012-5-2 下午3:53:51
 */
public class PrintLog {

    public static void i(String tag, String msg) {
        if (DDAPISettings.DEBUG) {
            Log.i(tag, "--------" + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DDAPISettings.DEBUG) {
            Log.v(tag, "--------" + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DDAPISettings.DEBUG) {
            Log.d(tag, "--------" + msg);
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        return Log.d(tag, "--------" + msg, tr);
    }

    public static void w(String tag, String msg) {
        if (DDAPISettings.DEBUG) {
            Log.w(tag, "--------" + msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        Log.w(tag, "--------" + msg, tr);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, "--------" + msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return Log.e(tag, "--------" + msg, tr);
    }

    //	private static final boolean SHOW_SEVERE_WARNING_DIALOG = false;    // Set to false before ship
    private static String prettyArray(String[] array) {
        if (array.length == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        int len = array.length - 1;
        for (int i = 0; i < len; i++) {
            sb.append(array[i]);
            sb.append(", ");
        }
        sb.append(array[len]);
        sb.append("]");
        return sb.toString();
    }

    private static String logFormat(String format, Object... args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String[]) {
                args[i] = prettyArray((String[]) args[i]);
            }
        }
        String s = String.format(format, args);
        s = "[" + Thread.currentThread().getId() + "] " + s;
        return s;
    }

    public static void debug(String tag, String format, Object... args) {
        Log.d(tag, logFormat(format, args));
    }

    public static void warn(String tag, String format, Object... args) {
        Log.w(tag, logFormat(format, args));
    }

    public static void error(String tag, String format, Object... args) {
        Log.e(tag, logFormat(format, args));
    }
    //    public static void warnPossibleRecipientMismatch(String tag, final String msg, final Activity activity) {
    //        Log.e(tag, "WARNING!!!! " + msg);
    //
    //        if (SHOW_SEVERE_WARNING_DIALOG) {
    //            activity.runOnUiThread(new Runnable() {
    //                public void run() {
    //                    new AlertDialog.Builder(activity)
    //                        .setIcon(android.R.drawable.ic_dialog_alert)
    //                        .setTitle(R.string.error_state)
    //                        .setMessage(msg + "\n\n" + activity.getString(R.string.error_state_text))
    //                        .setPositiveButton(R.string.btn_yes, new OnClickListener() {
    //                            public void onClick(DialogInterface dialog, int which) {
    //                                dialog.dismiss();
    //                            }
    //                        })
    //                        .show();
    //                }
    //            });
    //        }
    //    }
}
