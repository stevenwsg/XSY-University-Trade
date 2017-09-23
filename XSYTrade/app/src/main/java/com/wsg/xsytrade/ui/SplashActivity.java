package com.wsg.xsytrade.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wsg.xsytrade.MainActivity;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.runtimepermissions.PermissionsManager;
import com.wsg.xsytrade.runtimepermissions.PermissionsResultAction;
import com.wsg.xsytrade.util.ShareUtils;
import com.wsg.xsytrade.util.StaticClass;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：SplashActivity
 * 创建者：wsg
 * 创建时间：2017/9/16  17:08
 * 描述：欢迎页面
 */

public class SplashActivity extends Activity {


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    }
            }
            finish();

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {


        /**
         * 请求所有必要的权限----原理就是获取清单文件中申请的权限
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//              Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });


        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);

    }


    //判断程序是否第一次运行
    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this,StaticClass.SHARE_IS_LOGIN,true);
        if(isFirst){
            return true;
        }else {
            return false;
        }

    }


}
