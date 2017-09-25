package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Sell;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：ModifyMySellActivity
 * 创建者：wsg
 * 创建时间：2017/9/25  19:15
 * 描述：修改我的求购
 */

public class ModifyMySellActivity extends BaseActivity {
    @BindView(R.id.modifysell_title)
    EditText modifysellTitle;
    @BindView(R.id.modifysell_content)
    EditText modifysellContent;
    @BindView(R.id.modifysell_bt)
    Button modifysellBt;

    private  String   mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifymysell);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Intent i=getIntent();
        final String s=i.getStringExtra("id");
        mid=s;

        BmobQuery<Sell> query = new BmobQuery<Sell>();
        query.getObject(s, new QueryListener<Sell>() {
            @Override
            public void done(Sell sell, BmobException e) {
                String t=sell.getTitle();
                String c=sell.getContent();



                modifysellTitle.setText(t);
                modifysellContent.setText(c);
            }
        });
    }

    @OnClick(R.id.modifysell_bt)
    public void onViewClicked() {

        Sell se=new Sell();
        se.setTitle(modifysellTitle.getText().toString().trim());
        se.setContent(modifysellContent.getText().toString().trim());
        se.update(mid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Toast.makeText(ModifyMySellActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
