package com.wsg.xsytrade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wsg.xsytrade.R;
import com.wsg.xsytrade.entity.Buy;

import java.util.List;

/**
 * 项目名：XSYTrade
 * 包名：com.wsg.xsytrade.adapter
 * 文件名：MyBuyAdapter
 * 创建者：wsg
 * 创建时间：2017/9/23  9:15
 * 描述：我的求售
 */

public class MyBuyAdapter extends BaseAdapter {


    private Context mContext;
    private List<Buy> mList;
    //布局加载器
    private LayoutInflater inflater;
    private Buy data;


    public MyBuyAdapter(Context mContext, List<Buy> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder1=null;
        //如果是第一次加载
        if(view==null){
            viewHolder1=new ViewHolder();
            view=inflater.inflate(R.layout.item_mybuy,null);
            viewHolder1.mybuy_tv_title=(TextView)view.findViewById(R.id.mybuy_item_title);
            viewHolder1.mybuy_iv_modify=(ImageView)view.findViewById(R.id.mybuy_item_modify);
            viewHolder1.mybuy_iv_delete=(ImageView) view.findViewById(R.id.mybuy_item_delete);
            //设置缓存
            view.setTag(viewHolder1);
        }
        else {
            viewHolder1 = (ViewHolder)view.getTag();
        }


        //设置数据
        data=mList.get(i);
        viewHolder1.mybuy_tv_title.setText(data.getTitle());

//        L.d(data.getTitle());
        return view;
    }





    class ViewHolder{
        private TextView mybuy_tv_title;
        private ImageView mybuy_iv_modify;
        private  ImageView mybuy_iv_delete;
    }
}
