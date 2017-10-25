package com.wsg.xsytrade.util;

import android.util.Log;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.util
 * 文件名：L
 * 创建者：wsg
 * 创建时间：2017/9/16  17:14
 * 描述：Log的封装
 */

public class L {
    //开关
    public static final  boolean DEBUG = false;
    //TAG
    public static final String TAG = "WSG";

    //五个等级  VDIWE

    public static void v(String text){
        if(DEBUG){
            Log.v(TAG,text);
        }
    }



    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }
}
