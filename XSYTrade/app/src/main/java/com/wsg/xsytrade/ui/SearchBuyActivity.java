package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.BuyAdapter;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Buy;
import com.wsg.xsytrade.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：SearchBuyActivity
 * 创建者：wsg
 * 创建时间：2017/10/24  19:25
 * 描述：TODO
 */

public class SearchBuyActivity extends BaseActivity implements BuyAdapter.Callback {
    @BindView(R.id.search_buy_tv)
    TextView searchBuyTv;
    @BindView(R.id.search_buy_lv)
    ListView searchBuyLv;
    private String name;
    private List<Buy> mlist=new ArrayList<>();
    private BuyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbuy);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        Intent intent=getIntent();
        name=intent.getStringExtra("buy");

        BmobQuery<Buy> query = new BmobQuery<Buy>();
        query.addWhereEqualTo("title", name);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        query.findObjects(new FindListener<Buy>() {
            @Override
            public void done(List<Buy> list, BmobException e) {
                if(e==null){
                    mlist.addAll(list);
                    setdata();
                }
                else{

                }
            }
        });
    }

    private void setdata() {
        if(mlist.size()==0){
            searchBuyTv.setVisibility(View.VISIBLE);
        }
        else{

            adapter=new BuyAdapter(this,mlist,this);
            searchBuyLv.setAdapter(adapter);


        }
    }

    @Override
    public void click(View v) {
        int i=(Integer) v.getTag();


        String name =mlist.get(i).getMessageid();




        Intent chat = new Intent(this,ChatActivity.class);
        L.d(EaseConstant.EXTRA_USER_ID);
        L.d(name);
        chat.putExtra(EaseConstant.EXTRA_USER_ID,name);  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
        startActivity(chat);
    }
}
