package com.wsg.xsytrade.application;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
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
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //第一：默认初始化
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        //自动更新，只能使用一次
        //BmobUpdateAgent.initAppVersion();

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //集成环信easeUI
        EaseUI.getInstance().init(this, null);  //初始化EaseUI
        EMClient.getInstance().setDebugMode(true);  //设置debug模式
    }
}
