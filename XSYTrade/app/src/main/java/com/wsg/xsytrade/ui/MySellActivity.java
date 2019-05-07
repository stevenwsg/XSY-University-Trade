package com.wsg.xsytrade.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.MySellAdapter;
import com.wsg.xsytrade.base.BaseActivity;
import com.wsg.xsytrade.entity.MyUser;
import com.wsg.xsytrade.entity.Sell;
import com.wsg.xsytrade.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import static com.kymjs.rxvolley.toolbox.RxVolleyContext.toast;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.ui
 * 文件名：MySellActivity
 * 创建者：wsg
 * 创建时间：2017/9/22  20:31
 * 描述：我的求购
 */

public class MySellActivity extends BaseActivity implements MySellAdapter.Callback {
    private List<Sell> mList = new ArrayList<>();
    private MySellAdapter adapter;

    @BindView(R.id.mysell_lv)
    ListView mysellLv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysell);

        ButterKnife.bind(this);
        adapter = new MySellAdapter(MySellActivity.this, mList,this);
        mysellLv.setAdapter(adapter);

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


                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();;
                } else {
                    L.d(e.toString() + e.getErrorCode() + e.getMessage());
                    Toast.makeText(MySellActivity.this, "数据获取失败，请检查网络，亲~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    public void click(View v) {

        int i=(Integer) v.getTag();
        switch(v.getId()){
            case R.id.mysell_item_modify:
//                Toast.makeText(this,"测试——修改",Toast.LENGTH_SHORT).show();


//                修改数据的逻辑
                Intent intent=new Intent(this,ModifyMySellActivity.class);
                intent.putExtra("id",mList.get(i).getObjectId());
                startActivity(intent);


                break;
            case R.id.mysell_item_delete:
//                Toast.makeText(this,"测试——删除",Toast.LENGTH_SHORT).show();

                Sell  s=new Sell();
                s.setObjectId(mList.get(i).getObjectId());
                s.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {

                        if(e==null){
                            toast("删除成功:");
                            mList.clear();
                            onResume();
                        }else{
                            toast("删除失败：" + e.getMessage());
                        }
                    }
                });


                break;
        }
    }


}
