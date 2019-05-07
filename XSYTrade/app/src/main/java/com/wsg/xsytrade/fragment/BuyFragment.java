package com.wsg.xsytrade.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.wsg.xsytrade.R;
import com.wsg.xsytrade.adapter.BuyAdapter;
import com.wsg.xsytrade.entity.Buy;
import com.wsg.xsytrade.ui.ChatActivity;
import com.wsg.xsytrade.ui.NewBuyActivity;
import com.wsg.xsytrade.ui.SearchBuyActivity;
import com.wsg.xsytrade.util.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.fragment
 * 文件名：BuyFragment
 * 创建者：wsg
 * 创建时间：2017/9/16  21:37
 * 描述：闲置求售
 */

public class BuyFragment extends Fragment implements BuyAdapter.Callback {


    @BindView(R.id.buy_swipe_refresh)
    SwipeRefreshLayout buySwipeRefresh;

    private List<Buy> mList = new ArrayList<>();
    private BuyAdapter adapter;
    private String s;

    @BindView(R.id.buy_ed)
    EditText buyEd;
    @BindView(R.id.buy_search)
    ImageView buySearch;
    @BindView(R.id.buy_write)
    ImageView buyWrite;
    @BindView(R.id.buy_lv)
    ListView buyLv;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buy, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {


        //2、已经获取到集合

        //3、设置适配器
        adapter = new BuyAdapter(getActivity(), mList,this);
        buyLv.setAdapter(adapter);

        buySwipeRefresh.setColorSchemeResources(R.color.colorPrimary);   //设置下拉刷新进度条的颜色
        buySwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onResume();   //进行刷新操作
                buySwipeRefresh.setRefreshing(false);
            }



        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.buy_search, R.id.buy_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.buy_search:
                ////
                s=buyEd.getText().toString().trim();



                if (!TextUtils.isEmpty(s)){
                    Intent intent=new Intent(getActivity(), SearchBuyActivity.class);
                    intent.putExtra("buy",s);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(),"亲，输入框不能为空~~~~",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.buy_write:
                //添加的逻辑
                startActivity(new Intent(getActivity(), NewBuyActivity.class));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
         /*
        1、获取数据
        2、将数据添加到集合
        3、设置适配器
         */

        //1、获取表中存放的数据
        BmobQuery<Buy> query = new BmobQuery<Buy>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //按照时间降序
        query.order("-createdAt");
        //执行查询，第一个参数为上下文，第二个参数为查找的回调
        query.findObjects(new FindListener<Buy>() {
            @Override
            public void done(List<Buy> list, BmobException e) {
                if (e == null) {
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                } else {
                    L.d(e.toString() + e.getErrorCode() + e.getMessage());
                    Toast.makeText(getActivity(), "数据获取失败，请检查网络，亲~~~", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void click(View v) {
        int i=(Integer) v.getTag();


        String name =mList.get(i).getMessageid();




        Intent chat = new Intent(getActivity(),ChatActivity.class);
        L.d(EaseConstant.EXTRA_USER_ID);
        L.d(name);
        chat.putExtra(EaseConstant.EXTRA_USER_ID,name);  //对方账号
        chat.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat); //单聊模式
        startActivity(chat);

    }
}
