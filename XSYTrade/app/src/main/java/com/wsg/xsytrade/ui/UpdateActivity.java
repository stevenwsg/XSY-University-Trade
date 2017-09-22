package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.util.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：UpdateActivity
 * 创建者：wsg
 * 创建时间：2017/9/18  19:48
 * 描述：检查更新
 */

public class UpdateActivity extends BaseActivity {

    @BindView(R.id.update_tv)
    TextView updateTv;
    @BindView(R.id.update_bt)
    Button updateBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        updateTv.setText("当前版本："+ UtilTools.getVersion(this));
    }

    @OnClick(R.id.update_bt)
    public void onViewClicked() {
        BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);
    }
}
