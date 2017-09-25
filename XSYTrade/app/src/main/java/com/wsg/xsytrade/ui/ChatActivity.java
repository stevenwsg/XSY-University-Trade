package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.wsg.xsytrade.R;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：ChatActivity
 * 创建者：wsg
 * 创建时间：2017/9/25  15:55
 * 描述：聊天界面的实现
 */

public class ChatActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);


        EaseChatFragment easeChatFragment = new EaseChatFragment();  //环信聊天界面
        easeChatFragment.setArguments(getIntent().getExtras()); //需要的参数
        getSupportFragmentManager().beginTransaction().add(R.id.layout_chat,easeChatFragment).commit();  //Fragment切换
    }
}
