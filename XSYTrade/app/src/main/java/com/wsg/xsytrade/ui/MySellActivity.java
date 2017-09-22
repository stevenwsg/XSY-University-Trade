package com.wsg.xsytrade.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.MySellAdapter;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.entity.Sell;

import java.util.ArrayList;
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
 * 文件名：MySellActivity
 * 创建者：wsg
 * 创建时间：2017/9/22  20:31
 * 描述：我的求购
 */

public class MySellActivity extends BaseActivity {
    private List<Sell> mList = new ArrayList<>();
    private MySellAdapter adapter;

    @BindView(R.id.mysell_lv)
    ListView mysellLv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysell);
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
        BmobQuery<Sell> query = new BmobQuery<Sell>();
        query.addWhereEqualTo("name",userInfo.getUsername());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按照时间降序
        query.order("-createdAt");



        query.findObjects(new FindListener<Sell>() {
            @Override
            public void done(List<Sell> list, BmobException e) {
                if (e == null) {
                    //2、已经获取到集合

                    //3、设置适配器
                    adapter = new MySellAdapter(MySellActivity.this, list);
                    mysellLv.setAdapter(adapter);
                } else {
                    Toast.makeText(MySellActivity.this, "数据获取失败，请检查网络，亲~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
