package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.util.UtilTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：AboutActivity
 * 创建者：wsg
 * 创建时间：2017/9/18  15:56
 * 描述：关于页面
 */

public class AboutActivity extends BaseActivity {

    private List<String> mList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    @BindView(R.id.mListView)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        //去除阴影
        getSupportActionBar().setElevation(0);

        initData();

    }

    private void initData() {

        mList.add(getString(R.string.text_app_name) + getString(R.string.app_name));
        mList.add(getString(R.string.text_version) + UtilTools.getVersion(this));
        mList.add(getString(R.string.text_author));
        mList.add(getString(R.string.text_call));
        mList.add(getString(R.string.text_github));
        mList.add(getString(R.string.text_csdn));


        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mList);
        //设置适配器
        mListView.setAdapter(mAdapter);

    }
}
