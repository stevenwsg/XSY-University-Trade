package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Buy;

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
 * 文件名：ModifyMyBuyActivity
 * 创建者：wsg
 * 创建时间：2017/9/26  15:25
 * 描述：T
 */

public class ModifyMyBuyActivity extends BaseActivity {
    @BindView(R.id.modifybuy_title)
    EditText modifybuyTitle;
    @BindView(R.id.modifybuy_content)
    EditText modifybuyContent;
    @BindView(R.id.modifybuy_bt)
    Button modifybuyBt;

    private  String   mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifymybuy);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        Intent i=getIntent();
        final String s=i.getStringExtra("id");
        mid=s;

        BmobQuery<Buy> query=new BmobQuery<Buy>();
        query.getObject(s, new QueryListener<Buy>() {
            @Override
            public void done(Buy buy, BmobException e) {
                if(e==null){
                    String t=buy.getTitle();
                    String c=buy.getContent();



                    modifybuyTitle.setText(t);
                    modifybuyContent.setText(c);
                }

            }
        });
    }

    @OnClick(R.id.modifybuy_bt)
    public void onViewClicked() {


        Buy buy=new Buy();
        buy.setTitle(modifybuyTitle.getText().toString().trim());
        buy.setContent(modifybuyContent.getText().toString().trim());


        buy.update(mid, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(ModifyMyBuyActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
}
