package com.wsg.xsytrade.util;

import android.app.Activity;
import android.content.Context;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.util
 * 文件名：DemiUitls
 * 创建者：wsg
 * 创建时间：2017/10/23  15:49
 * 描述：单位转换
 */

public class DemiUitls {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Activity context) {
        return context.getWindowManager().getDefaultDisplay().getHeight();
    }
}
