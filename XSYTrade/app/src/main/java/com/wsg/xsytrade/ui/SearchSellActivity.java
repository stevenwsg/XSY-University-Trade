package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.SellAdapter;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Sell;
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
 * 文件名：SearchSellActivity
 * 创建者：wsg
 * 创建时间：2017/10/24  18:56
 * 描述：TODO
 */

public class SearchSellActivity extends BaseActivity implements SellAdapter.Callback {
    @BindView(R.id.search_sell_tv)
    TextView searchSellTv;
    @BindView(R.id.search_sell_lv)
    ListView searchSellLv;
    private String name;
    private List<Sell> mlist=new ArrayList<>();
    private SellAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchsell);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        Intent intent=getIntent();
        name=intent.getStringExtra("sell");
        BmobQuery<Sell> query = new BmobQuery<Sell>();
        query.addWhereEqualTo("title", name);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        query.findObjects(new FindListener<Sell>() {
            @Override
            public void done(List<Sell> list, BmobException e) {
                if(e==null){
                    mlist.addAll(list);
                    setdata();
                }
                else{

                }
            }
        });
    }

    //设置数据
    private void setdata() {
        if(mlist.size()==0){
            searchSellTv.setVisibility(View.VISIBLE);
        }
        else{

            adapter=new SellAdapter(this,mlist,this);
            searchSellLv.setAdapter(adapter);


        }
    }

    @Override
    public void click(View v) {


        int i=(Integer) v.getTag();

        String name =mlist.get(i).getMessageid();


        L.d(EaseConstant.EXTRA_USER_ID);
        L.d(name);


        Intent chat = new Intent(this,ChatActivity.class);
        chat.putExtra(EaseConstant.EXTRA_USER_ID,name);  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE); //单聊模式


        startActivity(chat);


    }
}
