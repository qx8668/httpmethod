package com.app.code.utils;

import android.util.Log;

/**
 * @author liuquanxing
 * email 330967811@qq.com
 */
public class L {

    final static boolean L = true;

    public static void e(String tag, String text) {
        if (L) {
            Log.e(tag, "" + text);
        }
    }
}
