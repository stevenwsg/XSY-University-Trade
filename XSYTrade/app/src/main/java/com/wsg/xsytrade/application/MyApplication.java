package com.wsg.xsytrade.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.wsg.xsytrade.util.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.application
 * 文件名：MyApplication
 * 创建者：wsg
 * 创建时间：2017/9/16  17:36
 * 描述：替代系统的Applica，完成自己的逻辑
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, false);
        //第一：默认初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
