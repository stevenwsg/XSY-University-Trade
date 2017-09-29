package com.wsg.xsytrade.fragment;

import com.hyphenate.easeui.ui.EaseConversationListFragment;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.fragment
 * 文件名：MyMessageFragment
 * 创建者：wsg
 * 创建时间：2017/9/29  17:47
 * 描述：TODO
 */

public class MyMessageFragment extends EaseConversationListFragment {
    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        initData();
    }



    private void initData() {
        // run in a second
        final long timeInterval = 10000;
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    // ------- code for task to run
                    conversationListView.refresh();
                    // ------- ends here
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
