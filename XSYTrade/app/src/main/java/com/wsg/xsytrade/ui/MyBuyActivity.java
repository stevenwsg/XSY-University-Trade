package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.MyBuyAdapter;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.Buy;
import com.wsg.xsytrade.entity.MyUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：MyBuyActivity
 * 创建者：wsg
 * 创建时间：2017/9/22  20:32
 * 描述：我得求售
 */

public class MyBuyActivity extends BaseActivity {

    private MyBuyAdapter adapter;

    @BindView(R.id.mybuy_lv)
    ListView mybuyLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybuy);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();


        /*
        1、获取数据
        2、将数据添加到集合
        3、设置适配器
         */

        //1、获取数据
        //根据姓名获取内容
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        userInfo.getUsername();



        //1、获取表中存放的数据
        BmobQuery<Buy> query = new BmobQuery<Buy>();
        query.addWhereEqualTo("name",userInfo.getUsername());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按照时间降序
        query.order("-createdAt");



        query.findObjects(new FindListener<Buy>() {
            @Override
            public void done(List<Buy> list, BmobException e) {
                if (e == null) {
                    //2、已经获取到集合

                    //3、设置适配器
                    adapter = new MyBuyAdapter(MyBuyActivity.this, list);
                    mybuyLv.setAdapter(adapter);
                } else {
                    Toast.makeText(MyBuyActivity.this, "数据获取失败，请检查网络，亲~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
