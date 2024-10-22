package com.example.bill.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author ZZ
 */
public class ViewUtil {

    public static void hideOneInputMethod(Activity act, View v) {
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 关闭屏幕上的输入法软键盘
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

}
