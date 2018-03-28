package deu.hlju.dawn.studentattendance.util;

import android.util.Log;

import deu.hlju.dawn.studentattendance.config.Constants;

public class LogUtil {
    public static void i(String tag, String msg) {
        if (Constants.IS_TEST) {
            Log.i(tag, msg);
        }
    }
}
